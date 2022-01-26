import java.io.File;
import java.net.URISyntaxException;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/*
 * this just makes getting sprites easier  
 * I'm sure it's pretty self explanatory
 */
public class SpriteSheet {
    public final int width, height;
    private BufferedImage img = null;

    SpriteSheet(String fileName, int width, int height) {
        ClassLoader classLoader = getClass().getClassLoader();
        File imgFile = null;
        this.width = width;
        this.height = height;
        
        System.out.println("Loading: " + fileName);
        try {
            imgFile = new File(classLoader.getResource(fileName).toURI());
            try {
                this.img = ImageIO.read(imgFile);
            } catch (IOException e) { e.printStackTrace(); }
        } catch (URISyntaxException e) { e.printStackTrace(); }
        System.out.println(fileName + ": Loaded Successfully");
        
    }

    public BufferedImage getSprite(int x, int y) {
        return img.getSubimage(x * this.width, y * this.height, this.width, this.height);
    }
}
