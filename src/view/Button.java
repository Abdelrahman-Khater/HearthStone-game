package view;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Button extends JButton{
	
	private JLabel name;
public Button(String s) {
	super(s);
	name=new JLabel();
	name.setSize(200,100);
	
}

public JLabel getJLabel() {
	return name;
}

}
