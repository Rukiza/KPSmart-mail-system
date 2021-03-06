package kps.data.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kps.enums.Priority;

public class Metrics{

    // fields
    private double totalRevenue;
    private double totalExpenditure;
    private List<Double> allRevenue;
    private List<Double> allExpenditure;
    private List<String> origins;
    private List<String> destinations;
    Map<BasicRoute, MailDelivered> mailDelivered;
    Map<BasicRoute, AverageTime> averageTimes;

    // business event counters
    private int totalMailDeliveryEvents;
    private int totalPriceUpdateEvents;
    private int totalTransportCostUpdateEvents;
    private int totalTransportDiscontinuedEvents;

    /**
     * Constructs an empty Metrics Object.
     */
    public Metrics(){
        totalRevenue = 0;
        totalExpenditure = 0;
        totalMailDeliveryEvents = 0;
        totalPriceUpdateEvents = 0;
        totalTransportCostUpdateEvents = 0;
        totalTransportDiscontinuedEvents = 0;
        allRevenue = new ArrayList<Double>();
        allExpenditure = new ArrayList<Double>();
        origins = new ArrayList<String>();
        destinations = new ArrayList<String>();
        mailDelivered = new HashMap<BasicRoute, MailDelivered>();
        averageTimes = new HashMap<BasicRoute, AverageTime>();
    }

    /**
     * Constructs a Metrics Object with the specified totals
     * for revenue and expenditure.
     *
     * @param totalRevenue
     * 		-- total revenue so far
     * @param totalExpenditure
     * 		-- total expenditure so far
     */
    public Metrics(double totalRevenue, double totalExpenditure){
        this.totalRevenue = totalRevenue;
        this.totalExpenditure = totalExpenditure;
        totalMailDeliveryEvents = 0;
        totalPriceUpdateEvents = 0;
        totalTransportCostUpdateEvents = 0;
        totalTransportDiscontinuedEvents = 0;
        allRevenue = new ArrayList<Double>();
        allExpenditure = new ArrayList<Double>();
        origins = new ArrayList<String>();
        destinations = new ArrayList<String>();
        mailDelivered = new HashMap<BasicRoute, MailDelivered>();
        averageTimes = new HashMap<BasicRoute, AverageTime>();
    }

    /**
     * Returns the total revenue.
     *
     * @return total revenue
     */
    public double getTotalRevenue(){
        return totalRevenue;
    }

    /**
	 * Returns the list of all the revenue received from each mail delivery
	 * event.
	 *
	 * @return List of revenue
	 */
	public List<Double> getAllRevenue(){
		return allRevenue;
	}

	/**
     * Adds the specified revenue amount to the
     * revenue total.
     *
     * @param revenue
     * 		-- revenue to be added
     */
    public void addRevenue(double revenue){
        totalRevenue += revenue;
    }

    /**
     * Returns the total expenditure.
     *
     * @return total expenditure
     */
    public double getTotalExpenditure(){
        return totalExpenditure;
    }

    /**
	 * Returns the list of all the expenditure received from each mail
	 * delivery event.
	 *
	 * @return List of expenditure
	 */
	public List<Double> getAllExpenditure(){
		return allExpenditure;
	}

	/**
     * Adds the specifed expenditure amount to the
     * total expenditure.
     *
     * @param expenditure
     * 		-- expenditure to be added
     */
    public void addExpenditure(double expenditure){
        totalExpenditure += expenditure;
    }

    /**
     * Returns to total number of mail delivery events.
     *
     * @return number of mail delivery events
     */
    public int getTotalMailDeliveryEvents(){
        return totalMailDeliveryEvents;
    }

    /**
     * Returns an array of all the origin locations for
     * customer routes.
     *
     * @return list of origins
     */
    public String[] getOrigins(){
    	Collections.sort(this.origins);
    	String[] origins = new String[this.origins.size()];
    	for(int i = 0; i < origins.length; i++){
    		origins[i] = this.origins.get(i);
    	}
    	return origins;
    }

    /**
     * Returns an array of destination locations for
     * customer routes.
     *
     * @return list of destinations
     */
    public String[] getDestinations(){
    	Collections.sort(this.destinations);
    	String[] destinations = new String[this.destinations.size()];
    	for(int i = 0; i < destinations.length; i++){
    		destinations[i] = this.destinations.get(i);
    	}
    	return destinations;
    }

    /**
     * Returns the total weight from all the packages sent from
     * the specified origin to the specified destination. If there
     * is no route between the parameters a value of zero is returned.
     *
     * @param origin
     * 		-- origin of mail
     * @param destination
     * 		-- destination of mail
     *
     * @return total weight of mail
     */
    public int getTotalMailWeight(String origin, String destination){
    	BasicRoute route = new BasicRoute(origin, destination);
    	if(mailDelivered.containsKey(route)){
    		return mailDelivered.get(route).getTotalWeight();
    	}
    	return 0;
    }

    /**
     * Returns the total volume from all the packages sent from
     * the specified origin to the specified destination. If there
     * is no route between the parameters a value of zero is returned.
     *
     * @param origin
     * 		-- origin of mail
     * @param destination
     * 		-- destination of mail
     *
     * @return total volume of mail
     */
    public int getTotalMailVolume(String origin, String destination){
    	BasicRoute route = new BasicRoute(origin, destination);
    	if(mailDelivered.containsKey(route)){
    		return mailDelivered.get(route).getTotalVolume();
    	}
    	return 0;
    }

