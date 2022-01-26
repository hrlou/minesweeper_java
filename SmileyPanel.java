import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/* 
 * smiley is the status for minesweeper
 * he indicates, whether you have won or lost
 */
class SmileyPanel extends JPanel {
    private Field field;
    private SpriteSheet smileys;
    private int scale;

    SmileyPanel(Field field, int scale) {
        this.field = field;
        this.scale = (int)((float)scale * 1.5F);
        this.smileys = new SpriteSheet("assets/smileys.png", 24, 24);
        this.setPreferredSize(new Dimension(24 * this.scale, 24 * this.scale));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                field.reset();
            }
        });
    }

    public void paint(Graphics g) {
        int sprite = 4;
        sprite = field.isWon() ? 1 : sprite;
        sprite = field.isFailed() ? 2 : sprite;
        BufferedImage img = smileys.getSprite(0, sprite);
        g.drawImage(img, 0, 0, img.getWidth() * this.scale, img.getHeight() * this.scale, null);
    }
}
