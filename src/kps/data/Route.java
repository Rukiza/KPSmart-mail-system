package kps.data;

import kps.enums.TransportType;
import kps.events.TransportCostUpdateEvent;

/**
 * @author Nicky van Hulst 300294657
 * */
public class Route {


	private double cost;

	//the type of transport this route supports
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (Double.doubleToLongBits(cost) != Double
				.doubleToLongBits(other.cost))
			return false;
		if ( transport.getDestination() == null) {
			if (other.getDest()!= null)
				return false;
		} else if (! transport.getDestination().equals(other.getCost()))
			return false;
		if (transport.getOrigin() == null) {
			if (other.getDest()!= null)
				return false;
		} else if (!transport.getOrigin().equals(other.getSrc()))
			return false;
		if (transport.getTransportType() != other.transport.getTransportType())
			return false;
		return true;
	}

	public String getCompany(){return transport.getTransportFirm();}

	@Override
	public String toString(){return  transport.getTransportFirm()+", "+transport.getOrigin() + " To " + transport.getDestination() +
			" Gram Price :"+transport.getGramPrice() +"\n"+ " Volumder $"+transport.getVolumePrice() +"\n"
			+ "Type " + transport.getTransportType();}
}
