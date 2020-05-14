package model.cards;

import model.cards.minions.Minion;

public abstract class Card implements Cloneable{
	private String name;
	private int manaCost;
	private Rarity rarity;
	
	public Card() {
		
	}
	
	public Card(String name,int manaCost,Rarity rarity) {
		 /*
		  The number of mana crystals needed to play the card. This value cannot be less
		  than 0 or greater than 10.
		 */
		this.name = name;
		this.manaCost = Math.max(0, Math.min(10, manaCost));
		this.rarity = rarity;
	}
	public Card clone() throws CloneNotSupportedException{
		return (Card)super.clone();
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = Math.max(0, Math.min(10, manaCost));
	}

	public Rarity getRarity() {
		return rarity;
	}

}

