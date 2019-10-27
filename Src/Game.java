
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

import org.w3c.dom.events.MouseEvent;



public class Game{

    private SFrame frame;
    private SPanel panel;
    private Player player;
    private Player[] players = new Player[3];
    private Boolean[] activKey = {false, false, false, false};
    private int tankSize = 30;
    private int delay = 50;
    private Graphics g1 = null;
    private Graphics g2 = null;
    private Graphics g3 = null;

    public Game(){
        this.initFrame();
        this.initPlayer();

        this.start();
    }


    public void initFrame(){
        frame = new SFrame();
        frame.addKeyListener(new SKAdapter());
        frame.addMouseListener(new SMAdapter());
        panel = frame.getPanel();
        g1 = panel.getGraphics();
        g2 = panel.getGraphics();
        g3 = panel.getGraphics();
    }

    public void initPlayer(){
        player = new Player(500, 500);
    }

    public void start(){
        wait(delay);
        while (true){
            wait(delay);
            this.move();
            this.display();
        }
    }

    public void display(){
        frame.update();
        g1.setColor(Color.BLACK);
        g1.fillRect(0, 0, 1300, 850);

        g2.setColor(Color.BLUE);

        AffineTransform rotate = AffineTransform.getRotateInstance(player.getAlphaMove(), player.getX(), player.getY());
        ((Graphics2D)g2).setTransform(rotate);
        g2.fillRect(player.getX()-tankSize/2, player.getY() - tankSize/2, tankSize, tankSize);
        
        g3.setColor(Color.RED);
        g3.fillRect(player.getX(), player.getY()-tankSize/8, tankSize, tankSize/4);
        
    }

    public void move(){
        player.move(activKey);
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
        public void mousPressed (MouseEvent e){
                // System.out.println("ok");
                int x = e.getScreenX();
                int y = e.getClientY();
                System.out.println(x);
                System.out.println(y);
        }
    }
}