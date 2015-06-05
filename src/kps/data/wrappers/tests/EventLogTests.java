package kps.data.wrappers.tests;

import kps.Setup;
import kps.data.wrappers.EventLog;
import kps.events.BusinessEvent;
import kps.events.MailDeliveryEvent;
import kps.events.PriceUpdateEvent;
import kps.events.TransportCostUpdateEvent;
import kps.events.TransportDiscontinuedEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;

import org.junit.*;

import static org.junit.Assert.*;

public class EventLogTests {

	private EventLog setup(){
		EventLog log = null;
		try {
			log = new EventLog(KPSParser.parseFile(Setup.EVENT_LOG));
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return log;
	}

	@Test
	public void correctOrderingTest(){
		EventLog eventLog = setup();
		BusinessEvent oldEvent = eventLog.getCurrentEvent();
		BusinessEvent newEvent = eventLog.getNextEvent();
		if (oldEvent.getTimeLogged() <= newEvent.getTimeLogged()){
			return;
		}
		fail("Loged times not in disending order");
	}

	@Test
	public void addingInDesendingOrder1(){
		EventLog log = new EventLog();
		log.addBusinessEvent(new PriceUpdateEvent(System.currentTimeMillis(), null, null, null));
		log.addBusinessEvent(new PriceUpdateEvent(System.currentTimeMillis(), null, null, null));
		BusinessEvent oldEvent = log.getCurrentEvent();
		BusinessEvent newEvent = log.getNextEvent();
		if (oldEvent.getTimeLogged() >= newEvent.getTimeLogged()){
			return;
		}
		fail("Loged times not in disending order" +oldEvent.getTimeLogged()
				+ " > " +newEvent.getTimeLogged());
	}

	@Test
	public void addingInDesendingOrder2(){
		EventLog log = new EventLog();
		int amount = 5;
		for (int i = 0; i < amount; i++){
			log.addBusinessEvent(new PriceUpdateEvent(System.currentTimeMillis(), null, null, null));
		}
		for (int i = 0; i < amount -1; i++){
			BusinessEvent oldEvent = log.getCurrentEvent();
			BusinessEvent newEvent = log.getNextEvent();
			if (oldEvent.getTimeLogged() < newEvent.getTimeLogged()){
				fail("Loged times not in disending order" + oldEvent.getTimeLogged()
						+ " <= "+ newEvent.getTimeLogged());
			}
		}
	}

	@Test
	public void addingHigherDatedEvent(){
		EventLog log = setup();
		log.addBusinessEvent(new PriceUpdateEvent(
				System.currentTimeMillis(), null, null, null));
		log.resetEventLogLocation();
		BusinessEvent oldEvent = log.getCurrentEvent();
		BusinessEvent newEvent = log.getNextEvent();
		if (oldEvent.getTimeLogged() <= newEvent.getTimeLogged()){
			fail("Loged times not in disending order" + oldEvent.getTimeLogged()
					+ " <= "+ newEvent.getTimeLogged());
		}
	}


	@Test
	public void loopingRoundEventLog1(){
		EventLog log = new EventLog();
		log.addBusinessEvent(new PriceUpdateEvent(System.currentTimeMillis(), null, null, null));
		log.addBusinessEvent(new PriceUpdateEvent(System.currentTimeMillis(), null, null, null));
		BusinessEvent oldEvent = log.getCurrentEvent();
		log.getNextEvent();
		BusinessEvent newEvent = log.getNextEvent();
		if (oldEvent == newEvent){
			return;
		}
		fail("Log should be in the same memory location");
	}

	@Test
	public void mailFilterTest(){
		EventLog log = setup();
		log.applyMailDeliveryFilter();
		BusinessEvent oldEvent = log.getFilterCurrentEvent();
		log.getFilterNextEvent();
		BusinessEvent newEvent = log.getFilterNextEvent();
		if (oldEvent instanceof MailDeliveryEvent && newEvent instanceof MailDeliveryEvent){
			return;
		}
		fail("Log should be in the same memory location");
	}

	@Test
	public void mailFilterTest2(){
		EventLog log = setup();
		log.applyMailDeliveryFilter();
		BusinessEvent oldEvent = log.getFilterCurrentEvent();
		int size = log.getFilterSize();
		for (int i =0 ; i< size; i++){
			System.out.println(log.getFilterNextEvent());
		}
		BusinessEvent newEvent = log.getFilterCurrentEvent();
		if (oldEvent instanceof MailDeliveryEvent && newEvent instanceof MailDeliveryEvent && oldEvent == newEvent){
			return;
		}
		fail("Log should be in the same memory location" + oldEvent + "    "+ newEvent);
	}

	@Test
	public void mailFilterTest3(){
		EventLog log = setup();
		log.applyMailDeliveryFilter();
		BusinessEvent oldEvent = log.getFilterCurrentEvent();
		int size = log.getFilterSize();
		for (int i = size -1 ; i >= 0; i--){
			log.getFilterPrevEvent();
		}
		BusinessEvent newEvent = log.getFilterCurrentEvent();
		if (oldEvent instanceof MailDeliveryEvent && newEvent instanceof MailDeliveryEvent && oldEvent == newEvent){
			return;
		}
		fail("Log should be in the same memory location");
	}

	@Test
	public void priceFilterTest(){
		EventLog log = setup();
		log.applyPriceUpdateFilter();
		BusinessEvent oldEvent = log.getFilterCurrentEvent();
		log.getFilterNextEvent();
		BusinessEvent newEvent = log.getFilterNextEvent();
		if (oldEvent instanceof PriceUpdateEvent && newEvent instanceof PriceUpdateEvent){
			return;
		}
		fail("Log should be in the same memory location");
	}

	@Test
	public void priceFilterTest2(){
		EventLog log = setup();
		log.applyPriceUpdateFilter();
		BusinessEvent oldEvent = log.getFilterCurrentEvent();
		int size = log.getFilterSize();
		for (int i =0 ; i< size; i++){
			log.getFilterNextEvent();
		}
		BusinessEvent newEvent = log.getFilterCurrentEvent();
		if (oldEvent instanceof PriceUpdateEvent && newEvent instanceof PriceUpdateEvent && oldEvent == newEvent){
			return;
		}
		fail("Log should be in the same memory location");
	}

	@Test
	public void priceFilterTest3(){
		EventLog log = setup();
		log.applyPriceUpdateFilter();
		BusinessEvent oldEvent = log.getFilterCurrentEvent();
		int size = log.getFilterSize();
		for (int i = size -1 ; i >= 0; i--){
			log.getFilterPrevEvent();
		}
		BusinessEvent newEvent = log.getFilterCurrentEvent();
		if (oldEvent instanceof PriceUpdateEvent && newEvent instanceof PriceUpdateEvent && oldEvent == newEvent){
			return;
		}
		fail("Log should be in the same memory location");
	}

	@Test
	public void transportUpdateTest(){
		EventLog log = setup();
		log.applyTransportCostUpdateFilter();
		BusinessEvent oldEvent = log.getFilterCurrentEvent();
		log.getFilterNextEvent();
		BusinessEvent newEvent = log.getFilterNextEvent();
		if (oldEvent instanceof TransportCostUpdateEvent && newEvent instanceof TransportCostUpdateEvent){
			return;
		}
		fail("Log should be in the same memory location");
	}

	@Test
	public void transportUpdateTest2(){
		EventLog log = setup();
		log.applyTransportCostUpdateFilter();
		BusinessEvent oldEvent = log.getFilterCurrentEvent();
		int size = log.getFilterSize();
		for (int i =0 ; i< size; i++){
			log.getFilterNextEvent();
		}
		BusinessEvent newEvent = log.getFilterCurrentEvent();
		if (oldEvent instanceof TransportCostUpdateEvent && newEvent instanceof TransportCostUpdateEvent && oldEvent == newEvent){
			return;
		}
		else if (log.isFilterEmpty()){
			return;
		}
		fail("Log should be in the same memory location");
	}

	@Test
	public void transportUpdateTest3(){
		EventLog log = setup();
		log.applyTransportCostUpdateFilter();
		BusinessEvent oldEvent = log.getFilterCurrentEvent();
		int size = log.getFilterSize();
		for (int i = size -1 ; i >= 0; i--){
			log.getFilterPrevEvent();
		}
		BusinessEvent newEvent = log.getFilterCurrentEvent();
		if (oldEvent instanceof TransportCostUpdateEvent && newEvent instanceof TransportCostUpdateEvent && oldEvent == newEvent){
			return;
		}
		else if (log.isFilterEmpty()){
			return;
		}
		fail("Log should be in the same memory location");
	}

	@Test
	public void transportDiscontiuedTest(){
		EventLog log = setup();
		log.applyTransportDiscontinuedFilter();
		BusinessEvent oldEvent = log.getFilterCurrentEvent();
		log.getFilterNextEvent();
		BusinessEvent newEvent = log.getFilterNextEvent();
		if (oldEvent instanceof TransportDiscontinuedEvent && newEvent instanceof TransportDiscontinuedEvent){
			return;
		}
		else if (log.isFilterEmpty()){
			return;
		}
		fail("Log should be in the same memory location");
	}

	@Test
	public void transportDiscontiuedTest2(){
		EventLog log = setup();
		log.applyTransportDiscontinuedFilter();
		BusinessEvent oldEvent = log.getFilterCurrentEvent();
		int size = log.getFilterSize();
		for (int i =0 ; i< size; i++){
			log.getFilterNextEvent();
		}
		BusinessEvent newEvent = log.getFilterCurrentEvent();
		if (oldEvent instanceof TransportDiscontinuedEvent && newEvent instanceof TransportDiscontinuedEvent && oldEvent == newEvent){
			return;
		}
		else if (log.isFilterEmpty()){
			return;
		}
		fail("Log should be in the same memory location");
	}

	@Test
	public void transportDiscontiuedTest3(){
		EventLog log = setup();
		log.applyTransportDiscontinuedFilter();
		BusinessEvent oldEvent = log.getFilterCurrentEvent();
		int size = log.getFilterSize();
		for (int i = size -1 ; i >= 0; i--){
			log.getFilterPrevEvent();
		}
		BusinessEvent newEvent = log.getFilterCurrentEvent();
		if (oldEvent instanceof TransportDiscontinuedEvent && newEvent instanceof TransportDiscontinuedEvent && oldEvent == newEvent){
			return;
		}
		else if (log.isFilterEmpty()){
			return;
		}
		fail("Log should be in the same memory location");
	}
}
