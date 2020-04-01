import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Main extends JFrame implements KeyListener, MouseListener{

    int w = 1680, h = 1028;
    private Menu first = new Menu(0, false), second = new Menu(1, true);
    private SimPane sp = new SimPane(10);
    int fps = 60;
    long st = 0;

    private Main() throws IOException{
        super("title");
        setSize(1680,1028);
        setVisible(true);
        requestFocusInWindow();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
        addMouseListener(this);
        getImages();

        while(true) {
            if (System.currentTimeMillis()-st < 1000 / fps) continue;
            st = System.currentTimeMillis();
            if (sp.active) sp.update();
            this.repaint();
        }
    }
    public static void main(String[] args) throws IOException{
        Main m = new Main();//basically starts program
    }
    public void paint(Graphics g){
        if(first.active) first.draw(g, 0, 0, this);
        if(second.active) second.draw(g, 0, 0, this);
        if(sp.active){
            sp.draw(g, 0, 0, this);
            sp.stats.draw(g,1300,0,this);
        }
    }
    private void getImages() throws IOException{

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(first.active) for(Button b : first.Butts) b.inside(e);
        if(second.active) for(Button b : second.Butts) b.inside(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("Slope: "+env.gs(e.getX())+" Angle: "+env.ga(e.getX()));
        //System.out.println(e.getY()+"  "+e.getX());
        if(e.getButton() == MouseEvent.BUTTON1){
            if(first.active && first.Butts[0].inside(e)){
                first.active = false;
                second.active = true;
                return;
            }
            if(second.active){
                if(first.Butts[0].inside(e)){
                    second.active = false;
                    sp.active = true;
                }
                return;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
