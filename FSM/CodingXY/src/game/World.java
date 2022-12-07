package game;

import java.awt.Graphics;
import java.util.ArrayList;

import enums.State;
import player.Element;
import player.Food;
import player.Player;
import utils.Constants;
import utils.Trigonometry;
import utils.Utils;

public class World {

	ArrayList<Player> players = new ArrayList<>();
	ArrayList<Element> foods = new ArrayList<>();
	
	public World() {
		for (int i = 0; i < 15; i++) {
			players.add(new Player());
		}
		
		for (int i = 0; i < 200; i++) {
			this.addFood();
		}
	}

	private void addFood() {
		foods.add(new Food(Utils.getRandomNumberBetweenNumbers(5, Constants.WINDOW_WIDTH), Utils.getRandomNumberBetweenNumbers(5, Constants.WINDOW_HEIGHT)));
	}

	public void paintObjects(Graphics g) {
		for(Element food: foods) {
			food.drawFood(g);
		}
	
		for(Player player: players) {
			player.drawPlayer(g);
		}
	}

	public void updatePlayers(double dt) {

		// Iterate players and remove the death players and convert it to food
		// also update states and coordinates
		for(int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			
			if(player.state != State.DEATH) {
				player.updatePlayer(dt);
				this.evaluateState(player, i);
				
			} else {
				players.remove(i);
				foods.add((Element)player);
				players.add(new Player());
			}
		}
	}

	public void evaluateState(Player player, int index) {
		
		switch(player.state) {
			case EXPLORE:
				if(player.isPlayerOutside()) {
					return;
				}
				
				player.changePlayerDirection();
				
				if(player.hungry < 50) {
					player.state = State.HUNGRY;
				} else if(player.tired < 50) {
					player.state = State.TIRED;
					player.isSleeping = true;
					player.speed = 0;
				}

				searchEnemies(index);
				break;

			case RETURN_GAME:

				if(System.currentTimeMillis() - player.initTime > 6000) {
					player.movementTimeDuration = Utils.getRandomNumberBetweenNumbers(6000, 10000);
				} else {
					return;
				}
				
				if(player.hungry < 50) {
					player.state = State.HUNGRY;
				} else {
					player.state = State.EXPLORE;
				}

				break;
				
			case HUNGRY:
				if(player.isPlayerOutside()) {
					break;
				}

				if(player.tired <= -10) {
					player.state = State.TIRED;
					player.isSleeping = true;
					player.speed = 0;
				} else if(player.hungry >= 100) {
					player.state = State.EXPLORE;
				}

				if(player.searchFood(foods)) {

					return;
				} else {
					player.changePlayerDirection();
				}

				break;
			case CHASE_FOOD:
				if(player.isPlayerOutside()) {
					return;
				}
				Element currentFood = foods.get(player.foodToChase);

				if(currentFood != null) {
					double foodDistance = Utils.isFoodInRange(player, currentFood);

					if(foodDistance >= 0 && foodDistance < currentFood.lenght) {
						if(player.hungry + 50 <= 100) {
							player.hungry += 50;
						} else {
							player.hungry = 100;
						}
						
						this.foods.remove(player.foodToChase);
						this.addFood();
						
						currentFood = null;
						player.state = State.HUNGRY;
					} else {
						player.searchFood(foods);
					}
				} else {
					player.searchFood(foods);
					player.state = State.HUNGRY;

					return;
				}

				break;
			case TIRED:
				// Prioritize hungry over TIRED
				if(player.hungry <= 10) {
					player.state = State.HUNGRY;
					player.isSleeping = false;
					player.speed = Constants.REGULAR_PLAYER_SPEED;
				} else if(player.tired >= 100) {
					player.state = State.EXPLORE;
					player.isSleeping = false;
					player.speed = Constants.REGULAR_PLAYER_SPEED;
				}

				break;
			case FIGHT:
					Player enemyPlayer = players.get(player.target);
					player.angle = Trigonometry.getAngleBetweenTwoPoints(enemyPlayer.x, enemyPlayer.y, player.x, player.y);
					double distance = Utils.isEnemyInRange(player, enemyPlayer);

					// If player state is fight and we have a distance < 15, then reduce the food/tired amount
					// Also ignore the enemy player in order to stop the attacks
					if(distance > 0 && distance < player.lenght) {
						if(Utils.getRandomNumberBetweenNumbers(0, 100) < 15) {
							enemyPlayer.state = State.FIGHT;
							enemyPlayer.target = index;
							enemyPlayer.atack = true;
						}
						enemyPlayer.hungry -= 10;
						enemyPlayer.tired -= 10;
						
						player.ignoredEnemies.add(player.target);
						player.atack = false;
						player.state = State.EXPLORE;
						player.target = -1;

					}

				break;
		default:
			break;
		}
	}
	
	/**
	 * Use current index to compare against all the other players, so we can
	 * know which one is in range
	 * 
	 * @param enemyIndex
	 */
	public void searchEnemies(int enemyIndex) {
		Player currentPlayer = players.get(enemyIndex);
		for(int i = 0; i < players.size(); i++) {
			// If current player is not being ignored and the player is not the same as the current index
			if(!currentPlayer.ignoredEnemies.contains(i) && currentPlayer.target == -1 && i != enemyIndex) {
				// If enemy is in range
				if(Utils.isEnemyInRange(currentPlayer, players.get(i)) != -1) {
					if(currentPlayer.state == State.EXPLORE) {
						// Low probability to fight 30 of 100 (random number)
						if(Utils.getRandomNumberBetweenNumbers(0, 100) < 30) {
							currentPlayer.state = State.FIGHT;
							currentPlayer.target = i;
							currentPlayer.atack = true;

							// fight - attack this enemy
						} else {
							// not fight - ignore this enemy
							players.get(enemyIndex).ignoredEnemies.add(i);
						}
					}
				}
			} else {
				// Remove the current player from the map ignoredEnemies, 
				// so they will be able to fight in the future
				if(Utils.isEnemyInRange(currentPlayer, players.get(i)) == -1) {
					players.get(i).ignoredEnemies.remove(enemyIndex);
					currentPlayer.ignoredEnemies.remove(i);
				}
			}
		}
	}
}
