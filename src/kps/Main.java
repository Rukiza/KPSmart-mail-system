package kps;

import java.io.File;
import java.util.List;

import kps.KPSmartSystem;
import kps.data.wrappers.EventLog;
import kps.events.BusinessEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;
import kps.ui.listener.AuthDetailsListener;
import kps.ui.window.KPSWindow;
import kps.ui.window.LogonBox;

public class Main {

	public static final String XML_FILE_PATH = "src"+File.separator+"kps"+File.separator+"xml"+File.separator;
	public static final String filename = XML_FILE_PATH + "kps_data.xml";

	public static void main(String[] args){
		try{
			List<BusinessEvent> bizEvents = KPSParser.parseFile(filename);
            EventLog eventLog = new EventLog(bizEvents);
            KPSmartSystem system = new KPSmartSystem(eventLog);

			new LogonBox(new AuthDetailsListener(){

				@Override
				public void onReceivedAuthDetails(String un, String pw) {
					// just assume un and pw are correct for now
					// use system.login when it's ready
					new KPSWindow(system);
				}

				@Override
				public void onCancel() {
					// do nothing
				}

			},"monkey", "bananas");


		}catch(ParserException e){ e.printStackTrace(); }
	}
}

