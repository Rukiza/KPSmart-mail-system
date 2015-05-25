package kps;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kps.Main;
import kps.data.CustomerRoute;
import kps.data.Route;
import kps.data.RouteGraph;
import kps.data.wrappers.BasicRoute;
import kps.data.wrappers.DeliveryPrice;
import kps.data.wrappers.EventLog;
import kps.data.wrappers.MailTransport;
import kps.data.wrappers.Metrics;
import kps.enums.Day;
import kps.enums.Priority;
import kps.enums.TransportType;
import kps.events.BusinessEvent;
import kps.events.MailDeliveryEvent;
import kps.events.PriceUpdateEvent;
import kps.events.TransportCostUpdateEvent;
import kps.events.TransportDiscontinuedEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;
import kps.users.KPSUser;

public class KPSmartSystem {

	// fields
	private double totalRevenue;
	private double totalExpenditure;
	private EventLog eventLog;
	private Map<BasicRoute, CustomerRoute> customerRoutes;
	private RouteGraph routeGraph;
	private Map<String,KPSUser> users;
	private KPSUser currentUser;

	private Metrics metrics;

	private final String EVENT_LOG_FILENAME = Main.XML_FILE_PATH+"kps_data.xml";

	/**
	 * Constructs an empty instance of KPSmartSystem
	 */
	public KPSmartSystem(EventLog eventLog){
		totalRevenue = 0;
		totalExpenditure = 0;
		this.eventLog = eventLog;
		customerRoutes = new HashMap<BasicRoute, CustomerRoute>();
		routeGraph = loadGraph();
		users = new HashMap<String, KPSUser>();
		currentUser = null;
	}

	public RouteGraph loadGraph(){
		List<BusinessEvent> events = new ArrayList<BusinessEvent>();
		try {
			events = KPSParser.parseFile(Main.XML_FILE_PATH+"graph.xml");
		} catch (ParserException e) {
			e.printStackTrace();
		}

		RouteGraph g = new RouteGraph();

		for(BusinessEvent e : events){
			if(e instanceof TransportCostUpdateEvent){
				g.addRoute(new Route((TransportCostUpdateEvent)e));
			}
		}
		return g;
	}

	/**
	 * Constructs an instance of KPSmartSystem based on the
	 * specified list of BusinessEvents.
	 *
	 * @param log
	 * 		-- list of business events
	 */
	public KPSmartSystem(List<BusinessEvent> log){
		totalRevenue = 0;
		totalExpenditure = 0;
		eventLog = new EventLog(log);
		customerRoutes = new HashMap<BasicRoute, CustomerRoute>();
		routeGraph = new RouteGraph();
		users = new HashMap<String, KPSUser>();
		currentUser = null;
		processBusinessEvents(log);
	}

	/**
	 * Returns the current total revenue for the KPSmartSystem.
	 *
	 * @return
	 * 		-- total revenue
	 */
	public double getTotalRevenue(){
		return totalRevenue;
	}

	/**
	 * Returns the current total expenditure for the KPSmartSystem.
	 *
	 * @return
	 * 		-- total expenditure
	 */
	public double getTotalExpenditure(){
		return totalExpenditure;
	}

	/**
	 * Returns the size of the route graph.
	 *
	 * @return
	 *  -- size of route graph
	 */
	public int getRouteGraphSize(){
		return routeGraph.getSize();
	}

	public RouteGraph getRouteGraph(){
		return this.routeGraph;
	}

	/**
	 * Returns the size of the event log.
	 *
	 * @return
	 * 	-- size of event log
	 */
	public int getEventLogSize(){
		return eventLog.getSize();
	}

	/**
	 * Returns the user name of the current user logged into
	 * the KPSmartSystem.
	 *
	 * @return
	 * 		-- current user's name
	 */
	public String getCurrentUser(){
		return currentUser.getUsername();
	}

	public Metrics getMetrics(){
		return metrics;
	}

	/**
	 * Adds a new MailDeliveryEvent to the KPSmartSystem based on the specified
	 * parameters. A new MailDeliveryEvent will not be added if:
	 *
	 * 	- KPS does not ship mail to the specified destination
	 *  - There is no shipping to the specified destination with the specified priority
	 *  - A transport link cannot be made to the destination with the specified priority
	 *
	 * @param to
	 * 		-- destination of mail
	 * @param from
	 * 		-- origin of mail
	 * @param day
	 * 		-- day mail was posted
	 * @param weight
	 * 		-- weight of mail (in grams)
	 * @param volume
	 * 		-- volume of mail (in cubic centimeters)
	 * @param priority
	 * 		-- priority of mail
	 */
	public void addMailDeliveryEvent(String to, String from, Day day, int weight, int volume, Priority priority){
		BasicRoute route = new BasicRoute(from, to);
		if(!customerRoutes.containsKey(route)){
			// cannot send mail
		}
		double revenue = customerRoutes.get(route).calculateDeliveryPrice(weight, volume, priority);
		if(revenue < 0){
			// cannot send mail
		}

		// calculate expenditure
		double expenditure = 1;
		metrics.addMailDeliveryEvent(revenue, expenditure);

		long timeLogged = System.currentTimeMillis();
		eventLog.addBusinessEvent(new MailDeliveryEvent(timeLogged, route, day, weight, volume, priority));
	}

