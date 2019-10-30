import java.awt.Color;
import java.awt.Graphics;
public class Shot{
    
    private int x,y;
    private double alpha;
    private int speed;
    private SPanel panel;
    private Graphics g1 = null;
    private Graphics g2 = null;
    private int shotSize;

    public Shot(int x, int y, double alpha, SPanel panel){
        this.x = x;
        this.y = y;
        this.alpha = alpha;
        this.speed = 30;
        this.panel = panel;
        g1 = panel.getGraphics();
        g2 = panel.getGraphics();
        this.shotSize = 3;
    }

    public void display(){
        g1.setColor(Color.BLACK);
        g1.fillRect(x-2*(shotSize), y-2*(shotSize), 4*shotSize, 4*shotSize);


        g2.setColor(Color.WHITE);
        g2.fillOval(x-shotSize/2, y-shotSize/2, shotSize, shotSize);
    }

    public void update(){
        this.x = (int) (this.x + Math.cos(this.alpha)*this.speed);
        this.y = (int) (this.y + Math.sin(this.alpha)*this.speed);
    }
}