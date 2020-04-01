import java.util.ArrayList;

class Environment {
    int w, h;
    double[] y;
    double g, fa, fric, mf;
    Orb[] orbs;
    ArrayList<RectangleX> rects = new ArrayList<>();
    Environment(int kw, int kh, double kg, int no){
        w = kw; h = kh; g = kg/10;
        y = new double[w];
        gen(500);
        genOrbs(no);
        fric = 4;
        mf = 1;
        fa = .95;
    }
    private void gen(int sty){
        for(int x = 0; x < w; x++){
            y[x] = (Math.cos(x/200.0)*(h/4.0)+sty);
            rects.add(new RectangleX(x, x+1, h, h-(int)y[x]));
        }
    }
    double ga(double x){
        double h = Math.atan2(gs(x),1);
        //if(h<0) h+=2*Math.PI;
        return h;
    }
    double gs(double x){
        return y[(int)x+1]-y[(int)x];
    }
    Orb co(double x, double y){
        double max = 0; int maxi = 0;
        for(int i = 0; i < orbs.length; i++)
            if(Math.hypot(orbs[i].y-y, orbs[i].x-x)>max){
                max = Math.hypot(orbs[i].y-y, orbs[i].x-x);
                maxi = i;
            }
        return orbs[maxi];
    }
    void genOrbs(int n){
        orbs = new Orb[n];
        for(int i = 0; i < n; i++)
            do {
                orbs[i] = new Orb((int)(Math.random()*w), (int)(Math.random()*h), 5);
            } while(orbs[i].y > h-y[orbs[i].x]);
    }
}
