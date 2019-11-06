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
import java.awt.Point;

public class Player {

    private int x, y;
    private double move;
    private double alphaMove;
    private double alphaCanon;
    private ArrayList<Shot> shots = new ArrayList<Shot>();
    private int tankSize = 30;
    private SPanel panel;
    private Graphics g = null;

    private Boolean[] canGo;
    private char[][] map;
    private int cellSize;

    private Image tank;
    private Image canon;

    public Player(int x, int y, SPanel panel, Map map){
        this.x = x;
        this.y = y;
        this.move = 5;
        this.alphaMove = 0;
        this.alphaCanon = 0;
        this.panel = panel;
        this.canGo = new Boolean[4];
        this.map = map.getTab();
        this.cellSize = map.getCellSize();
        g = panel.getGraphics();
        
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

        g.setColor(Color.BLACK);
        //g.fillRect(this.x-2*tankSize, this.y - 2*tankSize, tankSize*4, tankSize*4);
        
        for (int i = 0; i<shots.size(); i++){
            shots.get(i).display();
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
            shots.get(i).update();
        }
    }

    public void move(Boolean[] keys, double diff){
        // for (int i = 0 ; i<4 ; i++){this.canGo[i] = true;}
        // checkStraightCollision(diff);

        if (keys[0] && canGoFront(diff)){
            this.x = (int) (this.x + Math.cos(this.alphaMove)*this.move*diff);
            this.y = (int) (this.y + Math.sin(this.alphaMove)*this.move*diff);
        }

        if (keys[2] && canGoBack(diff)){
            this.x = (int) (this.x - Math.cos(this.alphaMove)*this.move*diff);
            this.y = (int) (this.y - Math.sin(this.alphaMove)*this.move*diff);
        }

        // checkRotateCollision(diff);

        if (keys[1] && canGoLeft(diff)){
            this.alphaMove = this.alphaMove + Math.PI/20*diff;
        }

        if (keys[3] && canGoRight(diff)){
            this.alphaMove = this.alphaMove - Math.PI/20*diff;
        }
    }

    /*
    Help to understand the 4th next methods

    A       B
    ---------
    |       |   =>
    |       |   =>
    ---------
    C       D


    */

    public boolean canGoFront(double diff){
        double norme = Math.sqrt( 1.0/2.0)*tankSize+move*diff;

        Point B = new Point((int) (x+ norme*Math.cos(alphaMove)) /cellSize, (int)  (y - norme*Math.sin(alphaMove)) /cellSize );
        Point D = new Point((int) (x+ norme*Math.cos(alphaMove)) /cellSize, (int)  (y + norme*Math.sin(alphaMove)) /cellSize );

        if (map[B.y][B.x] == 'W' || map[D.y][D.x] == 'W'){
            return false;
        }
        return true;
    }

    public boolean canGoBack(double diff){
        double norme = Math.sqrt( 1.0/2.0)*tankSize+move*diff;

        Point A = new Point((int) (x- norme*Math.cos(alphaMove)) /cellSize, (int)  (y - norme*Math.sin(alphaMove)) /cellSize );
        Point C = new Point((int) (x- norme*Math.cos(alphaMove)) /cellSize, (int)  (y + norme*Math.sin(alphaMove)) /cellSize );

        if (map[A.y][A.x] == 'W' || map[C.y][C.x] == 'W'){
            return false;
        }

        return true;
    }

    public boolean canGoLeft(double diff){
        double norme = Math.sqrt( 1.0/2.0)*tankSize;

        Point B = new Point((int) (x+ norme*Math.cos(alphaMove+Math.PI/20*diff)) /cellSize, (int)  (y - norme*Math.sin(alphaMove+Math.PI/20*diff)) /cellSize );
        Point C = new Point((int) (x- norme*Math.cos(alphaMove+Math.PI/20*diff)) /cellSize, (int)  (y + norme*Math.sin(alphaMove+Math.PI/20*diff)) /cellSize );

        if (map[B.y][B.x] == 'W' || map[C.y][C.x] == 'W'){
            return false;
        }
        return true;
    }

    public boolean canGoRight(double diff){
        double norme = Math.sqrt( 1.0/2.0)*tankSize;

        Point A = new Point((int) (x- norme*Math.cos(alphaMove-Math.PI/20*diff)) /cellSize, (int)  (y - norme*Math.sin(alphaMove-Math.PI/20*diff)) /cellSize );
        Point D = new Point((int) (x+ norme*Math.cos(alphaMove-Math.PI/20*diff)) /cellSize, (int)  (y + norme*Math.sin(alphaMove-Math.PI/20*diff)) /cellSize );

        if (map[A.y][A.x] == 'W' || map[D.y][D.x] == 'W'){
            return false;
        }
        return true;
    }

//     public void checkStraightCollision(double diff){

//         //XFront = coordonnées x à l'avant || xBack = coordonées x à l'arrière
//         int xFront = (int)( (x+ (tankSize/2+move*diff)*Math.cos(alphaMove)) /cellSize );
//         int xBack = (int)( (x- (tankSize/2+move*diff)*Math.cos(alphaMove)) /cellSize );

//         //YRight || YLeft
//         int yRight = (int)( (y+ (tankSize/2+move*diff)*Math.sin(alphaMove)) /cellSize );
//         int yLeft = (int)( (y- (tankSize/2+move*diff)*Math.sin(alphaMove)) /cellSize );

//         if(map[yRight][xFront] == 'W' || map[yLeft][xFront] == 'W'){ //Front
//             this.canGo[0] = false;
//         }
//         if(map[yRight][xBack] == 'W' || map[yLeft][xBack] == 'W'){ //Back
//             this.canGo[2] = false;
//         }
        
//     }

//     public void checkRotateCollision(double diff){
//         //XFront = coordonnées x à l'avant || xBack = coordonées x à l'arrière
//         int xFront = (int)( (x+ (tankSize/2)*Math.cos(alphaMove+Math.PI/20*diff)) /cellSize );
//         int xBack = (int)( (x- (tankSize/2)*Math.cos(alphaMove+Math.PI/20*diff)) /cellSize );

//         //YRight || YLeft
//         int yRight = (int)( (y+ (tankSize/2)*Math.sin(alphaMove+Math.PI/20*diff)) /cellSize );
//         int yLeft = (int)( (y- (tankSize/2)*Math.sin(alphaMove+Math.PI/20*diff)) /cellSize );

//         if(map[yRight][xFront] == 'W' || map[yLeft][xBack] == 'W'){ //Right
//             this.canGo[1] = false;
//         }

//                 // //XFront = coordonnées x à l'avant || xBack = coordonées x à l'arrière
//                 // int xFront = (int)( (x+ (tankSize/2)*Math.cos(alphaMove+Math.PI/20*diff)) /cellSize );
//                 // int xBack = (int)( (x- (tankSize/2)*Math.cos(alphaMove+Math.PI/20*diff)) /cellSize );
        
//                 // //YRight || YLeft
//                 // int yRight = (int)( (y+ (tankSize/2)*Math.sin(alphaMove+Math.PI/20*diff)) /cellSize );
//                 // int yLeft = (int)( (y- (tankSize/2)*Math.sin(alphaMove+Math.PI/20*diff)) /cellSize );
//         if(map[yLeft][xFront] == 'W' || map[yRight][xBack] == 'W'){ //Left
//             this.canGo[3] = false;
//         }
// // not achieved -> 4 functions CanGo -> to calculate X et Y
//     }
}