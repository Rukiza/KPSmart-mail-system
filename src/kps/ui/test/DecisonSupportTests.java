package kps.ui.test;

import javax.swing.JFrame;

import kps.data.wrappers.EventLog;
import kps.ui.DecisionSupport;

import org.junit.Test;

public class DecisonSupportTests {
	
	@Test
	public void basicTest(){
		JFrame frame = new JFrame();
		frame.add(new DecisionSupport(new EventLog()));
		frame.setVisible(true);
	}
}
