import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.Image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.awt.RenderingHints;

public class Player {

    private int x, y;
    private double move;
    private double alphaMove;
    private double alphaCanon;
    private ArrayList<Shot> shots = new ArrayList<Shot>();
    private int tankSize = 30;
    private SPanel panel;

    private Image tank;
    private Image canon;

    public Player(int x, int y, SPanel panel){
        this.x = x;
        this.y = y;
        this.move = 5;
        this.alphaMove = 0;
        this.alphaCanon = 0;
        this.panel = panel;
        
        try {
			tank = ImageIO.read(new File("./ressources/tanks/tank.png"));
			canon = ImageIO.read(new File("./ressources/tanks/canon.png"));
		}
		catch(IOException exc) {
            System.out.println("Image loading error");
			exc.printStackTrace();
		}
    }

    public void display(Graphics g){

        g.setColor(Color.BLACK);
        //g.fillRect(this.x-2*tankSize, this.y - 2*tankSize, tankSize*4, tankSize*4);
        
        for (int i = 0; i<shots.size(); i++){
            shots.get(i).display(g);
        }
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform old = g2d.getTransform();

        // Change this into g2d.transform
        g2d.translate(this.x, this.y);
        g2d.rotate(this.alphaMove);

        g2d.drawImage(tank,-tankSize/2,-tankSize/2, tankSize, tankSize, null, null);
        g2d.rotate(this.alphaCanon-this.alphaMove);
        g2d.drawImage(canon,-tankSize/2,-tankSize/2, tankSize, tankSize, null, null);

        g2d.setTransform(old);
        RenderingHints rh = new RenderingHints(
             RenderingHints.KEY_TEXT_ANTIALIASING,
             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
    }

    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public double getAlphaMove(){return this.alphaMove;}

    public void setAlphaCanon(double alpha){this.alphaCanon = alpha;}
    public void setAlphaCanon2(int xMouse, int yMouse){
        this.alphaCanon = Math.atan2(yMouse - this.y -30, xMouse - this.x);
    }

    public void shot(){
        Shot shot = new Shot(this.x, this.y, this.alphaCanon, this.panel);
        shots.add(shot);
    }

    public void update(Boolean[] keys, double diff){
        move(keys, diff);
        for (int i = 0; i<shots.size(); i++){
            shots.get(i).update(diff);
        }
    }

    public void move(Boolean[] keys, double diff){
        if (keys[0]){
            this.x = (int) (this.x + Math.cos(this.alphaMove)*this.move*diff);
            this.y = (int) (this.y + Math.sin(this.alphaMove)*this.move*diff);
        }
        if (keys[1]){
            this.alphaMove = this.alphaMove + Math.PI/20*diff;
        }
        if (keys[2]){
            this.x = (int) (this.x - Math.cos(this.alphaMove)*this.move*diff);
            this.y = (int) (this.y - Math.sin(this.alphaMove)*this.move*diff);
        }
        if (keys[3]){
            this.alphaMove = this.alphaMove - Math.PI/20*diff;
        }
    }
}