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
import kps.data.DijkstraSearch;
import kps.data.Mail;
import kps.data.wrappers.BasicRoute;
import kps.enums.Day;
import kps.enums.Priority;
import kps.enums.TransportType;
import kps.events.MailDeliveryEvent;
import kps.ui.formlistener.PackageFormListener;
import kps.ui.formlistener.RouteFormListener;
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

	private MetricsPanel metricsPanel;
	private DecisionSupportPanel dsPanel;
	private RouteGraphPanel graphPanel;

	public KPSWindow(KPSmartSystem system){
		super("KPSmart");
		this.system = system;
		final Dimension WINDOW_SIZE = new Dimension(1200,800);

		setSize(WINDOW_SIZE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		metricsPanel = new MetricsPanel();
		dsPanel = new DecisionSupportPanel(system.getEventLog());
		graphPanel = new RouteGraphPanel(system.getRouteGraph(), this);

		tabbedPane.addTab("Metrics", metricsPanel);
		tabbedPane.addTab("Decision Support", dsPanel);
		tabbedPane.addTab("Route graph", graphPanel);

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
				@Override
				public void onPackageFormSubmitted(Day day, String from, String to, int weight, int volume, Priority priority){
					system.addMailDeliveryEvent(to, from, day, weight, volume, priority);
				}
				@Override public void onCompletedFormUpdate(Day day, String from, String to, int weight, int volume, Priority priority){
					DijkstraSearch search = new DijkstraSearch(system.getRouteGraph());
					Mail mail = new Mail(new BasicRoute(from, to), day, weight, volume, priority);
					System.out.println("checking valid route with mail object:\n" + mail);
					if (search.isValidMailDelivery(mail)) {
						System.out.println("updating graph from window!");
						graphPanel.setRoute(mail);
					}
				}
				public void onCancel(){
					// cancelled
				}
			}, system.getRouteGraph().getNodes());
		});

		// oh lordy
		addRoute.addActionListener((ActionEvent e) -> new RouteFormWindow(new RouteFormListener(){
			@Override
			public void onRouteFormSubmitted(String company, String to, String from, TransportType type, double weightCost, double volCost
					, double maxWeight, double maxVol, double dur, double freq, Priority priority, Day day){
				// route form submitted
			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub

			}
		}));

		return sidebar;
	}
}