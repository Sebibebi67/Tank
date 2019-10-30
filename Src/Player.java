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
    private Graphics g1 = null;

    private Image tank;
    private Image canon;

    public Player(int x, int y, SPanel panel){
        this.x = x;
        this.y = y;
        this.move = 10;
        this.alphaMove = 0;
        this.alphaCanon = 0;
        this.panel = panel;
        g1 = panel.getGraphics();
        
        try {
			tank = ImageIO.read(new File("./ressources/tanks/tank.png"));
			canon = ImageIO.read(new File("./ressources/tanks/canon.png"));
		}
		catch(IOException exc) {
            System.out.println("Image loading error");
			exc.printStackTrace();
		}
    }

    public void display(SPanel panel){

        g1.setColor(Color.BLACK);
        g1.fillRect(this.x-2*tankSize, this.y - 2*tankSize, tankSize*4, tankSize*4);
        
        for (int i = 0; i<shots.size(); i++){
            shots.get(i).display();
        }

        //g1.setColor(Color.BLUE);
        Graphics2D g2d = (Graphics2D)g1;
        AffineTransform old = g2d.getTransform();

        // Change this into g2d.transform
        g2d.translate(this.x, this.y);
        g2d.rotate(this.alphaMove);

        //g2d.setColor(Color.BLUE);
        //g2d.fillRect(-tankSize/2,-tankSize/2, tankSize, tankSize);
        //g2d.drawImage(tank,-tankSize/2,-tankSize/2, tankSize, tankSize);
        g2d.drawImage(tank,-tankSize/2,-tankSize/2, tankSize, tankSize, null, null);

        //g2d.setColor(Color.RED);
        g2d.rotate(this.alphaCanon-this.alphaMove);
        //g2d.fillRect(0,-tankSize/8, tankSize, tankSize/4);
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
            this.alphaMove = this.alphaMove + Math.PI/10;
        }
        if (keys[2]){
            this.x = (int) (this.x - Math.cos(this.alphaMove)*this.move);
            this.y = (int) (this.y - Math.sin(this.alphaMove)*this.move);
        }
        if (keys[3]){
            this.alphaMove = this.alphaMove - Math.PI/10;
        }
    }
}