package kps.parser.tests;

import static org.junit.Assert.*;

import java.util.Scanner;

import kps.data.wrappers.BasicRoute;
import kps.enums.Day;
import kps.enums.Priority;
import kps.events.MailDeliveryEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;

import org.junit.Test;

/**
 * Tests to ensure that the parseMailDeliveryEvent in KPSParser is working correctly.
 *
 * @author David
 *
 */
public class ParseMailDelieveryEventTests {

	// fields

	// data for xml tags
	private long timeData = 0;
	private Day dayData = Day.MONDAY;
	private String toData = "Rome";
	private String fromData = "Wellington";
	private int weightData = 100;
	private int volumeData = 5;
	private Priority priorityData = Priority.INTERNATIONAL_AIR;
	private double revenueData = 10;
	private double expenditureData = 2;

	// shortcuts for xml tags
	private String mail = KPSParser.MAIL_DELIVERY_TAG;
	private String time = "<"+KPSParser.TIME_TAG+"> "+timeData+" </"+KPSParser.TIME_TAG+">";
	private String day = "<"+KPSParser.DAY_TAG+"> "+Day.convertDayToString(dayData)+" </"+KPSParser.DAY_TAG+">";
	private String to = "<"+KPSParser.DESTINATION_TAG+"> "+toData+" </"+KPSParser.DESTINATION_TAG+">";
	private String from = "<"+KPSParser.ORIGIN_TAG+"> "+fromData+" </"+KPSParser.ORIGIN_TAG+">";
	private String weight = "<"+KPSParser.WEIGHT_TAG+"> "+weightData+" </"+KPSParser.WEIGHT_TAG+">";
	private String volume = "<"+KPSParser.VOLUME_TAG+"> "+volumeData+" </"+KPSParser.VOLUME_TAG+">";
	private String priority = "<"+KPSParser.PRIORITY_TAG+"> "+Priority.convertPriorityToString(priorityData)+" </"+KPSParser.PRIORITY_TAG+">";
	private String revenue = "<"+KPSParser.REVENUE_TAG+"> "+revenueData+" </"+KPSParser.REVENUE_TAG+">";
	private String expenditure = "<"+KPSParser.EXPENDITURE_TAG+"> "+expenditureData+" </"+KPSParser.EXPENDITURE_TAG+">";
	private String incorrectTag = "<tag> incorrect </tag>";

	// objects for event construction
	private BasicRoute route = new BasicRoute(fromData, toData);
	private MailDeliveryEvent event = new MailDeliveryEvent(0, route, dayData, weightData, volumeData, priorityData, revenueData, expenditureData);

	/**
	 * Test that a MailDeliveryEvent can be parsed successfully.
	 */
	@Test public void correctParseMailDeliveryEvent_1(){
		String data = "<"+mail+"> "+time+" "+day+" "+to+" "+from+" "+weight+" "+volume+" "+priority+" "+revenue+" "+expenditure+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			MailDeliveryEvent test = KPSParser.parseMailDeliveryEvent(scan);
			if(!event.equals(test)){
				fail("Error: Both MailDeliveryEvents should be equal to eachother.");
			}
		}catch(ParserException e){fail(e.getMessage());}
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a MailDeliveryEvent.
	 */
	@Test public void incorrectParseMailDeliveryEvent_1(){
		String data = "<"+mail+"> "+time+" "+to+" "+from+" "+weight+" "+volume+" "+priority+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a MailDeliveryEvent.
	 */
	@Test public void incorrectParseMailDeliveryEvent_2(){
		String data = "<"+mail+"> "+time+" "+day+" "+from+" "+weight+" "+volume+" "+priority+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a MailDeliveryEvent.
	 */
	@Test public void incorrectParseMailDeliveryEvent_3(){
		String data = "<"+mail+"> "+time+" "+day+" "+to+" "+weight+" "+volume+" "+priority+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a MailDeliveryEvent.
	 */
	@Test public void incorrectParseMailDeliveryEvent_4(){
		String data = "<"+mail+"> "+time+" "+day+" "+to+" "+from+" "+volume+" "+priority+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a MailDeliveryEvent.
	 */
	@Test public void incorrectParseMailDeliveryEvent_5(){
		String data = "<"+mail+"> "+time+" "+day+" "+to+" "+from+" "+weight+" "+priority+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a MailDeliveryEvent.
	 */
	@Test public void incorrectParseMailDeliveryEvent_6(){
		String data = "<"+mail+"> "+time+" "+day+" "+to+" "+from+" "+weight+" "+volume+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are an incorrect number of values for
	 * a MailDeliveryEvent.
	 */
	@Test public void incorrectParseMailDeliveryEvent_7(){
		String data = "<"+mail+"> "+time+" "+day+" "+to+" "+from+" "+weight+" "+volume+" "+priority+" "+incorrectTag+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when the expected tags are out of sequence for
	 * a MailDeliveryEvent.
	 */
	@Test public void incorrectParseMailDeliveryEvent_8(){
		String data = "<"+mail+"> "+priority+" "+volume+" "+weight+" "+from+" "+to+" "+day+"" +time+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseMailDeliveryEvent_9(){
		String data = "<"+mail+""+time+" "+day+" "+to+" "+from+" "+weight+" "+volume+" "+priority+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseMailDeliveryEvent_10(){
		String data = ""+mail+"> "+time+" "+day+" "+to+" "+from+" "+weight+" "+volume+" "+priority+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseMailDeliveryEvent_11(){
		String data = "</"+mail+"> "+time+" "+day+" "+to+" "+from+" "+weight+" "+volume+" "+priority+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseMailDeliveryEvent_12(){
		String data = "<"+mail+"> "+time+" "+day+" "+to+" "+from+" "+weight+" "+volume+" "+priority+" <"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseMailDeliveryEvent_13(){
		String data = "<"+mail+"> "+time+" "+day+" "+to+" "+from+" "+weight+" "+volume+" "+priority+" /"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are not formatted
	 * correctly.
	 */
	@Test public void incorrectParseMailDeliveryEvent_14(){
		String data = "<"+mail+"> "+time+" "+day+" "+to+" "+from+" "+weight+" "+volume+" "+priority+" </"+mail+"";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are incorrect.
	 */
	@Test public void incorrectParseMailDeliveryEvent_15(){
		String data = "<incorrect> "+time+" "+day+" "+to+" "+from+" "+weight+" "+volume+" "+priority+" </"+mail+">";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are incorrect.
	 */
	@Test public void incorrectParseMailDeliveryEvent_16(){
		String data = "<"+mail+"> "+time+" "+day+" "+to+" "+from+" "+weight+" "+volume+" "+priority+" </incorrect>";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}

	/**
	 * Test that an error is thrown when there are xml tags which are incorrect.
	 */
	@Test public void incorrectParseMailDeliveryEvent_17(){
		String data = "<incorrect> "+time+" "+day+" "+to+" "+from+" "+weight+" "+volume+" "+priority+" </incorrect>";
		Scanner scan = new Scanner(data);
		try{
			KPSParser.parseMailDeliveryEvent(scan);
		}catch(ParserException e){return;}
		fail("Error: Should have caught a ParserException.");
	}
}
