package kps.parser.tests;

import static org.junit.Assert.*;

import java.util.Scanner;

import kps.parser.KPSParser;
import kps.parser.ParserException;

import org.junit.Test;

/**
 * Tests to ensure that the parseInt method in KPSParser is
 * working correctly.
 * 
 * @author David
 *
 */
public class ParseIntTests {

	/**
	 * Test that an int can be parsed successfully.
	 */
	@Test public void testCorrectIntParse_1(){
		int expected = 1;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		int result = 0;
		try{
			result = KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that an int can be parsed successfully.
	 */
	@Test public void testCorrectIntParse_2(){
		int expected = 456;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		int result = 0;
		try{
			result = KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that an int can be parsed successfully.
	 */
	@Test public void testCorrectIntParse_3(){
		int expected = 0;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		int result = -1;
		try{
			result = KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that an int can be parsed successfully.
	 */
	@Test public void testCorrectIntParse_4(){
		int expected = -1;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		int result = 0;
		try{
			result = KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that the max int value can be parsed successfully.
	 */
	@Test public void testCorrectIntParse_5(){
		int expected = Integer.MAX_VALUE;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		int result = 0;
		try{
			result = KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}

	/**
	 * Test that the minimum int value can be parsed successfully.
	 */
	@Test public void testCorrectIntParse_6(){
		int expected = Integer.MIN_VALUE;
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		int result = 0;
		try{
			result = KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}
	
	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testInorrectIntParse_1(){
		int expected = 1;
		String line = ""+expected;
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectIntParse_2(){
		int expected = 1;
		String line = "<tag "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectIntParse_3(){
		int expected = 1;
		String line = "tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectIntParse_4(){
		int expected = 1;
		String line = "<tag> "+expected+" /tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectIntParse_5(){
		int expected = 1;
		String line = "<tag> "+expected+" <tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectIntParse_6(){
		int expected = 1;
		String line = "<tag> "+expected+" </tag";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tags.
	 */
	@Test public void testIncorrectIntParse_7(){
		int expected = 1;
		String line = "<tag> "+expected+" </tags>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tags.
	 */
	@Test public void testIncorrectIntParse_8(){
		int expected = 1;
		String line = "<tags> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tags.
	 */
	@Test public void testIncorrectIntParse_9(){
		int expected = 1;
		String line = "<tags> "+expected+" </tags>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse something that is not
	 * an int.
	 */
	@Test public void testIncorrectIntParse_10(){
		String line = "<tag> one </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse something that is not
	 * an int.
	 */
	@Test public void testIncorrectIntParse_11(){
		String line = "<tag> number </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse a value outside the
	 * range of an int.
	 */
	@Test public void testIncorrectIntParse_12(){
		String line = "<tag> 4294967297 </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse a value outside the
	 * range of an int.
	 */
	@Test public void testIncorrectIntParse_13(){
		String line = "<tag> -4294967297 </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse something that is not
	 * an int.
	 */
	@Test public void testIncorrectIntParse_14(){
		String line = "<tag> 1.56 </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for trying to parse something that is not
	 * an int.
	 */
	@Test public void testIncorrectIntParse_15(){
		String line = "<tag> -1.56 </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseInt(scan, "tag");
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
	private void checkResults(int expected, int received){
		if(expected != received){
			fail("Expecting: "+expected+"\nReceived: "+received);
		}
	}
}
