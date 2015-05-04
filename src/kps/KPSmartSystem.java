package kps;

import java.util.*;

import kps.data.CustomerRoute;
import kps.data.RouteGraph;
import kps.data.wrappers.EventLog;
import kps.events.BusinessEvent;
import kps.parser.KPSParser;
import kps.users.KPSUser;

public class KPSmartSystem {

	// fields
	private double totalRevenue;
	private double totalExpenditure;
	private EventLog eventLog;
	private KPSParser parser;
	private List<CustomerRoute> customerRoutes;
	private RouteGraph routeGraph;
	private List<KPSUser> users;
	private KPSUser currentUser;

	public KPSmartSystem(){

	}
}
