import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.lang.Math;
import java.awt.geom.*;

class Monster extends Item{
  private BufferedImage[] run = new BufferedImage[12];
  private double anim_count;
  private double dx, dy;
  private Player player;
  private Room room;

  public Monster(int init_x, int init_y, BufferedImage sheet, Game game, 
                 Player player, Board board){
		x = init_x;
    y = init_y;
		this.w = 57;
		this.h = 57;
		this.game = game;
		this.board = board;
		this.health = 10;
		this.damage = 2;
    this.player = player;
    for(int i = 0; i < 7; i ++)
      run[i] = sheet.getSubimage(this.w * i, this.h, this.w, this.h); 
    visible = true;
    anim_count = 0;
		type = "monster";
  }

	public int getHealth(){
		return health;
	}

  public void setRoom(Room room){
    this.room = room;
  }

	public void Draw(Graphics g){
		if (visible == true){
      Graphics2D g2 = (Graphics2D)g;
      AffineTransform original = g2.getTransform();
      AffineTransform transform = new AffineTransform();
      /* FIX THIS. WON'T FLIP THE IMAGE
      if(flip == true){
        transform.scale(-1.0, 1.0);
        transform.translate(-run[0].getWidth(null), 0);
      }
      */
      g2.setTransform(transform);
      g2.drawImage(run[(int)anim_count], (int)x, (int)y, board);
      g2.setTransform(original);
    }
	}

	public void Move(){
    if(game.getCurrentRoom() == this.room){
      double angle = Math.atan2(player.gety() - this.y, player.getx() - 
                                this.x);
      dx = Math.cos(angle) * 0.5;
      dy = Math.sin(angle) * 0.5;
      if (Math.abs(this.x - player.getx()) > 10 || 
                   Math.abs(this.y - player.gety()) == 0){ 
        this.x += dx;
        this.y += dy;
        anim_count = (anim_count + .1) % 7;
      }
      else{
        anim_count = 0;
        if(visible)
          game.setDeath();
      }
    }
	}
} 
