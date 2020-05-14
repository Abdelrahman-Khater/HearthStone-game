package view.Buttonlib;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;
import model.heroes.*;
public class manaCrystalBar extends JLabel{
	private JTextField total;
	Hero h;
	public manaCrystalBar(Hero h) {
		this.h = h;
		this.setSize(400,50);
		this.setLayout(new FlowLayout());
		this.setIcon(new ImageIcon("res/manabar.png"));
		
		total=new JTextField();
		total.setOpaque(false);
		total.setBorder(null);
		total.setBackground(Color.BLACK);
		total.setSize(30,60);
		//deck.setContentAreaFilled(false);
		total.setLayout(null);
		total.setFont(new Font("Arial",Font.BOLD,20));
		total.setForeground(Color.WHITE);
		total.setText("/"+h.getTotalManaCrystals());
		total.setEditable(false);
		total.revalidate();
		total.repaint();
		
	    this.add(total);
		this.revalidate();
		this.repaint();
		update();
	}
	public void update() {
		ImageIcon img = new ImageIcon("res/manaCrystal.png");
		this.removeAll();
		total.setText("/"+h.getTotalManaCrystals());
		for(int i=0;i<h.getCurrentManaCrystals();i++) {
			ImageIcon tmp = new ImageIcon("res/manaCrystal.png");
			JLabel t = new JLabel();
			t.setIcon(tmp);
			t.setSize(50,50);
			this.add(t);
		}
	    this.add(total);
		this.revalidate();
		this.repaint();
	}
}
