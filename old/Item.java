import java.awt.*;
import javax.swing.*;

abstract class Item{
	protected double x, y;
  protected int w, h;
	protected boolean visible;
	protected Room guard;
	protected Board board;
	protected Game game;
	protected String type;
  protected int damage;
  protected int health;

	public void toggleVisible(){
		visible = !visible;
    if(guard != null)
      guard.toggleLock();
	}

	public boolean getVisible(){
		return visible;
	}
	
	public void setGuard(Room room){
		this.guard = room;
	}

  public void activate(){
  }

  public double getx(){
		return x;
	}

	public double gety(){
		return y;
	}

	public int getwidth(){
		return w;
	}

	public int getheight(){
		return h;
	}

	public String getType(){
		return type;
	}

	public int getDamage(){
		return damage;
	}

	public int getHealth(){
		return health;
	}

	public boolean Contact(int xhit, int yhit){
		if (xhit <= x + w && xhit >= x && yhit <= y + h && yhit >= y)
			return true;
		else
			return false;
  }

	abstract public void Draw(Graphics g);
}
