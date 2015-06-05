package kps.ui.test;

import javax.swing.JFrame;

import static org.junit.Assert.*;

import kps.Main;
import kps.data.wrappers.EventLog;
import kps.parser.KPSParser;
import kps.parser.ParserException;
import kps.ui.panel.DecisionSupportPanel;

import org.junit.Test;

public class DecisonSupportTests {

	@Test
	public void basicTest(){
		JFrame frame = new JFrame();
		frame.add(new DecisionSupportPanel(new EventLog()));
		frame.pack();
		frame.setVisible(true);
	}

	@Test
	public void basicTestWithData(){
		JFrame frame = new JFrame();
		try {
			frame.add(new DecisionSupportPanel(new EventLog(KPSParser.parseFile(Main.XML_FILE_PATH+"kps2.xml"))));
			System.out.println("File loaded");
		} catch (ParserException e) {
			fail("File Failed to load");
			e.printStackTrace();
		}
		frame.pack();
		frame.setVisible(true);
	}
}
