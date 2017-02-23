package Site;

/**
 * Representations for all valid directions the player faces,
 * along with the calculation of next Direction according to current direction
 * @author zjh
 *
 */
public enum Direction {
	//A value of each direction along with its string direction
	North("north"),
	East("east"),
	South("south"),
	West("west");
	
	//The direction string
	private String direction;
	
	/**
	 * Initialise with the corresponding direction string
	 * @param direction the direction string
	 */
	private Direction(String direction){
		this.direction = direction;
	}
	
	/**
	 * Return direction string of current Direction 
	 * @return the direction string
	 */
	public String getDirection(){
		return direction;
	}
	
	/**
	 * Return the Direction according to its ordinal in enum
	 * @param ordinal the order of direction we are looking for
	 * in enum; for example, in this enum, the ordinal of North is 0,
	 * and the ordinal of East is 1
	 * @return the corresponding direction
	 */
	public Direction getDirection(int ordinal){
		Direction direction=null;
		for(Direction d:Direction.values()){
			if(d.ordinal()==ordinal){
				direction=d;
				break;
			}
		}
		return direction;
	}
	
	/**
	 * Return next direction according to player current direction.
	 * Here, player will turn right, if the current direction is north,
	 * the next direction will be the east.
	 * @param site the current direction player faces
	 * @return the next direction
	 */
	public Direction turnRight(Direction site){
		int nextOrdinal = (site.ordinal()+1)%Direction.values().length;
		return getDirection(nextOrdinal);
	}
	
	/**
	 * Return next direction according to player current direction.
	 * Here, player will turn left, if the current direction is north,
	 * the next direction will be the west.
	 * @param site the current direction player faces
	 * @return the next direction
	 */
	public Direction turnLeft(Direction site){
		int nextOrdinal = (site.ordinal()-1+Direction.values().length)%Direction.values().length;
		return getDirection(nextOrdinal);
	}
	
}
