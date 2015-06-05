package kps;

import static org.junit.Assert.*;

import java.util.Map;

import kps.data.CustomerRoute;
import kps.data.Route;
import kps.data.wrappers.BasicRoute;
import kps.data.wrappers.EventLog;
import kps.enums.Day;
import kps.enums.Position;
import kps.enums.Priority;
import kps.enums.TransportType;
import kps.events.BusinessEvent;
import kps.events.MailDeliveryEvent;
import kps.events.PriceUpdateEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;
import kps.users.KPSUser;

import org.junit.Test;

public class KPSmartSystemTests {

	private String[] users = {"User1", "User2", "User3"};
	private String[] passwords = {"pass1", "pass2", "pass3"};

	/**
	 * Test that a user added to the system is contained in the system.
	 */
	@Test public void correctAddUser_1(){
		KPSmartSystem kps = new KPSmartSystem();
		kps.addKPSUser(users[0], passwords[0].hashCode(), Position.CLERK);
		assertTrue(kps.containsKPSUser(users[0]));
	}

	/**
	 * Test that a user added to the system is contained in the system.
	 */
	@Test public void correctAddUser_2(){
		KPSmartSystem kps = new KPSmartSystem();
		kps.addKPSUser(users[1], passwords[1].hashCode(), Position.MANAGER);
		assertTrue(kps.containsKPSUser(users[1]));
	}

	/**
	 * Test that a user added to the system is contained in the system.
	 */
	@Test public void correctAddUser_3(){
		KPSmartSystem kps = new KPSmartSystem();
		kps.addKPSUser(users[2], passwords[2].hashCode(), Position.CLERK);
		assertTrue(kps.containsKPSUser(users[2]));
	}

	/**
	 * Test that users added to the system are contained in the system.
	 */
	@Test public void correctAddUsers(){
		KPSmartSystem kps = constructSystemWithUsers();
		for(int i = 0; i < users.length; i++){
			assertTrue(kps.containsKPSUser(users[i]));
		}
	}

	/**
	 * Test that a user's password matches correctly.
	 */
	@Test public void correctPassword_1(){
		KPSmartSystem kps = new KPSmartSystem();
		kps.addKPSUser(users[0], passwords[0].hashCode(), Position.CLERK);
		assertTrue(kps.isCorrectPassword(users[0], passwords[0].hashCode()));
	}

	/**
	 * Test that a user's password matches correctly.
	 */
	@Test public void correctPassword_2(){
		KPSmartSystem kps = new KPSmartSystem();
		kps.addKPSUser(users[1], passwords[1].hashCode(), Position.MANAGER);
		assertTrue(kps.isCorrectPassword(users[1], passwords[1].hashCode()));
	}

	/**
	 * Test that a user's password matches correctly.
	 */
	@Test public void correctPassword_3(){
		KPSmartSystem kps = new KPSmartSystem();
		kps.addKPSUser(users[2], passwords[2].hashCode(), Position.CLERK);
		assertTrue(kps.isCorrectPassword(users[2], passwords[2].hashCode()));
	}

	/**
	 * Test that an incorrect password doesn't match correctly.
	 */
	@Test public void incorrectPassword_1(){
		KPSmartSystem kps = new KPSmartSystem();
		kps.addKPSUser(users[0], passwords[0].hashCode(), Position.CLERK);
		assertFalse(kps.isCorrectPassword(users[0], passwords[1].hashCode()));
	}

	/**
	 * Test that an incorrect password doesn't match correctly.
	 */
	@Test public void incorrectPassword_2(){
		KPSmartSystem kps = new KPSmartSystem();
		kps.addKPSUser(users[1], passwords[1].hashCode(), Position.MANAGER);
		assertFalse(kps.isCorrectPassword(users[1], passwords[2].hashCode()));
	}

	/**
	 * Test that an incorrect password doesn't match correctly.
	 */
	@Test public void incorrectPassword_3(){
		KPSmartSystem kps = new KPSmartSystem();
		kps.addKPSUser(users[2], passwords[2].hashCode(), Position.CLERK);
		assertFalse(kps.isCorrectPassword(users[2], passwords[0].hashCode()));
	}

	/**
	 * Test that a user has been logged in successfully.
	 */
	@Test public void correctLogin_1(){
		KPSmartSystem kps = constructSystemWithUsers();
		assertTrue(kps.login(users[0], passwords[0].hashCode()));
		assertEquals(users[0], kps.getCurrentUser());
	}

	/**
	 * Test that a user has been logged in successfully.
	 */
	@Test public void correctLogin_2(){
		KPSmartSystem kps = constructSystemWithUsers();
		assertTrue(kps.login(users[1], passwords[1].hashCode()));
		assertEquals(users[1], kps.getCurrentUser());
	}

