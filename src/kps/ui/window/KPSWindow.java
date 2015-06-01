package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
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
import kps.ui.formlistener.DeleteRouteListener;
import kps.ui.formlistener.PackageFormListener;
import kps.ui.formlistener.PriceUpdateListener;
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
		final Dimension WINDOW_SIZE = new Dimension(1050,800);

		setSize(WINDOW_SIZE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		metricsPanel = new MetricsPanel(system.getMetrics());
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
		ImageIcon deleteIcon = null;
		ImageIcon updateIcon = null;
		try {
			plusIconGreen = new ImageIcon(
					UIUtils.resizeImage(ImageIO.read(new File("res/plus_icon_green.png")), 30, 30)
			);
			plusIconBlue = new ImageIcon(
					UIUtils.resizeImage(ImageIO.read(new File("res/plus_icon_blue.png")), 30, 30)
			);
			deleteIcon = new ImageIcon(
					UIUtils.resizeImage(ImageIO.read(new File("res/delete_icon.png")), 30, 30)
			);
			updateIcon = new ImageIcon(
					UIUtils.resizeImage(ImageIO.read(new File("res/update_icon.png")), 30, 30)
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

		JButton deleteRoute = new JButton("Delete route");
		deleteRoute.setIcon(deleteIcon);
		deleteRoute.setVerticalTextPosition(SwingConstants.TOP);
		deleteRoute.setHorizontalTextPosition(SwingConstants.CENTER);
		sidebar.add(deleteRoute);

		JButton priceUpdate = new JButton("Update route price");
		priceUpdate.setIcon(updateIcon);
		priceUpdate.setVerticalTextPosition(SwingConstants.TOP);
		priceUpdate.setHorizontalTextPosition(SwingConstants.CENTER);
		sidebar.add(priceUpdate);

		// side bar events
		addPackage.addActionListener((ActionEvent e) -> {
			new PackageFormWindow(new PackageFormListener(){
				@Override
				public void onPackageFormSubmitted(Day day, String from, String to, int weight, int volume, Priority priority){
					system.addMailDeliveryEvent(from, to, day, weight, volume, priority);
				}
				@Override public void onCompletedFormUpdate(Day day, String from, String to, Priority priority, int weight, int volume){
					DijkstraSearch search = new DijkstraSearch(system.getRouteGraph());
					Mail mail = new Mail(new BasicRoute(from, to), day, weight, volume, priority);
					if (search.isValidMailDelivery(mail)) {
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
					, int maxWeight, int maxVol, int dur, int freq, Day day){
				system.addTransportCostUpdateEvent(from, to, company, type, weightCost, volCost, maxWeight, maxVol, dur, freq, day);
			}

			@Override
			public void onCancel() {
				// cancelled...
			}
		}));

		deleteRoute.addActionListener((ActionEvent e) -> new DeleteRouteWindow(new DeleteRouteListener(){
			@Override public void onDeleteFormSubmitted(String company, String to, String from, TransportType type){
				system.addTransportDiscontinuedEvent(to, from, company, type);
			}
			@Override public void onCompletedFormUpdate(String company, String to, String from, TransportType type){
				// updated
			}
			@Override public void onCancel(){
				// cancelled
			}
		}, system.getRouteGraph().getNodes()));

		priceUpdate.addActionListener((ActionEvent e) -> new PriceUpdateWindow(new PriceUpdateListener(){
			@Override public void onPriceUpdateSubmitted(String from, String to, Priority priority, double weightCost, double volumeCost){
				system.addPriceUpdateEvent(to, from, weightCost, volumeCost, priority);
			}
			@Override public void onCancel(){
				// cancelled
			}
		}, system.getRouteGraph().getNodes()));

		return sidebar;
	}
}