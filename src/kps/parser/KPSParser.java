package kps.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import kps.data.Route;
import kps.data.RouteGraph;
import kps.data.wrappers.BasicRoute;
import kps.data.wrappers.DeliveryPrice;
import kps.data.wrappers.MailTransport;
import kps.enums.Day;
import kps.enums.Position;
import kps.enums.Priority;
import kps.enums.TransportType;
import kps.events.BusinessEvent;
import kps.events.MailDeliveryEvent;
import kps.events.PriceUpdateEvent;
import kps.events.TransportCostUpdateEvent;
import kps.events.TransportDiscontinuedEvent;
import kps.users.KPSUser;

public class KPSParser {

	// fields
	public static final String FILE_TAG = "simulation";
	public static final String SCHEMA = "xsi:noNamespaceSchemaLocation=\"postal.xsd\"";
	public static final String MAIL_DELIVERY_TAG = "mail";
	public static final String PRICE_UPDATE_TAG = "price";
	public static final String TRANSPORT_COST_UPDATE_TAG = "cost";
	public static final String TRANSPORT_DISCONTINUED_TAG = "discontinue";
	public static final String TIME_TAG = "time";
	public static final String ORIGIN_TAG = "from";
	public static final String DESTINATION_TAG = "to";
	public static final String COMPANY_TAG = "company";
	public static final String WEIGHT_TAG = "weight";
	public static final String WEIGHT_COST_TAG = "weightcost";
	public static final String MAX_WEIGHT_TAG = "maxWeight";
	public static final String VOLUME_TAG = "volume";
	public static final String VOLUME_COST_TAG = "volumecost";
	public static final String MAX_VOLUME_TAG = "maxVolume";
	public static final String REVENUE_TAG = "revenue";
	public static final String EXPENDITURE_TAG = "expenditure";
	public static final String TRANSPORT_TYPE_TAG = "type";
	public static final String PRIORITY_TAG = "priority";
	public static final String DAY_TAG = "day";
	public static final String DURATION_TAG = "duration";
	public static final String FREQUENCY_TAG = "frequency";
	public static final String DELIVERY_TIME_TAG = "deliveryTime";
	public static final String USERS_FILE_TAG = "users";
	public static final String USER_TAG = "user";
	public static final String USERNAME_TAG = "username";
	public static final String PASSWORD_TAG = "passwordHash";
	public static final String POSITION_TAG = "position";
	public static final String GRAPH_FILE_TAG = "graph";


	/**
	 * Parses the file specified by the file name and returns the business
	 * events that are contained within it.
	 *
	 * @param filename
	 * 		-- the name of the file containing business event data
	 *
	 * @return a list of business events
	 *
	 * @throws ParserException
	 * 		-- thrown if the file cannot be opened, an incorrect tag is
	 * 		found or if a null value is attempted to be added to the event log.
	 */
	public static List<BusinessEvent> parseFile(String filename) throws ParserException{
		List<BusinessEvent> eventLog = new ArrayList<BusinessEvent>();

		// load file into scanner
		Scanner scan = null;
		try{
			scan = new Scanner(new File(filename));
		}catch(IOException e){throw new ParserException("ParseFile: Cannot load file "+filename);}

		// scan all of the file into a string
		String data = convertXMLDataToString(scan);
		scan.close();
		scan = new Scanner(data);

		gobble(scan, "<"+FILE_TAG);
		gobble(scan, SCHEMA+">");
		// parse data from string
		while(scan.hasNext()){
			BusinessEvent event = null;
			// check if mail delivery event is next
			if(scan.hasNext("<"+MAIL_DELIVERY_TAG+">")){
				event = parseMailDeliveryEvent(scan);
			}
			// check if price update event is next
			else if(scan.hasNext("<"+PRICE_UPDATE_TAG+">")){
				event = parsePriceUpdateEvent(scan);
			}
			// check if transport cost update event is next
			else if(scan.hasNext("<"+TRANSPORT_COST_UPDATE_TAG+">")){
				event = parseTransportCostUpdateEvent(scan);
			}
			// check if transport discontinued event is next
			else if(scan.hasNext("<"+TRANSPORT_DISCONTINUED_TAG+">")){
				event = parseTransportDiscontinuedEvent(scan);
			}
			// check if the end of the file has been reached
			else if(scan.hasNext("</"+FILE_TAG+">")){
				scan.next();
				break;
			}
			// otherwise an incorrect tag has been found
			else{
				scan.close();
				throw new ParserException("ParseFile: Incorrect tag found: "+scan.next());
			}

			// check that event is not null before adding to event log
			if(event != null){
				eventLog.add(event);
			}
			else{
				scan.close();
				throw new ParserException("ParseFile: Event should not be null after parsing.");
			}
		}
		// file parsing successful
		scan.close();
		return eventLog;
	}

