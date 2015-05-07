package kps.events;

import kps.data.wrappers.BasicRoute;
import kps.data.wrappers.DeliveryPrice;
import kps.enums.Day;
import kps.enums.Priority;
import kps.parser.KPSParser;

public class PriceUpdateEvent extends BusinessEvent{

	// fields
	private DeliveryPrice deliveryPrice;
	private Priority priority;

	public PriceUpdateEvent(long timeLogged, BasicRoute route, DeliveryPrice deliveryPrice, Priority priority){
		super(timeLogged, route);
		this.deliveryPrice = deliveryPrice;
		this.priority = priority;
	}

	/**
	 * Returns the price per gram for this price update.
	 *
	 * @return gram price (price per gram)
	 */
	public double getGramPrice(){
		return deliveryPrice.getGramPrice();
	}

	/**
	 * Returns the price per cubic centimeters for this price
	 * update.
	 *
	 * @return volume price (price per cubic centimeter)
	 */
	public double getVolumePrice(){
		return deliveryPrice.getVolumePrice();
	}

	/**
	 * Returns the transport type priority effected by this
	 * price update.
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
		String xml = "\t<"+KPSParser.PRICE_UPDATE_TAG+">\n";
		xml += "\t\t<"+KPSParser.TIME_TAG+">"+getTimeLogged()+"</"+KPSParser.TIME_TAG+">\n";
		xml += "\t\t<"+KPSParser.DESTINATION_TAG+">"+getDestination()+"</"+KPSParser.DESTINATION_TAG+">\n";
		xml += "\t\t<"+KPSParser.ORIGIN_TAG+">"+getOrigin()+"</"+KPSParser.ORIGIN_TAG+">\n";
		xml += "\t\t<"+KPSParser.PRIORITY_TAG+">"+Priority.convertPriorityToString(priority)+"</"+KPSParser.PRIORITY_TAG+">\n";
		xml += "\t\t<"+KPSParser.WEIGHT_COST_TAG+">"+getGramPrice()+"</"+KPSParser.WEIGHT_COST_TAG+">\n";
		xml += "\t\t<"+KPSParser.VOLUME_COST_TAG+">"+getVolumePrice()+"</"+KPSParser.VOLUME_COST_TAG+">\n";
		xml += "\t\t</"+KPSParser.PRICE_UPDATE_TAG+">\n";
		return xml;
	}

	/**
	 * Compares this PriceUpdateEvent to the specified object. Only
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
		if(o instanceof PriceUpdateEvent){
			// compare field values. return false as soon as one is incorrect
			PriceUpdateEvent obj = (PriceUpdateEvent)o;
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
			// gram price
			if(obj.getGramPrice() != getGramPrice()){
				return false;
			}
			// volume price
			if(obj.getVolumePrice() != getVolumePrice()){
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

	@Override
	public String getType() {
		return "Price Update Event";
	}
}
