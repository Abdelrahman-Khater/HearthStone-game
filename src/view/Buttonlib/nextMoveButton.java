package view.Buttonlib;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class nextMoveButton extends JButton {

	public nextMoveButton() {
		this.setSize(100,100);
		this.setBorder(null);
		this.setContentAreaFilled(false);
		this.setIcon(new ImageIcon("res/Buttons/Next_release.png"));
		this.addMouseListener(new MouseListener() {
		
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
				    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/Next_release.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
				    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/Next_pressed.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
			    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/Next_release.png"));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
			}
			
	
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				 try {
					    
					    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/Next_release.png"));
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

