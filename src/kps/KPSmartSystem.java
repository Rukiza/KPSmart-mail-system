package kps;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

	private final String EVENT_LOG_FILENAME = Main.XML_FILE_PATH+"kps_data.xml";

	public KPSmartSystem(){

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
