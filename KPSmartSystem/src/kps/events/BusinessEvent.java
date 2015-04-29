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
	 * @return
	 */
	public long timeLogged(){
		return timeLogged;
	}

	/**
	 * Returns the origin associated with this event.
	 *
	 * @return
	 */
	public String getOrigin(){
		return route.getOrigin();
	}

	/**
	 * Returns the destination associated with this event.
	 *
	 * @return
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
}
