import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JComponent implements MouseListener{
	private Room layout[];
  private Door doors[]; 
	private Room selectRoom; //player can select a room by clicking on it.
	private Item selectItem; //player can select an item in his current room
													 //by clicking on it.
  private int select_x, select_y;
	private Game game;
	private Main main;
	private int width = 1152;
	private int height = 720;
  private Player player;
  private Color brown = new Color(156, 93, 82);
  private boolean draw;

  public Board(Main main, Game game, Player player, boolean draw){
		this.main = main;
    setPreferredSize(new Dimension(width, height));
		addMouseListener(this);
		this.game = game;
    this.player = player;
    select_x = 200;
    select_y = 150;
    selectRoom = null;
    this.draw = draw;
	}

	public void createRoomsandDoors(Room[] layout, Door[] doors){
		this.layout = new Room[layout.length];
		for(int i = 0; i < layout.length; i++)
			this.layout[i] = layout[i];
    this.doors = new Door[doors.length];
		for(int i = 0; i < doors.length; i++)
			this.doors[i] = doors[i];
    selectRoom = layout[0];
	}

	public void mousePressed (MouseEvent event){
		int i = event.getPoint().x;
		int j = event.getPoint().y;
		// Selects the room player has clicked on. Selected rooms are outlined
		// in green.
    for(int x = 0; x < layout.length; x++){
      if(i >= layout[x].getx() && 
        i <= layout[x].getx() + layout[x].getwidth() &&
				j >= layout[x].gety() &&
				j <= layout[x].gety() + layout[x].getheight()){
				selectRoom = layout[x];
			}
		}
		// selected item is set with player mouse click.
		Item candidateItem = game.getCurrentRoom().Hit(i, j);
		if(candidateItem != null){
			if(candidateItem.getVisible() == true)
				selectItem = candidateItem;
		}
    // put move marker to show destination of character
    if(selectRoom == game.getCurrentRoom() && candidateItem == null &&
       i > game.getCurrentRoom().getx() && 
       i < game.getCurrentRoom().getx() + game.getCurrentRoom().getwidth() 
       && j > game.getCurrentRoom().gety() &&
       j < game.getCurrentRoom().gety()+game.getCurrentRoom().getheight()){
      select_x = i;
      select_y = j;
    } 
      
		main.setButtons(selectItem);
		repaint();
	}

	public Room getSelectRoom(){
		return selectRoom;
	}

  public int getSelectX(){
    return select_x;
  }
  
  public int getSelectY(){
    return select_y;
  }

  public void setSelect(int x, int y){
    this.select_x = x; this.select_y = y;
  }

	public Item getSelectItem(){
		return selectItem;
	}

	public void reDraw(){
		repaint();
	}

  public void setDraw(boolean draw){
    this.draw = draw;
  }

	public void paintComponent(Graphics g){
    System.out.println(draw);
    if(draw){
		  Graphics2D g2 = (Graphics2D) g;
		  //Draws the rooms.
      for(int x = 0; x < layout.length; x++){
        g.setColor(layout[x].getBackColor());
        g.fillRect(layout[x].getx(), layout[x].gety(),layout[x].getwidth(), 
								   layout[x].getheight());
			  layout[x].Draw(g);
      }
		  //draws doors
      g.setColor(brown);
      for(int x = 0; x < doors.length; x++)
        g.fillRect(doors[x].getx(), doors[x].gety(), doors[x].getw(), 
                   doors[x].geth());
		  // Colors the room the player is in in a lighter shade of gray.
      g.setColor(Color.lightGray);
		  g.fillRect(game.getCurrentRoom().getx(), 
			  				 game.getCurrentRoom().gety(),
				  			 game.getCurrentRoom().getwidth(), 
					  		 game.getCurrentRoom().getheight());
		  game.getCurrentRoom().Draw(g);
		  // Outlines the selected room in green.
		  if(selectRoom != null){
			  g2.setStroke(new BasicStroke(3));
			  g.setColor(Color.green);
			  g.drawRect(selectRoom.getx(), selectRoom.gety(), 
				  				 selectRoom.getwidth(), selectRoom.getheight());
		  }
		  //outlines selected item.
		  if(selectItem != null){
			  if(selectItem.getVisible() == true){
				  g.setColor(Color.red);
				  g.drawRect((int)selectItem.getx(), (int)selectItem.gety(), 
                     selectItem.getwidth(), selectItem.getheight());
			  }
		  }
      //draws selected spot.
      if(selectRoom == game.getCurrentRoom()){
        g.setColor(Color.green);
        g.fillOval(select_x - 10, select_y - 10, 20, 20);
      }
      //draws player
      player.Draw(g);
    }
    //checks for game over screens
    if(game.getGameOver()){
      g.setColor(Color.black);
      g.fillRect(0, 0, width, height);
      g.setColor(Color.white);
      g.drawString("Congratulations! You saved the Station!", 450, 100);
      g.drawString("Final Score: " + game.getScore(), 500, 200);
    }

    if(game.getDeath()){
      g.setColor(Color.black);
      g.fillRect(0, 0, width, height);
      g.setColor(Color.white);
      g.drawString("You've Died...", 500, 100);
      g.drawString("Final Score: " + game.getScore(), 500, 200);
    }
	}

	public void mouseClicked (MouseEvent event) {}
  public void mouseReleased (MouseEvent event) {}
  public void mouseEntered (MouseEvent event) {}
  public void mouseExited (MouseEvent event) {}
}