	/**
	 * Test that a user has been logged in successfully.
	 */
	@Test public void correctLogin_3(){
		KPSmartSystem kps = constructSystemWithUsers();
		assertTrue(kps.login(users[2], passwords[2].hashCode()));
		assertEquals(users[2], kps.getCurrentUser());
	}

	/**
	 * Test that an incorrect password does not allow a user to be logged in.
	 */
	@Test public void incorrectLogin_1(){
		KPSmartSystem kps = constructSystemWithUsers();
		assertFalse(kps.login(users[0], passwords[1].hashCode()));
		assertFalse(kps.isLoggedIn());
	}

	/**
	 * Test that an incorrect password does not allow a user to be logged in.
	 */
	@Test public void incorrectLogin_2(){
		KPSmartSystem kps = constructSystemWithUsers();
		assertFalse(kps.login(users[1], passwords[2].hashCode()));
		assertFalse(kps.isLoggedIn());
	}

	/**
	 * Test that an incorrect password does not allow a user to be logged in.
	 */
	@Test public void incorrectLogin_3(){
		KPSmartSystem kps = constructSystemWithUsers();
		assertFalse(kps.login(users[2], passwords[0].hashCode()));
		assertFalse(kps.isLoggedIn());
	}

	/**
	 * Test that calling logout on the system logs the current user out of the system.
	 */
	@Test public void correctLogout(){
		KPSmartSystem kps = constructSystemWithUsers();
		kps.login(users[0], passwords[0].hashCode());
		assertTrue(kps.isLoggedIn());
		kps.logout();
		assertFalse(kps.isLoggedIn());
	}

	/**
	 * Helper method that constructs a mock KPSmartSystem and populates
	 * it with users.
	 *
	 * @return kps
	 */
	private KPSmartSystem constructSystemWithUsers(){
		KPSmartSystem kps = new KPSmartSystem();
		for(int i = 0; i < users.length; i++){
			kps.addKPSUser(users[i], passwords[i].hashCode(), Position.CLERK);
		}
		return kps;
	}

	@Test public void testIncorrectMailDeliveryEvent_1(){

	}

	/**
	 * Test sending mail that should be correct.
	 *
	 * Node 1 to Node 4 (AIR) weight = 6, volume = 6
	 * Expected revenue = $20.40
	 * Expected expenditure = $16.80
	 */
	@Test public void testCorrectMailDeliveryEvent_1(){
		KPSmartSystem kps = constructSystemWithRoutes();
		kps.addMailDeliveryEvent("Node 1", "Node 4", Day.MONDAY, 6, 6, Priority.INTERNATIONAL_AIR);
		EventLog log = kps.getEventLog();
		checkMailDeliveryEvent(log.getCurrentEvent(), 20.4, 16.8);
	}

	/**
	 * Test sending mail that should be correct.
	 *
	 * Node 1 to Node 4 (STD) weight = 5, volume = 5
	 * Expected revenue = $13.50
	 * Expected expenditure = $11.50
	 */
	@Test public void testCorrectMailDeliveryEvent_2(){
		KPSmartSystem kps = constructSystemWithRoutes();
		kps.addMailDeliveryEvent("Node 1", "Node 4", Day.MONDAY, 5, 5, Priority.INTERNATIONAL_STANDARD);
		EventLog log = kps.getEventLog();
		checkMailDeliveryEvent(log.getCurrentEvent(), 13.5, 11.5);
	}

	/**
	 * Test sending mail that should be correct.
	 *
	 * Node 1 to Node 3 (AIR) weight = 5, volume = 6
	 * Expected revenue = $17.35
	 * Expected expenditure = $12.10
	 */
	@Test public void testCorrectMailDeliveryEvent_3(){
		KPSmartSystem kps = constructSystemWithRoutes();
		kps.addMailDeliveryEvent("Node 1", "Node 3", Day.MONDAY, 5, 6, Priority.INTERNATIONAL_AIR);
		EventLog log = kps.getEventLog();
		checkMailDeliveryEvent(log.getCurrentEvent(), 17.35, 12.1);
	}

	/**
	 * Test sending mail that should be correct.
	 *
	 * Node 1 to Node 5 (STD) weight = 6, volume = 5
	 * Expected revenue = $23.35
	 * Expected expenditure = $17.10
	 */
	@Test public void testCorrectMailDeliveryEvent_4(){
		KPSmartSystem kps = constructSystemWithRoutes();
		kps.addMailDeliveryEvent("Node 1", "Node 5", Day.MONDAY, 6, 5, Priority.INTERNATIONAL_STANDARD);
		EventLog log = kps.getEventLog();
		checkMailDeliveryEvent(log.getCurrentEvent(), 23.35, 17.1);
	}

