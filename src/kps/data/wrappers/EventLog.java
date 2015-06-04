package kps.data.wrappers;

import java.util.*;

import kps.events.BusinessEvent;
import kps.events.MailDeliveryEvent;
import kps.events.PriceUpdateEvent;
import kps.events.TransportCostUpdateEvent;
import kps.events.TransportDiscontinuedEvent;
import kps.parser.KPSParser;

public class EventLog {
	private List<BusinessEvent> eventLog;
	private int iterationLocation;
	private Filter currentFilter;

	public EventLog(){
		eventLog = new ArrayList<BusinessEvent>();
	}

	public EventLog(List<BusinessEvent> eventLog){
		eventLog.sort(new Comparator<BusinessEvent>() {

			@Override
			public int compare(BusinessEvent o1, BusinessEvent o2) {
				if (o1.getTimeLogged() < o2.getTimeLogged()){
					return -1;
				}
				else if (o1.getTimeLogged() == o2.getTimeLogged()){
					return 0;
				}
				else return 1;
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
		if (currentFilter != null){
			return currentFilter.getNextEvent();
		}
		else {
			if (iterationLocation + 1 >= eventLog.size()){
				iterationLocation = 0;
				return eventLog.get(iterationLocation);
			}
			return eventLog.get(++iterationLocation);
		}
	}

	/**
	 * Used to get the previous event in the list.
	 * Loops around to the end of the list when it reaches the start..
	 *
	 * @return - Business event
	 */
	public BusinessEvent getPrevEvent(){
		if (currentFilter != null){
			return currentFilter.getPrevEvent();
		}
		else {
			if (iterationLocation - 1 < 0){
				iterationLocation = eventLog.size() -1;
				return eventLog.get(iterationLocation);
			}
			return eventLog.get(--iterationLocation);
		}
	}

	/**
	 * Gets the current business event.
	 *
	 * @return - Current Business event.
	 */
	public BusinessEvent getCurrentEvent(){
		if (currentFilter != null){
			return currentFilter.getCurrentEvent();
		}
		else {
			return eventLog.get(iterationLocation);
		}
	}

	/**
	 * Resets the eventLog iteration.
	 */
	public void resetEventLogLocation(){
		if (currentFilter != null){
			currentFilter.resetEventLogLocation();;
		}
		else {
			iterationLocation = 0;
		}
	}

	/**
	 * Check if the event log is empty
	 * @return - true if its empty
	 */
	public boolean isEmpty(){
		if (currentFilter != null){
			return currentFilter.isEmpty();
		}
		else {
			return eventLog.isEmpty();
		}
	}

	/**
	 * @return - Size of the event log
	 */
	public int getSize(){
		if (currentFilter != null){
			return currentFilter.getSize();
		}
		else {
			return eventLog.size();
		}
	}

	public int getPosition(){
		if (currentFilter != null){
			return currentFilter.getPosition();
		}
		else {
			return iterationLocation;
		}
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
		if (currentFilter != null){
			return currentFilter.getListToCurrent();
		}
		else {
			return eventLog.subList(0, iterationLocation+1);
		}
	}

	public boolean applyMailDeliveryFilter(){
		currentFilter = new MailDeliveryFilter();
		return true;
	}
	public boolean applyPriceUpdateFilter(){
		currentFilter = new PriceUpdateFilter();
		return true;
	}
	public boolean applyTransportCostUpdateFilter(){
		currentFilter = new TransportCostUpdateFilter();
		return true;
	}
	public boolean applyTransportDiscontinuedFilter(){
		currentFilter = new TransportDiscontinuedFilter();
		return true;
	}

	public boolean removeFilter(){
		currentFilter = null;
		return true;
	}

	/**
	 * For filtering by event
	 * @author brewershan
	 *
	 */
	private interface Filter{
		/**
		 * Gets the size of the filtered list.
		 * @return - returns the size of the filtered list.
		 */
		public int getSize();
		/**
		 * Gets the filtered postion
		 * @return - Returns the postion of the pointer in the filtered list.
		 */
		public int getPosition();
		/**
		 * Gets the Prevous element in the filtered list.
		 * @return - previous event after filter.
		 */
		public BusinessEvent getPrevEvent();
		/**
		 * Gets the next element in the filtered list.
		 * @return - next event after filter.
		 */
		public BusinessEvent getNextEvent();
		/**
		 * Gets the current element in the filtered list.
		 * @return - current event after filter.
		 */
		public BusinessEvent getCurrentEvent();
		/**
		 * Resets the log.
		 */
		public void resetEventLogLocation();
		/**
		 * Returns the new filtered list.
		 * @return - a list of filtered events.
		 */
		public List<BusinessEvent> getListToCurrent();
		/**
		 * Checks if the filtered list is empty.
		 * @return - true if the list is empty.
		 */
		public boolean isEmpty();
	}

	/**
	 * For filtering out the so only mail events are left.
	 * @author brewershan
	 *
	 */
	private class MailDeliveryFilter implements Filter{
		private int filterPosition;
		private int resetPosition;


		public MailDeliveryFilter() {
			for (int i= 0; i < eventLog.size(); i++){
				if (eventLog.get(i) instanceof MailDeliveryEvent){
					filterPosition = i;
					resetPosition = i;
					break;
				}
			}
		}

		@Override
		public int getSize() {
			int amount = 0;
			for (BusinessEvent event: eventLog){
				if (event instanceof MailDeliveryEvent){
					amount++;
				}
			}
			return amount;
		}

		@Override
		public int getPosition() {
			return filterPosition;
		}

		@Override
		public BusinessEvent getPrevEvent() {
			if (isEmpty()) return null;
			if (filterPosition <= 0){
				filterPosition = eventLog.size();
			}
			int i = filterPosition -1;
			for ( ; i >= 0; i--){
				if (eventLog.get(i) instanceof MailDeliveryEvent){
					filterPosition = i;
					break;
				}
			}
			if (i < 0){
				for ( i = eventLog.size()-1; i >= 0; i--){
					if (eventLog.get(i) instanceof MailDeliveryEvent){
						filterPosition = i;
						break;
					}
				}
			}
			return eventLog.get(filterPosition);
		}

		@Override
		public BusinessEvent getNextEvent() {
			if (isEmpty()) return null;
			if (filterPosition >= eventLog.size()){
				filterPosition = -1;
			}
			int i = filterPosition +1;
			for ( ; i < eventLog.size(); i++){
				if (eventLog.get(i) instanceof MailDeliveryEvent){
					filterPosition = i;
					return eventLog.get(filterPosition);
				}
			}
			if (i >= eventLog.size()){
				System.out.println(i);
				for ( i = 0; i < eventLog.size(); i++){
					if (eventLog.get(i) instanceof MailDeliveryEvent){
						filterPosition = i;
						return eventLog.get(filterPosition);
					}
				}
			}
			return eventLog.get(filterPosition);
		}

		@Override
		public BusinessEvent getCurrentEvent() {
			return eventLog.get(filterPosition);
		}

		@Override
		public void resetEventLogLocation(){
			filterPosition = resetPosition;
		}

		@Override
		public List<BusinessEvent> getListToCurrent() {
			List<BusinessEvent> filterlist = new ArrayList<BusinessEvent>();
			int i = 0;
			for ( ; i < filterPosition+1; i++){
				if (eventLog.get(i) instanceof MailDeliveryEvent){
					filterlist.add(eventLog.get(i));
				}
			}
			return filterlist;
		}

		@Override
		public boolean isEmpty() {
			int i = 0;
			for ( ; i < eventLog.size(); i++){
				if (eventLog.get(i) instanceof MailDeliveryEvent){
					return false;
				}
			}
			return true;
		}

	}

	/**
	 * For filtering out the so only price update events events are left.
	 * @author brewershan
	 *
	 */
	private class PriceUpdateFilter implements Filter{
		private int filterPosition;
		private int resetPosition;


		public PriceUpdateFilter() {
			for (int i= 0; i < eventLog.size(); i++){
				if (eventLog.get(i) instanceof PriceUpdateEvent){
					filterPosition = i;
					resetPosition = i;
					break;
				}
			}
		}

		@Override
		public int getSize() {
			int amount = 0;
			for (BusinessEvent event: eventLog){
				if (event instanceof PriceUpdateEvent){
					amount++;
				}
			}
			return amount;
		}

		@Override
		public int getPosition() {
			return filterPosition;
		}

		@Override
		public BusinessEvent getPrevEvent() {
			if (isEmpty()) return null;
			if (filterPosition <= 0){
				filterPosition = eventLog.size();
			}
			int i = filterPosition -1;
			for ( ; i >= 0; i--){
				if (eventLog.get(i) instanceof PriceUpdateEvent){
					filterPosition = i;
					break;
				}
			}
			if (i < 0){
				for ( i = eventLog.size()-1; i >= 0; i--){
					if (eventLog.get(i) instanceof PriceUpdateEvent){
						filterPosition = i;
						break;
					}
				}
			}
			return eventLog.get(filterPosition);
		}

		@Override
		public BusinessEvent getNextEvent() {
			if (isEmpty()) return null;
			if (filterPosition >= eventLog.size()){
				filterPosition = -1;
			}
			int i = filterPosition +1;
			for ( ; i < eventLog.size(); i++){
				if (eventLog.get(i) instanceof PriceUpdateEvent){
					filterPosition = i;
					break;
				}
			}
			if (i >= eventLog.size()){
				for ( i = 0; i < eventLog.size(); i++){
					if (eventLog.get(i) instanceof PriceUpdateEvent){
						filterPosition = i;
						break;
					}
				}
			}
			return eventLog.get(filterPosition);
		}

		@Override
		public BusinessEvent getCurrentEvent() {
			return eventLog.get(filterPosition);
		}

		@Override
		public void resetEventLogLocation(){
			filterPosition = resetPosition;
		}

		@Override
		public List<BusinessEvent> getListToCurrent() {
			List<BusinessEvent> filterlist = new ArrayList<BusinessEvent>();
			int i = 0;
			for ( ; i < filterPosition+1; i++){
				if (eventLog.get(i) instanceof PriceUpdateEvent){
					filterlist.add(eventLog.get(i));
				}
			}
			return filterlist;
		}

		@Override
		public boolean isEmpty() {
			int i = 0;
			for ( ; i < eventLog.size(); i++){
				if (eventLog.get(i) instanceof PriceUpdateEvent){
					return false;
				}
			}
			return true;
		}

	}

	/**
	 * For filtering out the so only transport cost update events are left.
	 * @author brewershan
	 *
	 */
	private class TransportCostUpdateFilter implements Filter{
		private int filterPosition;
		private int resetPosition;


		public TransportCostUpdateFilter() {
			for (int i= 0; i < eventLog.size(); i++){
				if (eventLog.get(i) instanceof TransportCostUpdateEvent){
					filterPosition = i;
					resetPosition = i;
					break;
				}
			}
		}

		@Override
		public int getSize() {
			int amount = 0;
			for (BusinessEvent event: eventLog){
				if (event instanceof TransportCostUpdateEvent){
					amount++;
				}
			}
			return amount;
		}

		@Override
		public int getPosition() {
			return filterPosition;
		}

		@Override
		public BusinessEvent getPrevEvent() {
			if (isEmpty()) return null;
			if (filterPosition <= 0){
				filterPosition = eventLog.size();
			}
			int i = filterPosition -1;
			for ( ; i >= 0; i--){
				if (eventLog.get(i) instanceof TransportCostUpdateEvent){
					filterPosition = i;
					break;
				}
			}
			if (i < 0){
				for ( i = eventLog.size()-1; i >= 0; i--){
					if (eventLog.get(i) instanceof TransportCostUpdateEvent){
						filterPosition = i;
						break;
					}
				}
			}
			return eventLog.get(filterPosition);
		}

		@Override
		public BusinessEvent getNextEvent() {
			if (isEmpty()) return null;
			if (filterPosition >= eventLog.size()){
				filterPosition = -1;
			}
			int i = filterPosition +1;
			for ( ; i < eventLog.size(); i++){
				if (eventLog.get(i) instanceof TransportCostUpdateEvent){
					filterPosition = i;
					break;
				}
			}
			if (i >= eventLog.size()){
				for ( i = 0; i < eventLog.size(); i++){
					if (eventLog.get(i) instanceof TransportCostUpdateEvent){
						filterPosition = i;
						break;
					}
				}
			}
			return eventLog.get(filterPosition);
		}

		@Override
		public BusinessEvent getCurrentEvent() {
			return eventLog.get(filterPosition);
		}

		@Override
		public void resetEventLogLocation(){
			filterPosition = resetPosition;
		}

		@Override
		public List<BusinessEvent> getListToCurrent() {
			List<BusinessEvent> filterlist = new ArrayList<BusinessEvent>();
			int i = 0;
			for ( ; i < filterPosition+1; i++){
				if (eventLog.get(i) instanceof TransportCostUpdateEvent){
					filterlist.add(eventLog.get(i));
				}
			}
			return filterlist;
		}

		@Override
		public boolean isEmpty() {
			int i = 0;
			for ( ; i < eventLog.size(); i++){
				if (eventLog.get(i) instanceof TransportCostUpdateEvent){
					return false;
				}
			}
			return true;
		}

	}

	/**
	 * For filtering out the so only transport discontinued events are left.
	 * @author brewershan
	 *
	 */
	private class TransportDiscontinuedFilter implements Filter{
		private int filterPosition;
		private int resetPosition;


		public TransportDiscontinuedFilter() {
			for (int i= 0; i < eventLog.size(); i++){
				if (eventLog.get(i) instanceof TransportDiscontinuedEvent){
					filterPosition = i;
					resetPosition = i;
					break;
				}
			}
		}

		@Override
		public int getSize() {
			int amount = 0;
			for (BusinessEvent event: eventLog){
				if (event instanceof TransportDiscontinuedEvent){
					amount++;
				}
			}
			return amount;
		}

		@Override
		public int getPosition() {
			return filterPosition;
		}

		@Override
		public BusinessEvent getPrevEvent() {
			if (isEmpty()) return null;
			if (filterPosition <= 0){
				filterPosition = eventLog.size();
			}
			int i = filterPosition -1;
			for ( ; i >= 0; i--){
				if (eventLog.get(i) instanceof TransportDiscontinuedEvent){
					filterPosition = i;
					break;
				}
			}
			if (i < 0){
				for ( i = eventLog.size()-1; i >= 0; i--){
					if (eventLog.get(i) instanceof TransportDiscontinuedEvent){
						filterPosition = i;
						break;
					}
				}
			}
			return eventLog.get(filterPosition);
		}

		@Override
		public BusinessEvent getNextEvent() {
			if (isEmpty()) return null;
			if (filterPosition >= eventLog.size()){
				filterPosition = -1;
			}
			int i = filterPosition +1;
			for ( ; i < eventLog.size(); i++){
				if (eventLog.get(i) instanceof TransportDiscontinuedEvent){
					filterPosition = i;
					break;
				}
			}
			if (i >= eventLog.size() ){
				for ( i = 0; i < eventLog.size(); i++){
					if (eventLog.get(i) instanceof TransportDiscontinuedEvent){
						filterPosition = i;
						break;
					}
				}
			}
			return eventLog.get(filterPosition);
		}

		@Override
		public BusinessEvent getCurrentEvent() {
			return eventLog.get(filterPosition);
		}

		@Override
		public void resetEventLogLocation(){
			filterPosition = resetPosition;
		}

		@Override
		public List<BusinessEvent> getListToCurrent() {
			List<BusinessEvent> filterlist = new ArrayList<BusinessEvent>();
			int i = 0;
			for ( ; i < filterPosition+1; i++){
				if (eventLog.get(i) instanceof TransportDiscontinuedEvent){
					filterlist.add(eventLog.get(i));
				}
			}
			return filterlist;
		}

		@Override
		public boolean isEmpty() {
			int i = 0;
			for ( ; i < eventLog.size(); i++){
				if (eventLog.get(i) instanceof TransportDiscontinuedEvent){
					return false;
				}
			}
			return true;
		}

	}
}
