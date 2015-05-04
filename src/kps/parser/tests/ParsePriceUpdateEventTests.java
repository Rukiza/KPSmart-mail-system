package kps.parser.tests;

import static org.junit.Assert.*;

import java.util.Scanner;

import kps.data.wrappers.BasicRoute;
import kps.data.wrappers.DeliveryPrice;
import kps.enums.Priority;
import kps.events.PriceUpdateEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;

import org.junit.Test;

/**
 * Tests to ensure that the parsePriceUpdateEvent in KPSParser is working correctly.
 * 
 * @author David
 *
 */
public class ParsePriceUpdateEventTests {

	// fields
	
	// data for xml tags
	private long timeData = 0;
	private String toData = "Rome";
	private String fromData = "Wellington";
	private Priority priorityData = Priority.INTERNATIONAL_AIR;
	private double weightCostData = 6;
	private double volumeCostData = 8;
	
	// shortcuts for xml tags
	private String price = KPSParser.PRICE_UPDATE_TAG;
	private String time = "<"+KPSParser.TIME_TAG+"> "+timeData+" </"+KPSParser.TIME_TAG+">";
	private String to = "<"+KPSParser.DESTINATION_TAG+"> "+toData+" </"+KPSParser.DESTINATION_TAG+">";
	private String from = "<"+KPSParser.ORIGIN_TAG+"> "+fromData+" </"+KPSParser.ORIGIN_TAG+">";
	private String weightCost = "<"+KPSParser.WEIGHT_COST_TAG+"> "+weightCostData+" </"+KPSParser.WEIGHT_COST_TAG+">";
	private String volumeCost = "<"+KPSParser.VOLUME_COST_TAG+"> "+volumeCostData+" </"+KPSParser.VOLUME_COST_TAG+">";
	private String priority = "<"+KPSParser.PRIORITY_TAG+"> "+Priority.convertPriorityToString(priorityData)+" </"+KPSParser.PRIORITY_TAG+">";
	private String incorrectTag = "<tag> incorrect </tag>";
	
	// objects for event construction
	private BasicRoute route = new BasicRoute(fromData, toData);
	private DeliveryPrice deliveryPrice = new DeliveryPrice(weightCostData, volumeCostData);
	private PriceUpdateEvent event = new PriceUpdateEvent(0, route, deliveryPrice, priorityData);

	/**
	 * Test that a PriceUpdateEvent can be parsed successfully.
	 */
	@Test public void correctParsePriceUpdateEventTest_1(){
		String data = "<"+price+"> "+time+" "+to+" "+from+" "+priority+" "+weightCost+" "+volumeCost+" </"+price+">";
		Scanner scan = new Scanner(data);
		try{
			PriceUpdateEvent test = KPSParser.parsePriceUpdateEvent(scan);
			if(!event.equals(test)){
				fail("Error: Both PriceUpdateEvents should be identical.");
			}
		}catch(ParserException e){fail(e.getMessage());}
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a PriceUpdateEvent.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_1(){
		String data = "<"+price+"> "+time+" "+from+" "+priority+" "+weightCost+" "+volumeCost+" </"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a PriceUpdateEvent.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_2(){
		String data = "<"+price+"> "+time+" "+to+" "+priority+" "+weightCost+" "+volumeCost+" </"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a PriceUpdateEvent.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_3(){
		String data = "<"+price+"> "+time+" "+to+" "+from+" "+weightCost+" "+volumeCost+" </"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a PriceUpdateEvent.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_4(){
		String data = "<"+price+"> "+time+" "+to+" "+from+" "+priority+" "+volumeCost+" </"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a PriceUpdateEvent.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_5(){
		String data = "<"+price+"> "+time+" "+to+" "+from+" "+priority+" "+weightCost+" </"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a PriceUpdateEvent.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_6(){
		String data = "<"+price+"> "+time+" "+to+" "+from+" "+priority+" "+weightCost+" "+volumeCost+" "+incorrectTag+" </"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when the expected tags are out of sequence for
	 * a PriceUpdateEvent.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_7(){
		String data = "<"+price+"> "+time+" "+volumeCost+" "+weightCost+" "+priority+" "+from+" "+to+" </"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_8(){
		String data = "<"+price+" "+time+" "+to+" "+from+" "+priority+" "+weightCost+" "+volumeCost+" </"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_9(){
		String data = ""+price+"> "+time+" "+to+" "+from+" "+priority+" "+weightCost+" "+volumeCost+" </"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_10(){
		String data = "</"+price+"> "+time+" "+to+" "+from+" "+priority+" "+weightCost+" "+volumeCost+" </"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_11(){
		String data = "<"+price+"> "+time+" "+to+" "+from+" "+priority+" "+weightCost+" "+volumeCost+" /"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_12(){
		String data = "<"+price+"> "+time+" "+to+" "+from+" "+priority+" "+weightCost+" "+volumeCost+" <"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_13(){
		String data = "<"+price+"> "+time+" "+to+" "+from+" "+priority+" "+weightCost+" "+volumeCost+" </"+price+"";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are incorrect.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_14(){
		String data = "<incorrect> "+time+" "+to+" "+from+" "+priority+" "+weightCost+" "+volumeCost+" </"+price+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are incorrect.
	 */
	@Test public void incorrectParsePriceUpdateEventTest_15(){
		String data = "<"+price+"> "+time+" "+to+" "+from+" "+priority+" "+weightCost+" "+volumeCost+" </incorrect>";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parsePriceUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
}
