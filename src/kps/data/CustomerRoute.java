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

	/**
	 * Returns the weight cost for sending mail with the specified
	 * priority. If there is no delivery price for sending mail
	 * with this priority return zero.
	 *
	 * @param priority
	 * 		-- priority of mail
	 *
	 * @return weight cost
	 */
	public double getWeightCost(Priority priority){
		if(deliveryPrices.containsKey(priority)){
			return deliveryPrices.get(priority).getGramPrice();
		}
		return 0;
	}

	/**
	 * Returns the volume cost for sending mail with the specified
	 * priority. If there is no delivery price for sending mail
	 * with this priority return zero.
	 *
	 * @param priority
	 * 		-- priority of mail
	 *
	 * @return volume cost
	 */
	public double getVolumeCost(Priority priority){
		if(deliveryPrices.containsKey(priority)){
			return deliveryPrices.get(priority).getVolumePrice();
		}
		return 0;
	}

	/**
	 * Returns true if this CustomerRoute has a delivery cost for
	 * the specified priority.
	 *
	 * @param priority
	 * 		-- priority of mail
	 *
	 * @return true if has delivery cost, otherwise false
	 */
	public boolean hasPriority(Priority priority){
		return deliveryPrices.keySet().contains(priority);
	}

	/**
	 * If there currently is not a delivery price for the specified priority a
	 * new delivery price is added to this CustomerRoute. Otherwise the current
	 * delivery price for the specified priority is updated with the new prices.
	 *
	 * @param gramPrice
	 * 		-- new gram price
	 * @param volumePrice
	 * 		-- new volume price
	 * @param priority
	 * 		-- priority of mail
	 */
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

	/**
	 * Returns a String representation of this CustomerRoute.
	 */
	public String toString(){
		String data = route.toString()+"\n";
		for(Priority p : deliveryPrices.keySet()){
			data += "  "+Priority.convertPriorityToString(p)+" ---> "+deliveryPrices.get(p).toString()+"\n";
		}
		return data;
	}
}
