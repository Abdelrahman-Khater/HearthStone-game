package AI;
import java.io.IOException;
import java.util.ArrayList;

import engine.*;
import exceptions.FullHandException;
import model.heroes.Hunter;
public class mctsPlayer extends Player {
    public int maxIterations;
    public mctsPlayer(int maxIterations)throws CloneNotSupportedException,IOException{
        this.maxIterations = maxIterations;
    }

    public String play(AIListener game) throws CloneNotSupportedException,IOException,FullHandException{
        mctsNode root = new mctsNode(null,game);

        for(int i=0;i<maxIterations;i++){
            Game g = game.getClone();
            mctsNode node = root.select();
            node.expand();
            Reward reward = rollOut((AIListener)g);
            node.backProbagate(reward);
        }
        mctsNode mostVisitedChild = null;
        for(mctsNode m:root.getChildren()){
            if(mostVisitedChild==null){
                mostVisitedChild = m;
            }else if(mostVisitedChild.getNo_simulations()<m.getNo_simulations())
                mostVisitedChild = m;
        }
        return mostVisitedChild.getGame().getGameCommand();
    }
    public Reward rollOut(AIListener g1) throws IOException,CloneNotSupportedException,FullHandException{
        Game g = g1.getClone();
       // bestMovePlayer p1 = new bestMovePlayer();
        miniMaxPlayer p1 = new miniMaxPlayer();
        ArrayList<String > temp=g.getPossibleMoves();
        for(int i=0;i<8&&!g.isGameOver();i++){
                mctsNode.PerformGameCommand(p1.play((AIListener)g),g);
        }
       return ((AIListener)g).getReward();
    }
    public static String playRand (AIListener g1) {
        ArrayList<String> arr = g1.getPossibleMoves();
        int rand = (int)(Math.random()*arr.size());
        String res = arr.get(rand);
        arr.remove(rand);
        return res;
    }

}
