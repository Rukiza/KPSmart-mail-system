package kps.data;

import sun.rmi.transport.Transport;
import kps.enums.TransportType;
import kps.events.TransportCostUpdateEvent;

/**
 * @author Nicky van Hulst 300294657
 * */
public class Route {
	private double cost;
	private TransportType type;
	private TransportCostUpdateEvent transport;

	//public Route(String src, String dest, Double cost, TransportType type,TransportCostUpdateEvent trans ){
	public Route(TransportCostUpdateEvent trans){
		this.transport = trans;
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

	@Override
	public String toString(){return "src :" + transport.getOrigin() + " Dest :" + transport.getDestination() + " Cost :"+cost;}

	public String getSrc(){return transport.getOrigin();}
	public String getDest(){return transport.getDestination();}
	public TransportType getType(){return transport.getTransportType();}
	public double getCost(){return cost;}//TODO change to getCost(double volume, double weight)

	public double calculateCost(double volume, double weight){
		if(volume > transport.getMaxVolume())return -1;
		if(weight > transport.getMaxWeight())return -1;

		return (volume * transport.getVolumePrice()) + (weight * transport.getGramPrice());
	}
}
