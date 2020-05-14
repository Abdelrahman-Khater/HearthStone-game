package model.heroes;

import model.cards.minions.Minion;
import model.cards.spells.*;

import java.io.IOException;
import java.util.ArrayList;

import model.cards.*;

import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;

public class Warlock extends Hero {

	public Warlock() throws IOException, CloneNotSupportedException {
		super("Gul'dan");
		buildDeck();
	}
	public void buildDeck() throws IOException, CloneNotSupportedException {
		ArrayList<Minion> minions = getAllNeutralMinions("src/neutral_minions.csv");
		ArrayList<Minion>  tmp = getNeutralMinions(minions,13);
		this.getDeck().addAll(tmp);
		this.getDeck().add(new CurseOfWeakness());
		this.getDeck().add(new CurseOfWeakness());
		this.getDeck().add(new SiphonSoul());
		this.getDeck().add(new SiphonSoul());
		this.getDeck().add(new TwistingNether());
		this.getDeck().add(new TwistingNether());
		this.getDeck().add(new Minion("Wilfred Fizzlebang",6, Rarity.LEGENDARY,4,4,
				false,false,false));
		for(int i=0;i<super.getDeck().size();i++) {
			if(super.getDeck().get(i) instanceof Minion) {
				((Minion)super.getDeck().get(i)).setListener(this);
			}
		}
		shuffle();
	}
	public void useHeroPower() throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{
		super.useHeroPower();
		this.setCurrentHP(this.getCurrentHP()-2);
		this.drawCard();
	}
/////////////////////////////////////////// for AI ///////////////////////////////////////////////////
@Override
	public Warlock getClone() throws IOException,CloneNotSupportedException{
		Warlock res = new Warlock();
		res.setCurrentHP(this.getCurrentHP());
		res.setCurrentManaCrystals(this.getCurrentManaCrystals());
		res.setHeroPowerUsed(this.isHeroPowerUsed());
		res.setFatigueDamage(this.getFatigueDamage());
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
			res.getHand().add(c);
			c.setListener(res);
		}
		return res;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////
}
