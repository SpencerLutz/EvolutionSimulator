import java.util.ArrayList;

class JointX{
    double x, y, k, m, r, f, vx, vy, ftx, fty;
    boolean g, gp, c;
    ArrayList<Double> is, fs;
    JointX(double px, double py){
        x = px; y = py;
        vx = 0; vy = 0;
        k = Math.random();
        r = Math.random()*16+16;
        m = r*r;
        g = false;
        is = new ArrayList<>();
        fs = new ArrayList<>();
        ftx = 0; fty = 0;
        gp = false;
    }
    void update(Environment e){
        c = false;
        for(int j = 0; j < is.size(); j++){
            ftx += fs.get(j)*Math.cos(is.get(j));
            fty += fs.get(j)*Math.sin(is.get(j));
        }
        f = Math.hypot(ftx, fty);
        is.clear(); fs.clear();
        if(((e.h-e.y[(int)x])-Math.ceil(y))/Math.sqrt(e.gs(x)*e.gs(x)+1) < r)
            g = true;
        else g = false;
        if(g){
            c = !gp;
            double i = Math.atan2(fty, ftx);
            double t = e.ga((int)x);
            double fn = m*e.g*Math.cos(t);
            double fnx = -fn*Math.sin(t);
            double fny = fn*Math.cos(t);
            double fkx = -1*fnx*k;
            double fky = fny*k;
            double fx = fnx+fkx;
            double fy = fny+fky-(e.g*m);
            double ax = fx/m;
            double ay = fy/m;
            if(c) ay = -vy;
            System.out.println("ay: "+ay);
            vx += ax; vy += ay;
            System.out.println("vy: "+vy);
            x += vx; y -= vy;
        }
        else{
            vx += ftx/m;
            vy += (fty-(e.g*m))/m;
            x+=vx; y-=vy;
        }
        x=x<r?r:x;
        x=x>e.w-r?e.w-r:x;
        //y=y>e.h-e.y[(int)x]-r?e.h-e.y[(int)x]-r:y;
        ftx = 0; fty = 0;
        gp = g;
    }
}
