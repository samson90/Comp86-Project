import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.lang.Math;
import java.awt.geom.*;

class Player extends Item{
  private BufferedImage[] run = new BufferedImage[5];//array of images for
                                                     //animation
  private double anim_count;
  private double dest_x, dest_y;
  private double dx, dy;
  private boolean flip;

  public Player(int x, int y, BufferedImage sheet, Game game, Board board){
    this.x = x;
    this.y = y;
    this.dest_x = this.x;
    this.dest_y = this.y;
    this.w = 35;
    this.h = 30;
    for(int i = 0; i < 5; i++)
      run[i] = sheet.getSubimage(305 + w * i, 33, w, this.h);
    this.game = game;
    this.board = board;
    this.flip = true;
    visible = true;
    anim_count = 0;
    this.health = 50;
    this.damage = 0;
  }

  public void setDamage(int damage){
    this.damage += damage;
  }

  public void setPosition(double x, double y){
    this.x = x;
    this.y = y;
  }

  public void Draw(Graphics g){
    if(visible == true)
      g.drawImage(run[(int)anim_count], (int)x, (int)y, board);
  }

  //controls movement and animation.
  public void Move(){
    if (Math.abs(this.x - dest_x) > 5 || Math.abs(this.y - dest_y) > 5){ 
      this.x += dx;
      this.y += dy;
      anim_count = (anim_count + .2) % 5;
    }
    else{
      dx = 0;
      dy = 0;
      anim_count = 0;
    }
  }

  //sets the coordinates for the player to go to.
  public void setDestination(int x, int y){
    double angle;
    this.dest_x = x - 20;
    this.dest_y = y - 20;
    if(this.dest_x - this.x > 0)
      flip = true;
    else
      flip = false;
    angle = Math.atan2(dest_y - this.y, dest_x - this.x);
    dx = Math.cos(angle) * 2.0;
    dy = Math.sin(angle) * 2.0;
  }
}
