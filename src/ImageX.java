import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class ImageX {
    private int x, y;
    BufferedImage img;
    ImageX(int kx, int ky, String file) throws IOException {
        x = kx; y = ky;
        img = ImageIO.read(this.getClass().getResource("images/"+file));
    }
    void draw(Graphics g, Main obs){
        g.drawImage(img, x, y, obs);
    }
}
