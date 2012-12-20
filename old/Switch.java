import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
import java.io.*;

class Switch extends Item{
	private boolean activate;
  private double frame;
  private BufferedImage switchSheet;
  private BufferedImage[] img = new BufferedImage[3];
  private int countdown = 0;

	public Switch(int init_x, int init_y, Game game, Board board){
    //loads sprite sheet and initializes animation.
    try{
      switchSheet = ImageIO.read(new File("lever.png"));
    }
    catch(FileNotFoundException e){
      System.out.println("File lever.png does not exist");
    }
    catch(EOFException e){
      System.out.println("End of input reached");
    }
    catch(ObjectStreamException e){
      System.out.println("File lever.png is corrupted");
    }
    catch(IOException e){
      System.out.println("Error reading in the file");
    }
		x = init_x;
		y = init_y;
		this.w = 32;
		this.h = 32;
		visible = true;
    activate = false;
		this.board = board;
		this.game = game;
    for(int i = 0;i < 3; i++)
      img[i] = switchSheet.getSubimage(352, 32*i, this.w, this.h);
		type = "switch";
	}

  public void activate(){
    activate = true;
  }

  public void Update(){
    if (activate == true){
      if(frame < 2)
        frame += .1;
      countdown++;
      if (countdown == 120){
        game.newPhase();
        activate = false;
      }
    }
  }

	public void Draw(Graphics g){
		if (visible == true)
      g.drawImage(img[(int)frame], (int)x, (int)y, board);
	}
}
