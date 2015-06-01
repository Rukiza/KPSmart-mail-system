package kps;

import static org.junit.Assert.*;
import kps.enums.Position;
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

}
