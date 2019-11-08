package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
public class Shot{
    
    private int x,y;
    private double alpha;
    private int speed;
    private int shotSize;
    private Ellipse2D circle;

    public Shot(int x, int y, double alpha, SPanel panel){
        this.x = x;
        this.y = y;
        this.alpha = alpha;
        this.speed = 15;
        this.shotSize = 3;
        this.circle = newCircle();
    }

    public void display(Graphics g){
        g.setColor(Color.BLACK);

        g.setColor(Color.WHITE);
        g.fillOval(x-shotSize/2, y-shotSize/2, shotSize, shotSize);
    }

    public void update(double diff){
        this.x = (int) (this.x + Math.cos(this.alpha)*this.speed*diff);
        this.y = (int) (this.y + Math.sin(this.alpha)*this.speed*diff);
        this.circle = newCircle();
    }

    public Ellipse2D newCircle(){
        return new Ellipse2D.Float(x-shotSize/2, y-shotSize/2, shotSize, shotSize);
    }

    public Ellipse2D getShape(){return circle;}
}