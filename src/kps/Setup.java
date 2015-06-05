package kps;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import kps.data.RouteGraph;
import kps.data.wrappers.EventLog;
import kps.events.BusinessEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;
import kps.ui.formlistener.AuthDetailsListener;
import kps.ui.window.KPSWindow;
import kps.ui.window.LogonBox;
import kps.users.KPSUser;

public class Setup {

	public static final String XML_FILE_PATH = "xml"+File.separator;
	public static final String EVENT_LOG = XML_FILE_PATH + "eventlog.xml";
	public static final String USERS = XML_FILE_PATH + "users.xml";

	public static void main(String[] args){
		setup();
	}

	public static void setup(){
		try{
			List<BusinessEvent> bizEvents = KPSParser.parseFile(EVENT_LOG);
            EventLog eventLog = new EventLog(bizEvents);
            RouteGraph graph = KPSParser.parseGraph(XML_FILE_PATH+"eventlog-graph.xml");
            Map<String, KPSUser> users = KPSParser.parseKPSUsers(USERS);
            KPSmartSystem system = new KPSmartSystem(eventLog, graph, users);
            login(system);
		}catch(ParserException e){ e.printStackTrace(); }
	}
	public static void login(KPSmartSystem system){
			new LogonBox(new AuthDetailsListener(){

				@Override
				public boolean onReceivedAuthDetails(String un, String pw) {
					KPSUser user = system.login(un, pw.hashCode());
					if (user == null){
						// login unsuccessful
						return false;
					}

					KPSWindow window = new KPSWindow(system, user);

					window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					window.addWindowListener(new WindowAdapter(){
						@Override public void windowClosing(WindowEvent e){
							system.convertEventLogToXML();
							system.convertUsersMapToXML();
							System.out.println("closed while being listened to");
						}
					});
					return true;
				}

				@Override
				public void onCancel() {
					// do nothing
				}

			},"admin", "admin");
	}
}

