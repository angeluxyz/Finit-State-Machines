package forms;

import javax.swing.JFrame;

import game.Game;
import utils.Constants;

/**
 * @author jgomez
 *
 * Contains the window definition and also it contains the delta time logic
 * 
 * https://drewcampbell92.medium.com/understanding-delta-time-b53bf4781a03
 */
public class MainWindow extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private Game game;
	
	boolean continueGame = true;
	// Delta time
	long lastTime = System.nanoTime();
    double delta = 0.0;
    double UPS = 15;
    double ns = 1000000000.0 / UPS;
    long timer = System.currentTimeMillis();
    int frames = 0;
    
    Thread mainThread;
	
    public MainWindow(){
    	game = new Game();
        game.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        
        this.pack();
        this.setLocation(Constants.WINDOW_X, Constants.WINDOW_Y);
        this.setSize(Constants.WINDOW_WIDTH + this.getInsets().right, Constants.WINDOW_HEIGHT + this.getInsets().top);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(game);
        

        this.setResizable(false);
        this.setVisible(true);
        
        mainThread = new Thread(this);
        mainThread.run();
    }

	@Override
	public void run() {
		while(continueGame) {
	        long now = System.nanoTime();
	        delta += (now - lastTime) / ns;

	        lastTime = now;

	        if (delta >= 1.0) {
		    	game.updateGame(UPS);

	            delta--;
	        }
		}
	}
}
