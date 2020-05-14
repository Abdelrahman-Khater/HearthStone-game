package engine;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.KillCommand;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.ShadowWordDeath;
import model.cards.spells.Spell;
import model.heroes.*;

import java.io.IOException;
import java.util.ArrayList;

import AI.AIListener;
import AI.Player;
import AI.Reward;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;

public class Game implements ActionValidator,HeroListener , AIListener{

    private Hero firstHero;
    private Hero secondHero;
    private Hero currentHero;
    private Hero opponent;
    private GameListener listener;
    /////////////// for AI ///////////////////////////////////
    String GameCommand = "";
    //////////////////////////////////////////////////////////
    //AI Listener
    Reward reward = new Reward(0, 0);
    boolean gameover = false;
    boolean sim = false;
    public Game(Hero p1, Hero p2) throws IOException, FullHandException, CloneNotSupportedException {
    	 gameover = false;
        this.firstHero = p1;
        this.secondHero = p2;

        int c = (int) (2*Math.random());

        if(c==0){
            this.currentHero = firstHero;
            this.currentHero.setTotalManaCrystals(1);
            this.currentHero.setCurrentManaCrystals(1);
            this.opponent = secondHero;
        }else{
           this.currentHero = secondHero;
           this.opponent = firstHero;
            this.currentHero.setTotalManaCrystals(1);
            this.currentHero.setCurrentManaCrystals(1);
        }
        for(int i=0;i<3;i++) {
        	currentHero.drawCard();
        }for(int i=0;i<4;i++) {
        	opponent.drawCard();
        }
        currentHero.setListener(this);
        opponent.setListener(this);
        currentHero.setValidator(this);
        opponent.setValidator(this);
        
        
        //test
//        Minion temp1=new Minion("Stonetusk Boar",0,Rarity.BASIC,1,1,false,false,false);
//        temp1.setListener(opponent);
//        opponent.getField().add(temp1);
//        
//        
        
//        Minion temp=new Minion("Stonetusk Boar",0,Rarity.BASIC,1,1,false,false,false);
//        temp.setListener(currentHero);
//        Minion temp2=new Minion("Stonetusk Boar",0,Rarity.BASIC,1,1,false,false,false);
//        temp2.setListener(currentHero);
//        currentHero.getHand().add(temp2);
//        currentHero.getHand().add(temp);
//        currentHero.getHand().add(new KillCommand());   
//          currentHero.getHand().add(new KillCommand());   
//        currentHero.getHand().add(new KillCommand());   
//        currentHero.getHand().add(new KillCommand());   
//        currentHero.getHand().add(new KillCommand());   
//         Minion t=new Minion("Stonetusk Boar",0,Rarity.BASIC,1,1,true,true,false);
//         t.setListener(currentHero);
        //  currentHero.getField().add(t);
        
    }

	public Hero getCurrentHero() {
		return currentHero;
	}

	public Hero getOpponent() {
		return opponent;
	}

	@Override
	public void validateTurn(Hero user) throws NotYourTurnException {
		// TODO Auto-generated method stub
		if(!user.equals(currentHero))
			throw new NotYourTurnException();
		
	}

	@Override
	public void validateAttack(Minion attacker, Minion target)
			throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException {
		//check charge , if attacked before
		if(attacker.isSleeping() || attacker.isAttacked()|| attacker.getAttack()==0)
			throw new CannotAttackException();
		// >>>>>>>>>>>>>>>>>>>> needs more revision <<<<<<<<<<<<<<<<<
		// Invalid target - the target may be in the current hero Field
		if(currentHero.getField().contains(target))
			throw new InvalidTargetException();
		// summoned (google translate ) called - i think it is not in the field yet
		if(!opponent.getField().contains(target) || !currentHero.getField().contains(attacker))
			throw new NotSummonedException();
		// if there is a taunt Minion in the opponent field
		for(int i=0;i<opponent.getField().size()&&!target.isTaunt();i++) {
			if(opponent.getField().get(i).isTaunt())
				throw new TauntBypassException();
		}
	}

