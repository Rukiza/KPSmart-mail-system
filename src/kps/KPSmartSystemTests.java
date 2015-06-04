package kps;

import static org.junit.Assert.*;
import kps.data.wrappers.EventLog;
import kps.enums.Day;
import kps.enums.Position;
import kps.enums.Priority;
import kps.enums.TransportType;
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
	public void incrementTimeTest(){
		KPSmartSystem kp = new KPSmartSystem();




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

		assertTrue(kp.getAvarageDeliveryTime() == 29);//

		kp.addMailDeliveryEvent("Node 1", "Node 3", Day.TUESDAY,  1,  1, Priority.INTERNATIONAL_STANDARD);

		System.out.println("Avg " + kp.getAvarageDeliveryTime());
		assertTrue(kp.getAvarageDeliveryTime() == 173);//

		kp.addMailDeliveryEvent("Node 1", "Node 5", Day.THURSDAY,  1,  1, Priority.INTERNATIONAL_STANDARD);

		System.out.println("Avg " + kp.getAvarageDeliveryTime());
		assertTrue(kp.getAvarageDeliveryTime() == 149);//

		kp.addMailDeliveryEvent("Node 6", "Node 3", Day.TUESDAY,  1,  1, Priority.INTERNATIONAL_STANDARD);

		System.out.println("Avg " + kp.getAvarageDeliveryTime());
		assertTrue(kp.getAvarageDeliveryTime() == 173);//

	}


}