	/**
	 * Test sending mail that should be correct.
	 *
	 * Node 1 to Node 3 (STD) weight = 6, volume = 6
	 * Expected revenue = $13.80
	 * Expected expenditure = $12.00
	 */
	@Test public void testCorrectMailDeliveryEvent_5(){
		KPSmartSystem kps = constructSystemWithRoutes();
		kps.addMailDeliveryEvent("Node 1", "Node 3", Day.MONDAY, 6, 6, Priority.INTERNATIONAL_STANDARD);
		EventLog log = kps.getEventLog();
		checkMailDeliveryEvent(log.getCurrentEvent(), 13.8, 12);
	}


	/**
	 * Test adding a price update that should be correct.
	 *
	 * Adds a new customer route to the system:
	 * weight cost = 4
	 * volume cost = 3
	 *
	 */
	@Test public void testCorrectPriceUpdateEvent_1(){
		String origin = "Node 2";
		String destination = "Node 3";
		int weightCost = 4;
		int volumeCost = 3;
		Priority priority = Priority.INTERNATIONAL_STANDARD;

		KPSmartSystem kps = constructSystemWithRoutes();
		int deliveryPricesCount = kps.getDeliveryPriceCount();
		kps.addPriceUpdateEvent(origin, destination, weightCost, volumeCost, priority);
		// there should be one more delivery price in the system
		if(kps.getDeliveryPriceCount() != deliveryPricesCount + 1){
			fail("Expecting the number of delivery prices to be "+(deliveryPricesCount + 1)+", received "+kps.getDeliveryPriceCount());
		}

		// check that parameters match the constructed event
		checkPriceUpdateEvent(kps.getEventLog().getLastEventAdded(), origin, destination, weightCost, volumeCost, priority);

	}

	/**
	 * Test adding a price update that should be correct.
	 *
	 * Updates an existing customer route priority to a new price:
	 * new weight cost = 19
	 * new volume cost = 20
	 */
	@Test public void testCorrectPriceUpdateEvent_2(){
		String origin = "Node 1";
		String destination = "Node 3";
		int weightCost = 19;
		int volumeCost = 20;
		Priority priority = Priority.INTERNATIONAL_STANDARD;

		KPSmartSystem kps = constructSystemWithRoutes();
		int deliveryPricesCount = kps.getDeliveryPriceCount();
		kps.addPriceUpdateEvent(origin, destination, weightCost, volumeCost, priority);

		// there should be no change in the number of delivery prices in the system
		if(kps.getDeliveryPriceCount() != deliveryPricesCount){
			fail("Expecting the number of delivery prices to be "+deliveryPricesCount+", received "+kps.getDeliveryPriceCount());
		}

		// check that parameters match the constructed event
		checkPriceUpdateEvent(kps.getEventLog().getLastEventAdded(), origin, destination, weightCost, volumeCost, priority);
	}

	/**
	 * Test adding a price update that should be correct.
	 *
	 * Updates a customer route with a new priority delivery price:
	 * weight cost = 39
	 * volume cost = 45
	 *
	 */
	@Test public void testCorrectPriceUpdateEvent_3(){
		String origin = "Node 1";
		String destination = "Node 5";
		int weightCost = 39;
		int volumeCost = 45;
		Priority priority = Priority.INTERNATIONAL_AIR;

		KPSmartSystem kps = constructSystemWithRoutes();
		int deliveryPricesCount = kps.getDeliveryPriceCount();
		kps.addPriceUpdateEvent(origin, destination, weightCost, volumeCost, priority);

		// there should be one more delivery price in the system
		if(kps.getDeliveryPriceCount() != deliveryPricesCount + 1){
			fail("Expecting the number of delivery prices to be "+(deliveryPricesCount + 1)+", received "+kps.getDeliveryPriceCount());
		}

		// check that parameters match the constructed event
		checkPriceUpdateEvent(kps.getEventLog().getLastEventAdded(), origin, destination, weightCost, volumeCost, priority);
	}

