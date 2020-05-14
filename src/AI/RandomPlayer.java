package AI;

import java.util.ArrayList;

import engine.Game;

import java.io.IOException;
import java.util.*;
public class RandomPlayer extends Player{
    public RandomPlayer() throws CloneNotSupportedException,IOException{
    	
    }
    public String play (AIListener g1) {
        ArrayList<String> arr = g1.getPossibleMoves();
        if(g1.isGameOver())return "GameOver";
        int rand = (int)(Math.random()*arr.size());
        String res = arr.get(rand);
        arr.remove(rand);
        return res;
    }

}
