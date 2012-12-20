import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.*;

class Key extends Item{
  private BufferedImage img;
  private BufferedImage keySheet;

	public Key(int x, int y, Game game, Board board){
		this.x = x;
		this.y = y;
		this.w = 64;
		this.h = 64;
    try{
      keySheet = ImageIO.read(new File("keycard.png"));
    }
    catch(FileNotFoundException e){
      System.out.println("File keycard.png does not exist");
    }
    catch(EOFException e){
      System.out.println("End of input reached");
    }
    catch(ObjectStreamException e){
      System.out.println("File keycard.png is corrupted");
    }
    catch(IOException e){
      System.out.println("Error reading in the file");
    }
    img = keySheet.getSubimage(64, 64, this.w, this.h);
		this.w = 64 / 4;
		this.h = 64 / 4;
		this.game = game;
		this.board = board;
		visible = true;
		type = "key";
	}
	
  // Key is represented by a gray rectangle
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
