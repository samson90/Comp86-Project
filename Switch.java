import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
import java.io.*;

class Switch extends Item{
  private boolean activate;
  private double frame;
  private BufferedImage[] img = new BufferedImage[3];
  private int countdown = 0;

  public Switch(int init_x, int init_y, BufferedImage sheet, Game game, 
                Board board){
    x = init_x;
    y = init_y;
    this.w = 32;
    this.h = 32;
    visible = true;
    activate = false;
    this.board = board;
    this.game = game;
    for(int i = 0;i < 3; i++)
      img[i] = sheet.getSubimage(352, 32*i, this.w, this.h);
    type = "switch";
  }

  public void activate(){
    activate = true;
  }

  //animates the switch and starts and new game phase when it's activated.
  public void Update(){
    if (activate == true){
      if(frame < 2)
        frame += .1;
      countdown++;
      if (countdown == 120){
        game.setGameOver();
        activate = false;
      }
    }
  }

  public void Draw(Graphics g){
    if (visible == true)
      g.drawImage(img[(int)frame], (int)x, (int)y, board);
  }
}
