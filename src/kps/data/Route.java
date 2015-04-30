package kps.data;

import kps.enums.TransportType;


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
	
	
	public String getSrc(){return src;}
	public String getDest(){return dest;}
	public TransportType getType(){return type;}
	public double getCost(){return cost;}
}
