package AI;

import java.util.ArrayList;
import java.util.Random;
import engine.*;
import exceptions.FullHandException;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Mage;
import model.heroes.Priest;

public class mctsNode{
    private ArrayList<mctsNode> children;
    private int no_simulations;
    private Reward reward;
    public mctsNode parent;
    private Game game;
    private final int player;
    private ArrayList<Game> gameNodes;
//    public mctsNode(mctsNode parent,String command) {
//        this.game = (Game)game;
//        this.parent = parent;
//        children = new ArrayList<>();
//        no_simulations = 0;
//    //    this.player = (((Game)game).getCurrentHero()==((Game)game).getFirstHero())?1:2;
//        reward = new Reward(0,0);
//        this.gameNodes = game.getPossibleMoves();
//    	 this.player = (((Game)game).getCurrentHero()==((Game)game).getFirstHero())?1:2;
//    }
    public mctsNode(mctsNode parent, AIListener game){
        this.game = (Game)game;
        this.parent = parent;
        children = new ArrayList<mctsNode>();
        no_simulations = 0;
        this.player = (((Game)game).getCurrentHero()==((Game)game).getFirstHero())?1:2;
        reward = new Reward(0,0);
        gameNodes = new ArrayList<Game>();
        ArrayList<String > tmp = game.getPossibleMoves();
        for(int i=0;i<tmp.size();i++) {
        	try {
        		Game t = game.getClone();
        		PerformGameCommand(tmp.get(i), t);
        		this.gameNodes.add(t);
        		t.setGameCommand(tmp.get(i));
        	}catch (Exception e) {
        	}
        }
    }
    public mctsNode select() {
        mctsNode selectedNode = this;
        double max = Integer.MIN_VALUE;

        for (mctsNode child : getChildren()) {
            double uctValue = getUctValue(child);

            if (uctValue > max) {
                max = uctValue;
                selectedNode = child;
            }
        }

        return selectedNode;
    }
    public mctsNode expand(){
        if(gameNodes.size()==0)return this;
        int rand = (int)(Math.random()*gameNodes.size());
        mctsNode node = new mctsNode(this,gameNodes.get(rand));
        gameNodes.remove(rand);
        children.add(node);
        return node;
    }
    public void backProbagate(Reward reward){
        this.reward.addReward(reward);
        this.no_simulations++;
        if(parent!=null){
            parent.backProbagate(reward);
        }
    }
    private double getUctValue(mctsNode child) {
        double uctValue;
        if (child.getNo_simulations() == 0) {
            uctValue = 1;
        } else {
            uctValue
                    = (1.0 * child.getRewardForPlayer(getPlayer())) / (child.getNo_simulations() * 1.0)
                    + (Math.sqrt(2 * (Math.log(getNo_simulations() * 1.0) / child.getNo_simulations())));
        }

        Random r = new Random();
        uctValue += (r.nextDouble() / 10000000);
        return uctValue;
    }
    public ArrayList<mctsNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<mctsNode> childs) {
        this.children = childs;
    }

    public int getNo_simulations() {
        return no_simulations;
    }
    public double getRewardForPlayer(int player) {
        return reward.getRewardForPlayer(player);
    }
    public int getPlayer() {
        return player;
    }

    public void setNo_simulations(int no_simulations) {
        this.no_simulations = no_simulations;
    }

    public Reward getRewards() {

        return reward;
    }

    public void setRewards(Reward rewards) {
        this.reward = rewards;
    }

    public mctsNode getParent() {
        return parent;
    }

    public void setParent(mctsNode parent) {
        this.parent = parent;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
	public static void PerformGameCommand(String c,Game g)throws FullHandException {
		String command = c;
		//System.out.println(">>"+command);
		String[] commands = command.split(" ");
		if(commands[0].equals("PlayMinion")) {
			int Minionindex = Integer.parseInt(commands[1]);

			try {
				//System.out.println(">>"+g.getCurrentHero().getHand().get(Minionindex).getName() + " " +"_____ " +((Game) g.getCurrentHero().getListener()).getFirstHero().getCurrentHP());
				g.getCurrentHero().playMinion((Minion)g.getCurrentHero().getHand().get(Minionindex));
				//System.out.println("_________________________");
			}catch(Exception e) {
				//System.out.println(e.getMessage() + " " + e.getCause());
			}
		}
		else if(commands[0].equals("Castspell")) {
			int spellIndex = Integer.parseInt(commands[1]);
			Spell s = (Spell)g.getCurrentHero().getHand().get(spellIndex);
			//System.out.println(">>"+s.getName());
			if(s instanceof AOESpell) {
				try {
					g.getCurrentHero().castSpell((AOESpell)s, ((Game)g.getCurrentHero().getListener()).getOpponent().getField());
				}catch(Exception e) {}
			}else if(s instanceof FieldSpell) {
				try {
					g.getCurrentHero().castSpell((FieldSpell)s);
				}catch(Exception e) {}
			}else if(s instanceof HeroTargetSpell) {
				if(commands[3].equals("opp")) {
					try {
						g.getCurrentHero().castSpell((HeroTargetSpell)s,((Game)g.getCurrentHero().getListener()).getOpponent());
					}catch(Exception e) {}
				}else if(commands[3].equals("me")) {
					try {
						g.getCurrentHero().castSpell((HeroTargetSpell)s,g.getCurrentHero());
					}catch(Exception e) {}
				}
			}else if(s instanceof LeechingSpell) {
				int target = Integer.parseInt(commands[4]);
				try {
					g.getCurrentHero().castSpell((LeechingSpell)s,((Game)g.getCurrentHero().getListener()).getOpponent().getField().get(target));
				}catch(Exception e) {}
			}else if(s instanceof MinionTargetSpell) {
				int target = Integer.parseInt(commands[4]);
				try {
					g.getCurrentHero().castSpell((MinionTargetSpell)s,((Game)g.getCurrentHero().getListener()).getOpponent().getField().get(target));
				}catch(Exception e) {}
			}
		}
		else if(commands[0].equals("AttackMinion")) {
            
			int attacker = Integer.parseInt(commands[1]);
			int target = Integer.parseInt(commands[2]);
			//System.out.println(g.getCurrentHero().getField().get(attacker).getName()+"==> "+((Game)g.getCurrentHero().getListener()).getOpponent().getField().get(target).getName());
			try {
				g.getCurrentHero().attackWithMinion(g.getCurrentHero().getField().get(attacker), ((Game)g.getCurrentHero().getListener()).getOpponent().getField().get(target));
			}catch(Exception e) {}
			
		}
		else if(commands[0].equals("AttackHero")){
			int attacker = Integer.parseInt(commands[1]);
			try {
				g.getCurrentHero().attackWithMinion(g.getCurrentHero().getField().get(attacker), ((Game)g.getCurrentHero().getListener()).getOpponent());
			}catch(Exception e) {}
		}
		else if(commands[0].equals("endTurn")) {
		    try {
                g.getCurrentHero().endTurn();
            }catch (CloneNotSupportedException ex){};
		}
		else if(commands[0].equals("useHeroPower")){
		    if(commands.length==1){
                try {
                    g.getCurrentHero().useHeroPower();
                }catch(Exception e) {}
            }else if(commands[1].equals("curr")){
		        if(commands[2].equals("Hero")){
                    try {
                        ((Priest)g.getCurrentHero()).useHeroPower(g.getCurrentHero());
                    }catch(Exception e) {}
                }else{
		            int target = Integer.parseInt(commands[2]);
                    try {
                        ((Priest)g.getCurrentHero()).useHeroPower(g.getCurrentHero().getField().get(target));
                    }catch(Exception e) {}
                }
            }else if(commands[1].equals("opp")){
                if(commands[2].equals("Hero")){
                    try {
                        ((Mage)g.getCurrentHero()).useHeroPower(g.getOpponent());
                    }catch(Exception e) {}
                }else{
                    int target = Integer.parseInt(commands[2]);
                    try {
                        ((Mage)g.getCurrentHero()).useHeroPower(g.getOpponent().getField().get(target));
                    }catch(Exception e) {}
                }
            }
        }
		
	}
}
