import java.awt.*;
import javax.swing.*;

class Room{
  private Color backcolor;
  private int width, height, x, y;
  private boolean lock;
  // The different items located throughout the room.
  private Item[] contents;
  private int startx, starty;
  private Main main;

  public Room(int x, int y, int w, int h, int startx, int starty, 
              Item[] items, Color c, boolean lock, Main main){
    contents = new Item[items.length];
    for (int i = 0; i < items.length; i++){
      contents[i] = items[i];
    }
    width = w;
    height = h;
    this.x = x;
    this.y = y;
    this.startx = startx;
    this.starty = starty;
    backcolor = c;
    this.lock = lock;
    this.main = main;
  }

  public int getx(){
    return x;
  }

  public int gety(){
    return y;
  }

  public int getstartx(){
    return startx;
  }

  public int getstarty(){
    return starty;
  }

  public int getwidth(){
    return width;
  }

  public int getheight(){
    return height;
  }

  public Color getBackColor(){
    return backcolor;
  }  
	
  public boolean getLock(){
    return lock;
  }

  public void setLock(boolean b){
    lock = b;
  }

  //returns and item that the player has clicked on.
  public Item Hit(int xhit, int yhit){
    for (int i = 0; i < contents.length; i++){
      if (contents[i].Contact(xhit, yhit))
        return contents[i];
    }
    return null;
  }

  public void Draw(Graphics g){
    for(int i = 0; i < contents.length; i++)
      contents[i].Draw(g);
  }
}
