import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;


// drawing the field panel, and handling input output
public class FieldPanel extends JPanel {
    private Field field;
    private SpriteSheet fieldTiles;

    final int scale, width, height;
    final int tileSize;

    FieldPanel(Field field, int scale) {
        this.field = field;
        this.scale = scale;
        
        this.tileSize = 16;
        this.width = field.width * (this.tileSize * this.scale);
        this.height = field.height * (this.tileSize * this.scale);
        
        this.fieldTiles = new SpriteSheet("assets/tiles.png", this.tileSize, this.tileSize);

        this.setPreferredSize(new Dimension(this.width, this.height));

        this.setupMouse();
    }

    public void paint(Graphics g) {
        // stop from calling every time we paint
        boolean failed = field.isFailed();
        for (int y = 0; y < field.height; y++) {
            for (int x = 0; x < field.width; x++) {
                Cell cell = field.getCell(x, y);
                int sprite = 0;
                if (cell.isOpen()) {
                    int around = cell.minesAround(field);
                    // look at the sprite sheet and you'll understand
                    sprite = 15 - around;
                }
                sprite = cell.isFlagged() ? 1 : sprite;
                if (failed) {
                    // show remaining mines
                    if (cell.isMine()) {
                        Cell last = field.getLastOpened();
                        sprite = (cell.x == last.x && cell.y == last.y) ? 3 : 5;
                    } else {
                        // incorrect flag
                        sprite = cell.isFlagged() ? 4 : sprite;
                    }
                }
                BufferedImage img = this.fieldTiles.getSprite(0, sprite);
                g.drawImage(img, 
                    x * (this.tileSize * this.scale),
                    y * (this.tileSize * this.scale), 
                    this.tileSize * this.scale, 
                    this.tileSize * this.scale, 
                    null);
            }
        }
    }

    private void setupMouse() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getX() > width || e.getY() > height) {
                    return;
                }
                // get grid coordinate
                int x = (e.getX() / (tileSize * scale));
                int y = (e.getY() / (tileSize * scale));
                
                int button = e.getButton();
                System.out.println("Button: " + button + "; Pressed at: (" + x + ", " + y + ")");
                switch (button) {
                    // left click
                    case 1: 
                        field.openCell(field.getCell(x, y));
                        break;
                    // right click
                    case 3:
                        field.flagCell(field.getCell(x, y));
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            }
        });
    }
}