    /**
     * Returns the total amount of packages sent from the specified origin
     * to the specified destination. If there is no route between the parameters
     * a value of zero is returned.
     *
     * @param origin
     * 		-- origin of mail
     * @param destination
     * 		-- destination of mail
     *
     * @return total weight of mail
     */
    public int getTotalMailAmount(String origin, String destination){
    	BasicRoute route = new BasicRoute(origin, destination);
    	if(mailDelivered.containsKey(route)){
    		return mailDelivered.get(route).getTotalAmount();
    	}
    	return 0;
    }

    /**
     * Returns the average delivery time for sending mail from the specified origin
     * and destination for the specified priority. If there is no route between the
     * origin and destination then a value of zero is returned.
     *
     * @param origin
     * 		-- origin of mail
     * @param destination
     * 		-- destination of mail
     * @param priority
     * 		-- priority of mail
     *
     * @return average time for sending mail
     */
    public double getAverageDeliveryTime(String origin, String destination, Priority priority){
    	BasicRoute route = new BasicRoute(origin, destination);
    	if(averageTimes.containsKey(route)){
    		return averageTimes.get(route).getAverageTime(priority);
    	}
    	return 0;
    }

    /**
     * Adds
     * @param revenue
     * @param expenditure
     */
    public void addMailDeliveryEvent(double revenue, double expenditure, String origin, String destination, int weight, int volume, int time, Priority priority){
    	// add the revenue and expenditure data to metrics
    	totalRevenue += revenue;
    	totalExpenditure += expenditure;
    	allRevenue.add(revenue);
    	allExpenditure.add(expenditure);

    	BasicRoute route = new BasicRoute(origin, destination);
    	if(!mailDelivered.containsKey(route)){
    		mailDelivered.put(route, new MailDelivered());
    	}
    	mailDelivered.get(route).updateMailDelivered(weight, volume);

    	if(!averageTimes.containsKey(route)){
    		averageTimes.put(route, new AverageTime());
    	}
    	averageTimes.get(route).addTime(time, priority);

        totalMailDeliveryEvents++;
    }

    /**
     * Returns the total number of price update events.
     *
     * @return number of price update events
     */
    public int getTotalPriceUpdateEvents(){
        return totalPriceUpdateEvents;
    }

    /**
     * Increments the price update event count by one.
     */
    public void addPriceUpdateEvent(String origin, String destination, Priority priority){
    	// only add origin and destination if they aren't already in
    	// their corresponding lists
    	if(!origins.contains(origin)){
    		origins.add(origin);
    	}
    	if(!destinations.contains(destination)){
    		destinations.add(destination);
    	}

    	//BasicRoute route = new BasicRoute(origin, destination);

        totalPriceUpdateEvents++;
    }

    /**
     * Returns the total number of transport cost update events.
     *
     * @return number of transport cost update events
     */
    public int getTotalTransportCostUpdateEvents(){
        return totalTransportCostUpdateEvents;
    }

    /**
     * Increments the transport cost update event count by one.
     */
    public void addTransportCostUpdateEvent(){
        totalTransportCostUpdateEvents++;
    }

    /**
     * Returns the total number of transport discontinued events.
     *
     * @return number of tranpsort discontinued events
     */
    public int getTotalTransportDiscontinuedEvents(){
        return totalTransportDiscontinuedEvents;
    }

    /**
     * Increments the transport discontinued event count by one.
     */
    public void addTransportDiscontinuedEvent(){
        totalTransportDiscontinuedEvents++;
    }

    /**
     * Returns the total number of business events.
     *
     * @return number of business events
     */
    public int getTotalBusinessEvents(){
        return totalMailDeliveryEvents + totalPriceUpdateEvents + totalTransportCostUpdateEvents + totalTransportDiscontinuedEvents;
    }

    /**
     * Private class that is a wrapper for a map of priorities to list of times.
     * Used to calculate the average time for a mail delivery depending on the
     * priority.
     *
     * @author David Sheridan
     *
     */
    private class AverageTime{

    	//field
    	private Map<Priority, List<Integer>> times;

    	/**
    	 * Constructs an empty AverageTime Object.
    	 */
    	public AverageTime(){
    		times = new HashMap<Priority, List<Integer>>();
    	}

    	/**
    	 * Returns the average time for mail delivery for the
    	 * specified priority.
    	 *
    	 * @param priority
    	 * 		-- priority to get average time for
    	 *
    	 * @return the average time for mail delivery
    	 */
    	public double getAverageTime(Priority priority){
    		if(times.containsKey(priority)){
    			double total = 0;
    			for(int time : times.get(priority)){
    				total += time;
    			}
    			return total / times.get(priority).size();
    		}
    		return 0;
    	}

    	/**
    	 * Adds the specified time to the list of times specified
    	 * by the priority.
    	 *
    	 * @param time
    	 * 		-- time of mail delivery
    	 * @param priority
    	 * 		-- priority of mail delivery
    	 */
    	public void addTime(int time, Priority priority){
    		if(!times.containsKey(priority)){
    			times.put(priority, new ArrayList<Integer>());
    		}
    		times.get(priority).add(time);
    	}
    }
}