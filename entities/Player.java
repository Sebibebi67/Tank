package entities;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import message.SCMessage;

import java.awt.Image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.awt.RenderingHints;
// import java.awt.Shape;
import java.awt.Rectangle;

public class Player {

    private double x, y;
    private double move;
    private double alphaMove;
    private double alphaCanon;
    private ArrayList<Shot> shots = new ArrayList<Shot>();
    private static double tankSize = 30;
    private Map map;
    private int id;

    private static Image tank;
    private static Image canon;

    public Player(double x, double y, Map map, int id) {
        this.x = x;
        this.y = y;
        this.move = 2;
        this.alphaMove = 0;
        this.alphaCanon = 0;
        this.map = map;
        this.id = id;

        try {
            tank = ImageIO.read(new File("./ressources/tanks/tank.png"));
            canon = ImageIO.read(new File("./ressources/tanks/canon.png"));
        } catch (IOException exc) {
            System.out.println("Image loading error");
            exc.printStackTrace();
        }
    }

    public static void displayPlayers(Graphics g, ArrayList<SCMessage> messages) {
        g.setColor(Color.BLACK);


        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        for (int i = 0; i < messages.size(); i++) {

            Shot.displayShots(g, messages.get(i).getShotsPos());

            g2d.translate(messages.get(i).getXTank(), messages.get(i).getYTank());
            g2d.rotate(messages.get(i).getAlphaMove());

            g2d.drawImage(tank, (int) -tankSize / 2, (int) -tankSize / 2, (int) tankSize, (int) tankSize, null, null);
            g2d.rotate(messages.get(i).getAlphaCanon() - messages.get(i).getAlphaMove());
            g2d.drawImage(canon, (int) -tankSize / 2, (int) -tankSize / 2, (int) tankSize, (int) tankSize, null, null);

            g2d.setTransform(old);
            RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHints(rh);

        }


    }

    public void display(Graphics g){

        g.setColor(Color.BLACK);
        
        for (int i = 0; i<shots.size(); i++){
            shots.get(i).display(g);
        }
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform old = g2d.getTransform();

        // Change this into g2d.transform
        g2d.translate(this.x, this.y);
        g2d.rotate(this.alphaMove);

        g2d.drawImage(tank,(int)-tankSize/2,(int)-tankSize/2, (int)tankSize, (int)tankSize, null, null);
        g2d.rotate(this.alphaCanon-this.alphaMove);
        g2d.drawImage(canon,(int)-tankSize/2,(int)-tankSize/2, (int)tankSize, (int)tankSize, null, null);

        g2d.setTransform(old);
        RenderingHints rh = new RenderingHints(
             RenderingHints.KEY_TEXT_ANTIALIASING,
             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
    }

    public double getX(){return this.x;}
    public double getY(){return this.y;}
    public double getAlphaMove(){return this.alphaMove;}
    public double getAlphaCanon(){return this.alphaCanon;}
    public int getId(){return this.id;}
    /*public getShots(){
        Array
    }*/

    public void setAlphaCanon(double alpha){this.alphaCanon = alpha;}
    public void setAlphaCanon2(int xMouse, int yMouse){
        this.alphaCanon = Math.atan2(yMouse - this.y -30, xMouse - this.x);
    }

    public void shot(){
        Shot shot = new Shot(this.x, this.y, this.alphaCanon);
        shots.add(shot);
    }

    public void update(Boolean[] keys, double diff){
        move(keys, diff);
        for (int i = 0; i<shots.size(); i++){
            shots.get(i).update(diff);
            if (map.wallCollision(shots.get(i).getShape())){
                shots.remove(i);
            }
        }
    }



    public void move(Boolean[] keys, double diff){

        if (keys[0] && canGoFront(diff)){
            this.x = this.x + Math.cos(this.alphaMove)*this.move*diff;
            this.y = this.y + Math.sin(this.alphaMove)*this.move*diff;
        }

        if (keys[2] && canGoBack(diff)){
            this.x =this.x - Math.cos(this.alphaMove)*this.move*diff;
            this.y =this.y - Math.sin(this.alphaMove)*this.move*diff;
        }

        if (keys[1] && canGoLeft(diff)){
            this.alphaMove = this.alphaMove + Math.PI/20*diff;
        }

        if (keys[3] && canGoRight(diff)){
            this.alphaMove = this.alphaMove - Math.PI/20*diff;
        }
    }

    public boolean canGoFront(double diff){
        Rectangle rectPlayer = new Rectangle   ((int) (this.x + Math.cos(this.alphaMove)*this.move*diff - tankSize/2),
                                                (int) (this.y + Math.sin(this.alphaMove)*this.move*diff - tankSize/2),
                                                (int)tankSize, (int)tankSize);
        return !map.wallCollision(rectPlayer);
    }

    public boolean canGoBack(double diff){
        Rectangle rectPlayer = new Rectangle   ((int) (this.x - Math.cos(this.alphaMove)*this.move*diff - tankSize/2),
                                                (int) (this.y - Math.sin(this.alphaMove)*this.move*diff - tankSize/2),
                                                (int)tankSize, (int)tankSize);
        return !map.wallCollision(rectPlayer);
    }

    public boolean canGoLeft(double diff){
        // AffineTransform tx = new AffineTransform();
        // tx.rotate(this.alphaMove + Math.PI/20*diff, this.x, this.y);
        // Rectangle shape = new Rectangle((int) (this.x - tankSize/2), (int) (this.y - tankSize/2), tankSize, tankSize);
        // Shape newShape = tx.createTransformedShape(shape);
        // return !map.wallCollision(newShape);
        return true;
    }

    public boolean canGoRight(double diff){
        // AffineTransform tx = new AffineTransform();
        // tx.rotate(this.alphaMove - Math.PI/20*diff, this.x, this.y);
        // Rectangle shape = new Rectangle((int) (this.x - tankSize/2), (int) (this.y - tankSize/2), tankSize, tankSize);
        // Shape newShape = tx.createTransformedShape(shape);
        // return !map.wallCollision(newShape);
        return true;
    }
}