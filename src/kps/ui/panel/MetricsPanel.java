package kps.ui.panel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import kps.data.wrappers.Metrics;
import kps.enums.Priority;
import kps.ui.util.SpringUtilities;

public class MetricsPanel extends JPanel implements ActionListener{

    private static final long serialVersionUID = 1L;

    // field
    private Metrics metrics;
    private boolean isInitialised = false; // to prevent repainting of non-initialised components

    // components
    private GraphPanel graph;
    private ProfitPanel profit;
    private BusinessEventPanel events;
    private CustomerRoutePanel routes;

    /**
     * Constructs a new MetricsPanel Object with the specified metrics.
     * Sets up all of the components of the panel.
     *
     * @param metrics
     * 		-- metrics data to be displayed
     */
    public MetricsPanel(Metrics metrics){
        super();
        setPreferredSize(new Dimension(910, 730));
        this.metrics = metrics;
        // setup components
        graph = new GraphPanel(910, 428);
        //graph.setBorder(new TitledKPSBorder("Revenue and Expenditure"));
        profit = new ProfitPanel(392, 125);
        SpringUtilities.makeCompactGrid(profit, 3, 2, 6, 6, 6, 6);
        profit.setBorder(new TitledKPSBorder("Income"));
        events = new BusinessEventPanel(392, 177);
        SpringUtilities.makeCompactGrid(events, 5, 2, 6, 6, 6, 6);
        events.setBorder(new TitledKPSBorder("Business Events"));
        routes = new CustomerRoutePanel(518, 302, this);
        routes.setBorder(new TitledKPSBorder("Customer Routes"));
        SpringUtilities.makeCompactGrid(events, 2, 2, 6, 6, 6, 6);
        layoutComponents();
        isInitialised = true;
        repaint();
    }

    /**
     * Lays out the components in this MetricsPanel using
     * the GridBagLayout.
     */
    private void layoutComponents(){
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // graph panel
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 8;
        constraints.gridheight = 5;
        add(graph, constraints);
        // profit panel
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 4;
        constraints.gridheight = 1;
        add(profit, constraints);
        // business event panel
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 4;
        constraints.gridheight = 2;
        add(events, constraints);
        // customer route panel
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 4;
        constraints.gridy = 5;
        constraints.gridwidth = 4;
        constraints.gridheight = 3;
        add(routes, constraints);
    }

    public void repaint(){
    	if(isInitialised){
    		graph.repaint();
    		profit.setProfitMetrics(metrics.getTotalRevenue(), metrics.getTotalExpenditure());
    		profit.repaint();
    		int mail = metrics.getTotalMailDeliveryEvents();
        	int price = metrics.getTotalPriceUpdateEvents();
        	int cost = metrics.getTotalTransportCostUpdateEvents();
        	int discontinued = metrics.getTotalTransportDiscontinuedEvents();
        	int total = metrics.getTotalBusinessEvents();
        	events.setBusinessEventMetrics(mail, price, cost, discontinued, total);
    		events.repaint();
    		routes.repaint();
    	}
    }

    public void update(Metrics metrics){
    	profit.setProfitMetrics(metrics.getTotalRevenue(), metrics.getTotalExpenditure());
    	int mailEvents = metrics.getTotalMailDeliveryEvents();
    	int priceEvents = metrics.getTotalPriceUpdateEvents();
    	int costEvents = metrics.getTotalTransportCostUpdateEvents();
    	int discontinuedEvents = metrics.getTotalTransportDiscontinuedEvents();
    	int totalEvents = metrics.getTotalBusinessEvents();
    	events.setBusinessEventMetrics(mailEvents, priceEvents, costEvents, discontinuedEvents, totalEvents);
    }

    public void actionPerformed(ActionEvent event){
    	if(event.getSource() instanceof JButton){
    		String button = ((JButton)event.getSource()).getText();
    		if(button.equals("Generate Metrics") && isInitialised){
    			routes.repaint();
    		}
    	}
    }

    /**
     * Private abstract class for all of the sub components of MetricsPanel.
     * Contains the width and height for the sub component.
     *
     * @author David Sheridan
     *
     */
    private abstract class MetricComponent extends JPanel{

        private static final long serialVersionUID = 1L;

        // fields
        private int width;
        private int height;

        /**
         * Constructs a new MetricComponent Object with the specified
         * width and height.
         *
         * @param width
         * 		-- width of component
         * @param height
         * 		-- height of component
         */
        public MetricComponent(int width, int height){
            super();
            setPreferredSize(width, height);
        }

        /**
         * Sets the preferred size of this component to the specified
         * width and height.
         *
         * @param width
         * 		-- preferred width
         * @param height
         * 		-- preferred height
         */
        public void setPreferredSize(int width, int height){
            this.width = width;
            this.height = height;
            super.setPreferredSize(new Dimension(this.width, this.height));
        }

