package kps.data;

import kps.data.wrappers.BasicRoute;
import kps.enums.Day;
import kps.enums.Priority;

public class Mail{
	private Day day;
	private int weight; // weight is measured in grams
	private int volume; // volume is measured in cubic centimeters
	private Priority priority;
	private BasicRoute route;

	public Mail(BasicRoute route, Day day, int weight, int volume, Priority priority){
		this.day = day;
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
		this.route = route;
	}

	public String getOrigin(){return route.getOrigin();}
	public String getDestination(){return route.getDestination();}
	public int getWeight(){return weight;}
	public int getVolume(){return volume;}
	public Priority getPriority(){return priority;}
	public Day getDay(){return day;}
	
	public String toString(){
		return day.toString() + ", " + route.toString() + ", " + weight + "g, " + volume + "cm^3";  
	}
}