package AI;

import java.io.IOException;
import java.util.ArrayList;
import engine.*;
import exceptions.FullHandException;
public interface AIListener {
    public ArrayList<String> getPossibleMoves();
    public Reward getReward();
    public Game getClone() throws FullHandException;
    public boolean isGameOver();
    public int GameState();

}
