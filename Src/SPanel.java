import javax.swing.JPanel;
import java.awt.Color;
import java.awt.*;

public class SPanel extends JPanel{

    public SPanel(){
        super();
        this.setBackground(Color.BLACK);
    }

    public void display(){
        // repaint();
        setBackground(Color.BLACK);
        
    }
    
   	public void antilag(){
		Toolkit.getDefaultToolkit().sync(); //Pour éviter le lag
	}


    
}