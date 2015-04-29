package kps.events;

import kps.data.wrappers.BasicRoute;
import kps.data.wrappers.DeliveryPrice;
import kps.data.wrappers.MailTransport;
import kps.enums.Day;
import kps.enums.TransportType;

public class TransportCostUpdateEvent extends BusinessEvent{

	// fields
	private String transportFirm;
	private TransportType transportType;
	private DeliveryPrice deliveryPrice;
	private int maxWeight; // in grams
	private int maxVolume; // in cubic centimeters
	private MailTransport mailTransport;

	public TransportCostUpdateEvent(long timeLogged, BasicRoute route, String transportFirm,
			TransportType transportType, DeliveryPrice deliveryPrice, int maxWeight, int maxVolume){
		super(timeLogged, route);
	}

	/**
	 * Returns the transport firm associated with this
	 * transport cost update.
	 *
	 * @return transport firm
	 */
	public String getTransportFirm(){
		return transportFirm;
	}

	/**
	 * Returns the transport type that is effected by this
	 * transport cost update.
	 *
	 * @return transport type
	 */
	public TransportType getTransportType(){
		return transportType;
	}

	/**
	 * Returns the price per gram for sending mail with
	 * this transport cost update.
	 *
	 * @return gram price (price per gram)
	 */
	public double getGramPrice(){
		return deliveryPrice.getGramPrice();
	}

	/**
	 * Returns the price per cubic centimeter for sending
	 * mail with this transport cost update.
	 *
	 * @return volume price (price per cubic centimeters)
	 */
	public double getVolumePrice(){
		return deliveryPrice.getVolumePrice();
	}

	/**
	 * Returns the max weight of mail in grams that can be
	 * transported with this transport cost update.
	 *
	 * @return max weight (in grams)
	 */
	public int getMaxWeight(){
		return maxWeight;
	}

	/**
	 * Returns the max volume of mail in cubic centimeters that
	 * can be transported with this transport cost update.
	 *
	 * @return max volume (in grams)
	 */
	public int getMaxVolume(){
		return maxVolume;
	}

	/**
	 * Returns the amount of time in hours it takes for mail to arrive
	 * at its destination from the origin for this transport cost
	 * update.
	 *
	 * @return trip duration (in hours)
	 */
	public int getTripDuration(){
		return mailTransport.getTripDuration();
	}

	/**
	 * Returns the departure frequency of mail with this transport
	 * cost update. The departure frequency is the time in hours
	 * between two subsequent departures.
	 *
	 * @return departure frequency (in hours)
	 */
	public int getDepartureFrequency(){
		return mailTransport.getDepartureFrequency();
	}

	/**
	 * Returns the day that mail departs to be delivered with
	 * this transport cost update.
	 *
	 * @return day delivered
	 */
	public Day getDayDelivered(){
		return mailTransport.getDayDelivered();
	}

	/**
	 * Returns an XML representation of this event.
	 */
	public String toXML(){
		return "";
	}
}
