import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyButton extends JButton implements ActionListener {
    
    int score = 0;
    public MyButton (String label) {
	setText (label);
	addActionListener (this);
    }

    public void actionPerformed(ActionEvent e) {
	System.out.println ("You've increase your score by 1");
	score += 1;
    }
}