        /**
         * Returns the width of this component.
         */
        public int getWidth(){
            return width;
        }

        /**
         * Returns the height of this component.
         */
        public int getHeight(){
            return height;
        }
    }

    /**
     * Private class that is used to draw the graph showing the progression
     * of revenue and expenditure for the company.
     *
     * @author David Sheridan
     *
     */
    private class GraphPanel extends MetricComponent{

        private static final long serialVersionUID = 1L;

        // fields
        private JFreeChart chart;

        // components
        private ChartPanel chartPanel;

        /**
         * Constructs a new GraphPanel Object with the specified
         * width and height.
         *
         * @param width
         * 		-- width of component
         * @param height
         * 		-- height of component
         */
        public GraphPanel(int width, int height){
            super(width, height);
            chart = ChartFactory.createXYLineChart("Revenue and Expenditure", "Mail Deliveries", "Money (NZD)", createDataset());
            chartPanel = new ChartPanel(chart);
            add(chartPanel);
        }

        /**
         * Paints the graph to the GraphPanel.
         */
        public void paintComponent(Graphics g){

        }

        /**
         * Creates the data set used to display the revenue and
         * expenditure data on the graph.
         *
         * @return data set
         */
        private XYDataset createDataset(){
        	XYSeries revenue = new XYSeries("Revenue");
        	XYSeries expenditure = new XYSeries("Expenditure");
        	List<Double> revenueData = metrics.getAllRevenue();
        	List<Double> expenditureData = metrics.getAllExpenditure();
        	// initalise start values to zero
        	double currentRevenue = 0;
        	double currentExpenditure = 0;
        	revenue.add(0, currentRevenue);
        	expenditure.add(0, currentExpenditure);
        	// add revenue and expenditure data from each mail delivery event
        	for(int i = 0; i < revenueData.size(); i++){
        		currentRevenue += revenueData.get(i);
        		currentExpenditure += expenditureData.get(i);
        		revenue.add(i + 1, currentRevenue);
        		expenditure.add(i + 1, currentExpenditure);
        	}
        	XYSeriesCollection data = new XYSeriesCollection();
        	data.addSeries(revenue);
        	data.addSeries(expenditure);
        	return data;
        }
    }

    /**
     * Private class that is used to display the total revenue and expenditure
     * data from the system, as well as the profit that has been made.
     *
     * @author David Sheridan
     *
     */
    private class ProfitPanel extends MetricComponent{

        private static final long serialVersionUID = 1L;

        // fields
        private final String[] TYPES = {"Total Revenue", "Total Expenditure", "Total Profit"};
        private KPSLabel revenue = new KPSLabel();
        private KPSLabel expenditure = new KPSLabel();
        private KPSLabel profit = new KPSLabel();
        private final KPSLabel[] LABELS = {revenue, expenditure, profit};
        private final DecimalFormat NZD_FORMAT = new DecimalFormat("$###,###,###.##");

        /**
         * Constructs a new ProfitPanel Object with the specified
         * width and height.
         *
         * @param width
         * 		-- width of component
         * @param height
         * 		-- height of component
         */
        public ProfitPanel(int width, int height){
            super(width, height);
            setLayout(new SpringLayout());
            for(int i = 0; i < LABELS.length; i++){
                KPSLabel label = new KPSLabel(TYPES[i]+": ");
                label.setLabelFor(LABELS[i]);
                add(label);
                add(LABELS[i]);
            }
        }

        /**
         * Set the revenue and expenditure values to the specified values.
         * Profit is calculated from (revenue - expenditure)
         *
         * @param revenue
         * 		-- new revenue value
         * @param expenditure
         * 		-- new expenditure value
         */
        public void setProfitMetrics(double revenue, double expenditure){
        	this.revenue.setText(NZD_FORMAT.format(revenue));
        	this.expenditure.setText(NZD_FORMAT.format(expenditure));
        	this.profit.setText(NZD_FORMAT.format(revenue - expenditure));
        }
    }

    /**
     * Private class that is used to display the total number of MailDeliveryEvents,
     * PriceUpdateEvents, TransportCostUpdateEvents and TransportDiscontinuedEvents.
     *
     * @author David Sheridan
     *
     */
    private class BusinessEventPanel extends MetricComponent{

        private static final long serialVersionUID = 1L;

        // fields
        private final String[] TYPES = {"Mail Delivery", "Price Update", "Transport Cost Update", "Transport Discontinued", "Total"};
        private KPSLabel mail = new KPSLabel();
        private KPSLabel price = new KPSLabel();
        private KPSLabel cost = new KPSLabel();
        private KPSLabel discontinued = new KPSLabel();
        private KPSLabel total = new KPSLabel();
        private final KPSLabel[] LABELS = {mail, price, cost, discontinued, total};
        private final NumberFormat NUMBER_FORMAT = NumberFormat.getIntegerInstance();

