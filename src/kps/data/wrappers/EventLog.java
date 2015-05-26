package kps.data.wrappers;

import java.util.*;

import kps.events.BusinessEvent;
import kps.parser.KPSParser;

public class EventLog {
	private List<BusinessEvent> eventLog;
	private int iterationLocation;

	public EventLog(){
		eventLog = new ArrayList<BusinessEvent>();
	}

	public EventLog(List<BusinessEvent> eventLog){
		eventLog.sort(new Comparator<BusinessEvent>() {

			@Override
			public int compare(BusinessEvent o1, BusinessEvent o2) {
				if (o1.getTimeLogged() < o2.getTimeLogged()){
					return 1;
				}
				else if (o1.getTimeLogged() == o2.getTimeLogged()){
					return 0;
				}
				else return -1;
			}
		});
		this.eventLog = eventLog;
	}

	/**
	 * Adds a business event to the eventLog list in order.
	 * Ordering is based on date.
	 */
	public void addBusinessEvent(BusinessEvent event){
		eventLog.add(findPosition(event), event);
	}

	private int findPosition(BusinessEvent event){
		if (eventLog.isEmpty()){
			return 0;
		}

		for (int i = 0; i < eventLog.size(); i++){
			if (event.getTimeLogged() > eventLog.get(i).getTimeLogged()){
				return i;
			}
		}
		return eventLog.size();
	}

	/**
	 * Used to get the next event in the list.
	 * Loops around to the start of the list when reaches the end.
	 *
	 * @return - Business event
	 */
	public BusinessEvent getNextEvent(){
		if (iterationLocation + 1 >= eventLog.size()){
			iterationLocation = 0;
			return eventLog.get(iterationLocation);
		}
		return eventLog.get(++iterationLocation);
	}

	/**
	 * Used to get the previous event in the list.
	 * Loops around to the end of the list when it reaches the start..
	 *
	 * @return - Business event
	 */
	public BusinessEvent getPrevEvent(){
		if (iterationLocation - 1 < 0){
			iterationLocation = eventLog.size() -1;
			return eventLog.get(iterationLocation);
		}
		return eventLog.get(--iterationLocation);
	}

	/**
	 * Gets the current business event.
	 *
	 * @return - Current Business event.
	 */
	public BusinessEvent getCurrentEvent(){
		return eventLog.get(iterationLocation);
	}

	/**
	 * Resets the eventLog iteration.
	 */
	public void resetEventLogLocation(){
		iterationLocation = 0;
	}

	/**
	 * Check if the event log is empty
	 * @return - true if its empty
	 */
	public boolean isEmpty(){
		return eventLog.isEmpty();
	}

	/**
	 * @return - Size of the event log
	 */
	public int getSize(){
		return eventLog.size();
	}

	public int getPosition(){
		return iterationLocation;
	}

	/**
	 * Converts the event log into XML data.
	 *
	 * @return string of xml data
	 */
	public String toXML(){
		String xml = "<"+KPSParser.FILE_TAG+" "+KPSParser.SCHEMA+">\n";
		for(BusinessEvent event : eventLog){
			xml += event.toXML();
		}
		xml += "</"+KPSParser.FILE_TAG+">";
		return xml;
	}

	public List<BusinessEvent> getListToCurrent(){
		return eventLog.subList(0, iterationLocation+1);
	}
}
