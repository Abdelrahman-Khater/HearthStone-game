package view.Buttonlib;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MultiplayerB extends JButton {

	public MultiplayerB() {
		this.setSize(150,50);
		this.setBorder(null);
		this.setContentAreaFilled(false);
		this.setIcon(new ImageIcon("res/Buttons/multiplayer_released.png"));
		this.addMouseListener(new MouseListener() {
		
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
				    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/multiplayer_released.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
				    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/multiplayer_pressed.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
			    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/multiplayer_released.png"));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
			}
			
	
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				 try {
					    
					    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/multiplayer_released.png"));
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

