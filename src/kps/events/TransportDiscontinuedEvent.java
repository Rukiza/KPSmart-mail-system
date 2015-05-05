package kps.events;

import kps.data.wrappers.BasicRoute;
import kps.enums.TransportType;
import kps.parser.KPSParser;

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
		String xml = "<"+KPSParser.TRANSPORT_DISCONTINUED_TAG+">";
		xml += "<"+KPSParser.TIME_TAG+">"+getTimeLogged()+"</"+KPSParser.TIME_TAG+">";
		xml += "<"+KPSParser.COMPANY_TAG+">"+transportFirm+"</"+KPSParser.COMPANY_TAG+">";
		xml += "<"+KPSParser.DESTINATION_TAG+">"+getDestination()+"</"+KPSParser.DESTINATION_TAG+">";
		xml += "<"+KPSParser.ORIGIN_TAG+">"+getOrigin()+"</"+KPSParser.ORIGIN_TAG+">";
		xml += "<"+KPSParser.TRANSPORT_TYPE_TAG+">"+TransportType.convertTransportTypeToString(transportType)+"</"+KPSParser.TRANSPORT_TYPE_TAG+">";
		xml += "</"+KPSParser.TRANSPORT_DISCONTINUED_TAG+">";
		return xml;
	}

	/**
	 * Compares this TransportDiscontinuedEvent to the specified object. Only
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
		if(o instanceof TransportDiscontinuedEvent){
			// compare field values. return false as soon as one is incorrect
			TransportDiscontinuedEvent obj = (TransportDiscontinuedEvent)o;
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
			// company
			if(!obj.getTransportFirm().equals(transportFirm)){
				return false;
			}
			// type
			if(obj.getTransportType() != transportType){
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

	@Override
	public String getType() {
		return "Transport Discount Event";
	}
}
