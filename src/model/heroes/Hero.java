package model.heroes;
import java.util.*;
import java.util.logging.Level;

import engine.*;
import exceptions.*;
import model.cards.*;
import model.cards.minions.*;
import model.cards.spells.*;
import tools.CSVReader;
import java.io.*;
public abstract class Hero implements MinionListener,Cloneable{
	private String name;
	private int currentHP;
	private boolean heroPowerUsed;
	private int totalManaCrystals;
	private int currentManaCrystals;
	private ArrayList<Card> deck;
	private ArrayList<Minion> field;
	private ArrayList<Card> hand;
	private int fatigueDamage = 0; //this variable is neither read or write ???
	// how is fatigueDamage not intialized ????
	private HeroListener listener;
	private ActionValidator validator;
	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = Math.max(0,Math.min(30, currentHP));
		if(currentHP<=0)
			listener.onHeroDeath();
	}
	public boolean isHeroPowerUsed() {
		return heroPowerUsed;
	}

	public void setHeroPowerUsed(boolean heroPowerUsed) {
		this.heroPowerUsed = heroPowerUsed;
	}

	public int getTotalManaCrystals() {
		return totalManaCrystals;
	}

	public void setTotalManaCrystals(int totalManaCrystals) {
		this.totalManaCrystals = Math.max(0,Math.min(10, totalManaCrystals));
		//setCurrentManaCrystals(totalManaCrystals);
	}

	public int getCurrentManaCrystals() {
		return currentManaCrystals;
	}

	public void setCurrentManaCrystals(int currentManaCrystals) {
		this.currentManaCrystals = Math.max(0,Math.min(10, currentManaCrystals));
	}

	public String getName() {
		return name;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public ArrayList<Minion> getField() {
		return field;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
	public HeroListener getListener() {
		return listener;
	}

	public void setListener(HeroListener listener) {
		this.listener = listener;
	}
	public void setValidator(ActionValidator validator) {
		this.validator = validator;
	}

	public Hero(String name) {
		this.name = name;
		this.currentHP = 30;
		this.heroPowerUsed = false;
		this.totalManaCrystals = 0;
		this.currentManaCrystals = 0;
		deck = new ArrayList<Card>();
		field = new ArrayList<Minion>();
		hand = new ArrayList<Card>();

	}

	public final static ArrayList<Minion> getAllNeutralMinions(String filePath) throws IOException {
		ArrayList<Minion> neutralMinions = new ArrayList<Minion>();
		CSVReader csv = new CSVReader(filePath);
		while (csv.isEOF()) {
			String s = csv.getSc().nextLine();
			if (s.substring(0, 7).equals("Icehowl")) {
				neutralMinions.add(csv.nextIcehowl(s));
				}
			else {
				neutralMinions.add(csv.nextLineArgs(s));
			}
		}
		return neutralMinions;
	}

	public final static ArrayList<Minion> getNeutralMinions(ArrayList<Minion> minions, int count) throws CloneNotSupportedException {
		ArrayList<Minion> res = new ArrayList<Minion>();
		HashMap<Integer, Integer> cardsTaken = new HashMap<Integer, Integer>();
		// HashMap <Index,Count>
		for (int i = 0; i < count; ) {
			int rand = (int) (Math.random() * (minions.size()));
			if (!cardsTaken.containsKey(rand)) {
				res.add(minions.get(rand));
				cardsTaken.put(rand, 1);

				i++;
			} else {
				if (minions.get(rand).getRarity() == Rarity.LEGENDARY) {
					//do nothing
				} else if (cardsTaken.get(rand) < 2) {
					res.add(minions.get(rand).clone());
					cardsTaken.put(rand, cardsTaken.get(rand) + 1);
					i++;
				}
			}
		}
		return res;
	}

	void shuffle() {
		for (int i = 0; i < this.deck.size(); i++) {
			int j = (int) (this.deck.size() * Math.random());
			Card tmp = deck.get(i);
			deck.set(i, deck.get(j));
			deck.set(j, tmp);
		}
	}
	public void onMinionDeath(Minion m) {
		//removes the minion from the field
		field.remove(m);
	}
	public void useHeroPower() throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{
		validator.validateUsingHeroPower(this);
		validator.validateTurn(this);
		this.heroPowerUsed = true;
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-2);
	}
	public void playMinion(Minion m) throws NotYourTurnException,
	NotEnoughManaException, FullFieldException{
		validator.validatePlayingMinion(m);
		validator.validateTurn(this);
		validator.validateManaCost(m);
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-m.getManaCost());
		this.getHand().remove(m);
		this.getField().add(m);
	}
	public void attackWithMinion(Minion attacker, Minion target) throws
	CannotAttackException, NotYourTurnException, TauntBypassException,
	InvalidTargetException, NotSummonedException{
		validator.validateAttack(attacker, target);
		validator.validateTurn(this);
		attacker.attack(target);
	}
	public void attackWithMinion(Minion attacker, Hero target) throws
	CannotAttackException, NotYourTurnException, TauntBypassException,
	NotSummonedException, InvalidTargetException{
		validator.validateAttack(attacker, target);
		validator.validateTurn(this);
		attacker.attack(target);
	}
	
    public void castSpell(FieldSpell s) throws NotYourTurnException,
	NotEnoughManaException{
		validator.validateTurn(this);
		Spell tmp = (Spell)s;
		validator.validateManaCost(tmp);
		s.performAction(this.getField());
		//check Kalycgos
		boolean KalycgosFound = false;
		for(int i=0;i<this.getField().size();i++) {
			if(this.getField().get(i).getName().equals("Kalycgos")) {
				KalycgosFound = true;
				break;
			}
		}
		if(KalycgosFound && this instanceof Mage) {
			tmp.setManaCost(tmp.getManaCost()-4);
			this.setCurrentManaCrystals(this.getCurrentManaCrystals()-tmp.getManaCost());
		}else {
			this.setCurrentManaCrystals(this.getCurrentManaCrystals()-tmp.getManaCost());
		}
		this.getHand().remove(tmp);

	}
	public void castSpell(AOESpell s, ArrayList<Minion >oppField) throws
	NotYourTurnException, NotEnoughManaException{
		validator.validateTurn(this);
		Spell tmp = (Spell)s;
		validator.validateManaCost(tmp);
		s.performAction(oppField, this.getField());
		boolean KalycgosFound = false;
		for(int i=0;i<this.getField().size();i++) {
			if(this.getField().get(i).getName().equals("Kalycgos")) {
				KalycgosFound = true;
				break;
			}
		}
		if(KalycgosFound && this instanceof Mage) {
			tmp.setManaCost(tmp.getManaCost()-4);
			this.setCurrentManaCrystals(this.getCurrentManaCrystals()-tmp.getManaCost());
		}else {
			this.setCurrentManaCrystals(this.getCurrentManaCrystals()-tmp.getManaCost());
		}
		this.getHand().remove(tmp);

	}
	public void castSpell(MinionTargetSpell s, Minion m) throws NotYourTurnException,
	NotEnoughManaException, InvalidTargetException{
		validator.validateTurn(this);
		Spell tmp = (Spell)s;
		validator.validateManaCost(tmp);
		s.performAction(m);
		boolean KalycgosFound = false;
		for(int i=0;i<this.getField().size();i++) {
			if(this.getField().get(i).getName().equals("Kalycgos")) {
				KalycgosFound = true;
				break;
			}
		}
		if(KalycgosFound && this instanceof Mage) {
			tmp.setManaCost(tmp.getManaCost()-4);
			this.setCurrentManaCrystals(this.getCurrentManaCrystals()-tmp.getManaCost());
		}else {
			this.setCurrentManaCrystals(this.getCurrentManaCrystals()-tmp.getManaCost());
		}
		this.getHand().remove(tmp);

	}
	public void castSpell(HeroTargetSpell s, Hero h) throws NotYourTurnException,
	NotEnoughManaException{
		validator.validateTurn(this);
		Spell tmp = (Spell)s;
		validator.validateManaCost(tmp);
		s.performAction(h);
		boolean KalycgosFound = false;
		for(int i=0;i<this.getField().size();i++) {
			if(this.getField().get(i).getName().equals("Kalycgos")) {
				KalycgosFound = true;
				break;
			}
		}
		if(KalycgosFound && this instanceof Mage) {
			tmp.setManaCost(tmp.getManaCost()-4);
			this.setCurrentManaCrystals(this.getCurrentManaCrystals()-tmp.getManaCost());
		}else {
			this.setCurrentManaCrystals(this.getCurrentManaCrystals()-tmp.getManaCost());
		}
		this.getHand().remove(tmp);

	}
	public void castSpell(LeechingSpell s, Minion m) throws NotYourTurnException,
	NotEnoughManaException{
		validator.validateTurn(this);
		Spell tmp = (Spell)s;
		validator.validateManaCost(tmp);
		this.setCurrentHP(this.getCurrentHP() + s.performAction(m));
		boolean KalycgosFound = false;
		for(int i=0;i<this.getField().size();i++) {
			if(this.getField().get(i).getName().equals("Kalycgos")) {
				KalycgosFound = true;
				break;
			}
		}
		if(KalycgosFound && this instanceof Mage) {
			tmp.setManaCost(tmp.getManaCost()-4);
			this.setCurrentManaCrystals(this.getCurrentManaCrystals()-tmp.getManaCost());
		}else {
			this.setCurrentManaCrystals(this.getCurrentManaCrystals()-tmp.getManaCost());
		}
		this.getHand().remove(tmp);

	}

	
	
	public void endTurn() throws FullHandException, CloneNotSupportedException{
		this.listener.endTurn();
	}
	
	
	
	
	public Card drawCard() throws FullHandException, CloneNotSupportedException{
		if(this.getDeck().isEmpty()) {
			this.setCurrentHP(this.getCurrentHP()-fatigueDamage);
			//what if the hero died ? => handled in setCurrent HP
			fatigueDamage++;
			return null;
		}
		Card c = this.getDeck().remove(0);
		boolean ChromaggusFound = false;
		boolean WilFredFound = false;
		for(int i=0;i<this.getField().size();i++) {
			if(this.getField().get(i).getName().equals("Chromaggus")) {
				ChromaggusFound = true;
			}
			if(this.getField().get(i).getName().equals("Wilfred Fizzlebang")) {
				WilFredFound = true;
			}
		}
		if(WilFredFound && c instanceof Minion && heroPowerUsed)
			c.setManaCost(0);
		if(this.getHand().size()>=10)
			throw new FullHandException(c);
		this.getHand().add(c);
		//Chromaggus check 
		if(ChromaggusFound && this.getHand().size()<10)
			this.getHand().add(c.clone());
		if(this.getDeck().isEmpty()) {
			this.fatigueDamage = 1;
		}
		return c;
	}
	public abstract Hero getClone() throws IOException,CloneNotSupportedException;
/////////////////////////////////////////////////////////
	public void setFatigueDamage(int f) {
		this.fatigueDamage = f;
	}
	//////////////////////////////
	// i added this method ==== needs revision **********************************
	public abstract void buildDeck() throws IOException,CloneNotSupportedException;
	public int getFatigueDamage(){
		return this.fatigueDamage;
	}
	
}
