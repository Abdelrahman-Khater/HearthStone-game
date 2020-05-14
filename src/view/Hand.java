package view;

import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.Buttonlib.*;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.Spell;
import model.heroes.*;
public class Hand extends JPanel implements MouseListener{
private ArrayList<CardButton> handCards;

private battleField frame;
private multiBattleField multiframe;
private Hero currentPlayer;
private Hero opponent;
private Object attacker;
private Object target;
private HeroButton heroButton;
private manaCrystalBar manabar;
private HeroPower heroPowerButton;
    public Hand(ArrayList<CardButton> c,battleField frm,Hero firstPlayer,Hero secondPlayer) {
		super();
	    currentPlayer=firstPlayer;
		opponent=secondPlayer;
		handCards = c;
		frame=frm;
		heroButton = new HeroButton(currentPlayer);
		heroButton.addMouseListener(this);
		heroButton.setLocation(565,0);
		manabar = new manaCrystalBar(currentPlayer);
		manabar.setLocation(440,200);
		
		heroPowerButton = new HeroPower();
		heroPowerButton.addMouseListener(this);
		heroPowerButton.setLocation(720,100);
		this.add(heroPowerButton);
		this.add(manabar);
		this.add(heroButton);

    	this.setSize(new Dimension(1280,500));
    	this.setLocation(0,600);
        this.setLayout(null);	
        this.setOpaque(false);
        int x=0;
        int y=120;
//        for(int i=0;i<c.size();i++)
//    	{	this.add(c.get(i));c.get(i).addMouseListener(this);c.get(i).setLocation(x, y);x+=120;}
        update();  	
    }

    
    public Hand(ArrayList<CardButton> c, multiBattleField frm, Hero firstPlayer,
			Hero secondPlayer) {
    	  currentPlayer=firstPlayer;
  		opponent=secondPlayer;
  		handCards = c;

  		multiframe=frm;
  		heroButton = new HeroButton(currentPlayer);
  		heroButton.setLocation(565,0);
		heroButton.addMouseListener(this);
  		manabar = new manaCrystalBar(currentPlayer);
  		manabar.setLocation(440,200);
  		
  		heroPowerButton = new HeroPower();
  		heroPowerButton.addMouseListener(this);
  		heroPowerButton.setLocation(720,100);
  		this.add(heroPowerButton);
  		this.add(manabar);
  		this.add(heroButton);
      	this.setSize(new Dimension(1280,500));
      	this.setLocation(0,600);
          this.setLayout(null);	
          this.setOpaque(false);
          int x=0;
          int y=120;
//          for(int i=0;i<c.size();i++)
//      	{	this.add(c.get(i));c.get(i).addMouseListener(this);c.get(i).setLocation(x, y);x+=120;}
          update();  		}


	public void update() {
		
		heroButton.setHero(currentPlayer);

// 		this is old implementation which had some errors concerning not shifting cards
//    	for(int i=0;i<handCards.size();i++)
//    	{	this.add(handCards.get(i));handCards.get(i).addMouseListener(this);handCards.get(i).setLocation(x, y);x+=120;}
    	for(int i=0;i<handCards.size();) {
    		CardButton c = handCards.get(i);
    		if(!currentPlayer.getHand().contains(c.getCard())) {
    			handCards.remove(c);
    			this.remove(c);
    		}else {
    			i++;
    		}
    	}
    	for(Card c:currentPlayer.getHand()) {
    		boolean found = false;
    		for(CardButton b:handCards) {
    			if(c.equals(b.getCard())) {
    				found = true;
    				break;
    			}
    		}
    		if(!found) {
    			CardButton tmp = new CardButton(c.getName()+".png", c);
    			tmp.addMouseListener(this);
    			tmp.update();
    			handCards.add(tmp);
    			this.add(tmp);
    		}
    	}
    
    	int x=0;
    	int y=150;
    	for(CardButton c:handCards) {
    		c.setLocation(x, y);
    		x+=120;
    	}
    	
    	this.remove(heroButton);
		heroButton.setLocation(565,0);
		this.add(heroButton);
		this.remove(heroPowerButton);
		heroPowerButton.setLocation(720,100);
		this.add(heroPowerButton);
		this.remove(manabar);
		manabar = new manaCrystalBar(currentPlayer);
		manabar.setLocation(440,200);
		this.add(manabar);
		heroButton.update();

		this.manabar.update();
    	this.revalidate();
    	this.repaint();
    	
    	
    }


  
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		if(e.getSource() instanceof HeroPower) {

			if(frame!=null) {
			Field.setSpell(null);
			frame.setAttacker(null);
			}else {
				Field.setSpell(null);
				multiframe.setAttacker(null);
			}
			HeroPower tmp = (HeroPower)e.getSource();
			if(currentPlayer instanceof Hunter || currentPlayer instanceof Warlock || currentPlayer instanceof Paladin) {
				try {
					
					currentPlayer.useHeroPower();
					this.update();
					if(frame!=null) {frame.update();frame.getField().update();}
					if(multiframe!=null) {multiframe.update();multiframe.getField().update();}
				    
				} catch (NotEnoughManaException e1) {
				    SoundValidator.playManaSound(currentPlayer);	
				} catch (HeroPowerAlreadyUsedException e1) {
					SoundValidator.playPowerSound(currentPlayer);	
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(null,"Sorry it is not your turn");
				} catch (FullHandException e1) {
					
					SoundValidator.playFullHandSound(currentPlayer,e1.getBurned());
				} catch (FullFieldException e1) {
			
				     SoundValidator.playFullFieldSound(currentPlayer);
				} catch (CloneNotSupportedException e1) {
					JOptionPane.showMessageDialog(null,"Sorry some thing went wrong");
				}
			}else {
				if(currentPlayer.getCurrentManaCrystals()<2) {
					SoundValidator.playManaSound(currentPlayer);
				}else {
					if(frame!=null)
						this.frame.getField().setAttackerHero(currentPlayer);
					else {
						this.multiframe.getField().setAttackerHero(currentPlayer);
					}
				JOptionPane.showMessageDialog(null,"Please select a target now");
				}
			}
			
		
		}
		else if(e.getSource() instanceof CardButton){
		CardButton tmp=(CardButton)e.getSource();
		attacker=tmp;
		Card tmp2=tmp.getCard();
		if(tmp2 instanceof Minion) {
			Minion tmpMin=(Minion)tmp2;
			try {
				currentPlayer.playMinion(tmpMin);
				this.update();

				if(frame!=null) {frame.update();frame.getField().update();}
				if(multiframe!=null) {multiframe.update();multiframe.getField().update();}
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, "Sorry it is not your turn");
				} catch (NotEnoughManaException e1) {
					SoundValidator.playManaSound(currentPlayer);


				} catch (FullFieldException e1) {
				SoundValidator.playFullFieldSound(currentPlayer);
				}

		}

