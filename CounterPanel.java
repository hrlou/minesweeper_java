import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

// seven segment display
public class CounterPanel extends JPanel {
    private SpriteSheet digits;
    private int scale;
    
    CounterPanel(int scale) {
        this.scale = (int)((float)scale * 1.5F);
        this.digits = new SpriteSheet("assets/digits.png", 13, 23);
        this.setPreferredSize(new Dimension((13 * this.scale) * 3, 23 * this.scale));
    }

    private BufferedImage getDigit(int x) {
        return this.digits.getSprite(0, 9 - x);
    }

    public void paint(Graphics g) {
        String count = String.format("%03d", this.count());
        for (int i = 0; i <= 2; i++) {
            int x = count.charAt(i) - '0';
            BufferedImage img = this.getDigit(x);
            // System.out.println((img.getWidth() * this.scale) * i);
            g.drawImage(img, 
                i * (img.getWidth() * this.scale),
                0, 
                img.getWidth() * this.scale, 
                img.getHeight() * this.scale, 
                null);
        }
    }

    // we'll just override this method
    public int count() {
        return 0;
    }
}