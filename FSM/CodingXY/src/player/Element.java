package player;

import java.awt.Color;
import java.awt.Graphics;

public class Element {
	public int x, y;
	public Color color;
	public int lenght;
	
	public void drawFood(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, lenght, lenght);
	}
}
