package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import kps.KPSmartSystem;
import kps.data.wrappers.EventLog;
import kps.ui.listener.PackageFormListener;
import kps.ui.listener.RouteFormListener;
import kps.ui.panel.DecisionSupportPanel;
import kps.ui.panel.MetricsPanel;

/**
 * @author hardwiwill
 * Encapsulates all of the KPSmart GUI elements (aside from popup boxes)
 */
public class Window extends JFrame {

	private KPSmartSystem system = new KPSmartSystem();

	public Window(EventLog bizEvents){
		super("KPSmart");
		final Dimension WINDOW_SIZE = new Dimension(800,600);

		setSize(WINDOW_SIZE);
		setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Decision Support", new DecisionSupportPanel(bizEvents));
		tabbedPane.addTab("Metrics", new MetricsPanel());

		JPanel sidebar = makeSidebar();
		add(sidebar, BorderLayout.WEST);

		setVisible(true);
	}

	/**
	 * make the sidebar including buttons and their listeners
	 * @return
	 */
	private JPanel makeSidebar(){
		JPanel sidebar = new JPanel();
		sidebar.setPreferredSize(new Dimension(100, 0));
		sidebar.setLayout(new FlowLayout(FlowLayout.CENTER));
		sidebar.add(new JLabel("Sidebar"));

		JButton addPackage = new JButton("add package");
		sidebar.add(addPackage);

		// side bar events
		addPackage.addActionListener((ActionEvent e) -> {
			new PackageFormWindow(new PackageFormListener(){
				@Override
				public void onPackageFormSubmitted(String day, String from, double price, double volume, String priority){
					System.out.println("Submitted package form");
				}
				@Override
				public void onCancel(){
					System.out.println("Cancelled");
				}
			});
		});

		JButton addRoute= new JButton("Add route");
		sidebar.add(addRoute);

		// oh lordy
		addRoute.addActionListener((ActionEvent e) -> new RouteFormWindow(new RouteFormListener(){
			public void onRouteFormSubmitted(String company, String to, String from, String type, double weightCost, double volCost
					, double maxWeight, double maxVol, double dur, double freq, String priority, String day){
				System.out.println("Got form");
			}
			public void onCancel(){
				System.out.println("cancelled");
			}
		}));

		return sidebar;
	}

}
