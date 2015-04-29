package kps.events;

import kps.data.wrappers.BasicRoute;
import kps.enums.TransportType;

public class TransportDiscontinuedEvent extends BusinessEvent{

	// fields
	private String transportFirm;
	private TransportType transportType;

	public TransportDiscontinuedEvent(long timeLogged, BasicRoute route, String transportFirm, TransportType transportType){
		super(timeLogged, route);
		this.transportFirm = transportFirm;
		this.transportType = transportType;
	}

	/**
	 * Returns the transport firm associated with this transport
	 * discontinued event.
	 *
	 * @return transport firm
	 */
	public String getTransportFirm(){
		return transportFirm;
	}

	/**
	 * Returns the transport type effected by this transport
	 * discontinued event.
	 *
	 * @return transport type
	 */
	public TransportType getTransportType(){
		return transportType;
	}

	/**
	 * Returns an XML representation of this event.
	 */
	public String toXML(){
		return "";
	}
}
