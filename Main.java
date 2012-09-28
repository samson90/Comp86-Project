/*
 * Drawing in a Canvas
 * RIGHT WAY -- Does drawing in a callback
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

    JLabel scoreLabel = new JLabel ("Score: 0"); 
    JButton scoreButton = new JButton(new ClickHandler("Click"));

    public static void main (String [] args) {
	new Main ();
    }

    public Main () {
	// Window setup
	setSize (800, 600);
	setDefaultCloseOperation(EXIT_ON_CLOSE);

	Container content = getContentPane();
	content.setLayout(new BorderLayout());	
	
	// Put a Canvas at top left
	DrawingArea canvas = new DrawingArea (600, 400);
	canvas.setBorder(new LineBorder(Color.black, 2));
	content.add(canvas, BorderLayout.WEST);   

	// Control panel at bottom
	JPanel controlPanel = new JPanel ();
	controlPanel.setBorder (new LineBorder(Color.black, 2));
	controlPanel.setLayout (new FlowLayout ());

	// Add button to control panel
	controlPanel.add(scoreButton);
	content.add(controlPanel, BorderLayout.SOUTH);

	// Add state panel to right of window
	JPanel statePanel = new JPanel();
	statePanel.setBorder (new LineBorder(Color.black, 2));
	statePanel.add (scoreLabel);	
	content.add(statePanel, BorderLayout.EAST); 	
	
	// Show the window
	setVisible (true);
    }

    private class ClickHandler extends AbstractAction{
	int score;		

	public ClickHandler(String name){
 	    super(name);
  	}

	public void actionPerformed(ActionEvent e){
		scoreLabel.setText("Score: " + String.valueOf(++score));
		System.out.println ("Your score increased by one");
	}
    }	
}
