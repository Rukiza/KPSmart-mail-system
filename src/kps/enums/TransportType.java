package kps.enums;

/**
 * An enum representing the transport types available through KPS.
 *
 * @author David
 *
 */
public enum TransportType {
	AIR, LAND, SEA;
	
	/**
	 * Converts the specified string into the corresponding
	 * transport type value. Throws an IllegalArgumentException if
	 * the string cannot be converted.
	 *
	 * @param priority
	 * 		-- string to be converted to transport type
	 *
	 * @return the corresponding transport type value
	 */
	public static TransportType convertStringToTransportType(String type){
		switch(type){
		case "Air":
			return AIR;
		case "Land":
			return LAND;
		case "Sea":
			return SEA;
		default:
			throw new IllegalArgumentException(type+" is not a valid TransportType.");
		}
	}
	
	/**
	 * Converts the specified transport type into a string value corresponding
	 * to that transport type.
	 *
	 * @param day
	 * 		-- the transport type to be converted to a string
	 *
	 * @return the string value of the specified transport type
	 */
	public static String convertTransportTypeToString(TransportType type){
		switch(type){
		case AIR:
			return "Air";
		case LAND:
			return "Land";
		case SEA:
			return "Sea";
		default:
			return null;
		}
	}
}