	@Override
	public void validateAttack(Minion attacker, Hero target)
			throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException {
		//check charge , isattacked
		if(attacker.isSleeping() || attacker.isAttacked())
			throw new CannotAttackException();
		// the attacker is not in the field yet
		if(!currentHero.getField().contains(attacker))
			throw new NotSummonedException();
		// if there is a taunt Minion in the opponent field
		for(int i=0;i<opponent.getField().size();i++) {
			if(opponent.getField().get(i).isTaunt())
				throw new TauntBypassException();
		}
		//invalid target if the minion wants to attack its master hero or it is an instance of icehowl
		if(target.equals(currentHero) || (attacker instanceof Icehowl && !attacker.isPolymorphed()))
			throw new InvalidTargetException();
	}

	@Override
	public void validateManaCost(Card card) throws NotEnoughManaException {
		// TODO Auto-generated method stub
		if(currentHero.getCurrentManaCrystals()<card.getManaCost())
			throw new NotEnoughManaException();
	}

	@Override
	public void validatePlayingMinion(Minion minion) throws FullFieldException {
		// Each player can have up to seven minions in his field - source : Hearthstone game description file
		if(currentHero.getField().size()>=7)
			throw new FullFieldException();
	}

	@Override
	public void validateUsingHeroPower(Hero hero) throws NotEnoughManaException, HeroPowerAlreadyUsedException {
		// TODO Auto-generated method stub
		if(hero.isHeroPowerUsed())
			throw new HeroPowerAlreadyUsedException();
		//Using the hero power will always cost 2 mana crystals. - source : Hearthstone game description file
		if(currentHero.getCurrentManaCrystals()<2)
			throw new NotEnoughManaException();
	}

	@Override
	public void onHeroDeath() {
		// This method is triggered once any of the two heroes die and thus,
		// the game is over.(hint: The method should also trigger onGameOver method of the GameListener).
		gameover = true;
		if(!sim)
			this.listener.onGameOver();
	
	}

	@Override
	public void damageOpponent(int amount) {
		opponent.setCurrentHP(Math.max(opponent.getCurrentHP()-amount,0));
		// the hero may die => handled in the setCurrentHP()
	}

