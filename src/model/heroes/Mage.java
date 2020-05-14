package model.heroes;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.*;

import java.io.IOException;
import java.util.ArrayList;
import engine.*;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;

public class Mage extends Hero{

	public Mage() throws IOException, CloneNotSupportedException {
		super("Jaina Proudmoore");
		buildDeck();
	}

	public void buildDeck() throws IOException, CloneNotSupportedException {
		ArrayList<Minion> minions = getAllNeutralMinions("src/neutral_minions.csv");
		ArrayList<Minion>  tmp = getNeutralMinions(minions,13);
		super.getDeck().addAll(tmp);
		super.getDeck().add(new Polymorph());
		super.getDeck().add(new Polymorph());

		super.getDeck().add(new Flamestrike());
		super.getDeck().add(new Flamestrike());
		super.getDeck().add(new Pyroblast());
		super.getDeck().add(new Pyroblast());
		super.getDeck().add(new Minion("Kalycgos",10, Rarity.LEGENDARY,4,12,
				false,false,false));
		for(int i=0;i<super.getDeck().size();i++) {
			if(super.getDeck().get(i) instanceof Minion) {
				((Minion)super.getDeck().get(i)).setListener(this);
			}
		}
		shuffle();
	}
	public void useHeroPower(Minion target) throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{
		super.useHeroPower();
		//what if divine?
		if(target.isDivine()) {
			target.setDivine(false);
		}else {
			target.setCurrentHP(Math.max(0,target.getCurrentHP()-1));
		}
		//what if it dies? = handled in the setCurrentHP
	}
	public void useHeroPower(Hero target) throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{
		super.useHeroPower();
		target.setCurrentHP(Math.max(0,target.getCurrentHP()-1));
		//what if it dies?
		if(target.getCurrentHP()==0)
			target.getListener().onHeroDeath();
	}

/////////////////////////////////////////// for AI ///////////////////////////////////////////////////
@Override
	public Mage getClone() throws IOException,CloneNotSupportedException{
		Mage res = new Mage();
		res.setCurrentHP(this.getCurrentHP());
		res.setCurrentManaCrystals(this.getCurrentManaCrystals());
		res.setHeroPowerUsed(this.isHeroPowerUsed());
		res.setTotalManaCrystals(this.getTotalManaCrystals());
		res.getDeck().clear();
		res.setFatigueDamage(this.getFatigueDamage());
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
