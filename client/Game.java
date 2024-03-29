package client;

import entities.*;
import message.*;

import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
// import java.net.Socket;
import java.util.ArrayList;

public class Game implements Runnable {

    private SFrame frame;
    private SPanel panel;
    // private Map map;
    // private Player player;
    // private Player[] players = new Player[3];
    Dimension size = new Dimension(1600, 830);// Map de 40 par 20
    Graphics finalG = null;
    private volatile Boolean[] activKey = { false, false, false, false };
    private volatile Boolean activMouse = false;
    private double fpsTarget = 30;

	// private Socket socket = null;
	private ObjectInputStream in;
	private ObjectOutputStream out;
    private int id;
    private char[][] tab;
    private volatile ArrayList<SCMessage> messages;

    private int xMouse = 0, yMouse = 0;

    public Game(ObjectInputStream in, ObjectOutputStream out, int id, char[][] tab, ArrayList<SCMessage> messages) {
		this.in = in;
		this.out = out;
        this.id = id;
        this.tab = tab;
        this.messages = messages;

        //System.out.println("Players : "+messages.size());

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

    // public void initMap(char[][]tab) {
    //     this.map = new Map(tab);
    // }

    // public void initPlayer() {
    //     player = new Player(500, 500, map, id);
    // }

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

            //System.out.println("display : "+diff);
                // finalDiff = (System.currentTimeMillis()-previous)/(double)16;
        }
        // messages = new CSMessage(activKey, activMouse, xMouse, yMouse, id, finalDiff);

    }

    @Override
    public void run() {
        wait(100);
        
        try {
            
            double finalDiff = 0;
            double previous = 0;

            CSMessage csmessage = new CSMessage(activKey, activMouse, xMouse, yMouse, id, finalDiff);
            out.writeObject(csmessage);

            while (true) {
                previous = System.currentTimeMillis();

                // System.out.println(activKey[0]+" "+activKey[1]+" "+activKey[2]+" "+activKey[3]+" "+activMouse+" "+xMouse+" "+yMouse+" "+id+" "+finalDiff);
                csmessage = new CSMessage(activKey, activMouse, xMouse, yMouse, id, finalDiff);
                out.writeObject(csmessage);
                out.flush(); 
                System.out.println("input "+(System.currentTimeMillis()-previous));
                //REFRESH

                // this.update(finalDiff);
                

                //double previous1 = System.currentTimeMillis();
                //System.out.println("1 "+previous1);
                Object o = in.readObject();
                //System.out.println("input "+(System.currentTimeMillis()-previous1));
                if (o instanceof ArrayList<?>) {
                    // Get the List.
                    ArrayList<?> al = (ArrayList<?>) o;
                    if (al.size() > 0) {
                        messages = new ArrayList<>();
                        for (int i = 0; i < al.size(); i++) {
                            // Still not enough for a type.
                            Object obj = al.get(i);
                            if (obj instanceof SCMessage) {
                                // Here we go!
                                SCMessage s = (SCMessage) obj;
                                messages.add(s);
                            }
                        }
                    }
                }

                double diff = (System.currentTimeMillis() - previous);

                if (diff < 1/(fpsTarget)*1000) {
                    wait((int)(1/((double)fpsTarget)*1000-diff));
                }
                finalDiff = (System.currentTimeMillis()-previous)/(double)16;
            
                //System.out.println("update : "+diff);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(double diff) throws IOException{
        
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
                    // player.shot();
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