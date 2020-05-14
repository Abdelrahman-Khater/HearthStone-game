package model.cards.spells;

import java.util.ArrayList;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class HolyNova extends Spell implements AOESpell{
	public HolyNova() {

		super("Holy Nova",5,Rarity.BASIC);
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		for(int i=0;i<oppField.size();) {
			boolean f = false;
			if(!oppField.get(i).isDivine()) {
				if(oppField.get(i).getCurrentHP()-2<=0) {
					f = true;
				}
				oppField.get(i).setCurrentHP(oppField.get(i).getCurrentHP()-2);
			}else {
				oppField.get(i).setDivine(false);
			}
			if(!f)
				i++;
		}
		for(int i=0;i<curField.size();i++) {
			curField.get(i).setCurrentHP(curField.get(i).getCurrentHP()+2);
		}
		
	}

	@Override
	public HolyNova clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new HolyNova();
	}

}