	/**
	 * Adds a new PriceUpdateEvent to the KPSmartSystem based on the specified
	 * parameters. If there is already a price set for sending mail from the
	 * origin to location with the same priority, this price is updated.
	 *
	 * @param to
	 * 		-- destination
	 * @param from
	 * 		-- origin
	 * @param gramPrice
	 * 		-- price per gram
	 * @param volumePrice
	 * 		-- price per cubic centimeter
	 * @param priority
	 * 		-- priority of mail
	 */
	public void addPriceUpdateEvent(String to, String from, double gramPrice, double volumePrice, Priority priority){
		BasicRoute route = new BasicRoute(from, to);
		if(!customerRoutes.containsKey(route)){
			customerRoutes.put(route, new CustomerRoute(route));
		}
		customerRoutes.get(route).addDeliveryPrice(gramPrice, volumePrice, priority);

		long timeLogged = System.currentTimeMillis();
		DeliveryPrice price = new DeliveryPrice(gramPrice, volumePrice);
		eventLog.addBusinessEvent(new PriceUpdateEvent(timeLogged, route, price, priority));
	}

	/**
	 * Adds a new TransportCostUpdateEvent to the KPSmartSystem based on the specified
	 * parameters. If there is currently not a route with the same origin, destination, company
	 * and transport type a new route is created. Otherwise the existing route is updated.
	 *
	 * @param to
	 * 		-- destination
	 * @param from
	 * 		-- origin
	 * @param company
	 * 		-- transport firm
	 * @param type
	 * 		-- transport type
	 * @param gramPrice
	 * 		-- price per gram
	 * @param volumePrice
	 * 		-- price per cubic centimeter
	 * @param maxWeight
	 * 		-- max weight of mail on route
	 * @param maxVolume
	 * 		-- max volume of mail on route
	 * @param duration
	 * 		-- duration of the trip
	 * @param frequency
	 * 		-- frequency that transport departs
	 * @param day
	 * 		-- day that transport departs
	 */
	public void addTransportCostUpdateEvent(String to, String from, String company, TransportType type,
			double gramPrice, double volumePrice, int maxWeight, int maxVolume, int duration, int frequency, Day day){
		BasicRoute route = new BasicRoute(from, to);
		DeliveryPrice price = new DeliveryPrice(gramPrice, volumePrice);
		MailTransport transport = new MailTransport(duration, frequency, day);
		long timeLogged = System.currentTimeMillis();
		TransportCostUpdateEvent event = new TransportCostUpdateEvent(timeLogged, route, company, type, price, maxWeight, maxVolume, transport);
		eventLog.addBusinessEvent(event);
		// add route to graph
	}

	/**
	 * Adds a new TransportDiscontinuedEvent to the KPSmartSystem based on the specified
	 * parameters. Removes the route specified by these parameters from the route graph.
	 *
	 * @param to
	 * 		-- destination
	 * @param from
	 * 		-- origin
	 * @param company
	 * 		-- transport firm
	 * @param type
	 * 		-- transport type
	 */
	public void addTransportDiscontinuedEvent(String to, String from, String company, TransportType type){
		BasicRoute route = new BasicRoute(from, to);
		//RouteGraph.removeRoute(route, transportFirm, transportType); TO BE IMPLEMENTED
		long timeLogged = System.currentTimeMillis();
		eventLog.addBusinessEvent(new TransportDiscontinuedEvent(timeLogged, route, company, type));
	}

	/**
	 * Attempts to log the specified user into the system. Returns
	 * true if login is successful, otherwise returns false.
	 *
	 * @param username
	 * 		-- user to login
	 * @param passwordHash
	 * 		-- password of user
	 * @return
	 * 		-- true if login successful, otherwise false
	 */
	public boolean login(String username, int passwordHash){
		if(users.containsKey(username)){
			KPSUser user = users.get(username);
			if(user.getPasswordHash() == passwordHash){
				currentUser = user;
				return true;
			}
		}
		return false;
	}

	/**
	 * Logs the current user out of the system.
	 */
	public void logout(){
		currentUser = null;
	}

	private void processBusinessEvents(List<BusinessEvent> events){
		for(BusinessEvent event : events){
			if(event instanceof MailDeliveryEvent){

			}
			if(event instanceof PriceUpdateEvent){

			}
			if(event instanceof TransportCostUpdateEvent){
				routeGraph.addRoute(new Route((TransportCostUpdateEvent)event));
			}
			if(event instanceof TransportDiscontinuedEvent){

			}
		}
	}

	/**
	 * Generates and XML file from the event log.
	 */
	public void convertEventLogToXML(){
		PrintWriter writer;
		try {
			writer = new PrintWriter(EVENT_LOG_FILENAME, "UTF-8");
			writer.write(eventLog.toXML());
			writer.close();
		}catch(FileNotFoundException e){e.printStackTrace();}
		catch(UnsupportedEncodingException e){e.printStackTrace();}
	}

	public EventLog getEventLog() {
		return eventLog;
	}
}
