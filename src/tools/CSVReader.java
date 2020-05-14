package tools;
import model.cards.*;
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;

import java.io.*;
import java.util.*;
public class CSVReader {
	private Scanner sc;
	private int MANA_COST;
	private Rarity RARITY;
	private int ATTACK;
	private int MAX_HP;
	private boolean TAUNT;
	private boolean DIVINE;
	private boolean CHARGE;
	private  String name;
	public CSVReader(String dir) throws IOException {
		File f = new File(dir);
		setSc(new Scanner(f));
	}
	
	
	public Minion nextLineArgs(String s) {
		// this is a void method which updates the values of the current variables read from CSV File
		String[] in;
		in = s.split(",");
		name = in[0];
		MANA_COST = Integer.parseInt(in[1]);
		RARITY = RarityParser(in[2]);
		ATTACK = Integer.parseInt(in[3]);
		MAX_HP=Integer.parseInt(in[4]);
		TAUNT = (in[5].equals("TRUE"))?true:false;
		DIVINE = (in[6].equals("TRUE"))?true:false;
		CHARGE = (in[7].equals("TRUE"))?true:false;
    	Minion m=new Minion(name, MANA_COST,RARITY, ATTACK, MAX_HP, TAUNT, DIVINE, CHARGE);
    	return m; 
	}
	
	public Icehowl nextIcehowl(String s) {
		String[] in;
		in = s.split(",");
		name = in[0];
		MANA_COST = Integer.parseInt(in[1]);
		RARITY = RarityParser(in[2]);
		ATTACK = Integer.parseInt(in[3]);
		MAX_HP=Integer.parseInt(in[4]);
		TAUNT = (in[5].equals("TRUE"))?true:false;
		DIVINE = (in[6].equals("TRUE"))?true:false;
		CHARGE = (in[7].equals("TRUE"))?true:false;
    	Icehowl m=new Icehowl(name, MANA_COST,RARITY, ATTACK, MAX_HP, TAUNT, DIVINE, CHARGE);
    	return m; 
	}
	public boolean isEOF() {
		return getSc().hasNext();
	}
	public static Rarity RarityParser(String s) {
		if(s.equals("b"))return Rarity.BASIC;
		if(s.equals("c"))return Rarity.COMMON;
		if(s.equals("r"))return Rarity.RARE;
		if(s.equals("e"))return Rarity.EPIC;
		if(s.equals("l"))return Rarity.LEGENDARY;
		else return Rarity.BASIC; //this return is not used
	}


	public Scanner getSc() {
		return sc;
	}


	public void setSc(Scanner sc) {
		this.sc = sc;
	}
	

}
