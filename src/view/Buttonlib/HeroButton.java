package view.Buttonlib;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.heroes.Hero;
import model.heroes.Hunter;

public class HeroButton extends JButton {
private Hero hero;
private JTextField txtField,cardDeck;
private JTextField hptext;
private JLabel deck;

	public Hero getHero() {
	return hero;
}

public void setHero(Hero hero) {
	this.hero = hero;
}

	public HeroButton(Hero h) {
		super();
		hero=h;
		this.setBorder(null);
		this.setIcon(new ImageIcon("res/Heros/"+h.getName()+".png"));
		this.setSize(150,200);
		this.setContentAreaFilled(false);
		this.setLayout(null);
		int hp = h.getCurrentHP();
		 hptext = new JTextField();
		 hptext.setText(hp+"");

		hptext.setFont(new Font("Ariel",Font.BOLD,25));
		hptext.setForeground(Color.white);
		hptext.setEditable(false);
		hptext.setBorder(null);
		hptext.setSize(35,35);

		hptext.setOpaque(false);
		this.add(hptext);
		hptext.setLocation(110,110);
		
		deck=new JLabel();
		deck.setBorder(null);
		deck.setBackground(Color.BLACK);
		deck.setSize(30,70);
		cardDeck = new JTextField();
		cardDeck.setText(hero.getDeck().size()+"");
		cardDeck.setSize(40,30);
		
		cardDeck.setOpaque(false);
		cardDeck.setOpaque(false);
		cardDeck.setEditable(false);
		cardDeck.setBorder(null);
		
		cardDeck.setLocation(115,50);
		cardDeck.setForeground(Color.WHITE);
		cardDeck.setFont(new Font("Arial",Font.BOLD,17));
		this.add(cardDeck);
		
		//deck.setContentAreaFilled(false);
		deck.setLayout(null);
		deck.setIcon(new ImageIcon("res/Buttons/Carddeck.png"));
//		deck.setFont(new Font("Arial",Font.BOLD,20));
//		deck.setForeground(Color.WHITE);
		deck.revalidate();
		deck.repaint();
	    this.add(deck);
	    deck.setLocation(110, 30);
		this.revalidate();
		this.repaint();
		update();
	}
	
	public void update() {
		int hp = this.hero.getCurrentHP();
		this.setIcon(new ImageIcon("res/Heros/"+hero.getName()+".png"));
		this.hptext.setText(hp+"");
		cardDeck.setText(hero.getDeck().size()+"");
		//deck.setText(hero.getDeck().size()+"");
		this.revalidate();
		this.repaint();
	}
	
	public static void main(String[] args) throws Exception{
		JFrame f = new JFrame();
		f.setSize(500,500);
		f.setVisible(true);
		f.setLayout(null);
		
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		HeroButton b = new HeroButton( new Hunter());
		//f.add(b);
		f.add(new flippedHand(new Hunter()));
		f.repaint();
	}

}
