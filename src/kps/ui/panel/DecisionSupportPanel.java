package kps.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import kps.data.wrappers.EventLog;
import kps.enums.Day;
import kps.enums.Priority;
import kps.events.BusinessEvent;
import kps.events.MailDeliveryEvent;
import kps.events.PriceUpdateEvent;
import kps.events.TransportCostUpdateEvent;
import kps.events.TransportDiscontinuedEvent;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * @author Shane Brewer
 *
 */
public class DecisionSupportPanel extends JPanel {

	private EventLog data;
	private DataManager manager;
	private GraphPanel graphPanel;
	private DisplayPanel displayPanel;
	private SelectPanel selectPanel;
	private LoadBarPanel loadBarPanel;
	private Dimension size = new Dimension(900, 790);

	private final String typeString = "Type";
	private final String transString = "Transport";
	private final String discString = "Discontinued";
	private final String mailString = "Mail";
	private final String pricesString = "Price";



	/**
	 * Sets up the panels within it.
	 * @param data
	 *            - The Event log of the program.
	 */
	public DecisionSupportPanel(EventLog data) {
		this.data = data;
		this.setPreferredSize(size);
		this.setSize(size);
		// Warning must be added in this order.
		manager = new DataManager(this.data);
		this.setLayout(new GridBagLayout());
		graphPanel = new GraphPanel("Temp", manager);
		displayPanel = new DisplayPanel(manager);
		selectPanel = new SelectPanel(manager);
		loadBarPanel = new LoadBarPanel(data);
		// Warning
		GridBagConstraints con = new GridBagConstraints();
		con.fill = GridBagConstraints.NONE;
		con.anchor = GridBagConstraints.NORTHWEST;
		con.weightx = 0.5;
		con.gridx = 0;
		con.gridy = 0;
		con.gridheight = 2;
		this.add(graphPanel, con);
		con.fill = GridBagConstraints.NONE;
		con.anchor = GridBagConstraints.NORTHEAST;
		con.weightx = 0.5;
		con.gridx = 1;
		con.gridy = 0;
		this.add(displayPanel, con);
		con.fill = GridBagConstraints.NONE;
		con.anchor = GridBagConstraints.SOUTHEAST;
		con.weightx = 0.5;
		con.gridx = 1;
		con.gridy = 1;
		this.add(selectPanel, con);
		con.fill = GridBagConstraints.NONE;
		con.anchor = GridBagConstraints.SOUTH;
		con.weightx = 0.5;
		con.gridx = 0;
		con.gridy = 3;
		con.gridwidth = 2;
		this.add(loadBarPanel, con);
		manager.updateGraph();
		manager.updateDisplay();
	}

	/**
	 * Gets the key listener for the movement of the
	 * decision panel.
	 * @return - Keylistener that pressing left and right effects the
	 * 				panel.
	 */
	public KeyListener getKeyListener() {
		return manager.getKeyListener();
	}

	/**
	 * This class manages the data and comunication between the other panels.
	 * @author Shane Brewer
	 *
	 */
	private class DataManager {
		private EventLog data;
		private List<JTextField> textFields;
		private BusinessEvent event;
		private JPanel graphPanel;
		// Thanks to david.
		private final DecimalFormat TEXTFORMAT = new DecimalFormat("$###,###,###.##");
		private ButtonGroup group;
		private Map<String, JFreeChart> graphMap = new HashMap<String, JFreeChart>();
		private Map<String, Dataset> datasetMap = new HashMap<String, Dataset>();

		/**
		 * Sets up the data manager with the event log.
		 * @param eventLog
		 */
		public DataManager(EventLog eventLog) {
			data = eventLog;
		}

		/**
		 * Makes a key listener for the decision support panel.
		 * @return - Returns the key listener for use on them main panel.
		 */
		public KeyListener getKeyListener() {
			return new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (DecisionSupportPanel.this.isShowing()) {
						if (e.getID() == KeyEvent.VK_RIGHT) {
							event = data.getFilterNextEvent();
							updateDisplay();
						}
						if (e.getID() == KeyEvent.VK_LEFT) {
							event = data.getFilterPrevEvent();
							updateDisplay();
						}
					}
				}

