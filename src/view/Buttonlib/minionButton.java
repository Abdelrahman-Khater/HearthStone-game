package view.Buttonlib;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.cards.Rarity;
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;
import model.heroes.Hero;
import model.heroes.Hunter;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
    
public class minionButton extends JButton {
private Minion minion;
private Hero hero;
private JLabel taunt,sleep,divine,imgMinion;
private JTextField attacktext,hptext;
private JButton nameLabel;
static final ImageIcon imgTaunt = new ImageIcon("res/Minions/taunt2.png");
static final ImageIcon imgSleep = new ImageIcon("res/Minions/sleep.png");
static final ImageIcon imgDivine = new ImageIcon("res/Minions/taunt.png");

public minionButton(String dir,Minion m,Hero h) {
		super();
		setMinion(m);
		hero=h;
	
		this.setBorder(null);
	    nameLabel = new JButton();
	    String n=m.getName();
	    String r=""+m.getRarity();
	    String ma=""+m.getManaCost()+"  "+r;
	    nameLabel.setText("<html>"+n+"<br>"+ma+"</html>");//+ma+"/html");

		nameLabel.setFont(new Font("Ariel",Font.BOLD,13));
		nameLabel.setForeground(Color.WHITE);
		//nameLabel.setEditable(false);
		nameLabel.setBorder(null);
		nameLabel.setSize(130,70);
		nameLabel.setLocation(0,150);
		nameLabel.setOpaque(false);
		nameLabel.setContentAreaFilled(false);

		this.add(nameLabel);
		
		
		 taunt = new JLabel();
		this.add(taunt);
		 sleep = new JLabel();
		this.add(sleep);
		divine = new JLabel();
		this.add(divine);
		imgMinion = new JLabel();
		imgMinion.setLocation(0,-20);
		imgMinion.setIcon(new ImageIcon("res/Minions/"+m.getName()+".png"));
		imgMinion.setOpaque(false);
		//this.setIcon(new ImageIcon("res/Minions/"+dir+".png"));
		this.setSize(130,200);
		imgMinion.setSize(150,200);
		this.setContentAreaFilled(false);
		int attack=m.getAttack();
		int hp=m.getCurrentHP();
		boolean charge=!m.isSleeping();
		
		this.setLayout(null);
		
		
		 attacktext = new JTextField();
		
		
		attacktext.setFont(new Font("Ariel",Font.BOLD,25));
		attacktext.setForeground(Color.white);
		attacktext.setEditable(false);
		attacktext.setBorder(null);
		attacktext.setSize(50,30);
		attacktext.setLocation(92,112);
		attacktext.setOpaque(false);
		this.add(attacktext);
		
		 hptext = new JTextField();
	
		
		hptext.setFont(new Font("Ariel",Font.BOLD,25));
		hptext.setForeground(Color.white);
		hptext.setEditable(false);
		hptext.setBorder(null);
		hptext.setSize(50,30);
		hptext.setLocation(20,115);
		hptext.setOpaque(false);
		this.add(hptext);
		this.add(imgMinion);
		update();
		this.revalidate();
	}

 public void update() {
	
//	    nameLabel = new JButton();
//	    String n=minion.getName();
//	    String r=""+minion.getRarity();
//	    String ma=""+minion.getManaCost()+"  "+r;
	   // nameLabel.setText("<html>"+n+"<br>"+ma+"</html>");//+ma+"/html");
	 Minion m=minion;
	 if(m.isDivine()) {
			divine.setIcon(imgDivine);
			divine.setSize(50,50);
			divine.setLocation(5,0);
			divine.setVisible(true);
			this.revalidate();
			}else {
				divine.setVisible(false);
			}
	 if(m.isTaunt()) {
			taunt.setIcon(imgTaunt);
			taunt.setSize(165,50);
			taunt.setLocation(-15,135);
			taunt.setVisible(true);
			this.revalidate();
			}else {
				taunt.setVisible(false);
			}
	 if(m.isSleeping()) {
			sleep.setIcon(imgSleep);
			sleep.setSize(50,50);
			sleep.setLocation(75,-5);
			sleep.setVisible(true);
			this.revalidate();
		}else {
			
			sleep.setVisible(false);
		}
	 int attack=m.getAttack();
	 attacktext.setText(attack+"");
	 int hp=m.getCurrentHP();
	 hptext.setText(hp+"");
	 imgMinion.setIcon(new ImageIcon("res/Minions/"+minion.getName()+".png"));
}
	public static void main(String[] args) throws Exception{
		JFrame f = new JFrame();
		f.setSize(500,500);
		f.setVisible(true);
		f.setLayout(null);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		minionButton b = new minionButton("Charge", new Minion("Chilwind Yeti",5,Rarity.LEGENDARY,1,4,true, true,false), new Hunter());
		f.add(b);
		f.repaint();
	}
	public Minion getMinion() {
		return minion;
	}
	public void setMinion(Minion minion) {
		this.minion = minion;
	}

}
