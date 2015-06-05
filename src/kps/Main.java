package kps;

import java.io.File;
import java.util.List;
import java.util.Map;

import kps.data.wrappers.EventLog;
import kps.events.BusinessEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;
import kps.ui.formlistener.AuthDetailsListener;
import kps.ui.window.KPSWindow;
import kps.ui.window.LogonBox;
import kps.users.KPSUser;

public class Main {

	public static final String XML_FILE_PATH = "xml"+File.separator;
	public static final String EVENT_LOG = XML_FILE_PATH + "eventlog.xml";
	public static final String USERS = XML_FILE_PATH + "users.xml";

	public static void main(String[] args){
		try{
			List<BusinessEvent> bizEvents = KPSParser.parseFile(EVENT_LOG);
            EventLog eventLog = new EventLog(bizEvents);
            Map<String, KPSUser> users = KPSParser.parseKPSUsers(USERS);
            KPSmartSystem system = new KPSmartSystem(eventLog, users);

			new LogonBox(new AuthDetailsListener(){

				@Override
				public boolean onReceivedAuthDetails(String un, String pw) {
					KPSUser user = system.login(un, pw.hashCode());
					if (user == null){
						// login unsuccessful
						return false;
					}

					new KPSWindow(system, user);
					return true;
				}

				@Override
				public void onCancel() {
					// do nothing
				}

			},"admin", "admin");


		}catch(ParserException e){ e.printStackTrace(); }
	}
}

