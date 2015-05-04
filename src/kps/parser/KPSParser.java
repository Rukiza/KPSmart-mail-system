package kps.parser;

import java.util.NoSuchElementException;
import java.util.Scanner;

import kps.data.wrappers.BasicRoute;
import kps.data.wrappers.DeliveryPrice;
import kps.data.wrappers.MailTransport;
import kps.enums.Day;
import kps.enums.Priority;
import kps.enums.TransportType;
import kps.events.MailDeliveryEvent;
import kps.events.PriceUpdateEvent;
import kps.events.TransportCostUpdateEvent;
import kps.events.TransportDiscontinuedEvent;

public class KPSParser {
	
	// fields
	public static final String MAIL_DELIVERY_TAG = "mail";
	public static final String PRICE_UPDATE_TAG = "price";
	public static final String TRANSPORT_COST_UPDATE_TAG = "cost";
	public static final String TRANSPORT_DISCONTINUED_TAG = "discontinue";
	public static final String ORIGIN_TAG = "from";
	public static final String DESTINATION_TAG = "to";
	public static final String COMPANY_TAG = "company";
	public static final String WEIGHT_TAG = "weight";
	public static final String WEIGHT_COST_TAG = "weightcost";
	public static final String MAX_WEIGHT_TAG = "maxWeight";
	public static final String VOLUME_TAG = "volume";
	public static final String VOLUME_COST_TAG = "volumecost";
	public static final String MAX_VOLUME_TAG = "maxVolume";
	public static final String TRANSPORT_TYPE_TAG = "type";
	public static final String PRIORITY_TAG = "priority";
	public static final String DAY_TAG = "day";
	public static final String DURATION_TAG = "duration";
	public static final String FREQUENCY_TAG = "frequency";
		
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
		// add something for parsing time
		Day day = Day.convertStringToDay(parseString(scan, DAY_TAG));
		BasicRoute route = parseBasicRoute(scan);
		int weight = parseInt(scan, WEIGHT_TAG);
		int volume = parseInt(scan, VOLUME_TAG);
		Priority priority = Priority.convertStringToPriority(parseString(scan, PRIORITY_TAG));
		gobble(scan, "</"+MAIL_DELIVERY_TAG+">");
		
		// data has been successfully retrieved
		return new MailDeliveryEvent(0, route, day, weight, volume, priority);
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
		// add something for parsing time
		BasicRoute route = parseBasicRoute(scan);
		Priority priority = Priority.convertStringToPriority(parseString(scan, PRIORITY_TAG));
		DeliveryPrice price = parseDeliveryPrice(scan);
		gobble(scan, "</"+PRICE_UPDATE_TAG+">");
		
		// data has been successfully retrieved
		return new PriceUpdateEvent(0, route, price, priority);
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
		// add something for parsing time
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
		return new TransportCostUpdateEvent(0, route, company, type, price, maxWeight, maxVolume, transport);
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
		// add something for parsing time
		String company = parseString(scan, COMPANY_TAG);
		BasicRoute route = parseBasicRoute(scan);
		TransportType type = TransportType.convertStringToTransportType(parseString(scan, TRANSPORT_TYPE_TAG));
		gobble(scan, "</"+TRANSPORT_DISCONTINUED_TAG+">");
		
		// data has been successfully retrieved
		return new TransportDiscontinuedEvent(0, route, company, type);
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
