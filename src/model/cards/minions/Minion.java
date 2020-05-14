package model.cards.minions;

import exceptions.*;
import model.heroes.*;
import model.cards.*;
public class Minion extends Card implements Cloneable{
	private int attack;
	private int maxHP;
	private int currentHP;
	private boolean taunt;
	private boolean divine;
	private boolean sleeping;
	private boolean attacked;
	private boolean polymorphed;
	private MinionListener listener;
	public Minion() {
		
	}
	
	public Minion(String name,int manaCost,Rarity rarity, int attack,int maxHP,boolean
			taunt,boolean divine,boolean charge) {
		super(name,manaCost,rarity);
		this.attack = attack;
		this.maxHP = maxHP;
		this.currentHP = maxHP;
		this.taunt = taunt;
		this.divine = divine;
		attacked = false;
		sleeping = !charge;
		this.polymorphed = false;
	}

	public Minion clone()throws CloneNotSupportedException{
		// i do not know why the instructions want to throw CloneNotSupported exception for Minions 
		// it could be done without it - notice there will be a change in buildDeck()
//		Minion m = new Minion(this.getName(),this.getManaCost(),this.getRarity(),
//				this.getAttack(),this.getMaxHP(),this.isTaunt(),this.divine,!this.isSleeping());
//		m.attacked = this.attacked;
		
		return (Minion)(super.clone());
	}
	public void attack(Minion target) {
		if(!target.isDivine()) {
			target.setCurrentHP(target.getCurrentHP()-this.attack);
		}
		if(!this.isDivine()) {
			this.setCurrentHP(this.getCurrentHP()-target.attack);
		}
		this.setAttacked(true);
		//both devine will be worn out
		if(target.getAttack()>0)
			this.setDivine(false);
		if(this.getAttack()>0)
			target.setDivine(false);
		
	}
	public void attack(Hero target) throws InvalidTargetException{
		if(this instanceof Icehowl && !this.isPolymorphed())
			throw new InvalidTargetException();
		if(target.equals(this.listener))
			throw new InvalidTargetException();
		this.setAttacked(true);
		target.setCurrentHP(target.getCurrentHP()-this.attack);
		//what if the target died = handled in setCurrentHP;
	}
	// this function is not written in the game description in M2 but we added it in order to make things easier
	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = Math.max(0, attack);
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = Math.max(0,Math.min(currentHP,getMaxHP()));
		// calling on minion death here is more convinient
		if(currentHP<=0)
			listener.onMinionDeath(this);
	}
	public boolean isTaunt() {
		return taunt;
	}
	public void setTaunt(boolean taunt) {
		this.taunt = taunt;
	}
	public boolean isDivine() {
		return divine;
	}
	public void setDivine(boolean divine) {
		this.divine = divine;
	}
	public boolean isSleeping() {
		return sleeping;
	}
	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}

	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}
	public void setListener(MinionListener m) {
		listener = m;
	}

	public boolean isPolymorphed() {
		return polymorphed;
	}

	public void setPolymorphed(boolean polymorphed) {
		this.polymorphed = polymorphed;
	}

}
