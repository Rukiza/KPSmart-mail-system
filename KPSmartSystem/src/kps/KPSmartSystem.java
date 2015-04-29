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
}
