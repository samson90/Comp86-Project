import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.lang.Math;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;

class Monster extends Item{
  private BufferedImage[] run = new BufferedImage[7];//array of images for
                                                      //animation.
  private BufferedImage[] death = new BufferedImage[4];
  //determines the which sprite frame to dsiplay
  private double anim_count;
  //controls speed
  private double dx, dy;
  private Player player;
  private Room room;
  private int death_count = 0;

  public Monster(int init_x, int init_y, BufferedImage zombieSheet, 
                 BufferedImage marineSheet, Game game, Player player, 
                 Board board){
    x = init_x;
    y = init_y;
    this.w = 57;
    this.h = 57;
    this.game = game;
    this.board = board;
    this.health = 10;
    this.damage = 2;
    this.player = player;
    for(int i = 0; i < 7; i++)
      run[i] = zombieSheet.getSubimage(this.w * i, this.h, this.w, this.h);
    for(int i = 0; i < 4; i++)
      death[i] = marineSheet.getSubimage(470 + 64*i, 710, 64, 64);
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
    if (visible == true)
      g.drawImage(run[(int)anim_count], (int)x, (int)y, board);
    else if(visible == false && death_count < 20)
      g.drawImage(death[(int)(death_count / 5)], (int)x, (int)y, board);
  }

  public void setVisible(boolean b){
    this.visible = b;
    if(this.visible == false && guard != null)
      guard.setLock(false);
      try {
        // Open an audio input stream.
        URL url = this.getClass().getClassLoader().getResource("explosion.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
        // Get a sound clip resource.
        Clip clip = AudioSystem.getClip();
        // Open audio clip and load samples from the audio input stream.
        clip.open(audioIn);
        clip.start();
      } catch (UnsupportedAudioFileException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (LineUnavailableException e) {
        e.printStackTrace();
      }
  }


  public void Move(){
    if(game.getCurrentRoom() == this.room && visible == true){
      //sets speed and direction of enemy.
      double angle = Math.atan2(player.gety() - this.y, player.getx() - 
                                this.x);
      dx = Math.cos(angle) * 1.0/(float)game.getDifficulty();
      dy = Math.sin(angle) * 1.0/(float)game.getDifficulty();
      //enemy stops moving if it hits player and activates death screen.
      if (Math.abs(this.x - player.getx()) > 10 || 
          Math.abs(this.y - player.gety()) > 10){ 
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
    else if(visible == false && death_count < 20)
      death_count += 1;
  }
} 
