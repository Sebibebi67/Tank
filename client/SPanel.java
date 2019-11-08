package client;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.*;

public class SPanel extends JPanel{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SPanel() {
        super();
        this.setBackground(Color.BLACK);

    }

    public void display(){
        this.antilag();
        
    }
    
   	public void antilag(){
		Toolkit.getDefaultToolkit().sync(); //Pour éviter le lag
	}


    
}