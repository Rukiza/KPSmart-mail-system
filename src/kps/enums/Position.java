package kps.enums;

/**
 * An enum representing the positions that can be held by an
 * employee of KPS.
 *
 * @author David Sheridan
 *
 */
public enum Position {
	CLERK, MANAGER;


	/**
	 * Converts the specified string to the corresponding position
	 * value. Throws an IllegalArgumentException if the string does
	 * not correspond to a correct position value.
	 *
	 * @param position
	 * 		-- the string to be converted
	 *
	 * @return the corresponding position value
	 */
	public static Position convertStringToPosition(String position){
		switch(position){
		case "Clerk":
			return CLERK;
		case "Manager":
			return MANAGER;
		default:
			throw new IllegalArgumentException(position+" is not a valid position");
		}
	}
	/**
	 * Converts the specified position to the corresponding string
	 * value.
	 *
	 * @param position
	 * 		-- the position to be converted
	 *
	 * @return the corresponding string value
	 */
	public static String convertPositionToString(Position position){
		switch(position){
		case CLERK:
			return "Clerk";
		case MANAGER:
			return "Manager";
		default:
			// will not reach this state as position can only be one of the above cases
			return null;
		}
	}
}
