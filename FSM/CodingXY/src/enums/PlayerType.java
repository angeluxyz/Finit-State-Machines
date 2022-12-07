package enums;

import java.util.Random;

/**
 * 
 * @author jgomez
 *
 */
public enum PlayerType {

	RED,
	BLUE,
	GREEN,
	YELLOW,
	BLACK,
	CYAN,
	ORANGE;
	
  private static final PlayerType[] VALUES = values();
  private static final int SIZE = VALUES.length;
  private static final Random RANDOM = new Random();
  
  public static PlayerType getRandomPlayerType()  {
	    return VALUES[RANDOM.nextInt(SIZE)];
  }
}
