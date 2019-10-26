
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Game{

    private SFrame frame;
    private SPanel panel;
    private Player player;
    private Player[] players = new Player[3];
    private Boolean[] activKey = {false, false, false, false};
    private int tankSize = 30;
    private int delay = 50;
    private Graphics g = null;

    public Game(){
        this.initFrame();
        this.initPlayer();

        this.start();
    }


    public void initFrame(){
        frame = new SFrame();
        frame.addKeyListener(new TAdapter());
        panel = frame.getPanel();
        g = panel.getGraphics();
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
        g.setColor(Color.BLUE);
        g.fillRect(player.getX()-tankSize/2, player.getY() - tankSize/2, tankSize, tankSize);
        
    }

    public void move(){
        player.move(activKey);
    }

	public void wait(int time){
		try{
			Thread.sleep(time);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    
    private class TAdapter extends KeyAdapter {

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
}