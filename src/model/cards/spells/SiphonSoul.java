package model.cards.spells;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class SiphonSoul extends Spell implements LeechingSpell{

	public SiphonSoul() {
		super("Siphon Soul", 6, Rarity.RARE);
	}

	@Override
	public int performAction(Minion m) {
		m.setCurrentHP(0);
		// how to restore 3 health points to the hero???
		return 3;
	}

	@Override
	public SiphonSoul clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new SiphonSoul();
	}

}
