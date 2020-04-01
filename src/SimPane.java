import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SimPane {
    Environment env;
    Creature[] crs;
    int t;
    int idx;
    boolean active = false;
    StatPane stats;
    SimPane(int nc){
        env = new Environment(1300, 1028, 1, 12);
        crs = new Creature[nc];
        for(int i = 0; i < crs.length; i++){
            crs[i] = new Creature(env);
        }
        t = 0; idx = 0;
        stats = new StatPane(crs, 1680-env.w);
    }
    void draw(Graphics g, int x, int y, Main obs) {
        BufferedImage B = new BufferedImage(env.w, env.h, BufferedImage.TYPE_INT_RGB);
        Graphics r = B.getGraphics();
        Graphics2D g2 = (Graphics2D)r;
        /*for(int i = 0; i < env.w-1; i++){
            r.setColor(Color.GREEN);
            int[] xp = {i, i, i+1, i+1};
            int[] yp = {obs.h-(int)env.y[i], obs.h, obs.h, obs.h-(int)env.y[i+1]};
            r.fillPolygon(xp,yp,4);
        }*/
        r.setColor(Color.red);
        r.fillRect(437, 700, 380, 200);
        for(RectangleX t : env.rects){
            r.setColor(Color.GREEN);
            r.fillRect(t.x1, t.y2, t.x2-t.x1, t.y1-t.y2);
        }
        for(Muscle m : crs[idx].mc){
            g2.setColor(new Color(242,203,136));
            g2.setStroke(new BasicStroke((float)(8)));
            g2.drawLine((int)m.a.x, (int)m.a.y,
                    (int)m.b.x, (int)m.b.y);
        }
        for(JointY j : crs[idx].jt){
            r.setColor(new Color((int)((j.f)*1275), (int)((.2-j.f)*1275), 0));
            r.fillOval((int)((j.x-j.r/2)), (int)((j.y-j.r/2)), (int)(j.r), (int)(j.r));
            r.setColor(Color.YELLOW); r.fillOval((int)j.x, (int)j.y, 4, 4);
        }
        for(Orb o : env.orbs){
            if(o.v) {
                r.setColor(Color.CYAN);
                r.fillOval(o.x - o.r, o.y - o.r, 2 * o.r, 2 * o.r);
            }
        }
        g.drawImage(B, x, y+22, obs);
    }
    void update(){
        t++;
        if(t>300){
            System.out.println(crs[idx].fit);
            if(idx == crs.length-1){
                env = new Environment(1300, 1028, 1, 60);
                double maxfit = crs[0].fit;
                double minfit = maxfit;
                for(int i = 1; i < crs.length; i++){
                    if(crs[i].fit>maxfit) maxfit = crs[i].fit;
                    if(crs[i].fit<minfit) minfit = crs[i].fit;
                }
                ArrayList<Creature> modList = new ArrayList<>();
                for(int i = 0; i < crs.length; i++) {
                    if (Math.random() > (crs[i].fit - minfit) / (maxfit - minfit)) crs[i].dead = true;
                    else modList.add(crs[i].modify(.01));
                }
                for(int i = 0; i < crs.length; i++){
                    if(crs[i].dead){
                        if(modList.size() == 0) crs[i] = new Creature(env);
                        else {
                            int pick = (int) (Math.random() * modList.size());
                            crs[i] = modList.get(pick);
                            modList.remove(pick);
                        }
                    }
                }
                stats.update(crs, true);
                for(int i = 0; i < crs.length; i++){
                    crs[i].fit = 0;
                    crs[i].e = env;
                    for(int j = 0; j < crs[i].jt.length; j++) crs[i].jt[j].e = env;
                }
                idx = 0;
            }
            else {
                for (Orb b : env.orbs) b.v = true;
                idx++;
                stats.update(crs, false);
            }
            t = 0;
        }
        crs[idx].move(env);
    }
}
