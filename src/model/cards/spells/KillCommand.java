package model.cards.spells;

import exceptions.InvalidTargetException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.heroes.Hero;

public class KillCommand extends Spell implements HeroTargetSpell , MinionTargetSpell{
	public KillCommand() {

		super("Kill Command",3,Rarity.COMMON);
	}

	@Override
	public void performAction(Minion m) throws InvalidTargetException {
		//why to throw invalid target exception ??
		//for testing
//		throw new InvalidTargetException();
		//////
		if(!m.isDivine())
			m.setCurrentHP(m.getCurrentHP()-5);
		m.setDivine(false);
	}

	@Override
	public void performAction(Hero h) {
		h.setCurrentHP(h.getCurrentHP()-3);
	}

	@Override
	public KillCommand clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new KillCommand();
	}
}