	/**
	 * Parses the file specified by the file name and returns the KPSUsers that
	 * are contained within it.
	 *
	 * @param filename
	 * 		-- name of the file to be parsed
	 *
	 * @return a map of user names to users
	 *
	 * @throws ParserException
	 * 		-- thrown if the file cannot be opened, an incorrect tag is found
	 */
	public static Map<String, KPSUser> parseKPSUsers(String filename) throws ParserException{
		Map<String, KPSUser> users = new HashMap<String, KPSUser>();

		// load file into the scanner
		Scanner scan = null;
		try{
			scan = new Scanner(new File(filename));
		}catch(IOException e){throw new ParserException("ParseKPSUsers: Cannot load file "+filename);}

		String data = convertXMLDataToString(scan);
		scan.close();
		scan = new Scanner(data);

		gobble(scan, "<"+USERS_FILE_TAG+">");
		while(scan.hasNext()){
			// check if there is another user to parse
			if(scan.hasNext("<"+USER_TAG+">")){
				KPSUser user = parseKPSUser(scan);
				users.put(user.getUsername(), user);
			}
			// check if the end of the file has been reached
			else if(scan.hasNext("</"+USERS_FILE_TAG+">")){
				scan.next();
				break;
			}
			// an invalid tag has been found
			else{
				scan.close();
				throw new ParserException("ParseKPSUsers: Incorrect tag found: "+scan.next());
			}
		}

		// file parsing successful
		scan.close();
		return users;
	}

	public static RouteGraph parseGraph(String filename) throws ParserException{
		RouteGraph routeGraph = new RouteGraph();

		// load file into the scanner
		Scanner scan = null;
		try{
			scan = new Scanner(new File(filename));
		}catch(IOException e){throw new ParserException("ParseGraph: Cannot load file "+filename);}

		String data = convertXMLDataToString(scan);
		scan.close();
		scan = new Scanner(data);

		gobble(scan, "<"+GRAPH_FILE_TAG+">");
		while(scan.hasNext()){
			if(scan.hasNext("<"+TRANSPORT_COST_UPDATE_TAG+">")){
				Route route = new Route(KPSParser.parseTransportCostUpdateEvent(scan));
			}
		}

		return routeGraph;
	}

	/**
	 * Converts the XML data contained in the scanner into a String.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 *
	 * @return string of xml data
	 */
	private static String convertXMLDataToString(Scanner scan){
		String data = "";
		while(scan.hasNext()){
			data += scan.next() + " ";
		}
		// ensure that there are gaps after > and before <
		data = data.replaceAll(">", "> ");
		data = data.replaceAll("<", " <");
		return data;
	}

	/**
	 * Parses a Mail Delivery Event from the scanner.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 *
	 * @return new mail delivery event based on data
	 *
	 * @throws ParserException
	 * 		-- thrown if tags are incorrect or lines do not
	 * 		match the xml format
	 */
	public static MailDeliveryEvent parseMailDeliveryEvent(Scanner scan) throws ParserException{
		// parse data from the file
		gobble(scan, "<"+MAIL_DELIVERY_TAG+">");
		long time = parseLong(scan, TIME_TAG);
		Day day = Day.convertStringToDay(parseString(scan, DAY_TAG));
		BasicRoute route = parseBasicRoute(scan);
		int weight = parseInt(scan, WEIGHT_TAG);
		int volume = parseInt(scan, VOLUME_TAG);
		Priority priority = Priority.convertStringToPriority(parseString(scan, PRIORITY_TAG));
		double revenue = parseDouble(scan, REVENUE_TAG);
		double expenditure = parseDouble(scan, EXPENDITURE_TAG);
		int deliveryTime = parseInt(scan, DELIVERY_TIME_TAG);
		gobble(scan, "</"+MAIL_DELIVERY_TAG+">");

		// data has been successfully retrieved
		return new MailDeliveryEvent(time, route, day, weight, volume, priority, revenue, expenditure, deliveryTime);
	}

