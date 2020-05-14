package AI;

import java.util.*;

public class Reward {
    public HashMap<Integer, Integer> rewards = new HashMap<>();
    public Reward(int reward1, int reward2){
        rewards.put(1, reward1);
        rewards.put(2, reward2);
    }

    public HashMap<Integer, Integer> getReward(){
        return rewards;
    }

    public int getRewardForPlayer(int playerNumber){
        return rewards.get(playerNumber);
    }

    public void addReward(Reward reward) {
        rewards.put(1, rewards.get(1) + reward.getRewardForPlayer(1));
        rewards.put(2, rewards.get(2) + reward.getRewardForPlayer(2));
    }
}