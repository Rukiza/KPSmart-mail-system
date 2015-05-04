package kps.parser.tests;

import static org.junit.Assert.*;

import java.util.Scanner;

import kps.data.wrappers.BasicRoute;
import kps.data.wrappers.DeliveryPrice;
import kps.data.wrappers.MailTransport;
import kps.enums.Day;
import kps.enums.TransportType;
import kps.events.TransportCostUpdateEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;

import org.junit.Test;

public class ParseTransportCostUpdateEventTests {

	// fields
	
	// data for xml tags
	private long timeData = 0;
	private String companyData = "Air NZ";
	private String toData = "Rome";
	private String fromData = "Wellington";
	private TransportType typeData = TransportType.AIR;
	private double weightCostData = 6;
	private double volumeCostData = 6;
	private int maxWeightData = 350;
	private int maxVolumeData = 50;
	private int durationData = 16;
	private int frequencyData = 36;
	private Day dayData = Day.THURSDAY;

	// shortcuts for xml tags
	private String cost = KPSParser.TRANSPORT_COST_UPDATE_TAG;
	private String time = "<"+KPSParser.TIME_TAG+"> "+timeData+" </"+KPSParser.TIME_TAG+">";
	private String company = "<"+KPSParser.COMPANY_TAG+"> "+companyData+" </"+KPSParser.COMPANY_TAG+">";
	private String to = "<"+KPSParser.DESTINATION_TAG+"> "+toData+" </"+KPSParser.DESTINATION_TAG+">";
	private String from = "<"+KPSParser.ORIGIN_TAG+"> "+fromData+" </"+KPSParser.ORIGIN_TAG+">";
	private String type = "<"+KPSParser.TRANSPORT_TYPE_TAG+"> "+TransportType.convertTransportTypeToString(typeData)+" </"+KPSParser.TRANSPORT_TYPE_TAG+">";
	private String weightCost = "<"+KPSParser.WEIGHT_COST_TAG+"> "+weightCostData+" </"+KPSParser.WEIGHT_COST_TAG+">";
	private String volumeCost = "<"+KPSParser.VOLUME_COST_TAG+"> "+volumeCostData+" </"+KPSParser.VOLUME_COST_TAG+">";
	private String maxWeight = "<"+KPSParser.MAX_WEIGHT_TAG+"> "+maxWeightData+" </"+KPSParser.MAX_WEIGHT_TAG+">";
	private String maxVolume = "<"+KPSParser.MAX_VOLUME_TAG+"> "+maxVolumeData+" </"+KPSParser.MAX_VOLUME_TAG+">";
	private String duration = "<"+KPSParser.DURATION_TAG+"> "+durationData+" </"+KPSParser.DURATION_TAG+">";
	private String frequency = "<"+KPSParser.FREQUENCY_TAG+"> "+frequencyData+" </"+KPSParser.FREQUENCY_TAG+">";
	private String day = "<"+KPSParser.DAY_TAG+"> "+Day.convertDayToString(dayData)+" </"+KPSParser.DAY_TAG+">";
	private String incorrectTag = "<tag> incorrect </tag>";

	// objects for event construction
	private BasicRoute route = new BasicRoute(fromData, toData);
	private DeliveryPrice price = new DeliveryPrice(weightCostData, volumeCostData);
	private MailTransport  mail = new MailTransport(durationData, frequencyData, dayData);
	private TransportCostUpdateEvent event = new TransportCostUpdateEvent(0, route, companyData, typeData, price, maxWeightData, maxVolumeData, mail);

	/**
	 * Test that a TransportCostUpdateEvent can be parsed successfully.
	 */
	@Test public void correctParseTransportCostUpdateEvent_1(){
		String data = "<"+cost+"> "+time+" "+company+" "+to+" "+from+" "+type+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			TransportCostUpdateEvent test = KPSParser.parseTransportCostUpdateEvent(scan);
			if(!event.equals(test)){
				fail("Error: Both TransportCostUpdateEvents should be indentical.");
			}
		}catch(ParserException e){fail(e.getMessage());}
	}
	
	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_1(){
		String data = "<"+cost+"> "+time+" "+type+" "+to+" "+from+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_2(){
		String data = "<"+cost+"> "+time+" "+company+" "+to+" "+from+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_3(){
		String data = "<"+cost+"> "+time+" "+company+" "+type+" "+from+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_4(){
		String data = "<"+cost+"> "+time+" "+company+" "+type+" "+to+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_5(){
		String data = "<"+cost+"> "+time+" "+company+" "+type+" "+to+" "+from+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_6(){
		String data = "<"+cost+"> "+time+" "+company+" "+type+" "+to+" "+from+" "+weightCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_7(){
		String data = "<"+cost+"> "+time+" "+company+" "+type+" "+to+" "+from+" "+weightCost+" "+volumeCost+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_8(){
		String data = "<"+cost+"> "+time+" "+company+" "+type+" "+to+" "+from+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_9(){
		String data = "<"+cost+"> "+time+" "+company+" "+type+" "+to+" "+from+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_10(){
		String data = "<"+cost+"> "+time+" "+company+" "+type+" "+to+" "+from+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_11(){
		String data = "<"+cost+"> "+time+" "+company+" "+type+" "+to+" "+from+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_12(){
		String data = "<"+cost+"> "+time+" "+company+" "+to+" "+from+" "+type+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" "+incorrectTag+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when the expected tags are out of sequence for
	 * a TransportCostUpdateEvent.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_13(){
		String data = "<"+cost+"> "+time+" "+day+" "+frequency+" "+duration+" "+maxVolume+" "+volumeCost+" "+weightCost+" "+maxWeight+" "+type+" "+from+" "+to+" "+company+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_14(){
		String data = ""+cost+"> "+time+" "+company+" "+to+" "+from+" "+type+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_15(){
		String data = "<"+cost+" "+time+" "+company+" "+to+" "+from+" "+type+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_16(){
		String data = "</"+cost+"> "+time+" "+company+" "+to+" "+from+" "+type+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_17(){
		String data = "<"+cost+"> "+time+" "+company+" "+to+" "+from+" "+type+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" /"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_18(){
		String data = "<"+cost+"> "+time+" "+company+" "+to+" "+from+" "+type+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" <"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_19(){
		String data = "<"+cost+"> "+time+" "+company+" "+to+" "+from+" "+type+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+"";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are xml tags which are incorrect.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_20(){
		String data = "<tag> "+time+" "+company+" "+to+" "+from+" "+type+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </"+cost+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are xml tags which are incorrect.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_21(){
		String data = "<"+cost+"> "+time+" "+company+" "+to+" "+from+" "+type+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </tag>";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
	
	/**
	 * Test that an error is thrown when there are xml tags which are incorrect.
	 */
	@Test public void incorrectParseTransportCostUpdateEvent_22(){
		String data = "<tag> "+time+" "+company+" "+to+" "+from+" "+type+" "+weightCost+" "+volumeCost+" "+maxWeight+" "+maxVolume+" "+duration+" "+frequency+" "+day+" </tag>";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseTransportCostUpdateEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Expecting to catch a ParserException.");
	}
}
