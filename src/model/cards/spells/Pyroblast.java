package model.cards.spells;

import exceptions.InvalidTargetException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.heroes.Hero;

public class Pyroblast extends Spell implements HeroTargetSpell,MinionTargetSpell{
	public Pyroblast() {
		super("Pyroblast",10,Rarity.EPIC);
	}

	@Override
	public void performAction(Minion m) throws InvalidTargetException {
		if(!m.isDivine())
			m.setCurrentHP(m.getCurrentHP()-10);
		m.setDivine(false);
	}

	@Override
	public void performAction(Hero h) {
		h.setCurrentHP(h.getCurrentHP()-10);
		
	}

	@Override
	public Pyroblast clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new Pyroblast();
	}
}
