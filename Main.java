/** Minesweeper in Java
 * @author Hess Lewis
 * @date 11/01/2022
 * PLEASE READ README.md
*/

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main extends JFrame {
    private int fieldWidth, fieldHeight, fieldMines;
    public final int scale; // scale to render everything by

    // self explanatory
    public final Field field;
    public final SmileyPanel smileyPanel;
    public final FieldPanel fieldPanel;
    public final CounterPanel flagCounter, timeCounter;

    /* difficulty
        Beginner is default
        0   Beginner         9x9     10 mines
        1   Intermediate     16x16   40 mines
        2   Expert           30x16   100 mines
    */
    Main(int difficulty) {
        super("Minesweeper by Hess Lewis");
        
        switch (difficulty) {
            case 1:
                this.fieldWidth = 16;
                this.fieldHeight = 16;
                this.fieldMines = 40;
                this.scale = 2;
                break;
            case 2: 
                this.fieldWidth = 30;
                this.fieldHeight = 16;
                this.fieldMines = 100;
                this.scale = 2;
                break;
            default:
                this.fieldWidth = 9;
                this.fieldHeight = 9;
                this.fieldMines = 10;
                this.scale = 3;
                break;
        }
        // initialise the minesweeper game field
        this.field = new Field(this.fieldWidth, this.fieldHeight, this.fieldMines);

        // initialise panels and components
        this.smileyPanel = new SmileyPanel(this.field, this.scale);
        this.fieldPanel = new FieldPanel(this.field, this.scale);
        // initialise new counters of CounterPanels
        this.flagCounter = new CounterPanel(this.scale) {
            @Override
            public int count() { 
                return field.flagCount();
            }
        };

        this.timeCounter = new CounterPanel(this.scale) {
            @Override
            public int count() { 
                return field.getSeconds();
            }
        };

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        // make info panel
        JPanel info = new JPanel();
        info.setLayout(new FlowLayout());
        info.add(this.flagCounter);
        info.add(this.smileyPanel);
        info.add(this.timeCounter);

        this.add(info, BorderLayout.NORTH);
        this.add(this.fieldPanel, BorderLayout.CENTER);

        this.pack();
    }

    // get the difficulty from the player
    private static int getDifficulty() {
        String[] difficulties = { "Easy", "Intermediate", "Expert" };
        String input = (String) JOptionPane.showInputDialog(null,
            "Choose a Difficulty",
            "Difficulty Selector",
            JOptionPane.QUESTION_MESSAGE,
            null,
            difficulties,
            difficulties[0]);
        System.out.println(input);
        for (int i = 0; i < input.length(); i++) {
            if (difficulties[i].equals(input)) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int difficulty = getDifficulty();
        Main main = new Main(difficulty);
        main.setVisible(true);
        for (;;) {
            main.repaint();
            try {
                // delay render
                Thread.sleep(120);
            } catch(Exception e) { }
        }
    }
}