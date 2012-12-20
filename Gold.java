import java.awt.*;
import javax.swing.*;
import java.util.*;

class Gold extends Item{
  // gives the monetary value of the gold
  private Image img = Toolkit.getDefaultToolkit().getImage("gold.png");
  private ImageIcon icon = new ImageIcon(img);
  private int timer = 0;
  private int finish;
  private boolean blink;

  public Gold(int init_x, int init_y, Game game, Board board, int finish){
    x = init_x;
    y = init_y;
    this.w = icon.getIconWidth();
    this.h = icon.getIconHeight();
    this.game = game;
    this.board = board;
    this.visible = true;
    type = "gold";
    this.finish = finish;
    this.blink = true;
  }

  public void update(){
    timer++;
    if ((finish * game.getDifficulty()) < timer)
      setVisible(false);
    else if ((finish * game.getDifficulty()) - 200 < timer){
      if (timer % 2 == 0 && visible == true)
        blink = !blink;
    }
  }

  public void Draw(Graphics g){
    if (visible == true && blink == true)
      g.drawImage(img, (int)x, (int)y, board);	
  }
}
