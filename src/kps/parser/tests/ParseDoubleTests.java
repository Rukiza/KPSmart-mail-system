package kps.parser.tests;

import static org.junit.Assert.*;

import java.util.Scanner;

import kps.parser.KPSParser;
import kps.parser.ParserException;

import org.junit.Test;

/**
 * Tests to ensure that the parseDouble method in KPSParser is
 * working correctly.
 * 
 * @author David
 *
 */
public class ParseDoubleTests {
	
	/**
	 * Test that a double can be parsed successfully.
	 */
	@Test public void testCorrectDoubleParse_1(){
		double expected = 1.0;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		double result = 0;
		try{
			result = KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that a double can be parsed successfully.
	 */
	@Test public void testCorrectDoubleParse_2(){
		double expected = 456.0;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		double result = 0;
		try{
			result = KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that a double can be parsed successfully.
	 */
	@Test public void testCorrectDoubleParse_3(){
		double expected = 0.0;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		double result = -1;
		try{
			result = KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that a double can be parsed successfully.
	 */
	@Test public void testCorrectDoubleParse_4(){
		double expected = -1.0;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		double result = 0;
		try{
			result = KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that a double can be parsed successfully.
	 */
	@Test public void testCorrectDoubleParse_5(){
		double expected = 1.573;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		double result = 0;
		try{
			result = KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that a double can be parsed successfully.
	 */
	@Test public void testCorrectDoubleParse_6(){
		double expected = 456.2764;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		double result = 0;
		try{
			result = KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that a double can be parsed successfully.
	 */
	@Test public void testCorrectDoubleParse_7(){
		double expected = 0.6933;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		double result = -1;
		try{
			result = KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that a double can be parsed successfully.
	 */
	@Test public void testCorrectDoubleParse_8(){
		double expected = -1.210687;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		double result = 0;
		try{
			result = KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that the max double value can be parsed successfully.
	 */
	@Test public void testCorrectDoubleParse_9(){
		double expected = Double.MAX_VALUE;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		double result = 0;
		try{
			result = KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that the minimum double value can be parsed successfully.
	 */
	@Test public void testCorrectDoubleParse_10(){
		double expected = Double.MIN_VALUE;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		double result = 0;
		try{
			result = KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testInorrectDoubleParse_1(){
		double expected = 1;
		String line = ""+expected;
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectDoubleParse_2(){
		double expected = 1;
		String line = "<tag "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectDoubleParse_3(){
		double expected = 1;
		String line = "tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectDoubleParse_4(){
		double expected = 1;
		String line = "<tag> "+expected+" /tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectDoubleParse_5(){
		double expected = 1;
		String line = "<tag> "+expected+" <tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectDoubleParse_6(){
		double expected = 1;
		String line = "<tag> "+expected+" </tag";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tags.
	 */
	@Test public void testIncorrectDoubleParse_7(){
		double expected = 1;
		String line = "<tag> "+expected+" </tags>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tags.
	 */
	@Test public void testIncorrectDoubleParse_8(){
		double expected = 1;
		String line = "<tags> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tags.
	 */
	@Test public void testIncorrectDoubleParse_9(){
		double expected = 1;
		String line = "<tags> "+expected+" </tags>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse a value outside the
	 * range of a double.
	 */
	@Test public void testIncorrectDoubleParse_10(){
		double expected = 1.9446744e+19;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse a value outside the
	 * range of a double.
	 */
	@Test public void testIncorrectDoubleParse_11(){
		double expected = -1.9446744e+19;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse something that is not
	 * a double.
	 */
	@Test public void testIncorrectDoubleParse_12(){
		String line = "<tag> one </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse something that is not
	 * a double.
	 */
	@Test public void testIncorrectDoubleParse_13(){
		String line = "<tag> double </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseDouble(scan, "tag");
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
	private void checkResults(double expected, double received){
		if(expected != received){
			fail("Expecting: "+expected+"\nReceived: "+received);
		}
	}
}
