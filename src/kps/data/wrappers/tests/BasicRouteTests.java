package kps.data.wrappers.tests;

import static org.junit.Assert.*;
import kps.data.wrappers.BasicRoute;

import org.junit.Test;

/**
 * Tests to ensure that the equals method of BasicRoute is working correctly.
 *
 * @author David
 *
 */
public class BasicRouteTests {

	// fields
	private String origin = "Wellington";
	private String destination = "Rome";
	private BasicRoute route = new BasicRoute(origin, destination);

	/**
	 * A BasicRoute should be equal to itself.
	 */
	@Test public void correctBasicRouteEqualsTest_1(){
		if(!route.equals(route)){
			fail("Error: BasicRoute should equal itself.");
		}
	}

	/**
	 * A BasicRoute should be equal to another BasicRoute with the same fields.
	 */
	@Test public void correctBasicRouteEqualsTest_2(){
		BasicRoute test = new BasicRoute(origin, destination);
		if(!route.equals(test)){
			fail("Error: BasicRoute should equal another route with the same values.");
		}
	}

	/**
	 * A BasicRoute should be equal to another BasicRoute with the same fields.
	 */
	@Test public void correctBasicRouteEqualsTest_3(){
		BasicRoute test = new BasicRoute(new String(origin), new String(destination));
		if(!route.equals(test)){
			fail("Error: BasicRoute should equal another route with the same values.");
		}
	}

	/**
	 * A BasicRoute should not be equal to null.
	 */
	@Test public void incorrectBasicRouteEqualsTest_1(){
		if(route.equals(null)){
			fail("Error: BasicRoute should not equal null");
		}
	}

	/**
	 * A BasicRoute should not be equal to an object of another type.
	 */
	@Test public void incorrectBasicRouteEqualsTest_2(){
		if(route.equals(origin)){
			fail("Error: BasicRoute should not equal an object of another type.");
		}
	}

	/**
	 * A BasicRoute should not be equal to another BasicRoute with different fields.
	 */
	@Test public void inccorectBasicRouteEqualsTest_3(){
		BasicRoute test = new BasicRoute(destination, origin);
		if(route.equals(test)){
			fail("Error: BasicRoute should not equal another BasicRoute with different fields.");
		}
	}

	/**
	 * A BasicRoute should not be equal to another BasicRoute with different fields.
	 */
	@Test public void inccorectBasicRouteEqualsTest_4(){
		BasicRoute test = new BasicRoute("test", destination);
		if(route.equals(test)){
			fail("Error: BasicRoute should not equal another BasicRoute with different fields.");
		}
	}

	/**
	 * A BasicRoute should not be equal to another BasicRoute with different fields.
	 */
	@Test public void inccorectBasicRouteEqualsTest_5(){
		BasicRoute test = new BasicRoute(origin, "test");
		if(route.equals(test)){
			fail("Error: BasicRoute should not equal another BasicRoute with different fields.");
		}
	}
}
