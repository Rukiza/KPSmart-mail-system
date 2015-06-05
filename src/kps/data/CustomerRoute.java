package kps.data;

import java.util.HashMap;
import java.util.Map;

import kps.data.wrappers.BasicRoute;
import kps.data.wrappers.DeliveryPrice;
import kps.data.wrappers.MailDelivered;
import kps.enums.Priority;

/**
 * Represents a generic route for which mail will be delivered for the
 * customer. This does not include the actual route that the mail will
 * travel to reach it's destination. This class keeps track of the amount,
 * weight and volume of mail sent through this route. It also can calculate
 * the cost of sending mail through this route based on the weight, volume
 * and priority of the mail.
 *
 * @author David Sheridan
 *
 */
public class CustomerRoute {

	// fields
	private BasicRoute route;
	private Map<Priority, DeliveryPrice> deliveryPrices;
	private MailDelivered mailDelivered;

	/**
	 * Constructs a new Customer Route with the specified origin and
	 * destination points.
	 *
	 * @param origin
	 * 		-- the origin of the mail
	 * @param destination
	 * 		-- the destination of the mail
	 */
	public CustomerRoute(BasicRoute route){
		this.route = route;
		deliveryPrices = new HashMap<Priority, DeliveryPrice>();
		mailDelivered = new MailDelivered();
	}

	/**
	 * Returns the origin of this customer route.
	 *
	 * @return origin
	 */
	public String getOrigin(){
		return route.getOrigin();
	}

	/**
	 * Returns the destination of this customer route.
	 *
	 * @return destination
	 */
	public String getDestination(){
		return route.getDestination();
	}

	/**
	 * Returns the size of this CustomerRoute. Size of customer
	 * route is dependent on the number of priority-delivery cost
	 * pairings.
	 *
	 * @return size of customer route
	 */
	public int size(){
		return deliveryPrices.size();
	}

	public boolean hasPriority(Priority priority){
		return deliveryPrices.keySet().contains(priority);
	}

	public void addDeliveryPrice(double gramPrice, double volumePrice, Priority priority){
		if(deliveryPrices.containsKey(priority)){
			deliveryPrices.get(priority).updateDeliveryPrice(gramPrice, volumePrice);
		}
		else{
			deliveryPrices.put(priority, new DeliveryPrice(gramPrice, volumePrice));
		}
	}

	/**
	 * Calculates and returns the delivery price for mail to be delivered
	 * based on the weight, volume and priority of the package.
	 *
	 * @param weight
	 * 		-- weight of mail to be delivered (in grams)
	 * @param volume
	 * 		-- volume of mail to be delivered (in cubic cenimeters)
	 * @param priority
	 * 		-- priority of the mail being delivered
	 * @return
	 */
	public double calculateDeliveryPrice(int weight, int volume, Priority priority){
		if(deliveryPrices.containsKey(priority)){
			double price = deliveryPrices.get(priority).calculateDeliveryPrice(weight, volume);
			mailDelivered.updateMailDelivered(weight, volume);
			return price;
		}
		return -1;
	}

	/**
	 * Update the totals for the amount of mail delivered with the
	 * specified values.
	 *
	 * @param weight
	 * 		-- additional weight to be added
	 * @param volume
	 * 		-- additional volume to be added
	 */
	public void updateMailDelivered(int weight, int volume){
		mailDelivered.updateMailDelivered(weight, volume);
	}

	public String toString(){
		String data = route.toString()+"\n";
		for(Priority p : deliveryPrices.keySet()){
			data += "  "+Priority.convertPriorityToString(p)+" ---> "+deliveryPrices.get(p).toString()+"\n";
		}
		return data;
	}
}