        /**
         * Constructs a new BusinessEventPanel Object with the specified
         * width and height.
         *
         * @param width
         * 		-- width of component
         * @param height
         * 		-- height of component
         */
        public BusinessEventPanel(int width, int height){
            super(width, height);
            setLayout(new SpringLayout());
            for(int i = 0; i < LABELS.length; i++){
                KPSLabel label = new KPSLabel(TYPES[i]+": ");
                label.setLabelFor(LABELS[i]);
                add(label);
                add(LABELS[i]);
            }
        }

        /**
         * Sets the counts displayed for all the business events currently
         * in the system.
         *
         * @param mail
         * 		-- mail delivery event count
         * @param price
         * 		-- price update event count
         * @param cost
         * 		-- transport cost update event count
         * @param discontinued
         * 		-- transport discontinued event count
         * @param total
         * 		-- total business event count
         */
        public void setBusinessEventMetrics(int mail, int price, int cost, int discontinued, int total){
        	this.mail.setText(NUMBER_FORMAT.format(mail));
        	this.price.setText(NUMBER_FORMAT.format(price));
        	this.cost.setText(NUMBER_FORMAT.format(cost));
        	this.discontinued.setText(NUMBER_FORMAT.format(discontinued));
        	this.total.setText(NUMBER_FORMAT.format(total));
        }

    }

    private class CustomerRoutePanel extends MetricComponent{

        private static final long serialVersionUID = 1L;

        private JComboBox<String> origins;
        private JComboBox<String> destinations;

        // components
        private TotalMailPanel mail;
        private AverageTimesPanel times;

        public CustomerRoutePanel(int width, int height, ActionListener listener){
            super(width, height);
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            add(setupOptionsPanel());
            KPSButton button = new KPSButton("Generate Metrics");
            button.setAlignmentX(JButton.CENTER_ALIGNMENT);
            button.addActionListener(listener);
            add(button);
            add(setupMetricsPanel());
        }

        public JPanel setupOptionsPanel(){
        	KPSLabel originLabel = new KPSLabel("Origin: ");
            origins = new JComboBox<String>(metrics.getOrigins());
            originLabel.setLabelFor(origins);
            KPSLabel destLabel = new KPSLabel("Destination: ");
            destinations = new JComboBox<String>(metrics.getDestinations());
            destLabel.setLabelFor(destinations);
        	JPanel options = new JPanel();
        	options.add(originLabel);
        	options.add(origins);
        	options.add(destLabel);
        	options.add(destinations);
        	return options;
        }

        public JPanel setupMetricsPanel(){
        	mail = new TotalMailPanel((getWidth() / 2) - 11);
        	SpringUtilities.makeCompactGrid(mail, 3, 2, 6, 6, 6, 6);
        	mail.setBorder(new TitledKPSBorder("Total Amounts"));
        	times = new AverageTimesPanel((getWidth() / 2) - 11);
        	SpringUtilities.makeCompactGrid(times, 2, 2, 6, 6, 6, 6);
        	times.setBorder(new TitledKPSBorder("AverageTimes"));

        	JPanel metrics = new JPanel();
        	metrics.add(mail);
        	metrics.add(times);
        	return metrics;
        }

        public void repaint(){
        	if(isInitialised){
        		String origin = (String)origins.getSelectedItem();
        		String destination = (String)destinations.getSelectedItem();
        		mail.setWeight(metrics.getTotalMailWeight(origin, destination));
        		mail.setVolume(metrics.getTotalMailVolume(origin, destination));
        		mail.setAmount(metrics.getTotalMailAmount(origin, destination));
        		times.setAirTime(metrics.getAverageDeliveryTime(origin, destination, Priority.INTERNATIONAL_AIR));
        		times.setStandardTime(metrics.getAverageDeliveryTime(origin, destination, Priority.INTERNATIONAL_STANDARD));
        		super.repaint();
        	}

        }

        /**
         * Private class that is used to display the total weight, volume and amount
         * of mail sent via the customer route specified by the CustomerRoutePanel.
         *
         * @author David Sheridan
         *
         */
        private class TotalMailPanel extends JPanel{

			private static final long serialVersionUID = 1L;

			// fields
			private final String[] TYPES = {"Weight", "Volume", "Amount"};
        	private KPSLabel weight = new KPSLabel();
        	private KPSLabel volume = new KPSLabel();
        	private KPSLabel amount = new KPSLabel();
        	private final KPSLabel[] LABELS = {weight, volume, amount};
        	private final NumberFormat NUMBER_FORMAT = NumberFormat.getIntegerInstance();

