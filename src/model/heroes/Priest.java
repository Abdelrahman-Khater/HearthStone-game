package model.heroes;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.*;

import java.io.IOException;
import java.util.ArrayList;

import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;

public class Priest extends Hero{

	public Priest() throws IOException, CloneNotSupportedException {
		super("Anduin Wrynn");
		buildDeck();
	}
	public void buildDeck() throws IOException, CloneNotSupportedException {
		ArrayList<Minion> minions = getAllNeutralMinions("src/neutral_minions.csv");
		ArrayList<Minion>  tmp = getNeutralMinions(minions,13);
		super.getDeck().addAll(tmp);
		super.getDeck().add(new DivineSpirit());
		super.getDeck().add(new DivineSpirit());
		super.getDeck().add(new HolyNova());
		super.getDeck().add(new HolyNova());
		super.getDeck().add(new ShadowWordDeath());
		super.getDeck().add(new ShadowWordDeath());
		super.getDeck().add(new Minion("Prophet Velen",7, Rarity.LEGENDARY,7,7,
				false,false,false));
		for(int i=0;i<super.getDeck().size();i++) {
			if(super.getDeck().get(i) instanceof Minion) {
				((Minion)super.getDeck().get(i)).setListener(this);
			}
		}
		shuffle();
	}
	public void useHeroPower(Hero target) throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{
		super.useHeroPower();
		boolean ProphetVelenFound = false;
		for(int i=0;i<this.getField().size();i++) {
			if(this.getField().get(i).getName().equals("Prophet Velen")) {
				ProphetVelenFound = true;
				break;
			}
		}
		if(ProphetVelenFound) {
			target.setCurrentHP(target.getCurrentHP()+8);
		}else {
			target.setCurrentHP(target.getCurrentHP()+2);
		}
	}
	public void useHeroPower(Minion target) throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{
		super.useHeroPower();
		boolean ProphetVelenFound = false;
		for(int i=0;i<this.getField().size();i++) {
			if(this.getField().get(i).getName().equals("Prophet Velen")) {
				ProphetVelenFound = true;
				break;
			}
		}
		if(ProphetVelenFound) {
			target.setCurrentHP(target.getCurrentHP()+8);
		}else {
			target.setCurrentHP(target.getCurrentHP()+2);
		}
	}
/////////////////////////////////////////// for AI ///////////////////////////////////////////////////
@Override
	public Priest getClone() throws IOException,CloneNotSupportedException{
		Priest res = new Priest();
		res.setCurrentHP(this.getCurrentHP());
		res.setCurrentManaCrystals(this.getCurrentManaCrystals());
		res.setFatigueDamage(this.getFatigueDamage());
		res.setHeroPowerUsed(this.isHeroPowerUsed());
		res.setTotalManaCrystals(this.getTotalManaCrystals());
		res.getDeck().clear();
		res.getHand().clear();
		res.getField().clear();
		for(int i=0;i<this.getDeck().size();i++) {
			Card c = this.getDeck().get(i).clone();
			res.getDeck().add(c);
			if(c instanceof Minion)
				((Minion)c).setListener(res);
		}
		for(int i=0;i<this.getHand().size();i++) {
			Card c = this.getHand().get(i).clone();
			res.getHand().add(c);
			if(c instanceof Minion)
				((Minion)c).setListener(res);
		}
		for(int i=0;i<this.getField().size();i++) {
			Minion c =this.getField().get(i).clone();
			res.getField().add(c);
			c.setListener(res);
		}
		return res;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////
}
