import java.util.ArrayList;

class JointY{
    double x, y, vx, vy, m, f, r;//x and y are center
    Environment e;
    double minSize = 16, maxSize = 32, minFric = 0, maxFric = 0;
    ArrayList<Muscle> muscles;
    boolean g = false;
    int nm = 0;
    JointY(double tx, double ty, double tvx, double tvy, double tm, double tf, Environment te){
        x = tx;
        y = ty;
        vx = tvx;
        vy = tvy;
        m = tm;
        f = tf;
        e = te;
        r = m/0.015;
        muscles = new ArrayList<>();
    }
    void addMuscle(Muscle m){
        muscles.add(m);
        nm++;
    }
    void move(){
        double maxdis = getDistance(0);
        int index = 0;
        for(int i = 1; i < nm; i++) {
            if (getDistance(i) > maxdis){
                maxdis = getDistance(i);
                index = i;
            }
        }
        vy -= e.g;
        if(y > e.h-e.y[(int)x]-1){
            g = true;
            vx -= e.g*Math.sin(e.ga(x));
            vx *= (1-f);
        }
        y -= vy;
        x += vx;
        JointY jt = muscles.get(index).getOther(this);
        if(getDistance(index)>muscles.get(index).max){
            double dx = (getDistance(index)-muscles.get(index).max)*((jt.x-x)/getDistance(index))*.5;
            double dy = (getDistance(index)-muscles.get(index).max)*((jt.y-y)/getDistance(index))*.5;
            jt.x-=dx; x+=dx;
            jt.y-=dy; y+=dy;
        }
        x=x<0?0:x;
        x=x>1298?1298:x;
        jt.x=jt.x<0?0:jt.x;
        jt.x=jt.x>1298?1298:jt.x;
    }
    void hitWalls() {
        y = y > e.h-e.y[(int)x]?e.h-e.y[(int)x]:y;
    }
    double getDistance(int i){
        JointY jt = muscles.get(i).getOther(this);
        return Math.hypot(jt.x-x,jt.y-y);
    }
    boolean checkOrbs(){
        for(Orb b : e.orbs)
            if(b.v&&Math.pow(b.x-x,2)+Math.pow(b.y-y,2)<r*r){
                b.collect();
                return true;
            }
        return false;
    }
    boolean checkLoss(){
        if(y>700) return true;
        return false;
    }
}