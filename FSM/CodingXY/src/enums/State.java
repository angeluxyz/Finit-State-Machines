package enums;

/**
 * @author jgomez
 *
 */
public enum State {

	EXPLORE("Explore"),
	RETURN_GAME("Return To Game"),
	HUNGRY("Hungry"),
	CHASE_FOOD("Chase"),
	TIRED("Tired"),
	FIGHT("Fight"),
	DEATH("Death");
	
	private String stateType;
	
	private State(String stateType) {
		this.stateType = stateType;
	}
	
	public String getStringState() {
		return this.stateType;
	}
}
