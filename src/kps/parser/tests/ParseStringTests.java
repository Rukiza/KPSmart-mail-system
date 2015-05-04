package kps.parser.tests;

import static org.junit.Assert.*;

import java.util.Scanner;

import kps.parser.KPSParser;
import kps.parser.ParserException;

import org.junit.Test;


/**
 * Tests to ensure that the parseString method in KPSParser is
 * working correctly.
 * 
 * @author David
 *
 */
public class ParseStringTests {
	
	/**
	 * Test that a string can be parsed successfully.
	 */
	@Test public void testCorrectStringParse_1(){
		String expected = "Test";
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		String result = "";
		try{
			result = KPSParser.parseString(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}
	
	/**
	 * Test that a string containing letters and digits can be parsed successfully.
	 */
	@Test public void testCorrectStringParse_2(){
		String expected = "123Test";
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		String result = "";
		try{
			result = KPSParser.parseString(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}
	
	/**
	 * Test that a string containing letters and digits can be parsed successfully.
	 */
	@Test public void testCorrectStringParse_3(){
		String expected = "Test123";
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		String result = "";
		try{
			result = KPSParser.parseString(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}
	/**
	 * Test that a string containing only digits can be parsed successfully.
	 */
	@Test public void testCorrectStringParse_4(){
		String expected = "123";
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		String result = "";
		try{
			result = KPSParser.parseString(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}
	
	/**
	 * Test that a string containing multiple words can be parsed successfully.
	 */
	@Test public void testCorrectStringParse_5(){
		String expected = "Testing Again";
		String line = "<tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		String result = "";
		try{
			result = KPSParser.parseString(scan, "tag");
		}catch(ParserException e){fail(e.getMessage());}
		checkResults(expected, result);
	}
	
	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testInorrectStringParse_1(){
		String expected = "Test";
		String line = expected;
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseString(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectStringParse_2(){
		String expected = "Test";
		String line = "<tag "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseString(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectStringParse_3(){
		String expected = "Test123";
		String line = "tag> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseString(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectStringParse_4(){
		String expected = "Test123";
		String line = "<tag> "+expected+" /tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseString(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}
	
	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectStringParse_5(){
		String expected = "Test123";
		String line = "<tag> "+expected+" <tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseString(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}
	
	/**
	 * Test that error is thrown for incorrect tags.
	 */
	@Test public void testIncorrectStringParse_6(){
		String expected = "Test123";
		String line = "<tag> "+expected+" </tag";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseString(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}
	
	/**
	 * Test that error is thrown for incorrect tag format.
	 */
	@Test public void testIncorrectStringParse_7(){
		String expected = "Test123";
		String line = "<tag> "+expected+" </tags>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseString(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}

	/**
	 * Test that error is thrown for incorrect tags.
	 */
	@Test public void testIncorrectStringParse_8(){
		String expected = "Test123";
		String line = "<tags> "+expected+" </tag>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseString(scan, "tag");
		}catch(ParserException e){return;}
		fail("Tags to parse: "+line+"\nException should have been thrown.");
	}
	
	/**
	 * Test that error is thrown for incorrect tags.
	 */
	@Test public void testIncorrectStringParse_9(){
		String expected = "Test123";
		String line = "<tags> "+expected+" </tags>";
		Scanner scan = new Scanner(line);
		try{
			KPSParser.parseString(scan, "tag");
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
	private void checkResults(String expected, String received){
		if(!expected.equals(received)){
			fail("Expecting: "+expected+"\nReceived: "+received);
		}
	}
}
