package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import utils.Constants;

/**
 * @author jgomez
 *
 * Contains a component that can handle the drawing functionality
 */
public class Game extends JComponent {

	private static final long serialVersionUID = 1L;
	private World world;

	public Game() {
		this.world = new World();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		world.paintObjects(g);
	}
	
	/**
	 * This method will be called UPS times (Updates Per Second)
	 * 
	 * @param dt - selected UPS
	 */
	public void updateGame(double dt) {
		world.updatePlayers(dt);

		this.repaint();
	}
}
