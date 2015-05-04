package kps.data.wrappers.tests;

import static org.junit.Assert.*;
import kps.data.wrappers.MailTransport;
import kps.enums.Day;

import org.junit.Test;

/**
 * Tests to ensure that the MailTransport equals method is working correctly.
 *
 * @author David
 *
 */
public class MailTransportTests {

	// fields
	private int duration = 16;
	private int frequency = 36;
	private Day day = Day.THURSDAY;
	private MailTransport mail = new MailTransport(duration, frequency, day);

	/**
	 * A MailTransport object should be equal to iteself.
	 */
	@Test public void correctMailTransportEqualsTest_1(){
		if(!mail.equals(mail)){
			fail("Error: MailTrasport object should be equal to itself.");
		}
	}

	/**
	 * A MailTransport object should be equal to another MailTransport object with
	 * identical fields.
	 */
	@Test public void correctMailTransportEqualsTest_2(){
		MailTransport test = new MailTransport(duration, frequency, day);
		if(!mail.equals(test)){
			fail("Error: MailTransport object should equal another MailTransport object with identical fields.");
		}
	}

	/**
	 * A MailTransport object should not equal null
	 */
	@Test public void incorrectMailTransportEqualsTest_1(){
		if(mail.equals(null)){
			fail("Error: MailTransport object should not equal null.");
		}
	}

	/**
	 * A MailTransport object should not equal another object of a different type.
	 */
	@Test public void incorrectMailTransportEqualsTest_2(){
		if(mail.equals(new Object())){
			fail("Error: MailTransport object should not equal another object of a different type.");
		}
	}

	/**
	 * A MailTransport object should not equal another MailTranpsort object with differing fields.
	 */
	@Test public void incorrectMailTransportEqualsTest_3(){
		MailTransport test = new MailTransport(0, frequency, day);
		if(mail.equals(test)){
			fail("Error: MailTransport object should not equal another MailTransport object with differing fields.");
		}
	}

	/**
	 * A MailTransport object should not equal another MailTranpsort object with differing fields.
	 */
	@Test public void incorrectMailTransportEqualsTest_4(){
		MailTransport test = new MailTransport(duration, 0, day);
		if(mail.equals(test)){
			fail("Error: MailTransport object should not equal another MailTransport object with differing fields.");
		}
	}

	/**
	 * A MailTransport object should not equal another MailTranpsort object with differing fields.
	 */
	@Test public void incorrectMailTransportEqualsTest_5(){
		MailTransport test = new MailTransport(duration, frequency, Day.MONDAY);
		if(mail.equals(test)){
			fail("Error: MailTransport object should not equal another MailTransport object with differing fields.");
		}
	}

	/**
	 * A MailTransport object should not equal another MailTranpsort object with differing fields.
	 */
	@Test public void incorrectMailTransportEqualsTest_6(){
		MailTransport test = new MailTransport(0, 49, Day.WEDNESDAY);
		if(mail.equals(test)){
			fail("Error: MailTransport object should not equal another MailTransport object with differing fields.");
		}
	}
}
