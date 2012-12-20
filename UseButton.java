import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class UseButton extends JButton implements ActionListener{
  private Game g;
  private Board b;

  public UseButton(Game g, Board b){
    this.g = g;
    this.b = b;
    setText("Use");
    addActionListener(this);
  }
	
  public void actionPerformed(ActionEvent e){
    if (b.getSelectItem().getType() == "switch")
      b.getSelectItem().activate();
  }
}
