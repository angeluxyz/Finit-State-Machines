package player;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;

import enums.PlayerType;
import enums.State;
import utils.Constants;
import utils.Trigonometry;
import utils.Utils;

public class Player extends Element {

	public State state;
	public PlayerType playerType;
	//public double x, y;
	//public Color color;
	//public int lenght;
	public double speed = Constants.REGULAR_PLAYER_SPEED;
	public double angle;

	public boolean itemCollected = false;

	// Count time
	public long initTime = System.currentTimeMillis();
	// Movement time
	public int movementTimeDuration;

	// Hungry
	public int hungry;
	public long hungryTime = System.currentTimeMillis();

	// Tired
	public int tired;
	public long tiredTime = System.currentTimeMillis();
	public boolean isSleeping = false;

	// Detector
	public Detector detector;

	// Fight
	public int target;
	public boolean atack = false;
	public HashSet<Integer> ignoredEnemies = new HashSet<Integer>();
	public long atackTime = System.currentTimeMillis();

	public int foodToChase;

	public Player() {
		this.playerType = PlayerType.getRandomPlayerType();
		this.x = Utils.getRandomNumberBetweenNumbers(0, Constants.WINDOW_WIDTH);
		this.y = Utils.getRandomNumberBetweenNumbers(0, Constants.WINDOW_HEIGHT);
		this.state = State.EXPLORE;
		this.angle = Utils.getRandomNumber(360);
		this.movementTimeDuration = Utils.getRandomNumberBetweenNumbers(6000, 10000);
		this.hungry = Utils.getRandomNumberBetweenNumbers(10, 30);
		this.tired = Utils.getRandomNumberBetweenNumbers(50, 100);
		this.target = -1;

		detector = new Detector(Constants.DETECTOR_LENGHT);
		this.lenght = Constants.CELL_LENGHT;

		this.setColor();
	}

	/**
	 * The color depends on the player type
	 */
	private void setColor() {
		switch (playerType) {
			case RED:
				this.color = Color.RED;
			break;
			case BLUE:
				this.color = Color.BLUE;
			break;
			case GREEN:
				this.color = Color.GREEN;
			break;
			case YELLOW:
				this.color = Color.YELLOW;
			break;
			case BLACK:
				this.color = Color.BLACK;
			break;
			case CYAN:
				this.color = Color.CYAN;
			break;
			case ORANGE:
				this.color = Color.ORANGE;
			break;
			default:
			
			break;
		}
	}
	
	public void updatePlayer(double dt) {

		// If death do not move anymore
		if(state == State.DEATH) {
			return;
		}

		if(System.currentTimeMillis() - hungryTime > 10000) {
			hungryTime = System.currentTimeMillis();
			
			// if is sleeping slow down hungry reduction
			if(this.isSleeping) {
				this.hungry -= 10;
			} else {
				this.hungry -= 20;
			}
			
			if(this.hungry > 100) {
				this.hungry = 100;
			}
		}
		// Kill player when hungry lower than 0
		if(this.hungry <= 0) {
			this.state = State.DEATH;
		}

		if(System.currentTimeMillis() - tiredTime > 10000) {
			tiredTime = System.currentTimeMillis();
			
			// if not sleeping decrease tired amount
			if(!this.isSleeping) {
				this.tired -= 15;
			} else {
				this.tired += 30;
			}

			if(this.tired > 100) {
				this.tired = 100;
			}
		}
		
		// Slow down speed when tired
		if(this.tired <= 0 && this.tired >= -15) {
			this.speed = 20;
		} else if(tired < -15){
			// Kill player
			System.out.println("Death tired");
			this.state = State.DEATH;
		}
		
		// Calculate the next position when player is not sleeping
		if(!this.isSleeping) {
			x += lenghtDir_x(speed / dt, angle);
			y += lenghtDir_y(speed / dt, angle);
		}
	}

	/**
	 * Calculate the next x coordinate
	 * 
	 * @param distance - the result of d = v / t
	 * @param dir      - current angle
	 * @return
	 */
	private double lenghtDir_x(double distance, double dir) {
		double response = distance * Math.cos(Math.toRadians(dir - 90));
		
		return response;
	}
	
	/**
	 * Calculate the next y coordinate
	 * 
	 * @param distance - the result of d = v / t
	 * @param dir      - current angle
	 * @return
	 */
	private double lenghtDir_y(double distance, double dir) {
		double response = distance * Math.sin(Math.toRadians(dir - 90));
		
		return response;
	}
	
	/**
	 * Change player direction when initTime reaches the defined movementTime 
	 * 
	 * @param player
	 */
	public void changePlayerDirection() {
		if(System.currentTimeMillis() - this.initTime > this.movementTimeDuration) {
			this.initTime = System.currentTimeMillis();

			this.angle = Utils.getRandomNumber(360);
			this.movementTimeDuration = Utils.getRandomNumberBetweenNumbers(3000, 8000);
		}	
	}
	
	/**
	 * If the player is near food activate state CHASE_FOOD for the specific food in range
	 * 
	 * @param player - current player
	 * @return
	 */
	public boolean searchFood(ArrayList<Element> foods) {
		int index = 0;

		for(Element food: foods) {
			
			if(Utils.isFoodInRange(this, food) >= 0) {
				this.foodToChase = index;
				this.angle = Trigonometry.getAngleBetweenTwoPoints(food.x, food.y, this.x, this.y);
				this.state = State.CHASE_FOOD;
				return true;
			}

			index++;
		}
		return false;
	}
	
	/**
	 * If player is outside the player area activate state RETURN_GAME
	 * 
	 * @param currentPlayer - current player
	 * @return
	 */
	public boolean isPlayerOutside() {
		if(this.x < 0 || this.y < 0 || this.x > Constants.WINDOW_WIDTH || this.y > Constants.WINDOW_HEIGHT) {
			this.angle = Trigonometry.getAngleBetweenTwoPoints(Constants.WINDOW_WIDTH /2, Constants.WINDOW_HEIGHT / 2, this.x, this.y);
			this.initTime = System.currentTimeMillis();
			
			this.state = State.RETURN_GAME;
			return true;
		}
		
		return false;
	}

	/**
	 * Draw the player and the player information
	 * 
	 * @param g
	 */
	public void drawPlayer(Graphics g) {		
		g.setColor(Color.BLACK);
		g.drawString("State: " + this.state.getStringState(), (int)x, (int)y - 5);
		g.drawString("Hungry: " + this.hungry, (int)x, (int)y - 17);
		g.drawString("Tired: " + this.tired, (int)x, (int)y - 29);
		
		g.setColor(detector.color);
		g.fillOval((int)((x + lenght/2) - detector.size/2), (int)((y + lenght/2) - detector.size/2), detector.size, detector.size);


		g.setColor(color);
		g.fillOval((int)x, (int)y, lenght, lenght);
	}
}
