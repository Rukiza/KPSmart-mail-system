package kps;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import kps.data.CustomerRoute;
import kps.data.DijkstraSearch;
import kps.data.Node;
import kps.data.Route;
import kps.data.RouteGraph;
import kps.data.wrappers.BasicRoute;
import kps.data.wrappers.EventLog;
import kps.data.Mail;
import kps.enums.Day;
import kps.enums.Priority;
import kps.events.BusinessEvent;
import kps.events.MailDeliveryEvent;
import kps.events.PriceUpdateEvent;
import kps.events.TransportCostUpdateEvent;
import kps.events.TransportDiscontinuedEvent;
import kps.parser.KPSParser;
import kps.users.KPSUser;

public class KPSmartSystem {

	// fields
	private double totalRevenue;
	private double totalExpenditure;
	private EventLog eventLog;
	private List<CustomerRoute> customerRoutes;
	private RouteGraph routeGraph;
	private List<KPSUser> users;
	private KPSUser currentUser;

	private final String EVENT_LOG_FILENAME = Main.XML_FILE_PATH+"kps_data.xml";

	/**
	 * Constructs an empty instance of KPSmartSystem
	 */
	public KPSmartSystem(){
		totalRevenue = 0;
		totalExpenditure = 0;
		eventLog = new EventLog();
		customerRoutes = new ArrayList<CustomerRoute>();
		routeGraph = new RouteGraph();
		users = new ArrayList<KPSUser>();
		currentUser = null;
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
		customerRoutes = new ArrayList<CustomerRoute>();
		routeGraph = new RouteGraph();
		users = new ArrayList<KPSUser>();
		currentUser = null;
		processBusinessEvents(log);
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

	/**
	 * Returns the size of the event log.
	 *
	 * @return
	 * 	-- size of event log
	 */
	public int getEventLogSize(){
		return eventLog.getSize();
	}

	public void addMailDeliveryEvent(){

	}

	public void addPriceUpdateEvent(){

	}

	public void addTransportCostUpdateEvent(){

	}

	public void addTransportDiscontinuedEvent(){

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
}
