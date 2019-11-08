package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
public class Shot{
    
    private double x,y;
    private double alpha;
    private double speed;
    private double shotSize;
    private Rectangle rect;

    public Shot(double x, double y, double alpha, SPanel panel){
        this.x = x;
        this.y = y;
        this.alpha = alpha;
        this.speed = 15;
        this.shotSize = 7;
        this.rect = newrect();
    }

    public void display(Graphics g){
        g.setColor(Color.BLACK);

        g.setColor(Color.WHITE);
        g.fillOval((int)(x-shotSize/2), (int)(y-shotSize/2), (int)shotSize, (int)shotSize);
    }

    public void update(double diff){
        this.x = this.x + Math.cos(this.alpha)*this.speed*diff;
        this.y = this.y + Math.sin(this.alpha)*this.speed*diff;
        this.rect = newrect();
    }

    public Rectangle newrect(){
        return new Rectangle((int)(x-shotSize/2), (int)(y-shotSize/2), (int)shotSize, (int)shotSize);
    }

    public Rectangle getShape(){return rect;}
}