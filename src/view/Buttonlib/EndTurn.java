package view.Buttonlib;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class EndTurn extends JButton{
	
	public EndTurn() {
		this.setSize(150,50);
		this.setBorder(null);
		this.setContentAreaFilled(false);
		this.setIcon(new ImageIcon("res/Buttons/EndTurn_released.png"));
		this.addMouseListener(new MouseListener() {
		
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
				    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/EndTurn_hover.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
				    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/EndTurn_pressed.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
			    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/EndTurn_released.png"));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
			}
			
	
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				 try {
					    
					    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/EndTurn_hover.png"));
					  } catch (Exception ex) {
					    System.out.println(ex);
					  }
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		this.revalidate();
		
	}
	
}
