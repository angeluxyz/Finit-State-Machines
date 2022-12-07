package utils;

import java.util.Random;

import player.Element;
import player.Player;

public class Utils {

	public static int getRandomNumber(int limit) {
		return (int)(Math.random() * limit + 1);
	}
	
	public static int getRandomNumberBetweenNumbers(int low, int hight) {
		return new Random().nextInt(hight - low) + low;
	}
	
	/**
	 * Return the distance between current player and current food
	 * 
	 * @param player
	 * @param food
	 * @return distance between player and food
	 */
	public static double isFoodInRange(Player player, Element food) {
		double distanceXPlayer, distanceYPlayer;
		double distanceXFood, distanceYFood;
		distanceXPlayer = (player.x + player.lenght / 2);
		distanceYPlayer = (player.y + player.lenght / 2);
		distanceXFood = (food.x + food.lenght / 2);
		distanceYFood = (food.y + food.lenght / 2);
		double distanceBetween = Trigonometry.distanceBetweenTwoPoints(
				(int)distanceXPlayer, 
				(int)distanceYPlayer, 
				(int)distanceXFood, 
				(int)distanceYFood);
		
		
		if(distanceBetween < player.detector.size / 2) {

			return distanceBetween;
		}
		return -1;
	}
	
	/**
	 * Return the distance between two players
	 * 
	 * @param player
	 * @param player2
	 * @return distance between player and player2
	 */
	public static double isEnemyInRange(Player player, Player player2) {
		double distanceXPlayer, distanceYPlayer;
		double distanceXFood, distanceYFood;
		distanceXPlayer = (player.x + player.lenght / 2);
		distanceYPlayer = (player.y + player.lenght / 2);
		distanceXFood = (player2.x + player2.lenght / 2);
		distanceYFood = (player2.y + player2.lenght / 2);
		double distanceBetween = Trigonometry.distanceBetweenTwoPoints(
				(int)distanceXPlayer, 
				(int)distanceYPlayer, 
				(int)distanceXFood, 
				(int)distanceYFood);

		if(distanceBetween < player.detector.size) {

			return distanceBetween;
		}
		return -1;
	}
}
