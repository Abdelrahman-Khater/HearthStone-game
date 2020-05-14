package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


import view.Buttonlib.ExitButton;
import view.Buttonlib.MultiplayerB;
import view.Buttonlib.vsAI;

import javax.swing.*;

public class mainMenu extends States implements ActionListener {
private static final int WIDTH=1280;
private static final int HEIGHT=960;
private startGame start;
private startMultiGame startmulti;
private static BufferedImage img;
	public mainMenu() {
		
		super();
		int w=this.WIDTH;
		int h=this.HEIGHT;
		
		vsAI vsCPU=new vsAI();
		vsCPU.setActionCommand("VS AI");
		MultiplayerB vsHuman=new MultiplayerB();
		vsHuman.setActionCommand("MULTIPLAYER");
		ExitButton quit=new ExitButton();
		quit.setActionCommand("Exit");
		vsCPU.setSize(new Dimension(200,100));
		vsHuman.setSize(new Dimension(200,100));
		
		Font f=new Font("Arial",Font.BOLD,22);
		vsCPU.setLocation(w/2-100, h/2-150);

		vsHuman.setLocation(w/2-100, h/2);
		quit.setLocation(w/2-100, h/2+150);
		vsCPU.addActionListener(this);
		vsHuman.addActionListener(this);
		quit.addActionListener(this);
		File file=new File("res/back5.jpg");
		try{
			img=ImageIO.read(file);
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "An ERROR Occured while loading the background please restart the game");
			
		}
	      this.setContentPane(new JPanel() {
	          @Override
	          public void paintComponent(Graphics g) {
	             super.paintComponent(g);
	             g.drawImage(img, 0, 0, null);
	          }
	       });
//		this.createBufferStrategy(1);
//		Graphics g=this.getBufferStrategy().getDrawGraphics();
//		File file=new File("res/backGround.jpg");
//		BufferedImage img=null;
//		try{
//			img=ImageIO.read(file);
//		}catch(Exception e)
//		{
//			JOptionPane.showMessageDialog(null, "An ERROR Occured while loading the background please restart the game");
//			
//		}
		
		
		this.setContentPane(new ImagePanel(img, this));

		this.getContentPane().setLayout(null);
		this.getContentPane().add(vsCPU);
		this.getContentPane().add(vsHuman);
		this.getContentPane().add(quit);
		
		this.revalidate();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().contentEquals("VS AI"))
		{
		this.setVisible(false);
		 start=new startGame();
			
		}	
		
		else if(e.getActionCommand().contentEquals("MULTIPLAYER"))
			{
				//AppClient server = new AppClient();
			this.setVisible(false);
			startmulti = new startMultiGame();
			}	
				
		else if(e.getActionCommand().contentEquals("Exit"))
			System.exit(0);		
				
			
		
	}
	public void reportEnd() {
		
	}
}
