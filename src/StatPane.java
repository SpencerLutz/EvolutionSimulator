import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StatPane {
    boolean active = false;
    Creature[] crs;
    int graphWidth, graphHeight, margin;
    ArrayList<Double> avgs, mins, maxs;
    int width, height;
    StatPane(Creature[] crsx, int w){
        crs = crsx;
        width = w;
        height = 1028;
        graphWidth = (int)(w*.9);
        margin = (int)(w*.05);
        graphHeight = (int)(graphWidth*.7);
        avgs = new ArrayList<>();
        mins = new ArrayList<>();
        maxs = new ArrayList<>();
    }
    void draw(Graphics g, int x, int y, Main obs) {
        BufferedImage B = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics r = B.getGraphics();
        Graphics2D g2 = (Graphics2D)r;

        r.setColor(Color.white);
        r.drawLine(margin, margin, margin, margin+graphHeight);
        r.drawLine(margin, margin+graphHeight, margin+graphWidth, margin+graphHeight);

        if(avgs.size()>1) {
            double max = maxs.get(0), min = mins.get(0);
            for (int i = 1; i < maxs.size(); i++) {
                if (maxs.get(i) > max) max = maxs.get(i);
                if (mins.get(i) < min) min = mins.get(i);
            }
            for (int i = 0; i < avgs.size() - 1; i++) {
                if (mins.get(i) < min) System.out.println("WEEWOOO");

                r.setColor(Color.blue);
                int x1 = (int) ((graphWidth / (avgs.size() - 1)) * i);
                int x2 = (int) ((graphWidth / (avgs.size() - 1)) * (i + 1));
                int y1 = (int) (((avgs.get(i) - min) / (max - min)) * graphHeight);
                int y2 = (int) (((avgs.get(i + 1) - min) / (max - min)) * graphHeight);
                r.drawLine(x1 + margin, graphHeight + margin - y1, x2 + margin, graphHeight + margin - y2);

                r.setColor(Color.red);
                x1 = (int) ((graphWidth / (mins.size() - 1)) * i);
                x2 = (int) ((graphWidth / (mins.size() - 1)) * (i + 1));
                y1 = (int) (((mins.get(i) - min) / (max - min)) * graphHeight);
                y2 = (int) (((mins.get(i + 1) - min) / (max - min)) * graphHeight);
                r.drawLine(x1 + margin, graphHeight + margin - y1, x2 + margin, graphHeight + margin - y2);

                r.setColor(Color.green);
                x1 = (int) ((graphWidth / (maxs.size() - 1)) * i);
                x2 = (int) ((graphWidth / (maxs.size() - 1)) * (i + 1));
                y1 = (int) (((maxs.get(i) - min) / (max - min)) * graphHeight);
                y2 = (int) (((maxs.get(i + 1) - min) / (max - min)) * graphHeight);
                r.drawLine(x1 + margin, graphHeight + margin - y1, x2 + margin, graphHeight + margin - y2);
            }
        }
        g.drawImage(B, x, y + 22, obs);
    }
    void update(Creature[] crsx, boolean done) {
        if(done){
            double sum = 0;
            double min = crs[0].fit; double max = min;
            for(int i = 0; i < crs.length; i++){
                if(crs[i].fit>max) max = crs[i].fit;
                if(crs[i].fit<min) min = crs[i].fit;
                sum += crs[i].fit;
            }
            mins.add(min);
            maxs.add(max);
            avgs.add(sum/crs.length);
        }
    }
}
