package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.minions.Minion;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;
import view.Buttonlib.EndTurn;
import view.Buttonlib.minionButton;
import model.heroes.*;
public class Field extends JPanel implements MouseListener{
private Hero currentPlayer;
private Hero opponent;
private battleField frame;
private multiBattleField multiframe;
ArrayList<minionButton> firstHeroMinions;
ArrayList<minionButton> secondHeroMinions;
private Object target;
//why do you make it static ????????
//private static Spell spell;
private static Spell spell;
private Hero AttackerHero = null;
private JPanel firstHeroPanel;
private JPanel secondHeroPanel;
public EndTurn endturn;
private minionButton attacker = null;
	public Field(ArrayList<minionButton> firstHeroMinions,ArrayList<minionButton> secondHeroMinions,battleField frm,Hero h,Hero h2) {
		super();
        this.firstHeroMinions=firstHeroMinions;
        this.secondHeroMinions=secondHeroMinions;
        frame=frm;
        currentPlayer=h;
        opponent=h2;
        firstHeroPanel=new JPanel();
        secondHeroPanel=new JPanel();
        firstHeroPanel.setSize(1280,200);
        secondHeroPanel.setSize(1280,200);
        firstHeroPanel.setLayout(null);
        secondHeroPanel.setLayout(null);
        secondHeroPanel.setLocation(0,0);
        firstHeroPanel.setLocation(0,200);
        this.setOpaque(false);
        firstHeroPanel.setOpaque(false);
        secondHeroPanel.setOpaque(false);
  //       firstHeroPanel.setBackground(Color.RED);
 //       secondHeroPanel.setBackground(Color.green);
        endturn = new EndTurn();
        firstHeroPanel.add(endturn);
        endturn.addMouseListener(this);
        endturn.setLocation(1130,0);
       
        
        this.add(firstHeroPanel);
        this.add(secondHeroPanel);
        
        this.setLocation(0,0);
        this.setSize(1280, 400);
        this.setLayout(null);
		update();
		}
	
	
	public Hero getAttackerHero() {
		return AttackerHero;
	}


	public void setAttackerHero(Hero attackerHero) {
		AttackerHero = attackerHero;
	}


	public Field(ArrayList<minionButton> firstHeroMinions, ArrayList<minionButton> secondHeroMinions,
			multiBattleField frm, Hero h, Hero h2) {
		super();
        this.firstHeroMinions=firstHeroMinions;
        this.secondHeroMinions=secondHeroMinions;
        multiframe=frm;
        currentPlayer=h;
        opponent=h2;
        firstHeroPanel=new JPanel();
        secondHeroPanel=new JPanel();
        firstHeroPanel.setSize(1280,200);
        secondHeroPanel.setSize(1280,200);
        firstHeroPanel.setLayout(null);
        secondHeroPanel.setLayout(null);
        secondHeroPanel.setLocation(0,0);
        firstHeroPanel.setLocation(0,200);
        this.setOpaque(false);
        firstHeroPanel.setOpaque(false);
        secondHeroPanel.setOpaque(false);
  //       firstHeroPanel.setBackground(Color.RED);
 //       secondHeroPanel.setBackground(Color.green);
        endturn = new EndTurn();
        firstHeroPanel.add(endturn);
        endturn.addMouseListener(this);
        endturn.setLocation(1130,0);
       
        
        this.add(firstHeroPanel);
        this.add(secondHeroPanel);
        
        this.setLocation(0,0);
        this.setSize(1280, 400);
        this.setLayout(null);
		update();
	}


	public JPanel getFirstHeroPanel() {
		return firstHeroPanel;
	}


	public void setFirstHeroPanel(JPanel firstHeroPanel) {
		this.firstHeroPanel = firstHeroPanel;
	}


	public JPanel getSecondHeroPanel() {
		return secondHeroPanel;
	}


	public void setSecondHeroPanel(JPanel secondHeroPanel) {
		this.secondHeroPanel = secondHeroPanel;
	}


