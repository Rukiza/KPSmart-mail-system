package kps.data.wrappers.tests;

import kps.Main;
import kps.data.wrappers.EventLog;
import kps.events.BusinessEvent;
import kps.events.PriceUpdateEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;

import org.junit.*;

import static org.junit.Assert.*;

public class EventLogTests {
	
	private EventLog setup(){
		EventLog log = null;
		try {
			log = new EventLog(KPSParser.parseFile(Main.filename));
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
		if (oldEvent.getTimeLogged() >= newEvent.getTimeLogged()){
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
}
