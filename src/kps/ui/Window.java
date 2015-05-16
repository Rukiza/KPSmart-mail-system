package kps.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import kps.data.wrappers.EventLog;
import kps.ui.panel.DecisionSupportPanel;
import kps.ui.panel.MetricsPanel;

/**
 * @author hardwiwill
 * Encapsulates all of the KPSmart GUI elements (aside from popup boxes)
 */
public class Window extends JFrame {

	public Window(EventLog bizEvents){
		final Dimension WINDOW_SIZE = new Dimension(600,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_SIZE);
		setLocationRelativeTo(null); // centers the frame

		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane);

		tabbedPane.addTab("Decision Support", new DecisionSupportPanel(bizEvents));
		tabbedPane.addTab("Metrics", new MetricsPanel());

		setVisible(true);
	}

}
