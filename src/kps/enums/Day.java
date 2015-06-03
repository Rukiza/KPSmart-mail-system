package kps.enums;

/**
 * An enum representing the days of the week.
 *
 * @author David
 *
 */
public enum Day {
	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

	/**
	 * Converts the specified string into the corresponding
	 * day of the week. Throws an IllegalArgumentException if
	 * the string cannot be converted.
	 *
	 * @param day
	 * 		-- string to be converted into day
	 *
	 * @return the corresponding day value
	 */
	public static Day convertStringToDay(String day){
		switch(day){
		case "Monday":
			return MONDAY;
		case "Tuesday":
			return TUESDAY;
		case "Wednesday":
			return WEDNESDAY;
		case "Thursday":
			return THURSDAY;
		case "Friday":
			return FRIDAY;
		case "Saturday":
			return SATURDAY;
		case "Sunday":
			return SUNDAY;
		default:
			throw new IllegalArgumentException(day+" is not a valid day of the week.");
		}
	}

	/**
	 * Converts the specified day into a string value corresponding
	 * to that day.
	 *
	 * @param day
	 * 		-- the day to be converted to a string
	 *
	 * @return the string value of the specified day
	 */
	public static String convertDayToString(Day day){
		switch(day){
		case MONDAY:
			return "Monday";
		case TUESDAY:
			return "Tuesday";
		case WEDNESDAY:
			return "Wednesday";
		case THURSDAY:
			return "Thursday";
		case FRIDAY:
			return "Friday";
		case SATURDAY:
			return "Saturday";
		case SUNDAY:
			return "Sunday";
		default:
			// will not reach this stage as a day can only be one of the above cases
			return null;
		}
	}

	/**
	 * Returns the day proceeding the specified day.
	 *
	 * @param day
	 *
	 * @return the next day
	 */
	public static Day getNextDay(Day day){
		switch(day){
		case MONDAY:
			return TUESDAY;
		case TUESDAY:
			return WEDNESDAY;
		case WEDNESDAY:
			return THURSDAY;
		case THURSDAY:
			return FRIDAY;
		case FRIDAY:
			return SATURDAY;
		case SATURDAY:
			return SUNDAY;
		case SUNDAY:
			return MONDAY;
		default:
			// will not reach this stage as a day can only be one of the above cases
			return null;
		}
	}
}
