package client;

import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.image.*;

public class Game implements Runnable {

    private SFrame frame;
    private SPanel panel;
    private Map map;
    private Player player;
    // private Player[] players = new Player[3];
    Dimension size = new Dimension(1600, 830);//Map de 40 par 20
    Graphics finalG = null;
    private Boolean[] activKey = { false, false, false, false };
    private double fpsTarget = 60;

    private int xMouse = 0, yMouse = 0;

    public Game() {
        this.initFrame();
        this.wait(100);
        this.initMap();
        this.initPlayer();
        // this.startGame();

        new Thread(this).start();
        this.display();
    }

    public void initFrame() {
        frame = new SFrame(size);
        frame.addKeyListener(new SKAdapter());
        frame.addMouseListener(new SMAdapter());
        frame.addMouseMotionListener(new SMAdapter());
        
        panel = frame.getPanel();
        finalG = panel.getGraphics();
    }

    public void initMap() {
        this.map = new Map(this.panel);
    }

    public void initPlayer() {
        player = new Player(500, 500, panel, map);
    }

    public void start() {
        wait(100);
        double finalDiff = 0;
        double previous = 0;
        while (true) {
            previous = System.currentTimeMillis();           
            
            //REFRESH
            this.update(finalDiff);

            double diff = (System.currentTimeMillis() - previous);
            if (diff < 1/(fpsTarget)*1000) {
                wait((int)(1/((double)fpsTarget)*1000-diff));
            }
            finalDiff = (System.currentTimeMillis()-previous)/(double)16;
        }
    }

    public void display(){
        double finalDiff = 0;
        double previous = 0;

        while(true){
            previous = System.currentTimeMillis();
            this.update(finalDiff);

            frame.update();

            Image image = new BufferedImage((int)size.getWidth(), (int)size.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = image.getGraphics();

            map.display(g);
            player.display(g);

            finalG.drawImage(image,0,0,(int)size.getWidth(), (int)size.getHeight(), null, null);

            double diff = (System.currentTimeMillis() - previous);
            if (diff < 1/(fpsTarget)*1000) {
                wait((int)(1/((double)fpsTarget)*1000-diff));
            }
            finalDiff = (System.currentTimeMillis()-previous)/(double)16;
        }
    }

    public void update(double diff){
        player.update(activKey, diff);
        player.setAlphaCanon2(xMouse, yMouse);
    }

	public void wait(int time){
        try{Thread.sleep(time);}
        catch (InterruptedException e) {}
    }
    
    @Override
    public void run() {
        // while (true) {
        //     wait(delay);
        //     player.setAlphaCanon2(xMouse, yMouse);
        // }
    }
    
    private class SKAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            switch(key){
            	case KeyEvent.VK_LEFT:
            		activKey[3]=true;
            		break;

            	case KeyEvent.VK_RIGHT:
            		activKey[1]=true;
            		break;

            	case KeyEvent.VK_UP:
            		activKey[0]=true;
            		break;

            	case KeyEvent.VK_DOWN:
            		activKey[2]=true;
                    break;

                case KeyEvent.VK_SPACE:
                    player.shot();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            switch(key){
            	case KeyEvent.VK_LEFT:
            		activKey[3]=false;
            		break;

            	case KeyEvent.VK_RIGHT:
            		activKey[1]=false;
            		break;

            	case KeyEvent.VK_UP:
            		activKey[0]=false;
            		break;

            	case KeyEvent.VK_DOWN:
            		activKey[2]=false;
            		break;
            }
        }

    }

    private class SMAdapter extends MouseAdapter{

        @Override
        public void mousePressed(MouseEvent e) {
            int button = e.getButton();
            if (button == MouseEvent.BUTTON1) {
                player.shot();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            xMouse = e.getX();
            yMouse = e.getY();
        }
    }

}