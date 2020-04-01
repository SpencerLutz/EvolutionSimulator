import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class Menu{
    private ImageX[] Images;
    Button[] Butts;
    boolean active = false;
    Menu(int v, boolean ac) throws IOException{
        active = ac;
        if(v == 0) {
            Butts = new Button[1];
            Images = new ImageX[1];
            Butts[0] = new Button(765, 489, 150, 50, "Aa.png", "Ab.png");
            Images[0] = new ImageX(500, 500, "title.png");
        }
        if(v == 1){
            Images = new ImageX[0];
            Butts = new Button[1];
            Butts[0] = new Button(765, 489, 150, 50, "Ba.png", "Bb.png");
        }
    }
    void draw(Graphics g, int x, int y, Main obs) {
        if(!different()){
            return;
        }
        BufferedImage B = new BufferedImage(obs.w, obs.h, BufferedImage.TYPE_INT_RGB);
        Graphics r = B.getGraphics();

        for (ImageX image : Images) image.draw(r, obs);
        for (Button butt : Butts) butt.draw(r, obs);

        g.drawImage(B, x, y+22, obs);
    }
    private boolean different(){
        for(int i = 0; i < Butts.length; i++)
            if(Butts[i].d){
                Butts[i].d = false;
                return true;
            }
        return true;
    }
}
