package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ImagePanel extends JComponent {
	private BufferedImage img;
	private  mainMenu mainMenu=null;
	private startGame start=null;
	private battleField field=null;
	
	public ImagePanel( BufferedImage img,States window) {
		window.createBufferStrategy(1);
		this.img=img;
		if(window instanceof mainMenu)
		this.mainMenu=(mainMenu)window;
		else if(window instanceof startGame)
		this.start=(startGame)window;	
		else if(window instanceof battleField)
		this.field = (battleField)window;
	}
	
	protected void paintComponent( Graphics g) {
		super.paintComponent(g);
		if(mainMenu!=null)
			g.drawImage(img, 0, 0, mainMenu);
		else if(start!=null)
			g.drawImage(img, 0, 0, start);
		else if(field!=null)
			g.drawImage(img, 0, 0, field);
	}

}
