package view.Buttonlib;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class HeroPower extends JButton{

	public HeroPower() {
		this.setSize(100,100);
		this.setContentAreaFilled(false);
		this.setBorder(null);
		this.setIcon(new ImageIcon("res/Buttons/heroPower_released.png"));
		this.addMouseListener(new MouseListener() {
		
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
							}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
				    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/heroPower_pressed.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
			    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/heroPower_released.png"));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
			}
			
	
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				 try {
					    
					    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/heroPower_hover.png"));
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
