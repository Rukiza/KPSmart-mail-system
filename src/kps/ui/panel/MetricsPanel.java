package kps.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

import kps.ui.util.SpringUtilities;

public class MetricsPanel extends JPanel{

    private static final long serialVersionUID = 1L;

    // fields
    //private final int WIDTH = 660;
    //private final int HEIGHT = 520;

    // components
    private GraphPanel graph;
    private ProfitPanel profit;
    private BusinessEventPanel events;
    private CustomerRoutePanel routes;

    public MetricsPanel(){
        super();

        System.out.println(super.WIDTH);
        System.out.println(super.HEIGHT);

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
        routes = new CustomerRoutePanel(400, 230);
        routes.setBorder(new TitledBorder("Customer Routes"));
        SpringUtilities.makeCompactGrid(events, 2, 2, 6, 6, 6, 6);
        layoutComponents();
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

    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Test");
        frame.add(new MetricsPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
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

        private final String[] LABELS = {"Total Revenue", "Total Expenditure", "Total Profit"};
        private double[] metrics = {1000000000, 1234565, -1657.87};

        public ProfitPanel(int width, int height){
            super(width, height);
            setLayout(new SpringLayout());
            DecimalFormat format = new DecimalFormat("$###,###,###.##");
            for(int i = 0; i < LABELS.length; i++){
                JLabel label = new JLabel(LABELS[i]+": ");
                JLabel metric = new JLabel(format.format(metrics[i]));
                label.setLabelFor(metric);
                add(label);
                add(metric);
            }
        }
    }

    private class BusinessEventPanel extends MetricComponent{

        private static final long serialVersionUID = 1L;

        private final String[] LABELS = {"Mail Delivery", "Price Update", "Transport Cost Update", "Transport Discontinued", "Total"};
        private int[] counts = {0, 0, 0, 0, 0};

        public BusinessEventPanel(int width, int height){
            super(width, height);
            setLayout(new SpringLayout());
            for(int i = 0; i < LABELS.length; i++){
                JLabel label = new JLabel(LABELS[i]+": ");
                JLabel metric = new JLabel(""+counts[i]);
                label.setLabelFor(metric);
                add(label);
                add(metric);
            }
        }

    }

    private class CustomerRoutePanel extends MetricComponent{

        private static final long serialVersionUID = 1L;

        private JComboBox<String> origin;
        private JComboBox<String> destination;

        public CustomerRoutePanel(int width, int height){
            super(width, height);
            add(setupOptionsPanel());
            add(new JButton("Generate Metrics"));
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
        	private JLabel weight;
        	private JLabel volume;
        	private JLabel amount;

        	public TotalMailPanel(int width){
        		super();
        		setPreferredSize(new Dimension(width, 126));

        		setLayout(new SpringLayout());
        		JLabel weightLabel = new JLabel("Weight: ");
        		weight = new JLabel();
        		weightLabel.setLabelFor(weight);
        		JLabel volumeLabel = new JLabel("Volume: ");
        		volume = new JLabel();
        		volumeLabel.setLabelFor(volume);
        		JLabel amountLabel = new JLabel("Amount: ");
        		amount = new JLabel();
        		amountLabel.setLabelFor(amount);
        		add(weightLabel);
        		add(weight);
        		add(volumeLabel);
        		add(volume);
        		add(amountLabel);
        		add(amount);
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
        	private JLabel airTime;
        	private JLabel standardTime;

        	public AverageTimesPanel(int width){
        		super();
        		setPreferredSize(new Dimension(width, 126));

        		setLayout(new SpringLayout());
        		JLabel airLabel = new JLabel("Air: ");
        		airTime = new JLabel();
        		airLabel.setLabelFor(airTime);
        		JLabel standardLabel = new JLabel("Std: ");
        		standardTime = new JLabel();
        		standardLabel.setLabelFor(standardTime);
        		add(airLabel);
        		add(airTime);
        		add(standardLabel);
        		add(standardTime);
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