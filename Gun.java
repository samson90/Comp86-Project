import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;

class Gun extends Item{
	private int value;
  private BufferedImage img;

	public Gun(int init_x, int init_y, int init_damage, int init_value,
						 BufferedImage sheet, Game game, Board board){
		x = init_x;
		y = init_y;
		this.w = 20;
		this.h = 10;
		damage = init_damage;
		value = init_value;
		visible = true;
		this.board = board;
		this.game = game;
    img = sheet.getSubimage(553, 523, this.w, this.h);
    this.w *= 2;
    this.h *= 2;
		type = "gun";
	}

  public void setVisible(boolean b){
    this.visible = b;
    try {
      // Open an audio input stream.
      URL url = this.getClass().getClassLoader().getResource("gun.wav");
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

	public void Draw(Graphics g){
		if (visible == true){
      Graphics2D g2 = (Graphics2D)g;
      AffineTransform transform = new AffineTransform();
      transform.scale(2.0, 2.0);
      transform.translate(0.5 * -this.x, 0.5 * -this.y);
      g2.setTransform(transform);
      g2.drawImage(img, (int)x, (int)y, board);
      transform.translate(0.5 * this.x, 0.5 * this.y);
      transform.scale(0.5, 0.5);
      g2.setTransform(transform);
    }
	}
}
