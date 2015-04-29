package kps.events;

import kps.data.wrappers.BasicRoute;
import kps.enums.Day;
import kps.enums.TransportType;

public class MailDeliveryEvent extends BusinessEvent {

	// fields
	private Day day;
	private int weight; // weight is measured in grams
	private int volume; // volume is measured in cubic centimeters
	private TransportType priority;

	public MailDeliveryEvent(long timeLogged, BasicRoute route, Day day, int weight, int volume, TransportType priority){
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
	public TransportType getPriority(){
		return priority;
	}

	/**
	 * Returns an XML representation of this event.
	 */
	public String toXML(){
		String xml = "<mail>\n";
		xml += "\t<day>" + day + "</day>\n";
		xml += "\t<to>" + super.getOrigin() + "</to>\n";
		xml += "\t<from>" + super.getDestination() + "</from>\n";
		xml += "\t<weight>" + weight + "</weight>\n";
		xml += "\t<volume>" + volume + "</volume>\n";
		xml += "\t<priority>" + priority + "</priority>\n";
		xml += "</mail>";
		return xml;
	}
}
