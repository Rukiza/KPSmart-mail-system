package kps;

import java.io.File;
import java.util.List;

import kps.events.BusinessEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;

public class Main {

	public static final String XML_FILE_PATH = "src"+File.separatorChar+"kps"+File.separatorChar+"xml"+File.separatorChar;
	public static final String filename = XML_FILE_PATH + "kps_data.xml";

	public static void main(String[] args){
		try{
			List<BusinessEvent> eventLog = KPSParser.parseFile(filename);
			System.out.println("file parsed successfully");
		}catch(ParserException e){e.printStackTrace();}
	}
}
