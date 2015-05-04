package kps.parser.tests;

import static org.junit.Assert.*;

import java.util.Scanner;

import kps.parser.KPSParser;
import kps.parser.ParserException;

import org.junit.Test;

/**
 * Tests to ensure that the parseLong method in KPSParser is
 * working correctly.
 * 
 * @author David
 *
 */
public class ParseLongTests {

	/**
	 * Test that an long can be parsed successfully.
	 */
	@Test public void testCorrectLongParse_1(){
		long expected = 1;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		long result = 0;
		try{
			result = KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that an long can be parsed successfully.
	 */
	@Test public void testCorrectLongParse_2(){
		long expected = 456;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		long result = 0;
		try{
			result = KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that an long can be parsed successfully.
	 */
	@Test public void testCorrectLongParse_3(){
		long expected = 0;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		long result = -1;
		try{
			result = KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that an long can be parsed successfully.
	 */
	@Test public void testCorrectLongParse_4(){
		long expected = -1;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		long result = 0;
		try{
			result = KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that the max long value can be parsed successfully.
	 */
	@Test public void testCorrectLongParse_5(){
		long expected = Long.MAX_VALUE;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		long result = 0;
		try{
			result = KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that the minimum long value can be parsed successfully.
	 */
	@Test public void testCorrectLongParse_6(){
		long expected = Long.MIN_VALUE;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		long result = 0;
		try{
			result = KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}
	
	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectLongParse_1(){
		long expected = 1;
		String line = ""+expected;
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectLongParse_2(){
		long expected = 1;
		String line = "<tag "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectLongParse_3(){
		long expected = 1;
		String line = "tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectLongParse_4(){
		long expected = 1;
		String line = "<tag> "+expected+" /tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectLongParse_5(){
		long expected = 1;
		String line = "<tag> "+expected+" <tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectLongParse_6(){
		long expected = 1;
		String line = "<tag> "+expected+" </tag";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tags.
	 */
	@Test public void testIncorrectLongParse_7(){
		long expected = 1;
		String line = "<tag> "+expected+" </tags>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tags.
	 */
	@Test public void testIncorrectLongParse_8(){
		long expected = 1;
		String line = "<tags> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tags.
	 */
	@Test public void testIncorrectLongParse_9(){
		long expected = 1;
		String line = "<tags> "+expected+" </tags>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse something that is not
	 * a long.
	 */
	@Test public void testIncorrectLongParse_10(){
		String line = "<tag> one </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse something that is not
	 * a long.
	 */
	@Test public void testIncorrectLongParse_11(){
		String line = "<tag> number </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse a value outside the
	 * range of a long.
	 */
	@Test public void testIncorrectLongParse_12(){
		String line = "<tag> 18446744073709551616 </tag>"; // 2^64
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse a value outside the
	 * range of a long.
	 */
	@Test public void testIncorrectLongParse_13(){
		String line = "<tag> -18446744073709551616 </tag>"; // -(2^64)
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse something that is not
	 * a long.
	 */
	@Test public void testIncorrectLongParse_14(){
		String line = "<tag> 1.56 </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse something that is not
	 * a long.
	 */
	@Test public void testIncorrectLongParse_15(){
		String line = "<tag> -1.56 </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseLong(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Helper method which checks whether the expected result matches
	 * the received result.
	 * 
	 * @param expected
	 * 		-- expected result
	 * @param received
	 * 		-- received result
	 */
	private void checkResults(long expected, long received){
		if(expected != received){
			fail("Expecting: "+expected+"\nReceived: "+received);
		}
	}
}
