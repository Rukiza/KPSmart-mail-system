package kps.data;

import kps.enums.TransportType;

/**
 * @author Nicky van Hulst 300294657
 * */
public class Route {
	private String src;
	private String dest;
	private double cost;
	private TransportType type;
	
	public Route(String src, String dest, Double cost, TransportType type){
		this.cost = cost;
		this.src = src;
		this.dest = dest;
		this.type = type;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dest == null) ? 0 : dest.hashCode());
		result = prime * result + ((src == null) ? 0 : src.hashCode());
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
		if (dest == null) {
			if (other.dest != null)
				return false;
		} else if (!dest.equals(other.dest))
			return false;
		if (src == null) {
			if (other.src != null)
				return false;
		} else if (!src.equals(other.src))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	@Override
	public String toString(){return "Name :" + src + " Dest :" + dest + " Cost :"+cost;}

	public String getSrc(){return this.src;}
	public String getDest(){return this.dest;}
	public TransportType getType(){return type;}
	public double getCost(){return cost;}
}
