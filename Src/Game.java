import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Game implements Runnable{

    private SFrame frame;
    private SPanel panel;
    private Map map;
    private Player player;
    // private Player[] players = new Player[3];
    private Boolean[] activKey = {false, false, false, false};
    private int delay = 50;

    private int xMouse = 0, yMouse = 0;


    public Game(){
        this.initFrame();
        this.wait(delay);
        this.initMap();
        this.initPlayer();

        // this.start();
    }


    public void initFrame(){
        frame = new SFrame();
        frame.addKeyListener(new SKAdapter());
        frame.addMouseListener(new SMAdapter());
        frame.addMouseMotionListener(new SMAdapter());
        panel = frame.getPanel();
    }

    public void initMap(){
        this.map = new Map(this.panel);
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