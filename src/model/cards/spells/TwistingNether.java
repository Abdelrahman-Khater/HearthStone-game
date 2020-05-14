package model.cards.spells;

import java.util.ArrayList;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class TwistingNether extends Spell implements AOESpell{

	public TwistingNether() {
		super("Twisting Nether", 8, Rarity.EPIC);
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		for(int i=0;i<oppField.size();)
			oppField.get(0).setCurrentHP(0);
		for(int i=0;i<curField.size();)
			curField.get(0).setCurrentHP(0);
	}

	@Override
	public TwistingNether clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new TwistingNether();
	}

}
