import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

//creates a pair of radio buttons that pause and unpause the game.
public class PauseRadio extends JPanel implements ItemListener{
  private Game game;
	
  public PauseRadio(Game game){
    this.game = game;
    //creates the pause button
    ButtonGroup g = new ButtonGroup();
    JCheckBox cb = new JCheckBox("Pause", false);
    add(cb);
    g.add(cb);
    cb.addItemListener(this);
    //creates the unpause button
    cb = new JCheckBox("Unpause", true);
    add(cb);
    g.add(cb);
    cb.addItemListener(this);
  }

  public void itemStateChanged(ItemEvent e){
    if(e.getStateChange()==ItemEvent.SELECTED){
      if( ((JCheckBox)e.getItem()).getText() == "Pause")
        game.Pause();
      else
        game.Unpause();
    }
  }
}
			