		else {
			Spell temp=(Spell) tmp2;
			if(temp instanceof FieldSpell )
			{   FieldSpell temp2=(FieldSpell)temp;
				try {
					currentPlayer.castSpell(temp2);
					this.update();
					if(frame!=null) {frame.update();frame.getField().update();}
					if(multiframe!=null) {multiframe.update();multiframe.getField().update();}
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(null, "Sorry it is not your turn");
				} catch (NotEnoughManaException e1) {
			        SoundValidator.playManaSound(currentPlayer);
				}
			}
			else if(temp instanceof AOESpell )
				{   AOESpell temp2=(AOESpell)temp;
					try {
						currentPlayer.castSpell(temp2,opponent.getField());
						this.update();
						if(frame!=null) {frame.update();frame.getField().update();}
						if(multiframe!=null) {multiframe.update();multiframe.getField().update();}
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(null, "Sorry it is not your turn");
					} catch (NotEnoughManaException e1) {
				        SoundValidator.playManaSound(currentPlayer);
					}
				}
			else

			    if(currentPlayer.getCurrentManaCrystals()>=temp.getManaCost())
			    {
			    	JOptionPane.showMessageDialog(null, "Please Select a target now");
			    	Field.setSpellCard(temp);
			    }else {
			    	SoundValidator.playManaSound(currentPlayer);
					this.update();
					if(frame!=null) {frame.update();frame.getField().update();}
					if(multiframe!=null) {multiframe.update();multiframe.getField().update();}

			    }
			}
		}
		else if(e.getSource() instanceof HeroButton) {
				HeroButton h = (HeroButton)e.getSource();

				if(currentPlayer instanceof Priest) {
				if(frame!=null &&frame.getField().getAttackerHero()!=null) {
					try {
						((Priest)currentPlayer).useHeroPower(heroButton.getHero());
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
				if(multiframe!=null && multiframe.getField().getAttackerHero()!=null) {
						try {
							((Priest)currentPlayer).useHeroPower(heroButton.getHero());
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
				if(frame!=null)
				frame.getField().setAttackerHero(null);
				else {
					multiframe.getField().setAttackerHero(null);
				}
			}
		this.update();
		if(frame!=null)
			frame.update();
		else if(multiframe!=null)
			multiframe.update();
		//frame.repaint();
		
		}
		
		
	



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


	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		if(e.getSource() instanceof HeroPower)
		{try {
		    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/heroPower_hover.png"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		}
		else {
			if(e.getSource() instanceof CardButton)
		{CardButton tmp= (CardButton)e.getSource();
		tmp.setLocation(tmp.getLocation().x, tmp.getLocation().y-230);
		}
			}
		}



	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		if(e.getSource() instanceof HeroPower) {
			try {
		
			    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/heroPower_released.png"));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		}
		else if(e.getSource() instanceof CardButton){
		CardButton tmp= (CardButton)e.getSource();
		tmp.setLocation(tmp.getLocation().x, 150);
		}
	}



	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
         if(e.getSource() instanceof HeroPower)
          {
        	try {
		    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/heroPower_pressed.png"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
	}
	}


	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
	    if(e.getSource() instanceof HeroPower)
		{try {
		    ((JButton)e.getSource()).setIcon(new ImageIcon("res/Buttons/heroPower_hover.png"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }}
	  

	}
}
