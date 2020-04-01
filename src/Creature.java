import java.util.ArrayList;

class Creature {
    private int hs, is, nh, nj, nm, mw, mh;
    double fit;
    double[] i;
    JointY[] jt;
    Muscle[] mc;
    Brain brain;
    Environment e;
    boolean dead;
    private ArrayList<Muscle> mcx = new ArrayList<>();
    private ArrayList<JointY> jtx = new ArrayList<>();
    Creature(Environment env){
        e = env;
        mw = 200; mh = 200;
        nm = (int)(Math.random()*12); nj = (int)(Math.random()*nm*.8);
        jt = new JointY[nj];
        gen(env);
        hs = 4; nh = 2;
        is = (4 * jt.length) + 2;
        i = new double[is];
        brain = new Brain((4 * jt.length) + 2, hs, nh, nm);
        dead = false;
    }
    Creature(Creature c){
        e = c.e;
        mw = c.mw; mh = c.mh;
        nm = c.nm; nj = c.nj;
        for(int i = 0; i < nj; i++){
            
        }
    }
    private double[][] randArr(int r, int c){
        double[][] k = new double[r][c];
        for(int i = 0; i < r; i++)
            for(int j = 0; j < c; j++) k[i][j] = Math.random()*2-1;
        return k;
    }
    void move(Environment env){
        for(int j = 0; j < jt.length; j++) i[j] = jt[j].x/env.w;
        for(int j = 0; j < jt.length; j++) i[j+jt.length] = jt[j].y/env.h;
        for(int j = 0; j < jt.length; j++) i[j+2*jt.length] = jt[j].g?1:0;
        for(int j = 0; j < jt.length; j++) i[j+3*jt.length] = env.gs(jt[j].x);
        Orb orb = env.co(gav()[0], gav()[1]);
        i[4*jt.length] = orb.x; i[4*jt.length+1] = orb.y;
        Neuron[] out = new Neuron[nm];
        for(int i = 0; i < nm; i++) out[i] = new Neuron();
        out = brain.think(i, out);
        for(int i = 0; i < nm; i++){
            mc[i].applyForce(out[i].value);
        }
        for(int i = 0; i < nj; i++){
            JointY ni = jt[i];
            ni.move();
            ni.hitWalls();
            //fitness conditions
            if(ni.checkOrbs()) fit++;
            if(ni.checkLoss()) fit-=.01;
        }
    }
    private void gen(Environment env){
        jtx.add(new JointY(Math.random()*mw, Math.random()*mh, 0, 0, Math.random()*.2+.4, Math.random()*.2, env));
        for(int j = 1; j < nj; j++){
            jtx.add(new JointY(Math.random()*mw, Math.random()*mh, 0, 0, Math.random()*.2+.4, Math.random()*.2, env));
            double l1 = (Math.random()+.5)*120;
            double l2 = (Math.random()+.5)*120;
            JointY ja = jtx.get(j);
            JointY jb = jtx.get((int)(Math.random()*(j-1)));
            mcx.add(new Muscle(ja, jb, Math.max(l1,l2), env, jt));
            ja.addMuscle(mcx.get(mcx.size()-1));
            jb.addMuscle(mcx.get(mcx.size()-1));
        }
        if(mcx.size() != nm) for(int j = 0; mcx.size() < nm; j++){
            JointY ja = jtx.get((int)(Math.random()*nj));
            JointY jb = jbcheck(ja);
            double l1 = Math.random()+.5;
            double l2 = Math.random()+.5;
            mcx.add(new Muscle(ja, jb, Math.max(l1,l2), env, jt));
            ja.addMuscle(mcx.get(mcx.size()-1));
            jb.addMuscle(mcx.get(mcx.size()-1));
        }
        mc = mcx.toArray(new Muscle[mcx.size()]);
        jt = jtx.toArray(new JointY[jtx.size()]);
    }
    Creature modify(double rate){
        Creature ret = new Creature(this);
        for(int i = 0; i < ret.brain.axons.length; i++)
            for(int j = 0; j < ret.brain.axons[i].length; j++)
                for(int k = 0; k < ret.brain.axons[i][j].length; k++) {
                    if (Math.random() < rate)
                        ret.brain.axons[i][j][k] = new Axon(Math.random() * 2 - 1);
                }
        return ret;
    }
    private JointY jbcheck(JointY jax){
        JointY jbx = jtx.get((int)(Math.random()*nj));
        if(jbx == jax) return jbcheck(jax);
        return jbx;
    }
    double[] gav(){
        double sx = 0, sy = 0;
        for(JointY j: jt){
            sx += j.x;
            sy += j.y;
        }
        return new double[]{sx/jt.length, sy/jt.length};
    }
}
