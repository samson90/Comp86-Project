import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MoveButton extends JButton implements ActionListener{
	private Game g;
	private Board b;
  private Player player;
	
	public MoveButton(Game g, Board b){
		this.g = g;
		this.b = b;
		setText("Move");
		addActionListener(this);
    player = g.getPlayer();
	}

	public void actionPerformed(ActionEvent e){
    if(g.getCurrentRoom() == b.getSelectRoom())
      player.setDestination(b.getSelectX(), b.getSelectY());
		g.moveRoom(b.getSelectRoom());
		b.reDraw();
	}
}
