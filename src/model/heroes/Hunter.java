package model.heroes;

import model.cards.*;
import model.cards.minions.*;
import model.cards.spells.*;
import view.Buttonlib.CardButton;

import java.io.IOException;
import java.util.ArrayList;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;

public class Hunter extends Hero {
    
	
	
	public Hunter() throws IOException, CloneNotSupportedException {
		super("Rexxar");
		buildDeck();
	}

	public void buildDeck() throws IOException, CloneNotSupportedException {
		ArrayList<Minion> minions = getAllNeutralMinions("src/neutral_minions.csv");
		ArrayList<Minion>  tmp = getNeutralMinions(minions,15);
	
		
		super.getDeck().addAll(tmp);
		super.getDeck().add(new KillCommand());
		super.getDeck().add(new KillCommand());
		super.getDeck().add(new MultiShot());
		super.getDeck().add(new MultiShot());
		super.getDeck().add(new Minion("King Krush",9,Rarity.LEGENDARY,8,8,
				false,false,true));
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
		this.getListener().damageOpponent(2);
	}
	
	
/////////////////////////////////////////// for AI ///////////////////////////////////////////////////
	@Override
	public Hunter getClone() throws IOException,CloneNotSupportedException{
		Hunter res = new Hunter();
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
			res.getField().add(c);
			c.setListener(res);
		}
		return res;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////
}
