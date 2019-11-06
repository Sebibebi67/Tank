import java.awt.Color;
import java.awt.Graphics;
public class Shot{
    
    private int x,y;
    private double alpha;
    private int speed;
    private int shotSize;

    public Shot(int x, int y, double alpha, SPanel panel){
        this.x = x;
        this.y = y;
        this.alpha = alpha;
        this.speed = 30;
        this.shotSize = 3;
    }

    public void display(Graphics g){
        g.setColor(Color.BLACK);
        //g.fillRect(x-2*(shotSize+speed), y-2*(shotSize+speed), 4*(shotSize+speed), 4*(shotSize+speed));

        g.setColor(Color.WHITE);
        g.fillOval(x-shotSize/2, y-shotSize/2, shotSize, shotSize);
    }

    public void update(double diff){
        this.x = (int) (this.x + Math.cos(this.alpha)*this.speed*diff);
        this.y = (int) (this.y + Math.sin(this.alpha)*this.speed*diff);
    }
}