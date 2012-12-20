import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class AttackButton extends JButton implements ActionListener{
	private Game g;
	private Board b;

	public AttackButton(Game g, Board b){
		this.g = g;
		this.b = b;
		setText("Attack");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		g.attackItem(b.getSelectItem());
		b.reDraw();
	}
}
