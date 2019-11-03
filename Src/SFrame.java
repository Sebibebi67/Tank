import javax.swing.JFrame;

import java.awt.*;

public class SFrame extends JFrame{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    SPanel panel;
	Dimension size = new Dimension(1600, 830);//Map de 40 par 20
	boolean isDecorated = true;

    public SFrame() {
        this.setTitle("Tanks");
	    //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setSize( (int)size.getWidth(), (int)size.getHeight());
	    this.setResizable(false);
	    this.setUndecorated(!isDecorated);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new SPanel();
        
        this.setContentPane(panel);

        this.setVisible(true);
    }
    
    public SPanel getPanel(){return panel;}

    public void update(){
        // panel.removeAll();
        panel.antilag();
        panel.display();
        this.validate();

    }
}