package model.cards.spells;

import java.util.ArrayList;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class MultiShot extends Spell implements AOESpell{
	public MultiShot()
	{
		super("Multi-Shot",4,Rarity.BASIC);
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		if(oppField.isEmpty())
			return;
		if(oppField.size()==1) {
			if(!oppField.get(0).isDivine())
				oppField.get(0).setCurrentHP(oppField.get(0).getCurrentHP()-3);
			else
				oppField.get(0).setDivine(false);
			return;
		}
		int rand1 = (int)(Math.random()*oppField.size());
		//rand2 not equal rand1
		boolean dead = false;
		if(!oppField.get(rand1).isDivine()) {
			if(oppField.get(rand1).getCurrentHP()-3<=0)
				dead = true;
			oppField.get(rand1).setCurrentHP(oppField.get(rand1).getCurrentHP()-3);
		}else {
			oppField.get(rand1).setDivine(false);
		}
		int rand2 = (int)(Math.random()*oppField.size());
		while(!dead && rand2==rand1) {
			rand2 = (int)(Math.random()*oppField.size());
		}
		if(!oppField.get(rand2).isDivine())
			oppField.get(rand2).setCurrentHP(oppField.get(rand2).getCurrentHP()-3);
		else
			oppField.get(rand2).setDivine(false);
		//minion death is already handled in setCurrentHP()
	}

	@Override
	public MultiShot clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new MultiShot();
	}
}
