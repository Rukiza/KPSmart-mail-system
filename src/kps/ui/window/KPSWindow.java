package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import kps.KPSmartSystem;
import kps.Setup;
import kps.data.DijkstraSearch;
import kps.data.Mail;
import kps.data.Route;
import kps.data.RouteGraph;
import kps.data.wrappers.BasicRoute;
import kps.data.wrappers.EventLog;
import kps.enums.Day;
import kps.enums.Position;
import kps.enums.Priority;
import kps.enums.TransportType;
import kps.events.BusinessEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;
import kps.ui.formlistener.AuthDetailsListener;
import kps.ui.formlistener.CreateUserEvent;
import kps.ui.formlistener.CreateUserListener;
import kps.ui.formlistener.DeleteRouteListener;
import kps.ui.formlistener.DeleteUserEvent;
import kps.ui.formlistener.DeleteUserListener;
import kps.ui.formlistener.PackageFormListener;
import kps.ui.formlistener.PriceUpdateListener;
import kps.ui.formlistener.RouteFormListener;
import kps.ui.panel.DecisionSupportPanel;
import kps.ui.panel.MetricsPanel;
import kps.ui.panel.RouteGraphPanel;
import kps.ui.util.UIUtils;
import kps.users.KPSUser;


/**
 * @author hardwiwill
 * Contains all of the KPSmart GUI elements (aside from popup boxes)
 */
public class KPSWindow extends JFrame {

	private KPSmartSystem system;

	private MetricsPanel metricsPanel;
	private DecisionSupportPanel dsPanel;
	private RouteGraphPanel graphPanel;

	public KPSWindow(KPSmartSystem system, KPSUser user){
		super("KPSmart");
		this.system = system;
		final Dimension WINDOW_SIZE = new Dimension(1090,830);

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
		// if user is a manager, should be able to access decision support
		if (user.getPosition() == Position.MANAGER){
                tabbedPane.addTab("Decision Support", dsPanel);
		}
		tabbedPane.addTab("Route graph", graphPanel);

		JPanel sidebar = makeSidebar();
		add(sidebar, BorderLayout.WEST);

		JPanel userBar = makeUserBar(user);
		add(userBar, BorderLayout.NORTH);

		setVisible(true);
	}

	private JPanel makeUserBar(KPSUser user) {
		JPanel userbar = new JPanel();
		userbar.setLayout(new BorderLayout());

		JLabel username = new JLabel("Current user: " + user.getUsername());
		userbar.add(username, BorderLayout.CENTER);


		JPanel buttons = new JPanel();
		JButton createUser = new JButton("Create user");
		JButton deleteUser = new JButton("Delete user");
		JButton logout = new JButton("Log out");
		buttons.add(logout);
		buttons.add(createUser);
		buttons.add(deleteUser);
		userbar.add(buttons, BorderLayout.EAST);
		userbar.setPreferredSize(new Dimension(0, 30));

		// my lambdas didn't work for some reason
//		createUser.addActionListener((ActionEvent e) -> {
//			new CreateUserWindow((CreateUserEvent e) -> {
//                        system.addKPSUser(e.getUsername(), e.getPasswordHash(), e.getPosition());
//			});
//		});

//		deleteUser.addActionListener((ActionEvent e) -> {
//			new DeleteUserWindow((DeleteUserEvent e) -> {
//				system.removeKPSUser(e.getUserName());
//			});
//		});

		createUser.addActionListener((ActionEvent e) -> {
			new CreateUserWindow(new CreateUserListener(){
				@Override public boolean onUserSubmitted(CreateUserEvent e){
                       boolean containsUser = system.containsKPSUser(e.getUsername());
                       if (!containsUser){
                            system.addKPSUser(e.getUsername(), e.getPasswordHash(), e.getPosition());
                            JOptionPane.showMessageDialog(userbar, "User successfully created!");
                            return true;
                       }
                       else {
                    	   return false;
                       }
				}
				@Override public void onCancel(){
					// cancel
				}
			});
		});

		deleteUser.addActionListener((ActionEvent e) -> {
			new DeleteUserWindow(new DeleteUserListener(){
				@Override public boolean onUserSubmitted(DeleteUserEvent e){
					if (system.containsKPSUser(e.getUsername())){
                        system.removeKPSUser(e.getUsername());
                        JOptionPane.showMessageDialog(userbar, "User successfully deleted!");
                        return true;
					} else {
						return false;
					}
				}
				@Override public void onCancel(){
					// cancel
				}
			});
		});

		logout.addActionListener((ActionEvent e) -> {
			system.logout();
			this.dispose();
			Setup.login(system);
		});

		return userbar;
	}

	/**
	 * make the sidebar including buttons and their listeners
	 * @return
	 */
	private JPanel makeSidebar(){
		JPanel sidebar = new JPanel();
		sidebar.setPreferredSize(new Dimension(160, 0));
		sidebar.setLayout(new FlowLayout(FlowLayout.CENTER));

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
				public String onPackageFormSubmitted(Day day, String from, String to, int weight, int volume, Priority priority){
					return system.addMailDeliveryEvent(from, to, day, weight, volume, priority);
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
				graphPanel.graphUpdated();
			}

			@Override
			public void onCancel() {
				// cancelled...
			}
		}));

		deleteRoute.addActionListener((ActionEvent e) -> new DeleteRouteWindow(new DeleteRouteListener(){
			@Override public void onDeleteFormSubmitted(Route route){
				system.addTransportDiscontinuedEvent(route);
				graphPanel.graphUpdated();
			}
			@Override
			public void onRouteUpdate(Route route) {
				// updated
			}
			@Override public void onCancel(){
				// cancelled
			}
		}, system.getRouteGraph()));

		priceUpdate.addActionListener((ActionEvent e) -> new PriceUpdateWindow(new PriceUpdateListener(){
			@Override public void onPriceUpdateSubmitted(String from, String to, double weightCost, double volumeCost, Priority priority){
				system.addPriceUpdateEvent(from, to, weightCost, volumeCost, priority);
			}
			@Override public void onRouteUpdate(Route r){
				// update
			}
			@Override public void onCancel(){
				// cancelled
			}
		}, system));

		return sidebar;
	}
}