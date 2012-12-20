import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.*;

class Key extends Item{
  private BufferedImage img;
  private BufferedImage keySheet;

  public Key(int x, int y, Game game, Board board, BufferedImage img){
    this.x = x;
    this.y = y;
    this.w = 16;
    this.h = 16;
    this.img = img;
    this.game = game;
    this.board = board;
    visible = true;
    type = "key";
  }
	
  public void Draw(Graphics g){
    if(visible == true){
      Graphics2D g2 = (Graphics2D)g;
      AffineTransform transform = new AffineTransform();
      transform.scale(0.25, 0.25);
      transform.translate(3 * this.x, 3 * this.y);
      g2.setTransform(transform);
      g2.drawImage(img, (int)x, (int)y, board);
      transform.translate(3 * -this.x, 3 * -this.y);
      transform.scale(4.0, 4.0);
      g2.setTransform(transform);
    }
  }
}
