package model.cards.spells;

import exceptions.InvalidTargetException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
// this class is incomplete - please remember to revise it. - mina
public class Polymorph extends Spell implements MinionTargetSpell{
	public Polymorph() {
		super("Polymorph",4,Rarity.BASIC);
	}

	@Override
	public void performAction(Minion m) throws InvalidTargetException {
		//why invalid target exception
		m.setName("Sheep");
		m.setSleeping(true);
		m.setTaunt(false);
		m.setDivine(false);
		m.setMaxHP(1);
		m.setCurrentHP(1);
		m.setManaCost(1);
		m.setAttack(1);
		m.setPolymorphed(true);
		// handling icehowl case : i put extra variable polymorphed in Icehowl class
	}

	@Override
	public Polymorph clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new Polymorph();
	}
}
