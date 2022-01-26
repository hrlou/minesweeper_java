// represents a single cell on the field
public class Cell {
    // hold the coordinates of the cell
    public final int x, y;

    // initialise to -1, so we can quickly see if it's "uninitialised"
    private int minesAround = -1;

    // state conditions
    private boolean isMine = false;
    private boolean isFlagged = false;
    private boolean isOpen = false;
    
    // construct the cell using the coordinates
    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // getters
    public boolean isFlagged() { return this.isFlagged; }
    public boolean isOpen() { return this.isOpen; }
    public boolean isMine() { return this.isMine; }

    // "setters"
    public void setMine() { this.isMine = true; }

    // resets the cell to default
    public void reset() {
        this.isFlagged = false;
        this.isOpen = false;
        this.isMine = false;
        this.minesAround = -1;
    }
    
    // Counts the amount mines around the cell
    public int minesAround(Field field) {
        // we don't want to calculate around every single time so we use this truck
        // minesAround un initialised
        if (this.minesAround == -1) {
            this.minesAround = 0;
            if (this.isMine) {
                return 0;
            }
            Cell[] around = field.getAround(this);
            for (Cell cell : around) {
                // increment if cell is mine
                this.minesAround += cell.isMine() ? 1 : 0;
            }
        }
        return this.minesAround;
    }

    public void flag() { 
        // flip the state
        this.isFlagged = !this.isFlagged; 
    }

    // opens the cell
    public void open(Field field) {
        // cell cannot be opened if it is already opened
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;
        // if the cell is a mine or if there are mines around
        if (this.isMine() || this.minesAround(field) > 0) {
            return;
        }
        // recursively open cells around
        Cell[] around = field.getAround(this);
        for (Cell cell : around) {
            field.openCell(cell);
        }
    }
}
