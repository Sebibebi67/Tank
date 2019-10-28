import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Player{

    private int x, y;
    private double move;
    private double alphaMove;
    private double alphaCanon;
    private ArrayList<Shot> shots = new ArrayList<Shot>();
    private int tankSize = 30;
    private SPanel panel;
    private Graphics g1 = null;
    private Graphics g2 = null;
    private Graphics g3 = null;

    public Player(int x, int y, SPanel panel){
        this.x = x;
        this.y = y;
        this.move = 10;
        this.alphaMove = 0;
        this.alphaCanon = 0;
        this.panel = panel;
        g1 = panel.getGraphics();
        g2 = panel.getGraphics();
        g3 = panel.getGraphics();
    }

    public void display(SPanel panel){

        g1.setColor(Color.BLACK);
        g1.fillRect(this.x-2*tankSize, this.y - 2*tankSize, tankSize*4, tankSize*4);
        
        for (int i = 0; i<shots.size(); i++){
            shots.get(i).display();
        }

        g2.setColor(Color.BLUE);
        AffineTransform rotateTank = AffineTransform.getRotateInstance(this.alphaMove, this.x, this.y);
        ((Graphics2D)g2).setTransform(rotateTank);
        g2.fillRect(this.x-tankSize/2, this.y - tankSize/2, tankSize, tankSize);

        g3.setColor(Color.RED);
        AffineTransform rotateCanon = AffineTransform.getRotateInstance(this.alphaCanon, this.x, this.y);
        ((Graphics2D)g3).setTransform(rotateCanon);
        g3.fillRect(this.x, this.y-tankSize/8, tankSize, tankSize/4);

    }

    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public double getAlphaMove(){return this.alphaMove;}

    public void setAlphaCanon(double alpha){this.alphaCanon = alpha;}

    public void shot(){
        Shot shot = new Shot(this.x, this.y, this.alphaCanon, this.panel);
        shots.add(shot);
    }

    public void update(Boolean[] keys){
        move(keys);
        for (int i = 0; i<shots.size(); i++){
            shots.get(i).update();
        }

    }

    public void move(Boolean[] keys){
        if (keys[0]){
            this.x = (int) (this.x + Math.cos(this.alphaMove)*this.move);
            this.y = (int) (this.y + Math.sin(this.alphaMove)*this.move);
        }
        if (keys[1]){
            this.alphaMove = this.alphaMove + Math.PI/4;
        }
        if (keys[2]){
            this.x = (int) (this.x - Math.cos(this.alphaMove)*this.move);
            this.y = (int) (this.y - Math.sin(this.alphaMove)*this.move);
        }
        if (keys[3]){
            this.alphaMove = this.alphaMove - Math.PI/4;
        }
    }
}