	public void setTarget(Object t) {
		target=t;
	}
	public static void setSpellCard(Spell s) {
		spell=s;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof minionButton) {
		minionButton targetButton=(minionButton)e.getSource();
		if(spell!=null) {
			if(spell instanceof MinionTargetSpell)
			{ MinionTargetSpell temp=(MinionTargetSpell)spell;
			  Minion minTemp=(targetButton).getMinion();
				try {
					
					
					currentPlayer.castSpell(temp,minTemp);
			        targetButton.update();
			        if(frame!=null)this.frame.update();
					if(multiframe!=null) this.multiframe.update();
					//System.out.println("updated");
					//this.update();
					
				} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, "Sorry it is not your turn");
				
				} catch (NotEnoughManaException e1) {
					SoundValidator.playManaSound(currentPlayer);
					
				} catch (InvalidTargetException e1) {
					SoundValidator.playInvalidTargetSound(currentPlayer);
					
				}
				if(frame!=null)this.frame.update();
				if(multiframe!=null) this.multiframe.update();
			}else if(spell instanceof LeechingSpell) {
					LeechingSpell Leech=(LeechingSpell)spell;
						Minion tmpMin=(targetButton).getMinion();
						
					try {
					    
						currentPlayer.castSpell(Leech, tmpMin);
						targetButton.update();
//						this.update();
						if(frame!=null)this.frame.update();
						if(multiframe!=null) this.multiframe.update();
						} catch (NotYourTurnException e1) {
						    JOptionPane.showMessageDialog(null, "Sorry it is not your turn");
						} catch (NotEnoughManaException e1) {
							SoundValidator.playManaSound(currentPlayer);
						}
					if(frame!=null)this.frame.update();
					if(multiframe!=null) this.multiframe.update();
		}
	}else if(attacker==null &&AttackerHero==null) {
		//System.out.println("SSSSSSSSSSSSSSSssssss");
			attacker= (minionButton)e.getSource();
	}else if(attacker!=null){
			///minion  attack case
			AttackerHero = null;
				try {
				currentPlayer.attackWithMinion(attacker.getMinion(), ((minionButton)e.getSource()).getMinion());
				attacker=null;
				if(frame!=null)this.frame.update();
				if(multiframe!=null) this.multiframe.update();
				}catch (NotYourTurnException ex) {
					JOptionPane.showMessageDialog(null, "Not Your Turn!");
				}catch (NotSummonedException e2) {
					JOptionPane.showMessageDialog(null, "Minion not Summoned!");
				}catch (InvalidTargetException e3) {
					SoundValidator.playInvalidTargetSound(currentPlayer);
				}catch (TauntBypassException e4) {
					SoundValidator.playTauntSound(currentPlayer);
				}catch (CannotAttackException e5) {
					//System.out.println(( (minionButton)e.getSource() ).getMinion().isSleeping());
					SoundValidator.playCannotAttackSound(currentPlayer,( (minionButton)e.getSource() ).getMinion().isSleeping());
				}
				if(frame!=null)this.frame.update();
				if(multiframe!=null) this.multiframe.update();
				attacker = null;
			
		}else if(AttackerHero!=null){
			if(currentPlayer instanceof Mage) {
				try {
				((Mage)currentPlayer).useHeroPower(targetButton.getMinion());
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
				((Priest)currentPlayer).useHeroPower(targetButton.getMinion());
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
		//attacker = null;
		AttackerHero = null;
		spell=null;
		this.update();
		if(frame!=null)this.frame.update();
		if(multiframe!=null) this.multiframe.update();
		
		}else if(e.getSource() instanceof EndTurn){
			if(frame!=null) {

					spell=null;
					attacker=null;
					target=null;
					if(currentPlayer==frame.getGame().getCurrentHero()) {
						try {
						currentPlayer.endTurn();
						}catch (FullHandException ex) {
							JOptionPane.showMessageDialog(null, "AI Hand is Full");
						}catch (CloneNotSupportedException e2) {
							JOptionPane.showMessageDialog(null, "Something Went Wrong!");
						}
						frame.update();
					}else {
						JOptionPane.showMessageDialog(null, "This is not Your turn!");
					}

			} else {
				try {
					spell=null;
					attacker=null;
					target=null;
					currentPlayer.endTurn();
					multiframe.update();
				}catch (CloneNotSupportedException e2) {
					JOptionPane.showMessageDialog(null, "Something Went Wrong!");
				}catch (FullHandException ex) {
					SoundValidator.playFullHandSound(currentPlayer,ex.getBurned());
				}
				
				Hero tmp = currentPlayer;
				currentPlayer = opponent;
				opponent = tmp;
				System.out.println(currentPlayer.getName());
				multiframe.setCurrentPlayer(currentPlayer);
				multiframe.setOpponent(opponent);
				
//				ArrayList<minionButton> tmpp = firstHeroMinions;
//				firstHeroMinions = secondHeroMinions;
//				secondHeroMinions = tmpp;
				multiframe.getHandPanel().setCurrentPlayer(currentPlayer);
				multiframe.getHandPanel().setOpponent(opponent);
				multiframe.update();
				
			}
			
			this.update();
			if(frame!=null)this.frame.update();
			if(multiframe!=null) this.multiframe.update();
			
			
		}
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
public void update() {
	//this method had a logical error but i solved it - mina
	for(int i=0;i<firstHeroMinions.size();)
	{	Minion temp=firstHeroMinions.get(i).getMinion();
	    if(!currentPlayer.getField().contains(temp)) {
	    	firstHeroPanel.remove(firstHeroMinions.get(i));
	    	firstHeroMinions.remove(firstHeroMinions.get(i));
	    }else {
	    	i++;
	    }
	}
	for(int i=0;i<secondHeroMinions.size();)
	{	
		Minion temp=secondHeroMinions.get(i).getMinion();
	    if(!opponent.getField().contains(temp)) {
	    	secondHeroPanel.remove(secondHeroMinions.get(i));
	    	secondHeroMinions.remove(secondHeroMinions.get(i));
	    }else {
	    	i++;
	    }
	}
	
	for(int i=0;i<currentPlayer.getField().size();i++) {
		Minion temp=currentPlayer.getField().get(i);
		boolean found=false;
		for(int j=0;j<firstHeroMinions.size()&&!found;j++)
       {   
    	   if(temp.equals(firstHeroMinions.get(j).getMinion()))
    		   {found=true;}
       }
		if(!found)
	 	{   minionButton tmp=new minionButton(currentPlayer.getField().get(i).getName(),
	 			currentPlayer.getField().get(i),currentPlayer);
	 	
	 	tmp.addMouseListener(this);
	 	firstHeroMinions.add(tmp);
	 	firstHeroPanel.add(tmp);
		}
		this.revalidate();
		this.repaint();
	}
	
	
	
	for(int i=0;i<opponent.getField().size();i++) {
		Minion temp=opponent.getField().get(i);
		boolean found=false;
		for(int j=0;j<secondHeroMinions.size()&&!found;j++)
       {
    	   if(temp.equals(secondHeroMinions.get(j).getMinion()))
    		   {found=true;}
       }
		if(!found)
	 	{   minionButton tmp=new minionButton(opponent.getField().get(i).getName(),
	 			opponent.getField().get(i),opponent);
	 	tmp.addMouseListener(this);	
	 	secondHeroMinions.add(tmp);
	 	secondHeroPanel.add(tmp);
		}
		
	}
	int x = 0;
	int y = 0;
	for(minionButton b:firstHeroMinions) {
		b.update();
		b.setLocation(x,y);
		x+=155;
	}
	 x = 0;
	 y = 0;
	for(minionButton b:secondHeroMinions) {
		b.update();
		b.setLocation(x,y);
		x+=150;
	}
	
    firstHeroPanel.revalidate();
    firstHeroPanel.repaint();
	

	secondHeroPanel.revalidate();
    secondHeroPanel.repaint();
	if(this.frame!=null) {
		if(this.frame.getGame().getCurrentHero()==currentPlayer) {
			endturn.setEnabled(true);
		}else {
			endturn.setEnabled(false);
		}
	}
	endturn.revalidate();
	endturn.repaint();
	this.revalidate();
	this.repaint();
    
}


public  Spell getSpell() {
	return spell;
}


public static void setSpell(Spell spell) {
	Field.spell = spell;
}

public minionButton getAttacker() {
	return attacker;
}
public void setAttacker(minionButton m) {
	attacker=m;
}

}
