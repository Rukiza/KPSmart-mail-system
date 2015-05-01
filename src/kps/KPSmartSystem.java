package kps;

import java.util.*;

import kps.data.CustomerRoute;
import kps.data.RouteGraph;
import kps.events.BusinessEvent;
import kps.parser.KPSParser;
import kps.users.KPSUser;

public class KPSmartSystem {

	// fields
	private double totalRevenue;
	private double totalExpenditure;
	private List<BusinessEvent> eventLog;
	private int iterationLocation;
	private KPSParser parser;
	private List<CustomerRoute> customerRoutes;
	private RouteGraph routeGraph;
	private List<KPSUser> users;
	private KPSUser currentUser;

	public KPSmartSystem(){

	}

	/**
	 * Adds a business event to the eventLog list in order.
	 * Ordering is based on date.
	 */
	public void addBusinessEvent(BusinessEvent event){
		eventLog.add(findPosition(event), event);
	}

	private int findPosition(BusinessEvent event){
		if (eventLog.isEmpty()){
			return 0;
		}

		for (int i = 0; i < eventLog.size(); i++){
			if (event.timeLogged() > eventLog.get(i).timeLogged()){
				return i;
			}
		}
		return eventLog.size();
	}

	/**
	 * Used to get the next event in the list.
	 * Loops aroundto the start of the list when reaches the end.
	 *
	 * @return - Business event
	 */
	public BusinessEvent getNextEvent(){
		if (iterationLocation >= eventLog.size()){
			iterationLocation = 0;
		}
		return eventLog.get(iterationLocation++);
	}

	/**
	 * Used to get the previous event in the list.
	 * Loops around to the end of the list when it reaches the start..
	 *
	 * @return - Business event
	 */
	public BusinessEvent getPrevEvent(){
		if (iterationLocation < 0){
			iterationLocation = eventLog.size() -1;
		}
		return eventLog.get(iterationLocation--);
	}

	/**
	 * Resets the eventLog iteration.
	 */
	public void resetEventLogLocation(){
		iterationLocation = 0;
	}

	/**
	 * Check if the event log is empty
	 * @return - true if its empty
	 */
	public boolean eventLogIsEmpty(){
		return eventLog.isEmpty();
	}
}
