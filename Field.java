// for resizing arrays
import java.util.Arrays;
// for random number generation
import java.lang.Math;

public class Field {
    // I have opted to use a single dimensional array
    // two dimensional arrays are stored as such in memory
    // we can just use math operations to use it as a two dimensional array
    private Cell[] cells;
    private Cell lastOpened;
    // time field was last started
    private long startTime = 0;
    // counts
    private int cellsFlagged, cellsOpen;
    private boolean isStarted = false;
    public final int width, height, mines;

    public Field(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
        this.cells = new Cell[this.width * this.height];
        this.reset();
    }

    public int getIndex(int x, int y) {
        // convert coordinates to position on the array
        int coordinate = x + this.width * y;
        if (coordinate > cells.length && coordinate < 0) {
            // this should never execute
            System.out.println("Field panicking, invalid coordinate");
            System.exit(-1);
        }
        return coordinate;
    }

    // get cell by coordinate
    public Cell getCell(int x, int y) {
        int coordinate = this.getIndex(x, y);
        return this.cells[coordinate];
    }

    // get the last cell opened
    public Cell getLastOpened() {
        return this.lastOpened;
    }

    // get the amount of seconds since started
    public int getSeconds() {
        // if the game hasn't started rust return 0
        if (!this.isStarted()) {
            return 0;
        }
        return (int)((System.currentTimeMillis() - this.startTime) / 1000F); 
    }

    public Cell[] getAround(Cell parent) {
        // maximum that can be around a call is 8
        Cell[] around = new Cell[8];
        int index = 0;
        // check the index of parent and what is left and right of it

        // cell.x + -1 
        for (int x_offset = -1; x_offset <= 1; x_offset++) {
            for (int y_offset = -1; y_offset <= 1; y_offset++) {
                // this is pretty annoying to explain if it's on multiple lines
                // so I've divided it into 3 different if statements
                // this skips the "parent" cell
                if (x_offset == 0 && y_offset == 0) {
                    continue;
                }
                // prevents going to the next row/column
                if ((parent.x + x_offset) == this.width || (parent.y + y_offset) == this.height) {
                    continue;
                }
                // prevents going to the previous row/column
                if (parent.x + x_offset < 0 || parent.y + y_offset < 0) {
                    continue;
                }
                Cell child = this.getCell(parent.x + x_offset, parent.y + y_offset);
                // add child to the array
                around[index++] = child;
            }
        }
        // shrink array to fit
        return Arrays.copyOf(around, index);
    }
    
    // reset the field
    public void reset() {
        this.isStarted = false;
        this.cellsFlagged = 0;
        this.cellsOpen = 0;

        // it's a whole lot easier to iterate a single dimensional array
        for (int i = 0; i < cells.length; i++) {
            // generate coordinates so we can initialise cells
            int x = i % this.width;
            int y = i / this.width;
            cells[i] = new Cell(x, y);
        }
    }
    
    // flag a cell
    public void flagCell(Cell cell) {
        // stop interaction if won or failed
        if (this.isFailed() || this.isWon() || cell.isOpen()) {
            return;
        }
        // cannot flag cell, out of flags
        if (!cell.isFlagged() && this.flagCount() == 0) {
            System.out.println("Out of flags");
            return;
        }
        // flagged count increment if flagged, de-increment if not
        cell.flag();
        this.cellsFlagged++;
    }

    public void openCell(Cell cell) {
        if (this.isFailed() || this.isWon() || cell.isOpen()) {
            // stop interaction
            return;
        } else if (!this.isStarted()) {
            this.start(cell.x, cell.y);
            return;
        }
        cell.open(this);
        this.cellsOpen++;
        this.lastOpened = cell;
    }

    // minesweeper doesn't randomise until after player starts
    // so we need this method to start the game
    public void start(int x, int y) {
        // never gonna happen, doing it anyway
        if (this.isStarted()) {
            this.reset();
        }
        int start = this.getIndex(x, y);
        // generate random coordinates
        for (int i = 0; i < this.mines; i++) {
            int random = (int)(Math.random() * cells.length);
            Cell cell = this.cells[random];
            if (cell.isMine() || random == start) {
                // retry if already mine or is our starting position
                i--;
            } else {
                cell.setMine();
            }
        }
        // very important this comes first or we'll trigger an infinite loop
        this.isStarted = true;
        this.startTime = System.currentTimeMillis();
        this.openCell(this.cells[start]);
    }

    // return the amount of flags remaining
    public int flagCount() {
        return this.mines - this.cellsFlagged;
    }

    public int cellCount() {
        return this.width * this.height;
    }

    public boolean isStarted() {
        return this.isStarted;
    }

    public boolean isWon() {
        // win condition for minesweeper is
        // (numUnopenedCells == numMines) || allMinesFlagged
        if ((this.cellCount() - this.cellsOpen) == this.mines) {
            return true;
        }
        int count = 0;
        for (Cell cell : this.cells) {
            if (cell.isFlagged() && cell.isMine()) {
                count++;
            }
        }
        return (count == this.mines);
    }

    public boolean isFailed() {
        for (Cell cell : this.cells) {
            if (cell.isMine() && cell.isOpen()) {
                return true;
            }
        }
        return false;
    }

    public void debugPrint() {
        System.out.println("Flags: " + this.flagCount());
        System.out.println("Open: " + this.cellsOpen);
        System.out.println("Lasted Opened: (" + this.getLastOpened().x + ", " + this.getLastOpened().y + ")");
        System.out.print((this.isWon() ? "Game Won!\n" : "") + (this.isFailed() ? "Game Failed!\n" : ""));
        System.out.println("Field Status:");
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                Cell cell = this.getCell(x, y);
                char serial = 'x';
                serial = cell.isMine() ? 'M' : serial;
                serial = cell.isFlagged() ? 'F' : serial; 
                if (cell.isOpen()) {
                    int around = cell.minesAround(this);
                    serial = "_12345678".charAt(around);
                }
                System.out.printf("%2s", serial);
            }
            System.out.print('\n');
        }
    }
}