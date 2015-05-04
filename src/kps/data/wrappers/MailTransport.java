package kps.data.wrappers;

import kps.enums.Day;

/**
 * Represents the transportation of mail for a transport firm.
 * It is a wrapper for the trip duration, departure frequency and
 * the day of delivery.
 *
 * @author David
 *
 */
public class MailTransport {

	// fields
	private int tripDuration; // in hours
	private int departureFrequency; // in hours
	private Day dayDelivered;

	/**
	 * Constructs a new MailTransport with the specified trip duration,
	 * departure freqency and day of delivery.
	 *
	 * @param tripDuration
	 * 		-- duration of the trip (in hours)
	 * @param departureFrequency
	 * 		-- the time between departures (in hours)
	 * @param dayDelivered
	 * 		-- the day that deliveries occur on
	 */
	public MailTransport(int tripDuration, int departureFrequency, Day dayDelivered){
		this.tripDuration = tripDuration;
		this.departureFrequency = departureFrequency;
		this.dayDelivered = dayDelivered;
	}

	/**
	 * Returns the amount of time in hours it takes for mail to arrive
	 * at its destination from the origin.
	 *
	 * @return trip duration (in hours)
	 */
	public int getTripDuration(){
		return tripDuration;
	}

	/**
	 * Returns the departure frequency of mail. The departure
	 * frequency is the time in hours between two subsequent departures.
	 *
	 * @return departure frequency (in hours)
	 */
	public int getDepartureFrequency(){
		return departureFrequency;
	}

	/**
	 * Returns the day that mail departs to be delivered.
	 *
	 * @return day delivered
	 */
	public Day getDayDelivered(){
		return dayDelivered;
	}
	
	public boolean equals(Object o){
		if(o == this){
			return true;
		}
		if(o == null){
			return false;
		}
		if(o instanceof MailTransport){
			if(((MailTransport)o).getTripDuration() != tripDuration){
				return false;
			}
			if(((MailTransport)o).getDepartureFrequency() != departureFrequency){
				return false;
			}
			if(((MailTransport)o).getDayDelivered() != dayDelivered){
				return false;
			}
		}
		else{
			return false;
		}
		return true;
	}
}
