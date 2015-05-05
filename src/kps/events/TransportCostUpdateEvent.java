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
			TransportType transportType, DeliveryPrice deliveryPrice, int maxWeight, int maxVolume, MailTransport mailTransport){
		super(timeLogged, route);
		this.transportFirm = transportFirm;
		this.transportType = transportType;
		this.deliveryPrice = deliveryPrice;
		this.maxWeight = maxWeight;
		this.maxVolume = maxVolume;
		this.mailTransport = mailTransport;
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

	/**
	 * Compares this TransportCostUpdateEvent to the specified object. Only
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
		if(o instanceof TransportCostUpdateEvent){
			// compare field values. return false as soon as one is incorrect
			TransportCostUpdateEvent obj = (TransportCostUpdateEvent)o;
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
			// weight cost
			if(obj.getGramPrice() != getGramPrice()){
				return false;
			}
			// volume cost
			if(obj.getVolumePrice() != getVolumePrice()){
				return false;
			}
			// max weight
			if(obj.getMaxWeight() != maxWeight){
				return false;
			}
			// max volume
			if(obj.getMaxVolume() != maxVolume){
				return false;
			}
			// duration
			if(obj.getTripDuration() != getTripDuration()){
				return false;
			}
			// frequency
			if(obj.getDepartureFrequency() != getDepartureFrequency()){
				return false;
			}
			// day
			if(obj.getDayDelivered() != getDayDelivered()){
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
		return "Transport Cost Update";
	}
}
