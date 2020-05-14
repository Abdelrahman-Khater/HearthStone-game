package AI;

import java.io.IOException;
import java.util.ArrayList;
import engine.*;
public class bestMovePlayer extends Player{

	public bestMovePlayer() throws CloneNotSupportedException,IOException {
		
	}
	public String play(AIListener g) {
		ArrayList<String> tmp = g.getPossibleMoves();
		//System.out.println(tmp.size());
		 ArrayList<Game > moves = new ArrayList<Game>();
	        for(int i=0;i<tmp.size();i++) {
	        	
	        	try {
	        		Game t = ((Game)g).getClone();
	        		mctsNode.PerformGameCommand(tmp.get(i), t);
	        		moves.add(t);
	        		t.setGameCommand(tmp.get(i));
	        	}catch (Exception e) {
	        	}
	        }
	        int max = 0;
	        int ind = 0;
//	        Game t = (Game)g;
//	        try {
//	        	 t = ((Game)g).getClone();
//	        	moves.add(t);
//	        	t.endTurn();
//	        }catch (Exception e) {
//	        	System.out.println(t.getOpponent().getCurrentHP());
//			}
	        for(int i=0;i<moves.size();i++) {
	        	if(((Game)g).getFirstHero()==((Game)g).getCurrentHero()) {
	        	if(moves.get(i).getReward().getRewardForPlayer(1)>max) {
	        		max = moves.get(i).getReward().getRewardForPlayer(1);
	        		ind = i;
	        	}
	        }else{
	        	
	        	if(moves.get(i).getReward().getRewardForPlayer(2)>max) {
	        		max = moves.get(i).getReward().getRewardForPlayer(2);
	        		ind = i;
	        	}
	        	}
	       }
	       

	        
	        return moves.get(ind).getGameCommand();
	}
}