	/**
	 * Test adding a TransportCostUpdate to the system which does
	 * not currently exist.
	 */
	@Test public void testCorrectTransportCostUpdateEvent_1(){
		KPSmartSystem kps = constructSystemWithRoutes();
		int graphSize = kps.getRouteGraph().getAllRoutes().size();
		int logSize = kps.getEventLogSize();
		kps.addTransportCostUpdateEvent("Node 1", "Node 6", "Test", TransportType.AIR, 50, 50, 1000, 1000, 5, 5, Day.MONDAY);
		int newGraphSize = kps.getRouteGraph().getAllRoutes().size();
		int newLogSize = kps.getEventLogSize();
		if(newGraphSize != graphSize + 1){
			fail("Expecting graph size of "+(graphSize + 1)+", received "+newGraphSize);
		}
		if(newLogSize != logSize + 1){
			fail("Expecting event log size of "+(graphSize + 1)+", received "+newGraphSize);
		}
	}

	/**
	 * Test updating a TransportCostUpdateEvent in the system.
	 */
	@Test public void testCorrectTransportCostUpdateEvent_2(){
		KPSmartSystem kps = constructSystemWithRoutes();
		int graphSize = kps.getRouteGraph().getAllRoutes().size();
		int logSize = kps.getEventLogSize();
		kps.addTransportCostUpdateEvent("Node 1", "Node 2", "Test", TransportType.AIR, 30, 30, 100, 100, 5, 5, Day.MONDAY);
		int newGraphSize = kps.getRouteGraph().getAllRoutes().size();
		int newLogSize = kps.getEventLogSize();
		if(newGraphSize != graphSize){
			fail("Expecting graph size of "+graphSize+", received "+newGraphSize);
		}
		if(newLogSize != logSize + 1){
			fail("Expecting event log size of "+(graphSize + 1)+", received "+newGraphSize);
		}
	}

	/**
	 * Test adding a new TransportCostUpdateEvent via the same route
	 * and priority with a different company.
	 */
	@Test public void testCorrectTransportCostUpdateEvent_3(){
		KPSmartSystem kps = constructSystemWithRoutes();
		int graphSize = kps.getRouteGraph().getAllRoutes().size();
		int logSize = kps.getEventLogSize();
		kps.addTransportCostUpdateEvent("Node 1", "Node 2", "Company", TransportType.AIR, 30, 30, 1000, 1000, 5, 5, Day.MONDAY);
		int newGraphSize = kps.getRouteGraph().getAllRoutes().size();
		int newLogSize = kps.getEventLogSize();
		if(newGraphSize != graphSize + 1){
			fail("Expecting graph size of "+(graphSize + 1)+", received "+newGraphSize);
		}
		if(newLogSize != logSize + 1){
			fail("Expecting event log size of "+(graphSize + 1)+", received "+newGraphSize);
		}
	}

	/**
	 * Test adding a new TransportCostUpdateEvent via the same route
	 * with a different priority.
	 */
	@Test public void testCorrectTransportCostUpdateEvent_4(){
		KPSmartSystem kps = constructSystemWithRoutes();
		int graphSize = kps.getRouteGraph().getAllRoutes().size();
		int logSize = kps.getEventLogSize();
		kps.addTransportCostUpdateEvent("Node 3", "Node 5", "Test", TransportType.AIR, 30, 30, 1000, 1000, 5, 5, Day.MONDAY);
		int newGraphSize = kps.getRouteGraph().getAllRoutes().size();
		int newLogSize = kps.getEventLogSize();
		if(newGraphSize != graphSize + 1){
			fail("Expecting graph size of "+(graphSize + 1)+", received "+newGraphSize);
		}
		if(newLogSize != logSize + 1){
			fail("Expecting event log size of "+(graphSize + 1)+", received "+newGraphSize);
		}
	}


	/*
	@Test public void testCorrectTransportDiscontinuedEvent_1(){
		KPSmartSystem kps = constructSystemWithRoutes();
		int graphSize = kps.getRouteGraph().getAllRoutes().size();
		int logSize = kps.getEventLogSize();
		kps.addTransportDiscontinuedEvent(new Route());
		int newGraphSize = kps.getRouteGraph().getAllRoutes().size();
		int newLogSize = kps.getEventLogSize();
		if(newGraphSize != graphSize + 1){
			fail("Expecting graph size of "+(graphSize + 1)+", received "+newGraphSize);
		}
		if(newLogSize != logSize + 1){
			fail("Expecting event log size of "+(graphSize + 1)+", received "+newGraphSize);
		}

	}*/


	private KPSmartSystem constructSystemWithRoutes(){
		try{
			EventLog log = new EventLog(KPSParser.parseFile(Main.XML_FILE_PATH+"kps_testdata.xml"));
			return new KPSmartSystem(log);
		}catch(ParserException e){}
		return null;
	}

