package kps.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
import kps.ui.util.SpringUtilities;

public class MetricsPanel extends JPanel implements ActionListener{

    private static final long serialVersionUID = 1L;

    // field
    private Metrics metrics;

    // components
    private GraphPanel graph;
    private ProfitPanel profit;
    private BusinessEventPanel events;
    private CustomerRoutePanel routes;
    private boolean initialised = false;

    public MetricsPanel(Metrics metrics){
        super();
        setPreferredSize(new Dimension(910, 730));
        this.metrics = metrics;
        // setup components
        graph = new GraphPanel(910, 428);//new GraphPanel(660, 290);
        //graph.setBorder(new TitledKPSBorder("Revenue and Expenditure"));
        profit = new ProfitPanel(392, 125);//(260, 92);
        SpringUtilities.makeCompactGrid(profit, 3, 2, 6, 6, 6, 6);
        profit.setBorder(new TitledKPSBorder("Income"));
        events = new BusinessEventPanel(392, 177);//(260, 138);
        SpringUtilities.makeCompactGrid(events, 5, 2, 6, 6, 6, 6);
        events.setBorder(new TitledKPSBorder("Business Events"));
        routes = new CustomerRoutePanel(518, 302, this);//(400, 230, this);
        routes.setBorder(new TitledKPSBorder("Customer Routes"));
        SpringUtilities.makeCompactGrid(events, 2, 2, 6, 6, 6, 6);
        layoutComponents();
        initialised = true;
        repaint();
    }

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
    	if(initialised){
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
    		if(button.equals("Generate Metrics")){

    		}
    	}
    }

    private abstract class MetricComponent extends JPanel{

        private static final long serialVersionUID = 1L;

        private int width;
        private int height;

        public MetricComponent(int width, int height){
            super();
            setPreferredSize(width, height);
        }

        public void setPreferredSize(int width, int height){
            this.width = width;
            this.height = height;
            super.setPreferredSize(new Dimension(this.width, this.height));
        }

        public int getWidth(){
            return width;
        }

        public int getHeight(){
            return height;
        }
    }

    private class GraphPanel extends MetricComponent{

        private static final long serialVersionUID = 1L;

        private final int WIDTH;
        private final int HEIGHT;

        public GraphPanel(int width, int height){
            super(width, height);
            WIDTH = width;
            HEIGHT = height;
        }

        public void paintComponent(Graphics g){
        	JFreeChart chart = ChartFactory.createXYLineChart("Revenue and Expenditure", "Mail Deliveries", "Money (NZD)", createDataset());
        	ChartPanel panel = new ChartPanel(chart);
        	panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        	add(panel);
        }

        private XYDataset createDataset(){
        	XYSeries revenue = new XYSeries("Revenue");
        	XYSeries expenditure = new XYSeries("Expenditure");
        	List<Double> revenueData = metrics.getAllRevenue();
        	List<Double> expenditureData = metrics.getAllExpenditure();
        	double currentRevenue = 0;
        	double currentExpenditure = 0;
        	revenue.add(0, currentRevenue);
        	expenditure.add(0, currentExpenditure);
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

    private class ProfitPanel extends MetricComponent{

        private static final long serialVersionUID = 1L;

        private final String[] TYPES = {"Total Revenue", "Total Expenditure", "Total Profit"};
        private KPSLabel revenue = new KPSLabel();
        private KPSLabel expenditure = new KPSLabel();
        private KPSLabel profit = new KPSLabel();
        private final KPSLabel[] LABELS = {revenue, expenditure, profit};
        private final DecimalFormat FORMAT = new DecimalFormat("$###,###,###.##");

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

        public void setProfitMetrics(double revenue, double expenditure){
        	this.revenue.setText(FORMAT.format(revenue));
        	this.expenditure.setText(FORMAT.format(expenditure));
        	this.profit.setText(FORMAT.format(revenue - expenditure));
        }
    }

    private class BusinessEventPanel extends MetricComponent{

        private static final long serialVersionUID = 1L;

        private final String[] TYPES = {"Mail Delivery", "Price Update", "Transport Cost Update", "Transport Discontinued", "Total"};
        private KPSLabel mail = new KPSLabel();
        private KPSLabel price = new KPSLabel();
        private KPSLabel cost = new KPSLabel();
        private KPSLabel discontinued = new KPSLabel();
        private KPSLabel total = new KPSLabel();
        private final KPSLabel[] LABELS = {mail, price, cost, discontinued, total};

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

        public void setBusinessEventMetrics(int mail, int price, int cost, int discontinued, int total){
        	this.mail.setText(Integer.toString(mail));
        	this.price.setText(Integer.toString(price));
        	this.cost.setText(Integer.toString(cost));
        	this.discontinued.setText(Integer.toString(discontinued));
        	this.total.setText(Integer.toString(total));
        }

    }

    private class CustomerRoutePanel extends MetricComponent{

        private static final long serialVersionUID = 1L;

        private JComboBox<String> origin;
        private JComboBox<String> destination;

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
            origin = new JComboBox<String>();
            originLabel.setLabelFor(origin);
            KPSLabel destLabel = new KPSLabel("Destination: ");
            destination = new JComboBox<String>();
            destLabel.setLabelFor(destination);
        	JPanel options = new JPanel();
        	options.add(originLabel);
        	options.add(origin);
        	options.add(destLabel);
        	options.add(destination);
        	return options;
        }

        public JPanel setupMetricsPanel(){
        	TotalMailPanel mail = new TotalMailPanel((getWidth() / 2) - 11);
        	SpringUtilities.makeCompactGrid(mail, 3, 2, 6, 6, 6, 6);
        	mail.setBorder(new TitledKPSBorder("Total Amounts"));
        	AverageTimesPanel times = new AverageTimesPanel((getWidth() / 2) - 11);
        	SpringUtilities.makeCompactGrid(times, 2, 2, 6, 6, 6, 6);
        	times.setBorder(new TitledKPSBorder("AverageTimes"));

        	JPanel metrics = new JPanel();
        	metrics.add(mail);
        	metrics.add(times);
        	return metrics;
        }

        private class TotalMailPanel extends JPanel{

			private static final long serialVersionUID = 1L;

			// fields
			private final String[] TYPES = {"Weight", "Volume", "Amount"};
        	private KPSLabel weight = new KPSLabel();
        	private KPSLabel volume = new KPSLabel();
        	private KPSLabel amount = new KPSLabel();
        	private final KPSLabel[] LABELS = {weight, volume, amount};


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

        	public void setWeight(int weight){
        		this.weight.setText(weight+" g");
        	}

        	public void setVolume(int volume){
        		this.volume.setText(volume+" cm3");

        	}

        	public void setAmount(int amount){
        		this.amount.setText(""+amount);
        	}
        }

        private class AverageTimesPanel extends JPanel{

        	// fields
        	private final String[] TYPES = {"Air", "Std"};
        	private KPSLabel airTime = new KPSLabel();
        	private KPSLabel standardTime = new KPSLabel();
        	private final KPSLabel[] LABELS = {airTime, standardTime};

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
        		this.airTime.setText(airTime+" hours");
        	}

        	public void setStandardTime(double standardTime){
        		this.standardTime.setText(standardTime+" hours");
        	}
        }
    }

    // Layout components

    private class TitledKPSBorder extends TitledBorder{

		private static final long serialVersionUID = 1L;

		private final Font FONT = new Font(Font.DIALOG, Font.BOLD, 22);

    	public TitledKPSBorder(String title){
    		super(title);
    		setTitleFont(FONT);
    		setTitleJustification(TitledBorder.CENTER);
    		setTitlePosition(TitledBorder.CENTER);
    	}
    }

    private class KPSLabel extends JLabel{

		private static final long serialVersionUID = 1L;

		private final Font FONT = new Font(Font.DIALOG, Font.BOLD, 18);

    	public KPSLabel(String label){
    		super(label);
    		setupKPSLabel();
    	}

    	public KPSLabel(){
    		super();
    		setupKPSLabel();
    	}

    	private void setupKPSLabel(){
    		setFont(FONT);
    	}
    }

    private class KPSButton extends JButton{

		private static final long serialVersionUID = 1L;

		private final Font FONT = new Font(Font.DIALOG, Font.BOLD, 20);

    	public KPSButton(String text){
    		super(text);
    		setFont(FONT);
    	}
    }
}