package view;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.cards.Card;
import model.cards.minions.Icehowl;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;

public class SoundValidator {

	public static void playInvalidTargetSound(Hero h) {
		File f = null;
    	if(h instanceof Hunter) {
    		 f=new File("res/Sounds/PaladinInvalidTarget.wav");

    	}
    	else if(h instanceof Mage) {
        		 f=new File("res/Sounds/MageInvalidTarget.wav");
        	}
        else if(h instanceof Priest) {
            	 f=new File("res/Sounds/PaladinInvalidTarget.wav");
            }
        else if(h instanceof Warlock) {
                 f=new File("res/Sounds/WarlockInvalidTarget.wav");
            }
        else if(h instanceof Paladin) {
                 f=new File("res/Sounds/PaladinInvalidTarget.wav");
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
		JOptionPane.showMessageDialog(null, "Please choose a valid target");

    }
	public static void playManaSound(Hero h) {
    	File f = null;
    	if(h instanceof Hunter) {
    	  f=new File("res/Sounds/HunterMana.wav");
    	}
		else
    	if(h instanceof Paladin) {
    		f=new File("res/Sounds/PaladinMana.wav");
        	}
    	else
    	if(h instanceof Warlock) {
    		 f=new File("res/Sounds/WarlockMana.wav");
    			}
    	else
    	if(h instanceof Priest) {
    		 f=new File("res/Sounds/PaladinMana.wav");
       	 }else
       		if(h instanceof Mage) {
       		 f=new File("res/Sounds/MageMana.wav");
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
		 JOptionPane.showMessageDialog(null, "Sorry you do not have enough mana crystals");
    	
    	}
    
	public static  void playPowerSound(Hero h) {
    	File f = null;
    	if(h instanceof Hunter) {
    		 f=new File("res/Sounds/HunterAlreadyAttacked.wav");
    		
    		}else
    			if(h instanceof Mage) {
    	    		 f=new File("res/Sounds/MagePower.wav");
    	}
    			else
    				if(h instanceof Paladin) {
        	    		 f=new File("res/Sounds/PaladinHeroPower.wav");

        	}			
    				else
    					if(h instanceof Priest) {
    	    	    		 f=new File("res/Sounds/PriestAlreadyAttacked.wav");

    	    	}
    					else
    						if(h instanceof Warlock) {
    		    	    		 f=new File("res/Sounds/WarlockHeroPower.wav");

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
    	JOptionPane.showMessageDialog(null,"Sorry you have already used your hero power ");
	
	
    }
    
    public static void playFullHandSound(Hero h,Card c) {
    	String dir = c.getName();
    	 ImageIcon icon =new ImageIcon("res/"+dir+".png");
         JOptionPane.showMessageDialog(
                 null,
                 "Burned Card ",
                 "Your Hand is Full!", JOptionPane.INFORMATION_MESSAGE,
                 icon);
    	
    }
    
    
    public static void playFullFieldSound(Hero h) {
    	File f = null;
    	if(h instanceof Hunter) {
    		 f=new File("res/Sounds/HunterFullField.wav");
    	}
    	else
    		if(h instanceof Mage) {
        		 f=new File("res/Sounds/MageFullField.wav");
        	}
        	else	
        		if(h instanceof Paladin) {
            		 f=new File("res/Sounds/PriestFullField.wav");
            	}
            	else
            		if(h instanceof Warlock) {
                		 f=new File("res/Sounds/HunterFullField.wav");
                	}
                	else
                		if(h instanceof Paladin) {
                    		 f=new File("res/Sounds/PriestFullField.wav");
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
    	JOptionPane.showMessageDialog(null, "Sorry your field is full");                    	
    }
	public static void playCannotAttackSound(Hero h,boolean sleep) {
		File f = null;
    	if(h instanceof Hunter )
    	{
    		 f= sleep? new File("res/Sounds/PriestMinionCannotAttack.wav")
    			:new File("res/Sounds/HunterMinionAlreadyAttacked.wav");
    		 
    	}
    	else
    		if(h instanceof Mage )
        	{
    			f=sleep? new File("res/Sounds/PriestMinionCannotAttack.wav")
        			:new File("res/Sounds/MageMinionAlreadyAttacked.wav");

        	}
        	else
        		if(h instanceof Paladin )
            	{
        			f=sleep? new File("res/Sounds/PriestMinionCannotAttack.wav")
            	:new File("res/Sounds/PaladinMinionAlreadyAttacked.wav");

            	}
            	else
            		if(h instanceof Priest )
                	{
            			f=sleep? new File("res/Sounds/PriestMinionCannotAttack.wav"):
                	new File("res/Sounds/PriestMinionAlreadyAttacked.wav");

                	}
                	else
                		if(h instanceof Warlock )
                    	{
                			f=sleep? new File("res/Sounds/PriestMinionCannotAttack.wav")
                    	:new File("res/Sounds/WarlockMinionAlreadyAttacked.wav");

                    	}
    
	        try {
	            

	            AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
	            DataLine.Info info = new DataLine.Info(Clip.class, inputStream.getFormat());
	            Clip clip = (Clip) AudioSystem.getLine(info);
	            clip.open(inputStream);
	            clip.start();
	        } catch (Exception ex) {}
	  
		 JOptionPane.showMessageDialog(null,sleep?"MinionCannotAttack":"MinionAlreadyAttacked");

                    	
    }
	public static void playTauntSound(Hero h) {
		File f = null;
		if(h instanceof Hunter) {
			f=new File("res/Sounds/HunterMustAttackTaunt.wav");
		}
		else if(h instanceof Mage) {
			f=new File("res/Sounds/HunterMustAttackTaunt.wav");
		}
		else if(h instanceof Paladin) {
			f=new File("res/Sounds/PaladinTaunt.wav");
		}
		else if(h instanceof Priest) {
			f=new File("res/Sounds/PriestTaunt.wav");
		}
		else if(h instanceof Warlock) {
			f=new File("res/Sounds/HunterMustAttackTaunt.wav");
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
		JOptionPane.showMessageDialog(null,"there is a taunt in your opponent field");

	}
    public static void playDeathSound(Hero h) {
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
    public static void main(String[] args) {
    	try {
			playFullHandSound(new Mage(), new Icehowl());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
