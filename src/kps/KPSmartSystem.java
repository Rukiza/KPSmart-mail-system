package kps;

import java.util.List;

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
	public boolean addBusinessEvent(BusinessEvent event){
		eventLog.add(findPosition(event), event);
		return true;
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
}
