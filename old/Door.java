import java.awt.*;
import javax.swing.*;

class Door{
  private int x, y, w, h;
  private Board board;  

  public Door(int x, int y, int w, int h, Board board){
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.board = board;
  }
  
  public int getx(){
    return x;
  }
 
  public int gety(){
    return y;
  }

  public int getw(){
    return w;
  }

  public int geth(){
    return h;
  }
}
