package kps.ui.test;

import javax.swing.JFrame;

import kps.data.wrappers.EventLog;
import kps.parser.KPSParser;
import kps.parser.ParserException;
import kps.ui.DecisionSupport;

import org.junit.Test;

public class DecisonSupportTests {

	@Test
	public void basicTest(){
		JFrame frame = new JFrame();
		frame.add(new DecisionSupport(new EventLog()));
		frame.pack();
		frame.setVisible(true);
	}

	@Test
	public void basicTestWithData(){
		JFrame frame = new JFrame();
		try {
			frame.add(new DecisionSupport(new EventLog(KPSParser.parseFile("src/kps/xml/kps2.xml"))));
			System.out.println("File loaded");
		} catch (ParserException e) {
			System.out.println("File Failed to load");
			e.printStackTrace();
		}
		frame.pack();
		frame.setVisible(true);
	}
}
