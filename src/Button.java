import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

class Button {
    int x, y, w, h;
    boolean d;
    BufferedImage a, b, c;
    Button(int kx, int ky, int kw, int kh, String fileA, String fileB) throws IOException{
        x = kx; y = ky;
        w = kw; h = kh;
        a = ImageIO.read(this.getClass().getResource("images/"+fileA));
        b = ImageIO.read(this.getClass().getResource("images/"+fileB));
        c = a;
        d = true;
    }
    boolean inside(MouseEvent e){
        int ax = e.getX(), ay = e.getY()-22;
        if(ax > x && ay > y && ax - x < w && ay - y < h){
            click();
            return true;
        }
        return false;
    }
    private void click(){
        if(c == a) c = b;
        else c = a;
        d = true;
    }
    void draw(Graphics g, Main obs){
        g.drawImage(c, x, y, obs);
    }
}
