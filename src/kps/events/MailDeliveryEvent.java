package kps.events;

import kps.data.wrappers.BasicRoute;
import kps.enums.Day;
import kps.enums.Priority;

public class MailDeliveryEvent extends BusinessEvent {

	// fields
	private Day day;
	private int weight; // weight is measured in grams
	private int volume; // volume is measured in cubic centimeters
	private Priority priority;

	public MailDeliveryEvent(long timeLogged, BasicRoute route, Day day, int weight, int volume, Priority priority){
		super(timeLogged, route);
		this.day = day;
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
	}

	/**
	 * Returns the day that the mail was posted.
	 *
	 * @return day
	 */
	public Day getDay(){
		return day;
	}

	/**
	 * Returns the weight of the mail delivered.
	 * The weight of the mail is measured in grams.
	 *
	 * @return weight (in grams)
	 */
	public int getWeight(){
		return weight;
	}

	/**
	 * Returns the volume of the mail delivered.
	 * The volume of the mail is measured in cubic centimeters.
	 *
	 * @return volume (in cubic centimeters)
	 */
	public int getVolume(){
		return volume;
	}

	/**
	 * Returns the priority of the mail delivered.
	 *
	 * @return priority
	 */
	public Priority getPriority(){
		return priority;
	}

	/**
	 * Returns an XML representation of this event.
	 */
	public String toXML(){
		return "";
	}
	
	/**
	 * Compares this MailDeliveryEvent to the specified object. Only
	 * returns true if the object shares the same field values as this event.
	 * Will not return true if the object is null or not of the same type.
	 */
	public boolean equals(Object o){
		// return true if o is the same object
		if(o == this){
			return true;
		}
		// return false if o is null
		if(o == null){
			return false;
		}
		// check if o is of the same type
		if(o instanceof MailDeliveryEvent){
			// compare field values. return false as soon as one is incorrect
			MailDeliveryEvent obj = (MailDeliveryEvent)o;
			// time
			if(obj.getTimeLogged() != getTimeLogged()){
				return false;
			}
			// origin
			if(!obj.getOrigin().equals(getOrigin())){
				return false;
			}
			// destination
			if(!obj.getDestination().equals(getDestination())){
				return false;
			}
			// day
			if(obj.getDay() != day){
				return false;
			}
			// weight
			if(obj.getWeight() != weight){
				return false;
			}
			// volume
			if(obj.getVolume() != volume){
				return false;
			}
			// priority
			if(obj.getPriority() != priority){
				return false;
			}
		}
		else{
			// object not of the same type, return false
			return false;
		}
		// otherwise o is identical to this object
		return true;
	}
}
