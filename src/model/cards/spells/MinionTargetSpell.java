package model.cards.spells;
import model.cards.minions.*;
import exceptions.*;
public interface MinionTargetSpell {
	public void performAction(Minion m) throws InvalidTargetException;
}
