import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class PauseRadio extends JPanel implements ItemListener{
	private Game game;
	
	public PauseRadio(Game game){
		this.game = game;

		ButtonGroup g = new ButtonGroup();

		JCheckBox cb = new JCheckBox("Pause", false);
		add(cb);
		g.add(cb);
		cb.addItemListener(this);
	
		cb = new JCheckBox("Unpause", true);
		add(cb);
		g.add(cb);
		cb.addItemListener(this);
	}

	public void itemStateChanged(ItemEvent e){
		if(e.getStateChange()==ItemEvent.SELECTED){
			if( ((JCheckBox)e.getItem()).getText() == "Pause"){
				System.out.println("Pause");
				game.Pause();
			}
			else{
				System.out.println("Unpause");
				game.Unpause();
			}
		}
	}
}
			
