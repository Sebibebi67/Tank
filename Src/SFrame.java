import javax.swing.JFrame;
import java.awt.*;

public class SFrame extends JFrame{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // Panel panel;
	Dimension size = new Dimension(1200, 830);
	boolean isDecorated = true;

    public SFrame() {
        this.setTitle("Tanks");
	    //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setSize( (int)size.getWidth(), (int)size.getHeight());
	    this.setResizable(false);
	    this.setUndecorated(!isDecorated);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    // panel = new Panel(size, isDecorated);

	    // this.setContentPane(panel);
        this.setVisible(true);
        System.out.println("ok");
	}
}