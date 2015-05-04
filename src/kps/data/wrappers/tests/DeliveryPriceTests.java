package kps.data.wrappers.tests;

import static org.junit.Assert.*;
import kps.data.wrappers.DeliveryPrice;

import org.junit.Test;

/**
 * Tests to ensure that the DeliveryPrice equals method is working correctly.
 *
 * @author David
 *
 */
public class DeliveryPriceTests {

	// fields
	private double gramPrice = 6;
	private double volumePrice = 8;
	private DeliveryPrice price = new DeliveryPrice(gramPrice, volumePrice);

	/**
	 * A DeliveryPrice object should be equal to itself.
	 */
	@Test public void correctDeliveryPriceEqualsTest_1(){
		if(!price.equals(price)){
			fail("Error: DeliveryPrice object should be equal to itself.");
		}
	}

	/**
	 * A DeliveryPrice object should be equal to another DeliveryPrice object with
	 * identical fields.
	 */
	@Test public void correctDeliveryPriceEqualsTest_2(){
		DeliveryPrice test = new DeliveryPrice(gramPrice, volumePrice);
		if(!price.equals(test)){
			fail("Error: DeliveryPrice object should equal DeliveryPrice object with identical fields.");
		}
	}

	/**
	 * A DeliveryPrice object should not be equal to null.
	 */
	@Test public void incorrectDeliveryPriceEqualsTest_1(){
		if(price.equals(null)){
			fail("Error: DeliveryPrice object should not equal null.");
		}
	}

	/**
	 * A DeliveryPrice object should not equal another object of a different type.
	 */
	@Test public void incorrectDeliveryPriceEqualsTest_2(){
		if(price.equals(new Object())){
			fail("Error: DeliveryPrice object should not equal an object of differing type.");
		}
	}

	/**
	 * A DeliveryPrice object should not equal another DeliveryPrice object with
	 * different fields.
	 */
	@Test public void incorrectDeliveryPriceEqualsTest_3(){
		DeliveryPrice test = new DeliveryPrice(0, volumePrice);
		if(price.equals(test)){
			fail("Error: DeliveryPrice object should not equal DeliveryPrice object with differing fields.");
		}
	}

	/**
	 * A DeliveryPrice object should not equal another DeliveryPrice object with
	 * different fields.
	 */
	@Test public void incorrectDeliveryPriceEqualsTest_4(){
		DeliveryPrice test = new DeliveryPrice(gramPrice, 0);
		if(price.equals(test)){
			fail("Error: DeliveryPrice object should not equal DeliveryPrice object with differing fields.");
		}
	}

	/**
	 * A DeliveryPrice object should not equal another DeliveryPrice object with
	 * different fields.
	 */
	@Test public void incorrectDeliveryPriceEqualsTest_5(){
		DeliveryPrice test = new DeliveryPrice(24, 36);
		if(price.equals(test)){
			fail("Error: DeliveryPrice object should not equal DeliveryPrice object with differing fields.");
		}
	}
}
