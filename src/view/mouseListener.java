package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mouseListener implements ActionListener{
private mainMenu mainMenu;
private startGame start;
private startMultiGame startmulti;
	
	
	 public mouseListener() {
	  Controller control=new Controller();
	  mainMenu=control.getGameView();
	  start=new startGame();
	  startmulti = new startMultiGame();
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().contentEquals("vs CPU"))
		{
		 mainMenu.setVisible(false);
		 start.setVisible(true);		
		}	
		
		else if(e.getActionCommand().contentEquals("MULTIPLAYER"))
			{
			mainMenu.setVisible(false);
			startmulti.setVisible(false);
			}	
				
		else if(e.getActionCommand().contentEquals("Exit"))
			System.exit(0);		
				
			
		
	}

}
