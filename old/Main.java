import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class Main extends JFrame implements ChangeListener{
  // buttons
  private MoveButton btnMove;
  private TakeButton btnTake;
  private AttackButton btnAttack;
  private UseButton btnUse;
  private JPanel buttonPanel;

  // state Panel labels
  private JPanel statePanel;
  private JLabel scoreLabel;
  private JLabel inventoryLabel;
  private String inventoryText;

  private Game g;
  private Board gameBoard;
  private Container content;

  public static void main (String [] args) {
    new Main();
  }

  public Main () {
    // Window setup
    setSize (1366, 768);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    content = getContentPane();
    content.setLayout(new BorderLayout());	
	
    // State panel to the right.
    statePanel = new JPanel();
    statePanel.setLayout (new GridLayout(3, 1));
    statePanel.setBorder(new LineBorder(Color.black));
    content.add(statePanel, BorderLayout.EAST);

    // Add score, instructions, and iventory to state panel.
    scoreLabel = new JLabel("Score: 0");
    JLabel instructionsLabel = 
      new JLabel("<html>" +
        "You are an operative for the<br>Alliance fleet. You've been<br>" + 
        "asked to investigate a colonial<br>station that has not been<br>"+
        "responding to outside transmissions.<br> Find a way into the<br>"+
        "tram to the south of you and<br>head toward the main <br>"+
        "station. Click a spot and click<br> 'move' to move around<br>" +
        "the room. Click on an adjacent<br> and click 'move' to move<br>"+
        "to that room. Click on an object<br> and click 'take' to<br>" +
        "pick it up." + 
        "</html>");
    inventoryText = new String("Inventory:");
    inventoryLabel = new JLabel("<html>" + inventoryText + "</html>");
		
    // the progressPanel is stored in a scroll pane.
    statePanel.add(scoreLabel);
    statePanel.add(inventoryLabel);
    statePanel.add(instructionsLabel);
	
    // Put game at top left
    g = new Game(this);
    gameBoard = g.getGameBoard();
    content.add(gameBoard, BorderLayout.CENTER);   
    
    // Control panel at bottom
    JPanel controlPanel = new JPanel ();
    controlPanel.setBorder (new LineBorder(Color.black));
    controlPanel.setLayout (new GridLayout(1, 3));
    content.add (controlPanel, BorderLayout.SOUTH);

    // Creates the button panel.
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2,2));
    btnMove = new MoveButton(g, gameBoard);
    btnTake = new TakeButton(g, gameBoard, this);
    btnAttack = new AttackButton(g, gameBoard);
    btnUse = new UseButton(g, gameBoard);
    buttonPanel.add(btnMove);
    buttonPanel.add(btnTake);
    buttonPanel.add(btnAttack);
    buttonPanel.add(btnUse);

    // Widgets on the control panel
    PauseRadio sr = new PauseRadio(g);
    JSlider inventorySlider = new JSlider(JSlider.HORIZONTAL, 
                                          g.minCapacity(), g.maxCapacity(),
                                          g.getCapacity());
    inventorySlider.addChangeListener(this);
    inventorySlider.setMajorTickSpacing(1);
    inventorySlider.setPaintTicks(true);
    inventorySlider.setPaintLabels(true);
	
    // Add widgets to control Panel.  	
    controlPanel.add (buttonPanel);
    controlPanel.add (sr);
    controlPanel.add (inventorySlider);
    controlPanel.add (new JLabel("Capacity"));

    // Show the window
    setVisible (true);
  }

  public void setButtons(Item target){
    if(target != null){
      if (target.getType() == "monster"){
        btnTake.setEnabled(false);
        btnAttack.setEnabled(true);
        btnMove.setEnabled(true);
        btnUse.setEnabled(false);
      }
      else if(target.getType() == "switch"){
        btnAttack.setEnabled(false);
        btnTake.setEnabled(false);
        btnMove.setEnabled(true);
        btnUse.setEnabled(true);
      }
      else{
        btnAttack.setEnabled(false);
        btnTake.setEnabled(true);
        btnMove.setEnabled(true);
        btnUse.setEnabled(false);
      }  
    }
    else{
      btnTake.setEnabled(false);
      btnUse.setEnabled(false);
      btnAttack.setEnabled(false);
      btnMove.setEnabled(true);
    }
  }

  public void deactivateButtons(){
    btnTake.setEnabled(false);
    btnUse.setEnabled(false);
    btnAttack.setEnabled(false);
    btnMove.setEnabled(false);
  }
	
  public void updateInventory(Item item){
    statePanel.remove(instructionsLabel);
    statePanel.remove(inventoryLabel);
    inventoryLabel = null;
    inventoryText += "<br> " + item.getType();
    inventoryLabel = new JLabel("<html>" + inventoryText + "</html>");
    statePanel.add(inventoryLabel);
    statePanel.add(instructionsLabel);
    validate();
  }

  public void updateInstructions(String instructions){
    statePanel.remove(instructionsLabel);
    instructionsLabel = null;
    instructionsLabel = new JLabel(instructions);
    statePanel.add(instructionsLabel);
    validate();
  }

  public void stateChanged(ChangeEvent e){
    JSlider source = (JSlider)e.getSource();
    if (!source.getValueIsAdjusting())
      g.setCapacity((int)source.getValue());
  }
	
  public void updateScore(int score){
    scoreLabel.setText("Score: " + Integer.toString(score));	
  }
}
