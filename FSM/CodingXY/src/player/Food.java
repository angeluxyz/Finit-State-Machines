package player;

import java.awt.Color;
import java.awt.Graphics;

import utils.Constants;

/**
 * @author jgomez
 *
 */
public class Food extends Element {

	public Food(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.color = Color.PINK;
		this.lenght = Constants.FOOD_LENGHT;
	}

	public void drawFood(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, Constants.FOOD_LENGHT, Constants.FOOD_LENGHT);
	}
}
