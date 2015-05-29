package kps.data;

import kps.data.wrappers.BasicRoute;
import kps.enums.Day;
import kps.enums.Priority;

/**
 * @author Nicky van Hulst
 * */
public class Mail{

	//the day the mail is sent
	private Day day;

	//the weight of the mail in grams
	private int weight;

	//the volume of the mail measured in cubic centimeters
	private int volume;

	//priority of the mail
	private Priority priority;

	//the source and destination of the mail
	private BasicRoute route;


	/**
	 * The constructor for the Mail object
	 * */
	public Mail(BasicRoute route, Day day, int weight, int volume, Priority priority){
		this.day = day;
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
		this.route = route;
	}

	/**
	 * Gets the origin
	 * */
	public String getOrigin(){return route.getOrigin();}

	/**
	 * Gets the destination
	 * */
	public String getDestination(){return route.getDestination();}

	/**
	 * Gets the  weight
	 * */
	public int getWeight(){return weight;}

	/**
	 * Gets the  volume
	 * */
	public int getVolume(){return volume;}

	/**
	 * Gets the  priority
	 * */
	public Priority getPriority(){return priority;}

	/**
	 * Gets the  day
	 * */
	public Day getDay(){return day;}

	@Override
	public String toString(){
		return day.toString() + ", " + route.toString() + ", " + weight + "g, " + volume + "cm^3";
	}
}