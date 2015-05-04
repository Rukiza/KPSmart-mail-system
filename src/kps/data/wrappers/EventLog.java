package kps.data.wrappers;

import java.util.List;

import kps.events.BusinessEvent;

public class EventLog {
	private List<BusinessEvent> eventLog;
	private int iterationLocation;
	
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
		if (iterationLocation >= eventLog.size()){
			iterationLocation = 0;
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
		if (iterationLocation < 0){
			iterationLocation = eventLog.size() -1;
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
	public boolean eventLogIsEmpty(){
		return eventLog.isEmpty();
	}
}
