package model.cards.spells;

import exceptions.InvalidTargetException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class ShadowWordDeath extends Spell implements MinionTargetSpell{

	public ShadowWordDeath() {
		super("Shadow Word: Death", 3, Rarity.BASIC);
	}

	@Override
	public void performAction(Minion m) throws InvalidTargetException {
		if(m.getAttack()>=5)
			m.setCurrentHP(0);
		else 
			throw new InvalidTargetException();
	}

	@Override
	public ShadowWordDeath clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new ShadowWordDeath();
	}


}
