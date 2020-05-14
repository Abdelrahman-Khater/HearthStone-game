package AI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import engine.*;
import exceptions.FullHandException;

public class miniMaxNode implements Comparable<miniMaxNode>{
	Game g;
	//ArrayList<Game> childrenGames;
	ArrayList<miniMaxNode> children;
	PriorityQueue<miniMaxNode>p;
	final int BRANCHING_FACTOR = 4;
	public miniMaxNode(Game g) {
		this.g = g;
		children = new ArrayList<miniMaxNode>();
		//childrenGames = new ArrayList<Game>();
		p = new PriorityQueue<miniMaxNode>(Collections.reverseOrder());
	}
	public ArrayList<miniMaxNode>generateChildren(){
		ArrayList<String> moves = g.getPossibleMoves();
		for(int i=0;i<moves.size();i++) {
			try {

			Game tmp = g.getClone();
			tmp.setGameCommand(moves.get(i));
			p.add(new miniMaxNode(tmp));
			//System.out.println(tmp.getGameCommand());
			mctsNode.PerformGameCommand(moves.get(i), tmp);

			}catch (FullHandException ex) {}
		}
		int l = p.size();
		for(int i=0;i<Math.min(l, BRANCHING_FACTOR);i++) {
			children.add(new miniMaxNode(p.poll().g));
		}
		//p.clear();
		return children;
	}
	public int compareTo(miniMaxNode n) {
//		if(n.g.isGameOver()){
//			if(n.g.getFirstHero().getCurrentHP()==0)return 100000;
//			if(n.g.getSecondHero().getCurrentHP() == 0)return 0;
//		}
		return Integer.compare(this.g.getReward().getRewardForPlayer(2),
				n.g.getReward().getRewardForPlayer(2));
	}
}