	/**
	 * Parses a Price Update Event from the scanner.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 *
	 * @return new price update event based on data
	 *
	 * @throws ParserException
	 * 		-- thrown if tags are incorrect or lines do not
	 * 		match the xml format
	 */
	public static PriceUpdateEvent parsePriceUpdateEvent(Scanner scan) throws ParserException{
		// parse data from the file
		gobble(scan, "<"+PRICE_UPDATE_TAG+">");
		long time = parseLong(scan, TIME_TAG);
		BasicRoute route = parseBasicRoute(scan);
		Priority priority = Priority.convertStringToPriority(parseString(scan, PRIORITY_TAG));
		DeliveryPrice price = parseDeliveryPrice(scan);
		gobble(scan, "</"+PRICE_UPDATE_TAG+">");

		// data has been successfully retrieved
		return new PriceUpdateEvent(time, route, price, priority);
	}

	/**
	 * Parses a Transport Cost Update Event from the scanner.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 *
	 * @return new transport cost update event based on data
	 *
	 * @throws ParserException
	 * 		-- thrown if tags are incorrect or lines do not
	 * 		match the xml format
	 */
	public static TransportCostUpdateEvent parseTransportCostUpdateEvent(Scanner scan) throws ParserException{
		// parse data from the file
		gobble(scan, "<"+TRANSPORT_COST_UPDATE_TAG+">");
		long time = parseLong(scan, TIME_TAG);
		String company = parseString(scan, COMPANY_TAG);
		BasicRoute route = parseBasicRoute(scan);
		TransportType type = TransportType.convertStringToTransportType(parseString(scan, TRANSPORT_TYPE_TAG));
		DeliveryPrice price = parseDeliveryPrice(scan);
		int maxWeight = parseInt(scan, MAX_WEIGHT_TAG);
		int maxVolume = parseInt(scan, MAX_VOLUME_TAG);
		int duration = parseInt(scan, DURATION_TAG);
		int frequency = parseInt(scan, FREQUENCY_TAG);
		Day day = Day.convertStringToDay(parseString(scan, DAY_TAG));
		gobble(scan, "</"+TRANSPORT_COST_UPDATE_TAG+">");

		// data has been successfully retrieved
		MailTransport transport = new MailTransport(duration, frequency, day);
		return new TransportCostUpdateEvent(time, route, company, type, price, maxWeight, maxVolume, transport);
	}

	/**
	 * Parses a Transport Discontinued Event from the scanner.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 *
	 * @return new tranpsort discontinued event based on data
	 *
	 * @throws ParserException
	 * 		-- thrown if tags are incorrect or lines do not
	 * 		match the xml format
	 */
	public static TransportDiscontinuedEvent parseTransportDiscontinuedEvent(Scanner scan) throws ParserException{
		// parse data from file
		gobble(scan, "<"+TRANSPORT_DISCONTINUED_TAG+">");
		long time = parseLong(scan, TIME_TAG);
		String company = parseString(scan, COMPANY_TAG);
		BasicRoute route = parseBasicRoute(scan);
		TransportType type = TransportType.convertStringToTransportType(parseString(scan, TRANSPORT_TYPE_TAG));
		gobble(scan, "</"+TRANSPORT_DISCONTINUED_TAG+">");

		// data has been successfully retrieved
		return new TransportDiscontinuedEvent(time, route, company, type);
	}

	/**
	 * Parses a KPSUser, consisting of a username, passoword hash and position,
	 * from the scanner.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 *
	 * @return new KPSUser based on data
	 *
	 * @throws ParserException
	 * 		-- thrown if tags are incorrect or lines do not match the xml format
	 */
	public static KPSUser parseKPSUser(Scanner scan) throws ParserException{
		// parse data from file
		gobble(scan, "<"+USER_TAG+">");
		String username = parseString(scan, USERNAME_TAG);
		int passwordHash = parseInt(scan, PASSWORD_TAG);
		Position position = Position.convertStringToPosition(parseString(scan, POSITION_TAG));
		gobble(scan, "</"+USER_TAG+">");

		// data has been successfully retrieved
		return new KPSUser(username, passwordHash, position);
	}

	/**
	 * Parses a basic route, consisting of an origin and destination,
	 * from the scanner.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 *
	 * @return new basic route based on data
	 *
	 * @throws ParserException
	 * 		-- thrown if tags are incorrect or lines do not match the xml format
	 */
	private static BasicRoute parseBasicRoute(Scanner scan) throws ParserException{
		String destination = parseString(scan, DESTINATION_TAG);
		String origin = parseString(scan, ORIGIN_TAG);
		return new BasicRoute(origin, destination);
	}

