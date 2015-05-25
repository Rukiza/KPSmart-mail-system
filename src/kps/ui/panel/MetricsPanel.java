package kps.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

import kps.data.wrappers.Metrics;
import kps.ui.util.SpringUtilities;

public class MetricsPanel extends JPanel implements ActionListener{

    private static final long serialVersionUID = 1L;

    // fields
    //private final int WIDTH = 660;
    //private final int HEIGHT = 520;

    // components
    private GraphPanel graph;
    private ProfitPanel profit;
    private BusinessEventPanel events;
    private CustomerRoutePanel routes;
    private boolean initialised = false;

    public MetricsPanel(){
        super();
        setPreferredSize(new Dimension(660, 520));
        // setup components
        graph = new GraphPanel(660, 290);
        graph.setBorder(new TitledBorder("Revenue and Expenditure"));
        profit = new ProfitPanel(260, 92);
        SpringUtilities.makeCompactGrid(profit, 3, 2, 6, 6, 6, 6);
        profit.setBorder(new TitledBorder("Income"));
        events = new BusinessEventPanel(260, 138);
        SpringUtilities.makeCompactGrid(events, 5, 2, 6, 6, 6, 6);
        events.setBorder(new TitledBorder("Business Events"));
        routes = new CustomerRoutePanel(400, 230, this);
        routes.setBorder(new TitledBorder("Customer Routes"));
        SpringUtilities.makeCompactGrid(events, 2, 2, 6, 6, 6, 6);
        layoutComponents();
        initialised = true;
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

    public void repaintMetrics(Metrics metrics){
    	// update graph panel

    	// update profit panel
    	profit.setProfitMetrics(metrics.getTotalRevenue(), metrics.getTotalExpenditure());
    	// update events panel
    	int mail = metrics.getTotalMailDeliveryEvents();
    	int price = metrics.getTotalPriceUpdateEvents();
    	int cost = metrics.getTotalTransportCostUpdateEvents();
    	int discontinued = metrics.getTotalTransportDiscontinuedEvents();
    	int total = metrics.getTotalBusinessEvents();
    	events.setBusinessEventMetrics(mail, price, cost, discontinued, total);
    	// update customer route panel
    	repaint();
    }

    public void repaint(){
    	if(initialised){
    		graph.repaint();
    		profit.repaint();
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
    			// TEMPORARY
    			metrics.addMailDeliveryEvent(67, 40);
    			repaintMetrics(metrics);
    		}
    	}
    }

    private static Metrics metrics;

    public static void main(String[] args){
    	JFrame frame = new JFrame();
    	MetricsPanel mp = new MetricsPanel();
    	frame.add(mp);

    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.pack();
    	frame.setVisible(true);

    	metrics = new Metrics();
    	mp.update(metrics);

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

        private double[] revenue = {5, 7, 8, 15, 19, 30, 34, 51};
        private double[] expenditure = {2, 3, 11, 14, 21, 25, 31, 42};

        private final int X = 60;
        private final int Y = 40;

        public GraphPanel(int width, int height){
            super(width, height);
        }

        public void paintComponent(Graphics g){
            g.setColor(Color.WHITE);
            g.fillRect(X, Y, getWidth() - (X * 2), getHeight() - (Y * 2));
            drawAxis(g);
            drawGraph(g);
        }

        public void drawAxis(Graphics g){
            g.setColor(Color.BLACK);
            g.drawLine(X, Y, X, getHeight() - Y);
            g.drawLine(X, getHeight() - Y, getWidth() - X, getHeight() - Y);
        }

        public void drawGraph(Graphics g){
            int width = getWidth();
            int height = getHeight();
            int run = (width - (X * 2)) / revenue.length;
            double rise = (height - (Y * 2)) / Math.max(revenue[revenue.length - 1], expenditure[expenditure.length - 1]);
            int x = X;
            int revY = height - Y;
            int expY = height - Y;
            for(int i = 0; i < revenue.length; i++){
                g.setColor(Color.BLUE);
                g.drawLine(x, revY, x + run, height - Y - (int)(revenue[i] * rise));
                g.setColor(Color.RED);
                g.drawLine(x, expY, x + run, height - Y - (int)(expenditure[i] * rise));
                x += run;
                revY = height - Y - (int)(revenue[i] * rise);
                expY = height - Y - (int)(expenditure[i] * rise);
            }
        }

    }

    private class ProfitPanel extends MetricComponent{

        private static final long serialVersionUID = 1L;

        private final String[] TYPES = {"Total Revenue", "Total Expenditure", "Total Profit"};
        private JLabel revenue = new JLabel();
        private JLabel expenditure = new JLabel();
        private JLabel profit = new JLabel();
        private final JLabel[] LABELS = {revenue, expenditure, profit};
        private final DecimalFormat FORMAT = new DecimalFormat("$###,###,###.##");

        public ProfitPanel(int width, int height){
            super(width, height);
            setLayout(new SpringLayout());
            for(int i = 0; i < LABELS.length; i++){
                JLabel label = new JLabel(TYPES[i]+": ");
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
        private JLabel mail = new JLabel();
        private JLabel price = new JLabel();
        private JLabel cost = new JLabel();
        private JLabel discontinued = new JLabel();
        private JLabel total = new JLabel();
        private final JLabel[] LABELS = {mail, price, cost, discontinued, total};

        public BusinessEventPanel(int width, int height){
            super(width, height);
            setLayout(new SpringLayout());
            for(int i = 0; i < LABELS.length; i++){
                JLabel label = new JLabel(TYPES[i]+": ");
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
            add(setupOptionsPanel());
            JButton button = new JButton("Generate Metrics");
            button.addActionListener(listener);
            add(button);
            add(setupMetricsPanel());
        }

        public JPanel setupOptionsPanel(){
        	JLabel originLabel = new JLabel("Origin: ");
            origin = new JComboBox<String>();
            originLabel.setLabelFor(origin);
            JLabel destLabel = new JLabel("Destination: ");
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
        	mail.setBorder(new TitledBorder("Total Amounts"));
        	AverageTimesPanel times = new AverageTimesPanel((getWidth() / 2) - 11);
        	SpringUtilities.makeCompactGrid(times, 2, 2, 6, 6, 6, 6);
        	times.setBorder(new TitledBorder("AverageTimes"));

        	JPanel metrics = new JPanel();
        	metrics.add(mail);
        	metrics.add(times);
        	return metrics;
        }

        private class TotalMailPanel extends JPanel{

			private static final long serialVersionUID = 1L;

			// fields
			private final String[] TYPES = {"Weight", "Volume", "Amount"};
        	private JLabel weight = new JLabel();
        	private JLabel volume = new JLabel();
        	private JLabel amount = new JLabel();
        	private final JLabel[] LABELS = {weight, volume, amount};


        	public TotalMailPanel(int width){
        		super();
        		setPreferredSize(new Dimension(width, 126));

        		setLayout(new SpringLayout());
        		for(int i = 0; i < TYPES.length; i++){
        			JLabel label = new JLabel(TYPES[i]+": ");
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
        	private JLabel airTime = new JLabel();
        	private JLabel standardTime = new JLabel();
        	private final JLabel[] LABELS = {airTime, standardTime};

        	public AverageTimesPanel(int width){
        		super();
        		setPreferredSize(new Dimension(width, 126));

        		setLayout(new SpringLayout());
        		for(int i = 0; i < TYPES.length; i++){
        			JLabel label = new JLabel(TYPES[i]+": ");
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
}