package kps.events;

import kps.data.wrappers.BasicRoute;

public abstract class BusinessEvent {

	// fields
	private long timeLogged;
	private BasicRoute route;

	public BusinessEvent(long timeLogged, BasicRoute route){
		this.timeLogged = timeLogged;
		this.route = route;
	}

	/**
	 * Returns the time at which this event was logged.
	 *
	 * @return time event was logged (as a long)
	 */
	public long getTimeLogged(){
		return timeLogged;
	}

	/**
	 * Returns the origin associated with this event.
	 *
	 * @return the origin
	 */
	public String getOrigin(){
		return route.getOrigin();
	}

	/**
	 * Returns the destination associated with this event.
	 *
	 * @return the destination
	 */
	public String getDestination(){
		return route.getDestination();
	}

	/**
	 * Returns an XML representation of this event.
	 *
	 * @return an XML representation of this event
	 */
	public abstract String toXML();

	/**
	 * Returns the type of event it is.
	 *
	 * @return - type of event in string form.
	 */
	public abstract String getType();
}
