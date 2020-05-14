package view.Buttonlib;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.Caret;

import model.cards.Card;
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;

public class CardButton extends JButton{
private Card card;
private JLabel manacost;
	public CardButton(String dir,Card c) {
		card=c;
//		int manaCost=c.getManaCost();
//		int attack=
		if(dir.contains("Shadow Word: Death")){
			dir = "Shadow Word Death.png";
		}
		manacost = new JLabel();
		manacost.setText("0");
		manacost.setSize(20,30);
		manacost.setVisible(false);
		Font f = new Font("Ariel",Font.BOLD,30);
		manacost.setForeground(Color.YELLOW);
		manacost.setBackground(new Color(0,80,100));
		manacost.setOpaque(true);
		manacost.setLocation(30,105);
		manacost.setFont(f);
		this.add(manacost);
	    this.setBorder(null);
		this.setLayout(null);
		this.setContentAreaFilled(false);
     	this.setIcon(new ImageIcon("res/"+dir));
		this.setSize(200,400);

	}
	public void update(){
		if(this.card.getManaCost()==0)manacost.setVisible(true);
	}
	public Card getCard() {
		return card;
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setVisible(true);
		f.setLayout(null);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Minion m =new Icehowl();
		m.setManaCost(0);

		CardButton tmp = new CardButton(m.getName()+".png",m);
		tmp.update();
		f.add(tmp);
		f.repaint();
	}
}