	@Override
	public void endTurn() throws FullHandException, CloneNotSupportedException {
		//change the current hero
		Hero tmp = currentHero;
		currentHero = opponent;
		opponent = tmp;
		//the current hero should be updated according to the game rules.
		//The maximum number of mana crystals a player can have in his balance is ten - source : Game Description
		currentHero.setTotalManaCrystals(Math.min(10,currentHero.getTotalManaCrystals()+1));
		currentHero.setCurrentManaCrystals(currentHero.getTotalManaCrystals());
		currentHero.setHeroPowerUsed(false);
		for(int i=0;i<currentHero.getField().size();i++) {
			// all minions are awake
			currentHero.getField().get(i).setSleeping(false);
			//reset minion's attack usage
			currentHero.getField().get(i).setAttacked(false);
		}
		// the hero should draw a card
		if(secondHero instanceof Player){
			try {
				currentHero.drawCard();
			}catch (Exception e){}
		}else{
			currentHero.drawCard();
		}

	}
	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	/////////////////////////////////////// AI ///////////////////////////////////////////////////////
	@Override
	public ArrayList<String> getPossibleMoves(){
		ArrayList<String> moves = new ArrayList<String>();
		// end turn
		moves.add("endTurn");
		if(isGameOver())return moves;
		// playing a card (minion or cast a spell)
		for(int i=0;i<this.currentHero.getHand().size();i++) {
			
			if(this.currentHero.getHand().get(i) instanceof Minion) {
				try {
					this.validateTurn(currentHero);
					this.validateManaCost(currentHero.getHand().get(i));
					this.validatePlayingMinion((Minion)currentHero.getHand().get(i));
				
				moves.add("PlayMinion "+i);
				
				}catch(Exception e) {}
			}
			else if(this.currentHero.getHand().get(i) instanceof Spell){
				
					Spell s = (Spell)this.currentHero.getHand().get(i);
					if(s instanceof AOESpell) {
						try {
							this.validateTurn(currentHero);
							this.validateManaCost(s);							
							moves.add("Castspell "+ i + " target " + "opp");
						}catch (Exception e) {}
					}else
						if(s instanceof FieldSpell) {
						try {
						this.validateTurn(currentHero);
						this.validateManaCost(s);
						moves.add("Castspell "+ i + " target " + "opp");
						}catch (Exception e) {}
					}else if(s instanceof HeroTargetSpell) {
						try {
							this.validateTurn(currentHero);
							this.validateManaCost(s);
							
							moves.add("Castspell "+ i + " target " + "opp");
						}catch(Exception e) {}
						
						try {
							this.validateTurn(currentHero);
							this.validateManaCost(s);
						moves.add("Castspell "+ i + " target " + "me");
						}catch(Exception e) {}
					}
					else if(s instanceof LeechingSpell) {
						for(int j=0;j<this.opponent.getField().size();j++) {
							
							try {
								this.validateTurn(currentHero);
								this.validateManaCost(s);
								moves.add("Castspell "+ i + " target " + "opp" +" "+ j);
							}catch(Exception e){}
						}
					}else 
						if(s instanceof MinionTargetSpell) {
						for(int j=0;j<this.opponent.getField().size();j++) {
							
							try {
								this.validateTurn(currentHero);
								this.validateManaCost(s);
								moves.add("Castspell "+ i + " target " + "opp" +" " + j);
							}catch(Exception e){}
						}
						
					}
//					
			}
		}
		// attack with minion
		for(int i=0;i<this.currentHero.getField().size();i++) {
			for(int j = 0;j<this.opponent.getField().size();j++) {
				try {
				this.validateAttack((Minion)this.currentHero.getField().get(i), (Minion)this.opponent.getField().get(j));
					moves.add("AttackMinion " + i + " " + j);
				}catch(Exception e) {}
			}
		}
		
		// attack with minion
		for(int i=0;i<this.currentHero.getField().size();i++) {
				try {
                this.validateAttack((Minion)this.currentHero.getField().get(i), this.opponent);
				moves.add("AttackHero " + i);
				}catch(Exception e) {}
			
		}

		//use heroPower
		if(this.currentHero instanceof Hunter|this.currentHero instanceof  Paladin|this.currentHero instanceof Warlock){
			try {
				this.validateUsingHeroPower(this.currentHero);
				moves.add("useHeroPower");
			}catch (Exception e){}
		}else if(this.currentHero instanceof Mage){
			for(int i=0;i<this.opponent.getField().size();i++){
				try {
					validateUsingHeroPower(this.currentHero);
					moves.add("useHeroPower " + "opp " + i);
				}catch (Exception e){}
			}
			try {
				validateUsingHeroPower(this.currentHero);
				moves.add("useHeroPower " + "opp " + "Hero");
			}catch (Exception e){}
		}else if(this.currentHero instanceof Priest){
			for(int i=0;i<this.currentHero.getField().size();i++){
				try {
					validateUsingHeroPower(this.currentHero);
					moves.add("useHeroPower " + "curr " + i);
				}catch (Exception e){}
			}
			try {
				validateUsingHeroPower(this.currentHero);
				moves.add("useHeroPower " + "curr " + "Hero");
			}catch (Exception e){}
		}
		return moves;
	}

	@Override
	public Reward getReward() {
		// TODO Auto-generated method stub
		if(gameover &&!this.getGameCommand().equals("endTurn")){
			if(firstHero.getCurrentHP()==0)
			reward = new Reward(0,1000000);
			else reward = new Reward(1000000,0);
			return reward;
		}
		reward = (new Reward(heroEval(firstHero),heroEval(secondHero)-heroEval(firstHero)));
		return this.reward;
	}
	public int heroEval(Hero h) {
		int res =h.getCurrentHP()*3+ h.getField().size()*1;
		return res;
	}
	@Override
	public Game getClone() throws FullHandException{
		Game g=null;
		try {
		g = new Game(new Hunter(),new Hunter());
		g.firstHero = firstHero.getClone();
		g.secondHero = secondHero.getClone();
		g.reward = new Reward(0,0);
		if(currentHero == firstHero) {
			g.currentHero = g.firstHero;
			g.opponent = g.secondHero;
		}else {
			g.opponent = g.firstHero;
			g.currentHero = g.secondHero;
		}
		g.sim = true;
		g.gameover = this.gameover;
		g.firstHero.setListener(g);
		g.secondHero.setListener(g);
		g.firstHero.setValidator(g);
		g.secondHero.setValidator(g);
		
		}catch (CloneNotSupportedException e) {}
		catch (IOException e) {}
		
		return g;
	}
		

	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return gameover;
	}

	@Override
	public int GameState() {
		// TODO Auto-generated method stub
		return 0;
	}
	public Hero getFirstHero() {
		return firstHero;
	}
	public Hero getSecondHero() {
		return secondHero;
	}
	public String getGameCommand() {
		return this.GameCommand;
	}
	public void setGameCommand(String s) {
		GameCommand = s;
	}
}
