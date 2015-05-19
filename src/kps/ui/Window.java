package kps.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		final Dimension WINDOW_SIZE = new Dimension(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_SIZE);
		setLocationRelativeTo(null); // centers the frame

		setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Decision Support", new DecisionSupportPanel(bizEvents));
		tabbedPane.addTab("Metrics", new MetricsPanel());

		JPanel sidebar = makeSidebar();
		add(sidebar, BorderLayout.WEST);

		setVisible(true);
	}

	private JPanel makeSidebar(){
		JPanel sidebar = new JPanel();
		sidebar.setPreferredSize(new Dimension(100, 0));
		sidebar.setLayout(new FlowLayout());
		sidebar.add(new JLabel("Sidebar"));

		JButton addPackage = new JButton("add package");
		sidebar.add(addPackage);
		return sidebar;
	}

}
