import java.awt.*;
import javax.swing.*;

class Gold extends Item{
	// gives the monetary value of the gold
	private int value;
	private Image img = Toolkit.getDefaultToolkit().getImage("gold.png");
	private ImageIcon icon = new ImageIcon(img);

	public Gold(int init_x, int init_y, int init_value, Game game, 
							Board board){
		x = init_x;
		y = init_y;
		this.w = icon.getIconWidth();
		this.h = icon.getIconHeight();
		value = init_value;
		this.game = game;
		this.board = board;
		visible = true;
		type = "gold";
	}

	// Gold is represented by a yellow circle.
	public void Draw(Graphics g){
		if (visible == true)
			g.drawImage(img, (int)x, (int)y, board);	
	}
}