				@Override
				public void keyPressed(KeyEvent e) {
				}
			};
		}
		/**
		 * Sets up the event display.
		 * @param temp - is a list of jtext fields for modification.
		 */
		public void setupEventDisplay(List<JTextField> temp) {
			textFields = temp;
		}

		/**
		 * Sets up the graph panel display data.
		 * @param graphName - Name of the graph
		 * @param chart - the chart it is accociated to.
		 * @param panel - The panel that it is to be displayed on.
		 * @param dataset - The data set that is to be used.
		 */
		public void setupGraphDisplay(String graphName, JFreeChart chart, JPanel panel, Dataset dataset){
			graphPanel = panel;
			graphMap.put(graphName, chart);
			datasetMap.put(graphName, dataset);
		}

		/**
		 * Adds a button group to the panel to manage modifications.
		 * @param group - Button Group
		 */
		public void addButtonGroup(ButtonGroup group){
			this.group = group;
		}

		/**
		 * Updates the Graph panel depending on the current selected button group and graph avaliblity.
		 */
		public void updateGraph(){
			if (data.isFilterEmpty())
				return;
			if (data == null) return;

			switch (group.getSelection().getActionCommand()) {
			case typeString:
				updateTypeGraph(graphMap.get(typeString), (DefaultPieDataset)datasetMap.get(typeString));
				break;
			case transString:
				updateTransportGraph(graphMap.get(transString), (DefaultCategoryDataset)datasetMap.get(transString));
			default:
				break;
			}
		}

		/* All updates that invove a graph are updating the data from individual graphs. */
		private void updateTypeGraph(JFreeChart chart, DefaultPieDataset dataset){
			List<BusinessEvent>	events = data.getFilterListToCurrent();
			dataset.clear();
			for (BusinessEvent e : events){
				if (dataset.getKeys().contains(e.getType())){
					dataset.setValue(e.getType(), 1 + ( dataset.getValue(e.getType()).intValue()));
				}
				else {
					dataset.setValue(e.getType(), 1);
				}
			}
			graphPanel.paint(graphPanel.getGraphics());

		}

		/* All updates that invove a graph are updating the data from individual graphs. */
		private void updateTransportGraph(JFreeChart chart, DefaultCategoryDataset dataset){

		}

		/* All updates that invove a graph are updating the data from individual graphs. */
		private void updateDiscontinueGraph(){

		}

		/* All updates that invove a graph are updating the data from individual graphs. */
		private void updateMailGraph(){

		}

		/* All updates that invove a graph are updating the data from individual graphs. */
		private void updatePriceGraph(){

		}

		/**
		 * This updates the Display panel with using the text fields passed in eirler.
		 */
		public void updateDisplay() {
			if (data.isFilterEmpty())
				return;
			event = data.getFilterCurrentEvent();
			for (JTextField text : textFields) {
				text.setText(null);
			}
			if (event == null)
				return;
			textFields.get(0).setText("Name: ");
			textFields.get(1).setText(event.getType());
			if (event instanceof MailDeliveryEvent) {
				handleMailUpdate((MailDeliveryEvent) event);
			} else if (event instanceof TransportCostUpdateEvent) {
				handleCostUpdate((TransportCostUpdateEvent) event);
			} else if (event instanceof TransportDiscontinuedEvent) {
				handleDiscountinuedUpdate((TransportDiscontinuedEvent) event);
			} else if (event instanceof PriceUpdateEvent) {
				handlePriceUpdate((PriceUpdateEvent) event);
			}
			textFields.get(14).setText("Date: ");
			textFields.get(15).setText(
					new Date(event.getTimeLogged()).toString());
		}

		/*Modifys the text fields depending on event type.*/
		private void handlePriceUpdate(PriceUpdateEvent event) {
			textFields.get(2).setText("Desitination");
			textFields.get(3).setText(event.getDestination());
			textFields.get(4).setText("Volume Price");
			textFields.get(5).setText(TEXTFORMAT.format(event.getVolumePrice()/100));
			textFields.get(6).setText("Gram Price");
			textFields.get(7).setText(TEXTFORMAT.format(event.getGramPrice()/100));
			textFields.get(8).setText("Priority");
			textFields.get(9).setText(Priority.convertPriorityToString(event.getPriority()));
		}

		/*Modifys the text fields depending on event type.*/
		private void handleDiscountinuedUpdate(TransportDiscontinuedEvent event) {
			textFields.get(2).setText("Desitination");
			textFields.get(3).setText(event.getDestination());
			textFields.get(4).setText("Tansport Firm");
			textFields.get(5).setText(event.getTransportFirm());
			textFields.get(6).setText("Transport Type");
			textFields.get(7).setText(""+event.getTransportType());
		}

		/*Modifys the text fields depending on event type.*/
		private void handleCostUpdate(TransportCostUpdateEvent event) {
			textFields.get(2).setText("Desitination");
			textFields.get(3).setText(event.getDestination());
			textFields.get(4).setText("Volume Price");
			textFields.get(5).setText(TEXTFORMAT.format(event.getVolumePrice()/100));
			textFields.get(6).setText("Gram Price");
			textFields.get(7).setText(TEXTFORMAT.format(event.getGramPrice()/100));
			textFields.get(8).setText("Max Volume");
			textFields.get(9).setText("" + event.getMaxVolume()+"g");
			textFields.get(10).setText("Max Weight");
			textFields.get(11).setText("" + event.getMaxWeight()+"g");
			textFields.get(12).setText("Mail Transport Type");
			textFields.get(13).setText(""+ event.getTransportType());
		}

		/*Modifys the text fields depending on event type.*/
		private void handleMailUpdate(MailDeliveryEvent event) {
			textFields.get(2).setText("Desitination");
			textFields.get(3).setText(event.getDestination());
			textFields.get(4).setText("Volume");
			textFields.get(5).setText(event.getVolume() + "");
			textFields.get(6).setText("Weight");
			textFields.get(7).setText(event.getWeight() + "");
			textFields.get(8).setText("Day");
			textFields.get(9).setText(Day.convertDayToString(event.getDay()));
			textFields.get(10).setText("Revenue");
			textFields.get(11).setText(TEXTFORMAT.format(event.getRevenue()));
			textFields.get(12).setText("Expenditure");
			textFields.get(13).setText(TEXTFORMAT.format(event.getExpenditure()));

		}

		/**
		 * Passes a listner to be atached to the main buttons on the selecton panel.
		 * @return - Listner that listens for the next press.
		 */
		public ActionListener getNextLisener() {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					event = data.getFilterNextEvent();
					updateDisplay();
					updateGraph();
					loadBarPanel.paint(loadBarPanel.getGraphics());
				}
			};
		}

		/**
		 * Passes a listner to be atached to the main buttons on the selecton panel.
		 * @return - Listner that listens for the prev press.
		 */
		public ActionListener getPrevLisener() {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					event = data.getFilterPrevEvent();
					updateDisplay();
					updateGraph();
					loadBarPanel.paint(loadBarPanel.getGraphics());
				}
			};
		}

		/**
		 * Passes a listner to be atached to the main buttons on the selecton panel.
		 * @return - Listner that listens for the select of radio buttons.
		 */
		public ActionListener getRadioListener(){
			return new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JPanel p = new ChartPanel(graphMap.get(e.getActionCommand()));
					p.setBorder(new TitledBorder("Graph"));
					p.setPreferredSize(DecisionSupportPanel.this.graphPanel.sizeg);
					DecisionSupportPanel.this.graphPanel.remove(graphPanel);
					DecisionSupportPanel.this.graphPanel.add(p);
					switch (e.getActionCommand()) {
					case typeString:
						data.removeFilter();
						break;
					case transString:
						data.applyTransportCostUpdateFilter();
					case discString:
						data.applyTransportDiscontinuedFilter();
					case mailString:
						data.applyMailDeliveryFilter();
					case pricesString:
						data.applyPriceUpdateFilter();
					default:
						break;
					}
					graphPanel = p;
					data.resetFilterEventLogLocation();
					updateGraph();
				}
			};
		}
	}


	/**
	 * Panel for displaying of graph data.
	 * @author brewershan
	 *
	 */
	private class GraphPanel extends JPanel {
		private DataManager manager;
		public Dimension sizeg = new Dimension(size.width - size.width/3, size.height
				- size.height / 4 );

		public GraphPanel(String title, DataManager eventLog) {
			manager = eventLog;
			this.setPreferredSize(sizeg);
			this.setSize(sizeg);
			add(setupGraph(new DefaultPieDataset()));
		}

		/**
		 * Sets up the graph and adds it to a jpanel.
		 * It also adds it to the data manager class.
		 * @param dataset
		 * @return
		 */
		private JPanel setupGraph(DefaultPieDataset dataset) {
			Dataset temp = new DefaultCategoryDataset();
			JFreeChart chart = ChartFactory.createBarChart("Transport", "Destination", "Change Frequency", (DefaultCategoryDataset)temp);
			manager.setupGraphDisplay(transString, chart, null, temp);

			chart = ChartFactory.createPieChart("Amount of Events", dataset,
					true, true, false);
			PiePlot plot = (PiePlot) chart.getPlot();
			plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
			plot.setNoDataMessage("No data available");
			plot.setCircular(false);
			plot.setLabelGap(0.02);
			JPanel p = new ChartPanel(chart);
			p.setBorder(new TitledBorder("Graph"));
			manager.setupGraphDisplay(typeString, chart, p, dataset);
			p.setPreferredSize(sizeg);
			return p;
		}

	}

	/**
	 * Panel for displaying of event infromation.
	 * @author brewershan
	 *
	 */
	private class DisplayPanel extends JPanel {
		private DataManager manager;
		private Dimension sized = new Dimension(size.width / 3, size.height
				- size.height / 4 - size.height/3);
		private List<JTextField> textFields;

		public DisplayPanel(DataManager eventLog) {
			manager = eventLog;
			textFields = new ArrayList<JTextField>();
			manager.setupEventDisplay(textFields);
			this.setPreferredSize(sized);
			this.setSize(sized);
			textFieldSetup();
			this.setBorder(new TitledBorder("Event Details"));
		}

		/**
		 * Sets up the text fields
		 */
		private void textFieldSetup() {
			this.setLayout(new GridBagLayout());
			GridBagConstraints com = new GridBagConstraints();
			for (int i = 0; i < 17; i++) {
				textFields.add(makeTextField(i, com));
			}
		}

		/**
		 * Makes all the text firelds and adds them to the panel.
		 * Makes ure they are all in the correct grid bag layout.
		 * @param counter - number of componets.
		 * @param com - grid bag conraints.
		 * @return - the new text field
		 */
		private JTextField makeTextField(int counter, GridBagConstraints com) {
			JTextField field = new JTextField() {
				@Override
				public void setBorder(Border border) {
				}
			};
			field.setEditable(false);
			field.setFont(new Font("SansSerif", Font.PLAIN, 14));
			com.fill = GridBagConstraints.NONE;
			int temp = counter % 2;
			com.weightx = counter % 2 == 1 ? 0.5 : 0;
			com.weighty = counter % 2 == 1 ? 0.5 : 0;
			com.anchor = temp == 1 ? GridBagConstraints.NORTHEAST
					: GridBagConstraints.NORTHWEST;
			//com.insets = counter % 2 == 0 ? null : new Insets(10,0,0,0);  //top padding
			com.gridx = 0;//counter % 2;
			com.gridy = counter;//counter / 2;
			this.add(field, com);
			field.setMinimumSize(new Dimension(0, size.width / 4 - 10));
			return field;
		}
	}

	/**
	 * Selection Panel.
	 * @author Shane Brewer, 300289850
	 *
	 */
	private class SelectPanel extends JPanel {
		private DataManager manager;
		private Dimension sizeSelectPanel = new Dimension(size.width/3, size.height
				- size.height / 4 - size.height/2+50);

		public SelectPanel(DataManager eventLog) {
			manager = eventLog;
			this.setPreferredSize(sizeSelectPanel);
			this.setSize(sizeSelectPanel);
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBorder(new TitledBorder("Event Navigation"));
			buttonPanel.setLayout(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();
			buttonSetup(buttonPanel, constraints);
			JPanel radioPanel = new JPanel();
			radioPanel.setBorder(new TitledBorder("Event Filter"));
			radioPanel.setLayout(new GridBagLayout());
			radioSetup(radioPanel, constraints);
			this.setBorder(new TitledBorder("Selection Menu"));
		}

		/**
		 * Sets up the buttons and adds them to the
		 * panel and the data manager.
		 * @param panel - panel they are to added to.
		 * @param constraints - contraints to be followed
		 */
		public void buttonSetup(JPanel panel, GridBagConstraints constraints) {
			panel.setLayout(new GridBagLayout());
			GridBagConstraints con = new GridBagConstraints();
			JButton button = new JButton("Next");
			con.fill = GridBagConstraints.HORIZONTAL;
			con.gridx = 0;
			con.gridy = 0;
			button.addActionListener(manager.getNextLisener());
			panel.add(button, con);
			button = new JButton("Prev");
			con.fill = GridBagConstraints.HORIZONTAL;
			con.gridx = 1;
			button.addActionListener(manager.getPrevLisener());
			panel.add(button, con);
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.anchor = GridBagConstraints.NORTHWEST;
			constraints.gridx = 0;
			constraints.gridy = 0;
			this.add(panel, constraints);

		}

		/**
		 * Sets up the radio menu and adds it to the panel
		 * and passes it to the manager class.
		 * @param panel - panel it is to be added to
		 * @param constraints - constraints to be followed.
		 */
		public void radioSetup(JPanel panel, GridBagConstraints constraints){
			JRadioButton type = new JRadioButton("All Events by Type");
		    type.setActionCommand(typeString);
		    type.setSelected(true);

		    JRadioButton mailEvents = new JRadioButton("Mail Events only");
		    mailEvents.setActionCommand(mailString);

		    JRadioButton transport = new JRadioButton("Transport Events only");
		    transport.setActionCommand(transString);

		    JRadioButton discontinued = new JRadioButton("Transport Disconinted events only");
		    discontinued.setActionCommand(discString);

		    JRadioButton priceUpdate = new JRadioButton("Price Update events only");
		    priceUpdate.setActionCommand(pricesString);

		    ButtonGroup group = new ButtonGroup();
		    group.add(priceUpdate);
		    group.add(type);
		    group.add(transport);
		    group.add(mailEvents);
		    group.add(discontinued);
		    type.addActionListener(manager.getRadioListener());
		    priceUpdate.addActionListener(manager.getRadioListener());
		    transport.addActionListener(manager.getRadioListener());
		    mailEvents.addActionListener(manager.getRadioListener());
		    discontinued.addActionListener(manager.getRadioListener());
		    panel.setLayout(new GridBagLayout());
		    GridBagConstraints con = new GridBagConstraints();
		    manager.addButtonGroup(group);
			con.gridx = 0;
			con.gridy = 0;
			con.anchor= GridBagConstraints.WEST;
		    panel.add(type, con);
			con.gridy = 1;
		    panel.add(mailEvents, con);
			con.gridy = 2;
		    panel.add(transport, con);
			con.gridy = 3;
		    panel.add(discontinued, con);
			con.gridy = 4;
		    panel.add(priceUpdate, con);
			con.gridy = 5;
		    constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = 0;
			constraints.gridy = 1;
			this.add(panel, constraints);
		}
	}

	/**
	 * Load bar panel for displaying the load bar.
	 * @author Shane Brewer
	 *
	 */
	private class LoadBarPanel extends JPanel{
		private Dimension sizeb = new Dimension(size.width, size.height / 10);
		private EventLog log;

		public LoadBarPanel(EventLog log){
			this.log = log;
			this.setBorder(new TitledBorder("Loading Bar"));
			this.setPreferredSize(sizeb);
			this.setSize(sizeb);
			//System.out.println((int)(sizeb.width * (log.getPosition()/(log.getSize()+0.0))));
		}

		@Override
		public void print(Graphics g) {
			// TODO Auto-generated method stub
			super.print(g);
			g.setColor(Color.black);
			g.fillRect(10, 10, (int)(sizeb.width * (log.getPosition()/(log.getSize()+0.0))), sizeb.height);
		}

	}
}
