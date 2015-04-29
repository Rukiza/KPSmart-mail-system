package kps.events;

import kps.data.wrappers.BasicRoute;
import kps.data.wrappers.DeliveryPrice;
import kps.enums.TransportType;

public class PriceUpdateEvent extends BusinessEvent{

	// fields
	private DeliveryPrice deliveryPrice;
	private TransportType priority;

	public PriceUpdateEvent(long timeLogged, BasicRoute route, DeliveryPrice deliveryPrice, TransportType priority){
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
	public TransportType getPriority(){
		return priority;
	}

	/**
	 * Returns an XML representation of this event.
	 */
	public String toXML(){
		return "";
	}
}
