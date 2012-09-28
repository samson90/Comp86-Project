import java.awt.*;
import javax.swing.*;

public class DrawingArea extends JPanel {
    
    public DrawingArea(int width, int height){
	setPreferredSize(new Dimension(width, height));
    }

    public void paintComponent(Graphics g){
	//          x   y1  x1    y1   
	g.drawLine(40, 160, 360, 160); //tLeft
	g.drawLine(200, 40, 300, 360); //top
	g.drawLine(360, 160, 100, 360); //farright
	g.drawLine(300, 360, 40, 160); //bottom right
	g.drawLine(100, 360, 200, 40); //bottom left
    }
}
