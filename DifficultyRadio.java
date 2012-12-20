import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

//creates a pair of radio buttons that pause and unpause the game.
public class DifficultyRadio extends JPanel implements ItemListener{
  private Game game;
	
  public DifficultyRadio(Game game){
    this.game = game;
    //easy button
    ButtonGroup g = new ButtonGroup();
    JCheckBox cb = new JCheckBox("Easy", false);
    add(cb);
    g.add(cb);
    cb.addItemListener(this);
    //medium button
    cb = new JCheckBox("Medium", true);
    add(cb);
    g.add(cb);
    cb.addItemListener(this);
    //hard button
    cb = new JCheckBox("Hard", true);
    add(cb);
    g.add(cb);
    cb.addItemListener(this);
  }

  public void itemStateChanged(ItemEvent e){
    if(e.getStateChange()==ItemEvent.SELECTED){
      if( ((JCheckBox)e.getItem()).getText() == "Easy")
        game.setDifficulty(3);
      else if( ((JCheckBox)e.getItem()).getText() == "Medium")
        game.setDifficulty(2);
      else
        game.setDifficulty(1);
    }
  }
}
