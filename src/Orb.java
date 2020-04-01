class Orb {
    int x, y, r;
    boolean v;
    Orb(int kx, int ky, int kr){
        x = kx; y = ky; r = kr; v = true;
    }
    void collect(){
        v = false;
    }
}
