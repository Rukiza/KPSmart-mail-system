package kps.data;

import kps.enums.Day;
import kps.enums.TransportType;
import kps.events.TransportCostUpdateEvent;

/**
 * @author Nicky van Hulst 300294657
 * */
public class Route {


	private double cost;

	private TransportType type;


	//the transport event
	private TransportCostUpdateEvent transport;

	/**
	 * Constructor for the Route object
	 *
	 * @param the immutable event creating the route object
	 * */
	public Route(TransportCostUpdateEvent trans){
		this.transport = trans;
	}


	/**
	 * Calculates The cost
	 * */
	public double calculateCost(double volume, double weight){
		if(volume > transport.getMaxVolume())return Double.POSITIVE_INFINITY;
		if(weight > transport.getMaxWeight())return Double.POSITIVE_INFINITY;

		return (volume * transport.getVolumePrice()) + (weight * transport.getGramPrice());
	}


	/**
	 * returns the max weight of the route
	 * */
	public double maxWeight(){
		return transport.getMaxWeight();
	}


	/**
	 * returns the max volume of the route
	 * */
	public double maxVolume(){
		return transport.getMaxVolume();
	}

	public double getWeightPrice(){
		return transport.getGramPrice();
	}

	public double getVolumePrice(){
		return transport.getVolumePrice();
	}

	/**
	 * returns the source of the route
	 * */
	public String getSrc(){return transport.getOrigin();}


	/**
	 * returns the destination of the route
	 * */
	public String getDest(){return transport.getDestination();}


	/**
	 * returns the type of the route
	 * */
	public TransportType getType(){return transport.getTransportType();}


	/**
	 * returns the cost of the route
	 * */
	public double getCost(){return cost;}//TODO change to getCost(double volume, double weight)

	/**
	 * Gets the duration for the route to get to the destination
	 * */
	public int getDuration(){
		return transport.getTripDuration();
	}

	/**
	 * Returns the day the route can send the package away
	 * */
	public Day getDay(){
		return transport.getDayDelivered();
	}


	/**
	 * Returns the day the route can send the package away
	 * */
	public int  getFrequency() {
		return transport.getDepartureFrequency();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)return true;

		if (obj == null)return false;

		if (getClass() != obj.getClass())return false;

		Route other = (Route) obj;

		if(transport.getDayDelivered() != other.transport.getDayDelivered())return false;

		if(transport.getDepartureFrequency() != other.transport.getDepartureFrequency())return false;

		if(!transport.getDestination().equals(other.transport.getDestination()))return false;

		if(!transport.getOrigin().equals(other.transport.getOrigin()))return false;

		if(transport.getMaxWeight() != other.transport.getMaxWeight())return false;

		if(transport.getMaxVolume() != other.transport.getMaxVolume())return false;

		if(!transport.getTransportFirm().equals(other.transport.getTransportFirm()))return false;

		if(transport.getTripDuration() !=other.transport.getTripDuration())return false;

		if(transport.getTransportType() != other.transport.getTransportType())return false;

		return true;

	}


	public String getCompany(){return transport.getTransportFirm();}

	public String toXML(){
		return transport.toXML();
	}

	@Override
	public String toString(){return  transport.getTransportFirm()+", "+transport.getOrigin() + " To " + transport.getDestination() +
			" Weight Price: $"+transport.getGramPrice() +"\n"+ " Volume Price: $"+transport.getVolumePrice() +"\n"
			+ "Type " + transport.getTransportType();}
}
