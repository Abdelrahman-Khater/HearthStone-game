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

public class Paladin extends Hero{

	public Paladin() throws IOException, CloneNotSupportedException {
		super("Uther Lightbringer");
		buildDeck();
	}
	public void buildDeck() throws IOException, CloneNotSupportedException {
		ArrayList<Minion> minions = getAllNeutralMinions("src/neutral_minions.csv");
		ArrayList<Minion>  tmp = getNeutralMinions(minions,15);
		super.getDeck().addAll(tmp);
		super.getDeck().add(new SealOfChampions());
		super.getDeck().add(new SealOfChampions());
		super.getDeck().add(new LevelUp());
		super.getDeck().add(new LevelUp());

		super.getDeck().add(new Minion("Tirion Fordring",4, Rarity.LEGENDARY,6,6,
				true,true,false));
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
		Minion c = new Minion("Silver Hand Recruit",1,Rarity.BASIC,1,1,false,false,false);
		if(this.getField().size()>=7)
			throw new FullFieldException();
//		if(this.getCurrentManaCrystals()<1)
//			throw new NotEnoughManaException();
		this.getField().add(c);
		//this.setCurrentManaCrystals(getCurrentManaCrystals()-1);
	}
/////////////////////////////////////////// for AI ///////////////////////////////////////////////////
@Override
	public Paladin getClone() throws IOException,CloneNotSupportedException{
		Paladin res = new Paladin();
		res.setCurrentHP(this.getCurrentHP());
		res.setCurrentManaCrystals(this.getCurrentManaCrystals());
		res.setHeroPowerUsed(this.isHeroPowerUsed());
		res.setTotalManaCrystals(this.getTotalManaCrystals());
		res.setFatigueDamage(this.getFatigueDamage());
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