	/**
	 * Parses a delivery price, consisting of a weight and volume
	 * price, from the scanner.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 *
	 * @return new delivery based on data
	 *
	 * @throws ParserException
	 * 		-- thrown if tags are incorrect or lines do not match the xml format
	 */
	private static DeliveryPrice parseDeliveryPrice(Scanner scan) throws ParserException{
		double weightCost = parseDouble(scan, WEIGHT_COST_TAG);
		double volumeCost = parseDouble(scan, VOLUME_COST_TAG);
		return new DeliveryPrice(weightCost, volumeCost);
	}

	/**
	 * Parses a string value from the scanner using the specified
	 * XML tags.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 * @param tag
	 * 		-- the current tag expected to be parsed
	 *
	 * @return the string value contained between the xml tags
	 *
	 * @throws ParserException
	 * 		-- thrown if tags are incorrect or line does not match the xml format
	 */
	public static String parseString(Scanner scan, String tag) throws ParserException{
		gobble(scan, "<"+tag+">");
		String data = "";
		// collect all data separated by white space
		while(scan.hasNext()){
			if(!scan.hasNext("</"+tag+">")){
				data += scan.next();
				// if the close tags aren't next then add a space
				data += (!scan.hasNext("</"+tag+">")) ? " " : "";
			}
			else{
				break;
			}
		}
		gobble(scan, "</"+tag+">");
		return data;
	}

	/**
	 * Parses an integer value from the scanner using the specified
	 * XML tags.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 * @param tag
	 * 		-- the current tag expected to be parsed

	 * @return the integer value contained between the xml tags
	 *
	 * @throws ParserException
	 * 		-- thrown if tags are incorrect or line does not match
	 * 		up with the xml format or an integer cannot be parsed
	 */
	public static int parseInt(Scanner scan, String tag) throws ParserException{
		gobble(scan, "<"+tag+">");
		int data = 0;
		String next = scan.next();
		// try catch statement to catch trying to parse incorrect type
		try{
			data = Integer.parseInt(next);
		}catch(NumberFormatException e){throw new ParserException("ParserException: Expecting an integer, received "+next);}
		gobble(scan, "</"+tag+">");
		return data;
	}

	/**
	 * Parses a double value from the scanner using the specified
	 * XML tags.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 * @param tag
	 * 		-- the current tag expected to be parsed
	 *
	 * @return the double value contained between the xml tags
	 *
	 * @throws ParserException
	 * 		-- thrown if tags are incorrect, line does not match
	 * 		up with the xml format or if a double cannot be parsed
	 */
	public static double parseDouble(Scanner scan, String tag) throws ParserException{
		gobble(scan, "<"+tag+">");
		double data = 0;
		String next = scan.next();
		// try catch statement to catch trying to parse an incorrect type
		try{
			data = Double.parseDouble(next);
		}catch(NumberFormatException e){throw new ParserException("ParserException: Expecting a double, recieved "+next);}
		gobble(scan, "</"+tag+">");
		return data;
	}

	/**
	 * Parses a long value from the scanner using the specified
	 * XML tags.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 * @param tag
	 * 		-- the current tag expected to be parsed

	 * @return the long value contained between the xml tags
	 *
	 * @throws ParserException
	 * 		-- thrown if tags are incorrect or line does not match
	 * 		up with the xml format or a long cannot be parsed
	 */
	public static long parseLong(Scanner scan, String tag) throws ParserException{
		gobble(scan, "<"+tag+">");
		long data = 0;
		String next = scan.next();
		// try catch statement to catch trying to parse incorrect type
		try{
			data = Long.parseLong(next);
		}catch(NumberFormatException e){throw new ParserException("ParserException: Expecting an integer, received "+next);}
		gobble(scan, "</"+tag+">");
		return data;
	}

	/**
	 * Attempts to gobble the expected tag from the file currently being
	 * scanned. Throws a ParserException if the expected tag is not found
	 * or if the end of the file has been reached prematurely.
	 *
	 * @param scan
	 * 		-- scanner containing xml data
	 * @param tag
	 * 		-- the current tag expected to be gobbled
	 *
	 * @throws ParserException
	 * 		-- thrown if the correct tag is not gobbled or the end of
	 * 		the file has been reached prematurely
	 */
	private static void gobble(Scanner scan, String tag) throws ParserException{
		if(!scan.hasNext(tag)){
			String received = "";
			// try catch statement to catch reaching the end of the file
			try{
				received = scan.next();
			}catch(NoSuchElementException e){throw new ParserException("ParserException: End of file reached prematurely.");}

			// expected tag was not found
			throw new ParserException("ParserException: Expecting "+tag+", received "+received);
		}
		// gobble the expected tag
		scan.next();
	}
}