	private void checkMailDeliveryEvent(BusinessEvent event, double revenue, double expenditure){
		if(event instanceof MailDeliveryEvent){
			MailDeliveryEvent mail = (MailDeliveryEvent)event;
			if(revenue != mail.getRevenue()){
				fail("Expecting revenue of "+revenue+", received "+mail.getRevenue());
			}
			if(expenditure != mail.getExpenditure()){
				fail("Expecting expenditure of "+expenditure+", received "+mail.getExpenditure());
			}
		}
		else{
			fail("Expecting a MailDeliveryEvent");
		}
	}

	public void checkPriceUpdateEvent(BusinessEvent event, String origin, String destination, int weightCost, int volumeCost, Priority priority){
		if(event instanceof PriceUpdateEvent){
			PriceUpdateEvent price = (PriceUpdateEvent)event;
			// check origin
			if(!origin.equals(price.getOrigin())){
				fail("Expecting "+origin+", retrieved "+price.getOrigin());
			}
			// check destination
			if(!destination.equals(price.getDestination())){
				fail("Expecting "+destination+", retrieved "+price.getDestination());
			}
			// check weight cost
			if(weightCost != price.getGramPrice()){
				fail("Expecting "+weightCost+", retrieved "+price.getGramPrice());
			}
			// check volume cost
			if(volumeCost != price.getVolumePrice()){
				fail("Expecting "+volumeCost+", retrieved "+price.getVolumePrice());
			}
			// check priority
			if(priority != price.getPriority()){
				fail("Expecting "+Priority.convertPriorityToString(priority)+", retrieved "+Priority.convertPriorityToString(price.getPriority()));
			}
		}
		else{
			fail("Expecting a PriceUpdateEvent, retrieved"+event.getType());
		}
	}



	@Test
	public void timeToWaitTestNoTime(){
		KPSmartSystem kp = new KPSmartSystem();

		//time starts on zero
		assertTrue(kp.timeToWait(0, 2) == 0 );
		assertTrue(kp.timeToWait(1, 1) == 0 );
		assertTrue(kp.timeToWait(12, 2) == 0 );
		assertTrue(kp.timeToWait(12, 3) == 0 );
		assertTrue(kp.timeToWait(24, 1) == 0 );
	}

	@Test
	public void timeToWaitTestMustWait(){
		KPSmartSystem kp = new KPSmartSystem();

		//time starts on zero

		assertTrue(kp.timeToWait(2, 3) == 1 );
		assertTrue(kp.timeToWait(23, 4) == 1 );
		assertTrue(kp.timeToWait(23, 5) == -1 );

	}

	@Test
	public void testTimeTaken(){
		EventLog log = null;
		try {
			log = new EventLog(KPSParser.parseFile(Main.XML_FILE_PATH+"kps_testdata.xml"));
		} catch (ParserException e) {
			System.out.println("oh no");
			e.printStackTrace();
		}
		KPSmartSystem kp = new KPSmartSystem(log);
		kp.addTransportCostUpdateEvent("Node 6", "Node 2", "Test", TransportType.AIR, 50, 50, 1000, 1000, 21, 5, Day.TUESDAY);
		kp.addPriceUpdateEvent("Node 6", "Node 3", 160, 180, Priority.INTERNATIONAL_STANDARD);

		kp.addMailDeliveryEvent("Node 1", "Node 3", Day.MONDAY,  1,  1, Priority.INTERNATIONAL_STANDARD);
		checkValidTimeTaken(kp.getEventLog().getLastEventAdded(), 29);

		kp.addMailDeliveryEvent("Node 1", "Node 3", Day.TUESDAY,  1,  1, Priority.INTERNATIONAL_STANDARD);
		checkValidTimeTaken(kp.getEventLog().getLastEventAdded(), 173);

		kp.addMailDeliveryEvent("Node 1", "Node 5", Day.THURSDAY,  1,  1, Priority.INTERNATIONAL_STANDARD);
		checkValidTimeTaken(kp.getEventLog().getLastEventAdded(), 149);

		kp.addMailDeliveryEvent("Node 6", "Node 3", Day.TUESDAY,  1,  1, Priority.INTERNATIONAL_STANDARD);
		checkValidTimeTaken(kp.getEventLog().getLastEventAdded(), 173);

	}

	private void checkValidTimeTaken(BusinessEvent event, int expectedTime){
		if(event instanceof MailDeliveryEvent){
			MailDeliveryEvent mail = (MailDeliveryEvent)event;
			if(mail.getDeliveryTime() != expectedTime){
				fail("Expecting delivery time of "+expectedTime+" hours, received time of "+mail.getDeliveryTime()+" hours.");
			}
		}
		else{
			fail("Expecting a MailDeliveryEvent, received a "+event.getClass());
		}
	}
}