        	/**
        	 * Constructs a new TotalMailPanel Object with the specified
        	 * width.
        	 *
        	 * @param width
        	 * 		-- width of component
        	 */
        	public TotalMailPanel(int width){
        		super();
        		setPreferredSize(new Dimension(width, 254));

        		setLayout(new SpringLayout());
        		for(int i = 0; i < TYPES.length; i++){
        			KPSLabel label = new KPSLabel(TYPES[i]+": ");
        			label.setLabelFor(LABELS[i]);
        			add(label);
        			add(LABELS[i]);
        		}
        	}

        	/**
        	 * Set the weight currently displayed by the panel to the
        	 * specified weight.
        	 *
        	 * @param weight
        	 * 		-- weight to display
        	 */
        	public void setWeight(int weight){
        		this.weight.setText(NUMBER_FORMAT.format(weight)+" g");
        	}

        	/**
        	 * Set the volume currently displayed by the panel to the
        	 * specified volume.
        	 *
        	 * @param volume
        	 * 		-- volume to display
        	 */
        	public void setVolume(int volume){
        		this.volume.setText(NUMBER_FORMAT.format(volume)+" cm^3");

        	}

        	/**
        	 * Set the amount currently displayed by the panel to the
        	 * specified amount.
        	 *
        	 * @param amount
        	 * 		-- amount to display
        	 */
        	public void setAmount(int amount){
        		this.amount.setText(NUMBER_FORMAT.format(amount));
        	}
        }

        private class AverageTimesPanel extends JPanel{

        	// fields
        	private final String[] TYPES = {"Air", "Std"};
        	private KPSLabel airTime = new KPSLabel();
        	private KPSLabel standardTime = new KPSLabel();
        	private final KPSLabel[] LABELS = {airTime, standardTime};
        	private final NumberFormat NUMBER_FORMAT = NumberFormat.getIntegerInstance();

        	public AverageTimesPanel(int width){
        		super();
        		setPreferredSize(new Dimension(width, 254));

        		setLayout(new SpringLayout());
        		for(int i = 0; i < TYPES.length; i++){
        			KPSLabel label = new KPSLabel(TYPES[i]+": ");
        			label.setLabelFor(LABELS[i]);
        			add(label);
        			add(LABELS[i]);
        		}
        	}

        	public void setAirTime(double airTime){
        		this.airTime.setText(NUMBER_FORMAT.format(airTime)+" hours");
        	}

        	public void setStandardTime(double standardTime){
        		this.standardTime.setText(NUMBER_FORMAT.format(standardTime)+" hours");
        	}
        }
    }

    // Layout components

    /**
     * Private class which determines how the TitledBorders look in the MetricsPanel
     * and all of its sub components.
     *
     * @author David Sheridan
     *
     */
    private class TitledKPSBorder extends TitledBorder{

		private static final long serialVersionUID = 1L;

		/**
		 * Font to display the title
		 */
		private final Font FONT = new Font(Font.DIALOG, Font.BOLD, 22);

		/**
		 * Constructs a new TitledKPSBorder Object which displays
		 * the specified title.
		 *
		 * @param title
		 * 		-- title to display
		 */
    	public TitledKPSBorder(String title){
    		super(title);
    		setTitleFont(FONT);
    		setTitleJustification(TitledBorder.CENTER);
    		setTitlePosition(TitledBorder.CENTER);
    	}
    }

    /**
     * Private class which determines how the JLabels look in the MetricsPanel and
     * all of its sub components.
     *
     * @author David Sheridan
     *
     */
    private class KPSLabel extends JLabel{

		private static final long serialVersionUID = 1L;

		/**
		 * Font to display the JLabel text.
		 */
		private final Font FONT = new Font(Font.DIALOG, Font.BOLD, 18);

		/**
		 * Constructs a new KPSLabel Object which displays the
		 * specified label.
		 *
		 * @param label
		 * 		-- label to display
		 */
    	public KPSLabel(String label){
    		super(label);
    		setupKPSLabel();
    	}

    	/**
    	 * Constructs a new KPSLabel Object with no label
    	 * to display.
    	 */
    	public KPSLabel(){
    		super();
    		setupKPSLabel();
    	}

    	/**
    	 * Private method that sets the font of this KPSLabel
    	 * to the correct font.
    	 */
    	private void setupKPSLabel(){
    		setFont(FONT);
    	}
    }

    /**
     * Private class which determines how JButtons look on the MetricsPanel
     * and all of its sub components.
     *
     * @author David Sheridan
     *
     */
    private class KPSButton extends JButton{

		private static final long serialVersionUID = 1L;

		/**
		 * Font to display the JButton text.
		 */
		private final Font FONT = new Font(Font.DIALOG, Font.BOLD, 20);

		/**
		 * Constructs a new KPSButton Object which displays
		 * the specified text.
		 *
		 * @param text
		 * 		-- text to display
		 */
    	public KPSButton(String text){
    		super(text);
    		setFont(FONT);
    	}
    }
}