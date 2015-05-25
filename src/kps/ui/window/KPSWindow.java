package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import kps.KPSmartSystem;
import kps.data.Node;
import kps.data.wrappers.EventLog;
import kps.enums.Day;
import kps.enums.Priority;
import kps.enums.TransportType;
import kps.ui.form.PackageFormListener;
import kps.ui.form.RouteFormListener;
import kps.ui.panel.DecisionSupportPanel;
import kps.ui.panel.MetricsPanel;
import kps.ui.panel.RouteGraphPanel;
import kps.ui.util.UIUtils;

/**
 * @author hardwiwill
 * Contains all of the KPSmart GUI elements (aside from popup boxes)
 */
public class KPSWindow extends JFrame {

	private KPSmartSystem system; 
	private RouteGraphPanel routeGraph;
	private MetricsPanel metrics;
	private DecisionSupportPanel decisionSupport;

	public KPSWindow(KPSmartSystem system){
		super("KPSmart");
		this.system = system;

		final Dimension WINDOW_SIZE = new Dimension(800,600);

		setSize(WINDOW_SIZE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);
		
		decisionSupport = new DecisionSupportPanel(system.getEventLog());
		metrics = new MetricsPanel();
		routeGraph = new RouteGraphPanel(system.getRouteGraph(),this);

		tabbedPane.addTab("Metrics", metrics);
		tabbedPane.addTab("Decision Support", decisionSupport);
		tabbedPane.addTab("Route Graph", routeGraph);

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
		sidebar.setPreferredSize(new Dimension(120, 0));
		sidebar.setLayout(new FlowLayout(FlowLayout.CENTER));
		sidebar.add(new JLabel("Sidebar"));

		// button setup
		ImageIcon plusIconGreen = null;
		ImageIcon plusIconBlue = null;
		try {
			plusIconGreen = new ImageIcon(
					UIUtils.resizeImage(ImageIO.read(new File("res/plus_icon_green.png")), 30, 30)
			);
			plusIconBlue = new ImageIcon(
					UIUtils.resizeImage(ImageIO.read(new File("res/plus_icon_blue.png")), 30, 30)
			);
		} catch (IOException e) { e.printStackTrace(); }

		JButton addPackage = new JButton("Add package");
		addPackage.setIcon(plusIconGreen);
		addPackage.setVerticalTextPosition(SwingConstants.TOP);
		addPackage.setHorizontalTextPosition(SwingConstants.CENTER);
		sidebar.add(addPackage);

		JButton addRoute = new JButton("Add route");
		addRoute.setIcon(plusIconBlue);
		addRoute.setVerticalTextPosition(SwingConstants.TOP);
		addRoute.setHorizontalTextPosition(SwingConstants.CENTER);
		sidebar.add(addRoute);

		// side bar events
		addPackage.addActionListener((ActionEvent e) -> {
			new PackageFormWindow(new PackageFormListener(){
				public void onPackageFormSubmitted(Day day, Node from, Node to, double price, double volume, Priority priority){
//					if (system.getRouteGraph().isValidRoute(to, from)){
//						routeGraph.update();
//						system.addMailDeliveryEvent(day, from, to, price, volume, priority);
//					}
				}
				public void onRouteChosen(Node to, Node from){
//					routeGraph.setRoute(to, from);
				}
				public void onCancel(){
					// cancelled
				}
			}, system.getRouteGraph().getNodes());
		});

		// oh lordy
		addRoute.addActionListener((ActionEvent e) -> new RouteFormWindow(new RouteFormListener(){
			public void onRouteFormSubmitted(String company, String to, String from, TransportType type, double weightCost, double volCost
					, double maxWeight, double maxVol, double dur, double freq, Priority priority, Day day){
				// route form submitted
			}
			public void onCancel(){
				// cancelled
			}
		}));

		return sidebar;
	}
}