package client;

import entities.*;
import message.CSMessage;
import message.SCMessage;

import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Game implements Runnable {

    private SFrame frame;
    private SPanel panel;
    private Map map;
    private Player player;
    // private Player[] players = new Player[3];
    Dimension size = new Dimension(1600, 830);// Map de 40 par 20
    Graphics finalG = null;
    private Boolean[] activKey = { false, false, false, false };
    private Boolean activMouse;
    private double fpsTarget = 60;

    private Socket socket = null;
    private int id;
    private char[][] tab;
    private ArrayList<SCMessage> messages;

    private int xMouse = 0, yMouse = 0;

    public Game(Socket socket, int id, char[][] tab, ArrayList<SCMessage> messages) {
        this.socket = socket;
        this.id = id;
        this.tab = tab;
        this.messages = messages;

        this.initFrame();
        Player.initImage();
        this.wait(100);
        // this.initMap(tab);
        // this.initPlayer();
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

    public void initMap(char[][]tab) {
        this.map = new Map(tab);
    }

    public void initPlayer() {
        player = new Player(500, 500, map, id);
    }

    // public void start() {

    // }

    public void display() {
        // double finalDiff = 0;
        double previous = 0;

        while (true) {
            previous = System.currentTimeMillis();
            // this.update(finalDiff);

            frame.update();

            Image image = new BufferedImage((int) size.getWidth(), (int) size.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = image.getGraphics();

            // map.display(g);
            // player.display(g);

            Map.displayMap(g, tab);
            Player.displayPlayers(g, messages);

            finalG.drawImage(image, 0, 0, (int) size.getWidth(), (int) size.getHeight(), null, null);

            double diff = (System.currentTimeMillis() - previous);
            if (diff < 1 / (fpsTarget) * 1000) {
                wait((int) (1 / ((double) fpsTarget) * 1000 - diff));
            }
            // finalDiff = (System.currentTimeMillis()-previous)/(double)16;
        }
    }

    @Override
    public void run() {
        wait(100);
        
        try {
            
            double finalDiff = 0;
            double previous = 0;
            while (true) {
                previous = System.currentTimeMillis();

                
                // ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                // CSMessage csmessage = new CSMessage(activKey, activMouse, xMouse, yMouse, id);
                // out.writeObject(csmessage);
                //REFRESH

                this.update(finalDiff);
                
                double diff = (System.currentTimeMillis() - previous);
                if (diff < 1/(fpsTarget)*1000) {
                    wait((int)(1/((double)fpsTarget)*1000-diff));
                }
                finalDiff = (System.currentTimeMillis()-previous)/(double)16;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(double diff) throws IOException{
        
        // ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        
        // player.update(activKey, diff);
        // player.setAlphaCanon2(xMouse, yMouse);
    }

	public void wait(int time){
        try{Thread.sleep(time);}
        catch (InterruptedException e) {}
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
                // player.shot();
                activMouse = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int button = e.getButton();
            if (button == MouseEvent.BUTTON1) {
                // player.shot();
                activMouse = false;
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            xMouse = e.getX();
            yMouse = e.getY();
        }
    }

}