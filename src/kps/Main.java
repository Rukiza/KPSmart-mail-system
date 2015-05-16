package kps;

import java.io.File;
import java.util.List;

import kps.data.wrappers.EventLog;
import kps.events.BusinessEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;
import kps.ui.AuthDetailsListener;
import kps.ui.LogonBox;
import kps.ui.Window;

public class Main {

	public static final String XML_FILE_PATH = "src"+File.separator+"kps"+File.separator+"xml"+File.separator;
	public static final String filename = XML_FILE_PATH + "kps_data.xml";

	public static void main(String[] args){
		try{
			List<BusinessEvent> bizEvents = KPSParser.parseFile(filename);

			// some crazy shit
			new LogonBox(new AuthDetailsListener(){

				@Override
				public void onReceivedAuthDetails(String un, String pw) {
					// just assume un and pw are correct for now
					new Window(new EventLog(bizEvents));
				}

				@Override
				public void onReceivedCancel() {
					// do nothing
				}

			},"monkey", "bananas");


		}catch(ParserException e){e.printStackTrace();}
	}
}
