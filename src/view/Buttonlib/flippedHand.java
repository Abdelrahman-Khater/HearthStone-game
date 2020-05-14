package view.Buttonlib;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.heroes.*;
public class flippedHand extends JLabel {
	Hero h;
	public flippedHand(Hero h) {
		this.h = h;
		this.setSize(1280,200);
		this.setLayout(new FlowLayout());
		this.setOpaque(false);
		this.setBorder(null);
		this.setLayout(null);
		this.revalidate();
		this.repaint();
		update();
	}
	public void update() {
		this.removeAll();
		int x = 0,y =-200;
		for(int i=0;i<h.getHand().size();i++) {
			ImageIcon tmp = new ImageIcon("res/flippedCard.png");
			JLabel t = new JLabel();
			t.setIcon(tmp);
			t.setSize(200,300);
			t.setOpaque(false);
			t.setLocation(x,y);
			x+=70;
			this.add(t);
		}
		this.revalidate();
		this.repaint();
	}

}
