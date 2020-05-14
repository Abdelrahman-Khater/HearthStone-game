package view;

import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.heroes.*;
import view.Buttonlib.CardButton;
import engine.*;
public class AICardHand extends JFrame{
	public Hand hand;
	public Hero h;
	public AICardHand(Hero h,battleField f) {
		hand = new Hand(new ArrayList<CardButton>(), f, h,((Game)h.getListener()).getFirstHero());
		this.h = h;
		this.add(hand);
		hand.setLocation(0,0);
		this.update();
		this.setLayout(null);
		this.setSize(1280,300);
		this.setVisible(true);
	}
	public void update() {
		hand.update();
		this.revalidate();
		this.repaint();
		
	}

}
