package player;

import java.awt.Color;

/**
 * @author jgomez
 *
 * This class represents the big circle being drawn in every player,
 * the size of the circle is also the max distance that can be used 
 * to detect food and players.
 *
 */
public class Detector {
	public Color color;
	public int size;
	
	public Detector() {
		this.color = new Color(37, 150, 190, 210);
	}

	public Detector(int size) {
		this.color = new Color(37, 150, 190, 210);
		this.size = size;
	}
}
