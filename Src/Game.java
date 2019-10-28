
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.MouseInfo;
import java.awt.Dimension;

// import org.w3c.dom.events.MouseEvent;



public class Game{

    private SFrame frame;
    private SPanel panel;
    private Player player;
    private Player[] players = new Player[3];
    private Boolean[] activKey = {false, false, false, false};
    private double alphaMouse;
    private int tankSize = 30;
    private int delay = 50;


    public Game(){
        this.initFrame();
        this.initPlayer();

        this.start();
    }


    public void initFrame(){
        frame = new SFrame();
        frame.addKeyListener(new SKAdapter());
        frame.addMouseListener(new SMAdapter());
        frame.addMouseMotionListener(new SMAdapter());
        panel = frame.getPanel();
    }

    public void initPlayer(){
        player = new Player(500, 500, panel);
    }

    public void start(){
        wait(delay);
        while (true){
            wait(delay);
            this.update();
            this.display();
        }
    }

    public void display(){
        frame.update();

        player.display(this.panel);
        
    }

    public void update(){
        player.update(activKey);
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
        public void mousePressed (MouseEvent e){
            int button = e.getButton();
            if (button == MouseEvent.BUTTON1){
               player.shot();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e){
            // int x = MouseInfo.getPointerInfo().getLocation().x;
            // int y = MouseInfo.getPointerInfo().getLocation().y;
            int x = e.getX();
            int y = e.getY();
            
            x = x - player.getX();
            y = y - player.getY()-30;
            // System.out.println(x+ " "+y);
            player.setAlphaCanon(Math.atan2(y, x));
        }
    }
}