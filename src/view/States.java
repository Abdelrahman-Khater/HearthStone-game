package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class States extends JFrame {
	private static final int WIDTH=1280;
	private static final int HEIGHT=960;
	
	public States() {
        super("HearthStone");
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = this.WIDTH;
        int h = this.HEIGHT;
        System.out.println(dim.height);
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        this.setLocation(x, y);
        
     	this.setSize(w, h);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
}
}
