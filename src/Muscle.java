class Muscle{
    JointY a, b;
    double force;
    double max;
    JointY[] jt;
    Environment e;
    Muscle(JointY tc1, JointY tc2, double tmax, Environment te, JointY[] jtx){
        a = tc1;
        b = tc2;
        max = tmax;
        e = te;
        jt = jtx;
    }
    void applyForce(double f){
        double angle = Math.atan2(a.y-b.y,a.x-b.x);
        force = f*.1;
        a.vx += Math.cos(angle)*force/a.m;
        a.vy += Math.sin(angle)*force/a.m;
        b.vx -= Math.cos(angle)*force/b.m;
        b.vy -= Math.sin(angle)*force/b.m;
    }
    Muscle copyMuscle(){
        return new Muscle(a,b,max,e,jt);
    }
    JointY getOther(JointY jt){
        return jt.equals(a)?b:a;//if alwasy returns 0 for distance then switch second a and b
    }
}