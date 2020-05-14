package model.cards.spells;

import java.util.ArrayList;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class Flamestrike extends Spell implements AOESpell {
	
	public Flamestrike()
	{
		super("Flamestrike",7,Rarity.BASIC);
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		for(int i=0;i<oppField.size();) {
			boolean f = false;
			if(!oppField.get(i).isDivine()) {
				if(oppField.get(i).getCurrentHP()-4<=0)
					f = true;
				oppField.get(i).setCurrentHP(oppField.get(i).getCurrentHP()-4);
			}else {
				
				oppField.get(i).setDivine(false);
			}
			if(!f) {
				i++;
			}
		}
		
	}

	@Override
	public Flamestrike clone() throws CloneNotSupportedException {
		return new Flamestrike();
	}

}
