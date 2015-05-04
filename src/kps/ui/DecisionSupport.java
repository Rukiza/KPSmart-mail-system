package kps.ui;

import javax.swing.JPanel;

import kps.data.wrappers.EventLog;
import kps.events.BusinessEvent;

public class DecisionSupport extends JPanel{
	
	private EventLog data;
	
	public DecisionSupport(EventLog data){
		this.data = data;
	}
	
	/**
	 * Repaints the panel.
	 */
	public void repaint(){
		BusinessEvent event = data.getCurrentEvent();
		
	}
}
