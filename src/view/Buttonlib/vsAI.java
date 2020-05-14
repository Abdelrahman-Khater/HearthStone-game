package view.Buttonlib;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class vsAI extends JButton {

	public vsAI() {
		this.setSize(150,50);
		this.setBorder(null);
		this.setContentAreaFilled(false);
		this.setIcon(new ImageIcon("res/Buttons/vsAI_released.png"));
		this.addMouseListener(new MouseListener() {
		
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
				    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/vsAI_released.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
				    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/vsAI_pressed.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
			    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/vsAI_released.png"));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
			}
			
	
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				 try {
					    
					    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/vsAI_released.png"));
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

