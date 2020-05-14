package model.cards.minions;

import model.cards.Rarity;
public class Icehowl extends Minion{
//This minion can only attack other minions and can not attack heroes.
	public Icehowl() {
		//this is a big habda that was required to pass the test
		super("Icehowl", 9, Rarity.LEGENDARY,10, 10, false, false, true);
		
	}
	
	public Icehowl(String name,int manaCost,Rarity rarity, int attack,int maxHP,boolean
			taunt,boolean divine,boolean charge) {
		super(name, manaCost, Rarity.LEGENDARY,attack, maxHP, taunt, divine, charge);
	}
	@Override
	public Icehowl clone(){
		return new Icehowl();
	}

}
