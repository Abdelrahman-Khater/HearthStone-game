package view;

import AI.Player;
import AI.mctsNode;
import AI.miniMaxPlayer;
import engine.Game;
import engine.GameListener;
import exceptions.*;
import model.cards.spells.HeroTargetSpell;
import model.heroes.Hero;
import model.heroes.Mage;
import model.heroes.Priest;
import view.Buttonlib.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class battleField extends States implements MouseListener,GameListener{
   private Game game;
   private ArrayList<CardButton> firstHeroCards;
   private ArrayList<minionButton> firstHeroFieldCards;
   private ArrayList<minionButton> secondHeroFieldCards;
   private mainMenu main;
   
   private flippedHand opphand;
   private Hero currentPlayer;
   private Hero opponent;
   private Hand handPanel;	
   private Field field;
   private Object attacker,target;
   private HeroButton oppButton;
   private manaCrystalBar oppMana;
   private static BufferedImage img;
   
	public Hero getCurrentPlayer() {
	return currentPlayer;
}

public void setCurrentPlayer(Hero currentPlayer) {
	this.currentPlayer = currentPlayer;
}

public Hero getOpponent() {
	return opponent;
}

public void setOpponent(Hero opponent) {
	this.opponent = opponent;
}

public Object getAttacker() {
	return attacker;
}

public void setAttacker(Object attacker) {
	this.attacker = attacker;
}

public Object getTarget() {
	return target;
}

public void setTarget(Object target) {
	this.target = target;
}

	public battleField(Hero h) {
		
//		File file=new File("res/Font/BackGround2.jpg");
//		BufferedImage img=null;
//		try{
//			img=ImageIO.read(file);
//		}catch(Exception e)
//		{
//			JOptionPane.showMessageDialog(null, "An ERROR Occured while loading the background please restart the game");
//			
//		}
//		
//		this.createBufferStrategy(1);
//		this.setContentPane(new ImagePanel(img,this));
		File file=new File("res/Font/BackGround2.jpg");
		try{
			img=ImageIO.read(file);
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "An ERROR Occured while loading the background please restart the game");
			
		}
	      this.setContentPane(new JPanel() {
	          @Override
	          public void paintComponent(Graphics g) {
	             super.paintComponent(g);
	             g.drawImage(img, 0, 0, null);
	          }
	       });
		
	    currentPlayer=h;
	    try {
	    	opponent=new miniMaxPlayer();
		    game=new Game(h,opponent);
		    game.setListener(this);
		}  catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Some problem occured ! please restart the game");
		}
	    
	    oppMana = new manaCrystalBar(opponent);
	    this.add(oppMana);
	    oppMana.setLocation(800,50);
	    firstHeroCards=new ArrayList<CardButton>();
	    firstHeroFieldCards=new ArrayList<minionButton>();
	    secondHeroFieldCards=new ArrayList<minionButton>();
	    this.setLayout(null);
	    handPanel=new Hand(firstHeroCards,this,currentPlayer,opponent);
	    this.add(handPanel);
	    field=new Field(firstHeroFieldCards,secondHeroFieldCards, this, currentPlayer,opponent);
	    field.setLocation(0,200);
	    oppButton = new HeroButton(opponent);
	    oppButton.setLocation(565,0);
	    oppButton.addMouseListener(this);
	    this.add(oppButton);
		this.add(field);
	 
		
		nextMoveButton b=  new nextMoveButton();
		b.addMouseListener(this);
		//this.setLocation(1100, 150);
		this.add(b);
		opphand = new flippedHand(opponent);
		opphand.setLocation(200,0);
		this.add(opphand);
		// aiHand = new AICardHand(this.opponent,this);
		 update();
	}
    
    public battleField(Hero h1,Hero h2) {
    	
    }
    
    public void draw() {
    	for(int i=0;i<firstHeroCards.size();i++)
    	{	CardButton tmp=firstHeroCards.get(i);
    	    handPanel.add(tmp);
    	}
    }
    public void update() {
//    	for(int i=0;i<h.getHand().size();i++) {
//			Card tmp =h.getHand().get(i);
//			CardButton tmp2=new CardButton(tmp.getName()+".png",tmp);
//			firstHeroCards.add(tmp2);
//			
//		}
    	handPanel.update();
    	field.update();
    	oppButton.update();
    	opphand.update();
    	oppMana.update();
    	//aiHand.update();
    	this.revalidate();
    	this.repaint();
    	
    }

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public ArrayList<CardButton> getFirstHeroCards() {
		return firstHeroCards;
	}

	public void setFirstHeroCards(ArrayList<CardButton> firstHeroCards) {
		this.firstHeroCards = firstHeroCards;
	}

	public ArrayList<minionButton> getFirstHeroFieldCards() {
		return firstHeroFieldCards;
	}

	public void setFirstHeroFieldCards(ArrayList<minionButton> firstHeroFieldCards) {
		this.firstHeroFieldCards = firstHeroFieldCards;
	}

	public Hand getHandPanel() {
		return handPanel;
	}

	public void setHandPanel(Hand handPanel) {
		this.handPanel = handPanel;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field firstHeroField) {
		this.field = firstHeroField;
	}

    
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() instanceof HeroButton) {
			HeroButton h = (HeroButton)e.getSource();
			if(field.getSpell()!=null) {
				
				if(field.getSpell() instanceof HeroTargetSpell) {
					try {
					currentPlayer.castSpell((HeroTargetSpell)field.getSpell(), h.getHero());
					}catch(NotEnoughManaException ex){
						SoundValidator.playManaSound(currentPlayer);
					}catch (NotYourTurnException ex2) {
						JOptionPane.showMessageDialog(null, "Sorry it is not your turn");
					}
				}
			}else if(field.getAttacker()!=null) {
					try {
						currentPlayer.attackWithMinion(field.getAttacker().getMinion(),h.getHero());
						this.field.update();
						this.update();
						this.field.setAttacker(null);
					} catch (CannotAttackException e1) {
					SoundValidator.playCannotAttackSound(currentPlayer,field.getAttacker().getMinion().isSleeping());
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(null,"Sorry it is not your turn");
					} catch (TauntBypassException e1) {
						SoundValidator.playTauntSound(currentPlayer);
					} catch (NotSummonedException e1) {
						JOptionPane.showMessageDialog(null,"this minion is not summoned");
					} catch (InvalidTargetException e1) {
						SoundValidator.playInvalidTargetSound(currentPlayer);
					}
					
				}else if(field.getAttackerHero()!=null){
					if(currentPlayer instanceof Mage) {
						try {
						((Mage)currentPlayer).useHeroPower(h.getHero());
						}catch (NotYourTurnException ex) {
							JOptionPane.showMessageDialog(null, "Not Your Turn!");
						}catch (CloneNotSupportedException e3) {
							JOptionPane.showMessageDialog(null,"Something went wrong please restart the game!");
						}catch (FullFieldException e4) {
							SoundValidator.playFullFieldSound(currentPlayer);
						}catch (FullHandException e5) {
							SoundValidator.playFullHandSound(currentPlayer,e5.getBurned());
						}catch(HeroPowerAlreadyUsedException ex){
							SoundValidator.playPowerSound(currentPlayer);
						}catch (NotEnoughManaException e2) {
							SoundValidator.playManaSound(currentPlayer);
						}
					}else if(currentPlayer instanceof Priest) {
						try {
						((Priest)currentPlayer).useHeroPower(h.getHero());
						}catch (NotYourTurnException ex) {
							JOptionPane.showMessageDialog(null, "Not Your Turn!");
						}catch (CloneNotSupportedException e3) {
							JOptionPane.showMessageDialog(null,"Something went wrong please restart the game!");
						}catch (FullFieldException e4) {
							SoundValidator.playFullFieldSound(currentPlayer);
						}catch (FullHandException e5) {
							SoundValidator.playFullHandSound(currentPlayer,e5.getBurned());
						}catch(HeroPowerAlreadyUsedException ex){
							SoundValidator.playPowerSound(currentPlayer);
						}catch (NotEnoughManaException e2) {
							SoundValidator.playManaSound(currentPlayer);
						}
					}
				}
			
			
		}else if(e.getSource() instanceof nextMoveButton) {
			nextMoveButton b = (nextMoveButton)(e.getSource());

			if(game.getCurrentHero()==opponent) {
				int k = 0;
				String s = "";
					try {
						s = ((Player)opponent).play(getGame());
						mctsNode.PerformGameCommand(s,getGame());
						System.out.println(s);
					}catch (FullHandException ex) {
						SoundValidator.playFullHandSound(currentPlayer,ex.getBurned());
					}catch (CloneNotSupportedException ex ){

					}catch (IOException ex){}
					if(s.equals("")){
						s = "endTurn";
						try {
							System.out.println(s);
							mctsNode.PerformGameCommand(s,getGame());

						}catch (FullHandException ex) {
							SoundValidator.playFullHandSound(currentPlayer,ex.getBurned());
						}catch (Exception ex){
						}
					}
					k++;
				}
				if(getGame().getFirstHero()==getGame().getCurrentHero()) {
					field.endturn.setEnabled(true);
				}else {
					field.endturn.setEnabled(false);
				}
			this.update();
			field.update();
			
		}
			
		field.setAttackerHero(null);
		field.setAttacker(null);
		Field.setSpell(null);
		this.update();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
	
	@Override
	public void onGameOver() {
		this.update();
		if(currentPlayer.getCurrentHP()==0) {
		SoundValidator.playDeathSound(currentPlayer);
		String msg=   "player 2 won ";
		JOptionPane.showMessageDialog(null,msg);}
		else
		{
			SoundValidator.playDeathSound(opponent);
		String msg=   "player 1 won ";
		JOptionPane.showMessageDialog(null,msg);
		}
		
		////////////// rabena yostor //////////////////  
		dispose();
		///////////////////////////////////////////////
		main=new mainMenu();
	}
    
}
