import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class TakeButton extends JButton implements ActionListener{
  private Game g;
  private Board b;
  private Main main;

  public TakeButton(Game g, Board b, Main main){
    this.g = g;
    this.b = b;
    this.main = main;
    setText("Take");
    addActionListener(this);
  }
	
  public void actionPerformed(ActionEvent e){
    if (g.getCapacity() > g.getNumItems() || 
        b.getSelectItem().type == "gold"){
      g.takeItem(b.getSelectItem());
      b.reDraw();
    }
    else
      main.updateInstructions("Can't carry anymore items");
  }
}
