package view;

import engine.Game;
import engine.GameListener;
import exceptions.*;
import model.cards.spells.HeroTargetSpell;
import model.heroes.*;
import view.Buttonlib.CardButton;
import view.Buttonlib.HeroButton;
import view.Buttonlib.minionButton;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class multiBattleField extends States implements MouseListener,GameListener{
   private Game game;
   private ArrayList<CardButton> firstHeroCards;
   private ArrayList<minionButton> firstHeroFieldCards;
   private ArrayList<minionButton> secondHeroFieldCards;
   private mainMenu main;
   
   
   private Hero currentPlayer;
   private Hero opponent;
   private Hand handPanel;	
   private Field field;
   private Object attacker,target;
   private HeroButton oppButton;
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

	public multiBattleField(Hero h1,Hero h2) {
//		File file=new File("res/Font/back3.jpg");
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
	    try {
		    game=new Game(h1,h2);
		    game.setListener(this);
	    }catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Some problem occured ! please restart the game");
		}
	    currentPlayer=game.getCurrentHero();
	    opponent = game.getOpponent();
	 	
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

		 update();
   
	
	///////////////////////
		 ///test////
	//opponent.setCurrentHP(1);	 
	}
    
    
    public void draw() {
    	for(int i=0;i<firstHeroCards.size();i++)
    	{	CardButton tmp=firstHeroCards.get(i);
    	    handPanel.add(tmp);
    	}
    }
    public void update() {
        oppButton.setHero(opponent);
    	handPanel.update();
    	field.update();
    	oppButton.update();
    	
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
			
			
		}
		field.setAttackerHero(null);
		field.setAttacker(null);
		Field.setSpell(null);
		this.update();
	}
//	public void mouseClicked(MouseEvent e) {
//		if(e.getSource() instanceof HeroButton) {
//			HeroButton h = (HeroButton)e.getSource();
//			if(field.getSpell()!=null) {
//				
//				if(field.getSpell() instanceof HeroTargetSpell) {
//					try {
//					currentPlayer.castSpell((HeroTargetSpell)field.getSpell(), h.getHero());
//					}catch(NotEnoughManaException ex){
//						SoundValidator.playManaSound(currentPlayer);
//					}catch (NotYourTurnException ex2) {
//						JOptionPane.showMessageDialog(null, "Sorry it is not your turn");
//					}
//				}
//			}else
//			{
//				if(field.getAttacker()!=null) {
//					try {
//						currentPlayer.attackWithMinion(field.getAttacker().getMinion(),h.getHero());
//						this.field.update();
//						this.update();
//						this.field.setAttacker(null);
//					} catch (CannotAttackException e1) {
//						SoundValidator.playCannotAttackSound(currentPlayer,field.getAttacker().getMinion().isSleeping());
//					} catch (NotYourTurnException e1) {
//						JOptionPane.showMessageDialog(null,"Sorry it is not your turn");
//					} catch (TauntBypassException e1) {
//						SoundValidator.playTauntSound(currentPlayer);
//					} catch (NotSummonedException e1) {
//						JOptionPane.showMessageDialog(null,"this minion is not summoned");
//					} catch (InvalidTargetException e1) {
//						SoundValidator.playInvalidTargetSound(currentPlayer);
//					}
//					
//				}
//			}
//		}
//			
//		
//		field.setSpell(null);
//		this.update();
//		
//	}
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
	public void playDeathSound(Hero h) {
    	File f = null;
    	if(h instanceof Hunter) {
    		 f=new File("res/Sounds/HunterDeath.wav");
    	}else
    		if(h instanceof Hunter) {
    		 f=new File("res/Sounds/HunterDeath.wav");
        }else if(h instanceof Warlock) {
        	 f=new File("res/Sounds/HunterDeath.wav");
    	}else if(h instanceof Paladin) {
    	     f=new File("res/Sounds/PaladinDeath.wav");
        }else if(h instanceof Priest) {
        	 f=new File("res/Sounds/PriestEnd.wav");
        }else if(h instanceof Mage) {
        	 f=new File("res/Sounds/MageDeath.wav");
        }
    	try {
	        DataLine.Info daInfo = new DataLine.Info(Clip.class, null);
	        try {
	            

	            AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
	            DataLine.Info info = new DataLine.Info(Clip.class, inputStream.getFormat());
	            Clip clip = (Clip) AudioSystem.getLine(info);
	            clip.open(inputStream);
	            clip.start();
	        } catch (IOException ex) {}
	    } catch (Exception e) {}
		JOptionPane.showMessageDialog(null,"GameOver");

    }
	@Override
	public void onGameOver() {
		this.update();
		if(currentPlayer.getCurrentHP()==0) {
		playDeathSound(currentPlayer);
		String msg=   "player 2 won ";
		JOptionPane.showMessageDialog(null,msg);}
		else
		{playDeathSound(opponent);
		String msg=   "player 1 won ";
		JOptionPane.showMessageDialog(null,msg);
		}
		
		////////////// rabena yostor //////////////////  
		dispose();
		///////////////////////////////////////////////
		main=new mainMenu();
	}
	
			
    
}
