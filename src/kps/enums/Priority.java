package kps.enums;

public enum Priority {
	DOMESTIC_AIR, DOMESTIC_LAND, DOMESTIC_SEA, INTERNATIONAL_AIR, INTERNATIONAL_SEA;

	/**
	 * Converts the specified string into the corresponding
	 * priority value. Throws an IllegalArgumentException if
	 * the string cannot be converted.
	 *
	 * @param priority
	 * 		-- string to be converted to priority
	 *
	 * @return the corresponding priority value
	 */
	public static Priority convertStringToPriority(String priority){
		switch(priority){
		case "Domestic Air":
			return DOMESTIC_AIR;
		case "Domestic Land":
			return DOMESTIC_LAND;
		case "Domestic Sea":
			return DOMESTIC_SEA;
		case "International Air":
			return INTERNATIONAL_AIR;
		case "International Sea":
			return INTERNATIONAL_SEA;
		default:
			throw new IllegalArgumentException(priority+" is an invalid priority value.");
		}
	}

	/**
	 * Converts the specified priority into a string value corresponding
	 * to that priority.
	 *
	 * @param day
	 * 		-- the priority to be converted to a string
	 *
	 * @return the string value of the specified priority
	 */
	public static String convertPriorityToString(Priority priority){
		switch(priority){
		case DOMESTIC_AIR:
			return "Domestic Air";
		case DOMESTIC_LAND:
			return "Domestic Land";
		case DOMESTIC_SEA:
			return "Domestic Sea";
		case INTERNATIONAL_AIR:
			return "International Air";
		case INTERNATIONAL_SEA:
			return "International Sea";
		default:
			// will not reach this stage as a priority can only be one of the above cases
			return null;
		}
	}
}
