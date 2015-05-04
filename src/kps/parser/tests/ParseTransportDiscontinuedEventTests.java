package kps.parser.tests;

import static org.junit.Assert.*;

import java.util.Scanner;

import kps.data.wrappers.BasicRoute;
import kps.enums.TransportType;
import kps.events.TransportDiscontinuedEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;

import org.junit.Test;

/**
 * Tests to ensure that the parseTransportDiscontinuedEvent in KPSParser is working correctly.
 * 
 * @author David
 *
 */
public class ParseTransportDiscontinuedEventTests {

	// fields
	
	// data for xml tags
	private long timeData = 0;
	private String companyData = "Kelburn Airways";
	private String toData = "Sydney";
	private String fromData ="Wellington";
	private TransportType typeData = TransportType.AIR;

	// shortcuts for xml tags
	private String discontinue = KPSParser.TRANSPORT_DISCONTINUED_TAG;
	private String time = "<"+KPSParser.TIME_TAG+"> "+timeData+" </"+KPSParser.TIME_TAG+">";
	private String company = "<"+KPSParser.COMPANY_TAG+"> "+companyData+" </"+KPSParser.COMPANY_TAG+">";
	private String to = "<"+KPSParser.DESTINATION_TAG+"> "+toData+" </"+KPSParser.DESTINATION_TAG+">";
	private String from = "<"+KPSParser.ORIGIN_TAG+"> "+fromData+" </"+KPSParser.ORIGIN_TAG+">";
	private String type = "<"+KPSParser.TRANSPORT_TYPE_TAG+"> "+TransportType.convertTransportTypeToString(typeData)+" </"+KPSParser.TRANSPORT_TYPE_TAG+">";
	private String incorrectTag = "<tag> incorrect </tag>";

	// objects for event construction
	private BasicRoute route = new BasicRoute(fromData, toData);
	private TransportDiscontinuedEvent event = new TransportDiscontinuedEvent(0, route, companyData, typeData);

	/**
	 * Test that a PriceUpdateEvent can be parsed successfully.
	 */
	@Test public void correctParseTransportDiscontinuedEvent_1(){
		String data = "<"+discontinue+"> "+time+" "+company+" "+to+" "+from+" "+type+" </"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			TransportDiscontinuedEvent test = KPSParser.parseTransportDiscontinuedEvent(scan);
			if(!event.equals(test)){
				fail("Error: Both TransportDiscontinuedEvents should be identical.");
			}
		}catch(ParserException e){fail(e.getMessage());}
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportDiscontinuedEvent.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_1(){
		String data = "<"+discontinue+"> "+time+" "+to+" "+from+" "+type+" </"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportDiscontinuedEvent.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_2(){
		String data = "<"+discontinue+"> "+time+" "+company+" "+from+" "+type+" </"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportDiscontinuedEvent.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_3(){
		String data = "<"+discontinue+"> "+time+" "+company+" "+to+" "+type+" </"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportDiscontinuedEvent.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_4(){
		String data = "<"+discontinue+"> "+time+" "+company+" "+to+" "+from+" </"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportDiscontinuedEvent.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_5(){
		String data = "<"+discontinue+"> "+time+" "+company+" "+to+" "+from+" "+type+" "+incorrectTag+" </"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */	
	@Test public void incorrectParseTransportDiscontinuedEvent_6(){
		String data = "<"+discontinue+" "+time+" "+company+" "+to+" "+from+" "+type+" </"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_7(){
		String data = ""+discontinue+"> "+time+" "+company+" "+to+" "+from+" "+type+" </"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_8(){
		String data = "</"+discontinue+"> "+time+" "+company+" "+to+" "+from+" "+type+" </"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_9(){
		String data = "<"+discontinue+"> "+time+" "+company+" "+to+" "+from+" "+type+" /"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_10(){
		String data = "<"+discontinue+"> "+time+" "+company+" "+to+" "+from+" "+type+" <"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_11(){
		String data = "<"+discontinue+"> "+time+" "+company+" "+to+" "+from+" "+type+" </"+discontinue+"";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are incorrect.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_12(){
		String data = "<"+discontinue+"> "+time+" "+company+" "+to+" "+from+" "+type+" </incorrect>";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are incorrect.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_13(){
		String data = "<incorrect> "+time+" "+company+" "+to+" "+from+" "+type+" </"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are incorrect.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_14(){
		String data = "<incorrect> "+time+" "+company+" "+to+" "+from+" "+type+" </incorrect>";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}

	/**
	 * Test that an error is thrown when the expected tags are out of sequence for
	 * a TransportDiscontinuedEvent.
	 */
	@Test public void incorrectParseTransportDiscontinuedEvent_15(){
		String data = "<"+discontinue+"> "+time+" "+type+" "+from+" "+to+" "+company+" </"+discontinue+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportDiscontinuedEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting a ParserException to be thrown.");
	}
}
