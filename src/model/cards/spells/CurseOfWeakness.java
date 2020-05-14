package model.cards.spells;

import java.util.ArrayList;

import model.cards.*;
import model.cards.minions.*;

public class CurseOfWeakness extends Spell implements AOESpell{
	
	public CurseOfWeakness() {

		super("Curse of Weakness",2,Rarity.RARE);
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		for(int i=0;i<oppField.size();i++) {
			oppField.get(i).setAttack(oppField.get(i).getAttack()-2);
		}
		
	}
	public Spell clone() {
		return new CurseOfWeakness();
	}
}
