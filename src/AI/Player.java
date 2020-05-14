package AI;
import java.io.IOException;
import java.util.ArrayList;

import model.cards.*;
import model.cards.minions.*;
import model.cards.spells.*;
import engine.*;
import exceptions.FullHandException;
import model.heroes.*;
public abstract class Player extends Hunter{
    public Player() throws IOException, CloneNotSupportedException {
		super();
	}
	public abstract String play(AIListener g)throws IOException,CloneNotSupportedException,FullHandException;
	

public void PerformGameCommand(String c)throws FullHandException {
	String command = c;
	//System.out.println(">>"+command);
	String[] commands = command.split(" ");
	if(commands[0].equals("PlayMinion")) {
		int Minionindex = Integer.parseInt(commands[1]);

		try {
			//System.out.println(">>"+this.getHand().get(Minionindex).getName() + " " +"_____ " +((Game) this.getListener()).getFirstHero().getCurrentHP());
			this.playMinion((Minion)this.getHand().get(Minionindex));
			//System.out.println("_________________________");
		}catch(Exception e) {
			//System.out.println(e.getMessage() + " " + e.getCause());
		}
	}
	else if(commands[0].equals("Castspell")) {
		int spellIndex = Integer.parseInt(commands[1]);
		Spell s = (Spell)this.getHand().get(spellIndex);
		//System.out.println(">>"+s.getName());
		if(s instanceof AOESpell) {
			try {
				this.castSpell((AOESpell)s, ((Game)this.getListener()).getOpponent().getField());
			}catch(Exception e) {}
		}else if(s instanceof FieldSpell) {
			try {
				this.castSpell((FieldSpell)s);
			}catch(Exception e) {}
		}else if(s instanceof HeroTargetSpell) {
			if(commands[3].equals("opp")) {
				try {
					this.castSpell((HeroTargetSpell)s,((Game)this.getListener()).getOpponent());
				}catch(Exception e) {}
			}else if(commands[3].equals("me")) {
				try {
					this.castSpell((HeroTargetSpell)s,this);
				}catch(Exception e) {}
			}
		}else if(s instanceof LeechingSpell) {
			int target = Integer.parseInt(commands[4]);
			try {
				this.castSpell((LeechingSpell)s,((Game)this.getListener()).getOpponent().getField().get(target));
			}catch(Exception e) {}
		}else if(s instanceof MinionTargetSpell) {
			int target = Integer.parseInt(commands[4]);
			try {
				this.castSpell((MinionTargetSpell)s,((Game)this.getListener()).getOpponent().getField().get(target));
			}catch(Exception e) {}
		}
	}
	else if(commands[0].equals("AttackMinion")) {

		int attacker = Integer.parseInt(commands[1]);
		int target = Integer.parseInt(commands[2]);
		//System.out.println(this.getField().get(attacker).getName()+"==> "+((Game)this.getListener()).getOpponent().getField().get(target).getName());
		try {
			this.attackWithMinion(this.getField().get(attacker), ((Game)this.getListener()).getOpponent().getField().get(target));
		}catch(Exception e) {}

	}
	else if(commands[0].equals("AttackHero")){
		int attacker = Integer.parseInt(commands[1]);
		try {
			this.attackWithMinion(this.getField().get(attacker), ((Game)this.getListener()).getOpponent());
		}catch(Exception e) {}
	}
	else if(commands[0].equals("endTurn")) {
		try {
			this.endTurn();
		}catch (CloneNotSupportedException ex){};
	}
	else if(commands[0].equals("useHeroPower")){
		if(commands.length==1){
			try {
				this.useHeroPower();
			}catch(Exception e) {}
		}
	}

}

	public static void main(String[] args) throws Exception{
		Player p1 = new mctsPlayer(10);
		Game g = new Game(new Mage(), new Hunter());
		for(int i=0;i<20&&!g.isGameOver();i++) {
		String s = "";
		
		do{
			s = p1.play(g);
			
			mctsNode.PerformGameCommand(s,g);
			System.out.println(s);
		}while(!s.equals("endTurn"));
	}
	}
}