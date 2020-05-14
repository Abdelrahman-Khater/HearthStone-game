package AI;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;
import engine.*;
import model.heroes.Hunter;
public class miniMaxPlayer extends Player{
	final int DEPTH = 6;
	public miniMaxPlayer() throws CloneNotSupportedException,IOException{
	}
	public static miniMaxNode miniMaxAlg(miniMaxNode n,boolean t,int k) {
		PriorityQueue<miniMaxNode>p;
		if(k<=0||n.g.isGameOver()) {
			return n;
		}
		if(t) {
			p = new PriorityQueue<>(Collections.reverseOrder());
		}else {
			p = new PriorityQueue<miniMaxNode>();
		}
		n.generateChildren();
		for(int i=0;i<n.children.size();i++) {
			miniMaxNode m = miniMaxAlg(n.children.get(i), (n.children.get(i).equals("endTurn")?!t:t),k-1);
			if(m.g.isGameOver()) {
				m.g.setGameCommand(n.children.get(i).g.getGameCommand());
				if(t && n.g.getFirstHero().getCurrentHP()==0)return m;
			}
			m.g.setGameCommand(n.children.get(i).g.getGameCommand());
			p.add(m);
		}
		//if(p.size()==0)return n;
		miniMaxNode res  = new miniMaxNode(p.poll().g);
		return res;
	}
	public String play(AIListener g) {
		miniMaxNode n = new miniMaxNode((Game)g);
		miniMaxNode res = miniMaxAlg(n, true, DEPTH);
		//System.out.println(n.g.isGameOver());
		return res.g.getGameCommand();
	}
	public static void main(String[] args) throws Exception{
		Player p1 = new miniMaxPlayer();
		Game g = new Game(new Hunter(),new Hunter());
		for(int i=0;i<100;i++) {
			try {
				String s = p1.play(g);
				System.out.println(s);
			mctsNode.PerformGameCommand(s, g);
			System.out.println(s);
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		miniMaxNode n = new miniMaxNode(g);
	}
}
