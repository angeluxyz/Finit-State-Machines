package utils;

public class Trigonometry {

	/**
	 * Returns the angle between two points (x, y)
	 * 
	 * @param x1 - x coordinate of object 1 
	 * @param y1 - y coordinate of object 1 
	 * @param x2 - x coordinate of object 1 
	 * @param y2 - y coordinate of object 1 
	 * @return  angle between (x1, y1) and (x2, y2)
	 */
	public static double getAngleBetweenTwoPoints(double x1, double y1, double x2, double y2) {
		double deltaX = Math.abs(x2 - x1);
		double deltaY = Math.abs(y2 - y1);
		
		double angleInDegrees = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
		
		if(y2 > y1) {
			if(x2 < x1) {
				angleInDegrees = 90 - angleInDegrees;
			} else {
				angleInDegrees += 270;
			}
		} else if(x2 < x1) {
			angleInDegrees += 90;
		} else {
			angleInDegrees = 270 - angleInDegrees;
		}
		
		return angleInDegrees;
	}
	
	/**
	 * 
	 * @param x1 - x coordinate of object 1 
	 * @param y1 - y coordinate of object 1 
	 * @param x2 - x coordinate of object 1 
	 * @param y2 - y coordinate of object 1 
	 * @return distance between x1, y1 and x2, y2
	 */
	public static double distanceBetweenTwoPoints(int x1, int y1, int x2, int y2) {
		double response;
		
		response = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
		
		return response;
	}
}
