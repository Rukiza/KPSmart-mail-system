package kps.ui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import kps.Main;
import kps.data.wrappers.EventLog;
import kps.events.BusinessEvent;
import kps.events.MailDeliveryEvent;
import kps.events.PriceUpdateEvent;
import kps.events.TransportCostUpdateEvent;
import kps.events.TransportDiscontinuedEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


/**
 * @author Shane Brewer
 *
 */
public class DecisionSupportPanel extends JPanel{

	private EventLog data;
	private LogManager manager;
	private GraphPanel graphPanel;
	private DisplayPanel displayPanel;
	private SelectPanel selectPanel;
	private Dimension size = new Dimension(600, 600);
	



	/**
	 * @param data - The Event log of the program.
	 */
	public DecisionSupportPanel(EventLog data){
		this.data = data;
		this.setPreferredSize(size);
		this.setSize(size);
		manager = new LogManager(this.data);
		this.setLayout(new GridBagLayout());
		graphPanel = new GraphPanel("Temp",manager);
		displayPanel = new DisplayPanel(manager);
		selectPanel = new SelectPanel(manager);
		GridBagConstraints con = new GridBagConstraints();
		con.fill = GridBagConstraints.NONE;
		con.weightx = 0.5;
		con.gridx = 0;
		con.gridy = 0;
		this.add(graphPanel, con);
		con.fill = GridBagConstraints.NONE;
		con.weightx = 0.5;
		con.gridx = 1;
		con.gridy = 0;
		this.add(displayPanel, con);
		con.fill = GridBagConstraints.NONE;
		con.anchor = GridBagConstraints.PAGE_END;
		con.gridx = 0;
		con.gridy = 1;
		con.gridwidth = 2;
		this.add(selectPanel, con);		
		
	}
	
	public KeyListener getKeyListener(){
		return manager.getKeyListener();
	}

	private class LogManager{
		private EventLog data;
		private List<JTextField> textFields;
		private BusinessEvent event;
		
		public LogManager(EventLog eventLog){
			data = eventLog;
		}
		
		public KeyListener getKeyListener() {
			return new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {}
				
				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getID() == KeyEvent.VK_RIGHT){
						event = data.getNextEvent();
						updateDisplay();
					}
					if (e.getID() == KeyEvent.VK_LEFT){
						event = data.getPrevEvent();
						updateDisplay();
					}
				}
				
				@Override
				public void keyPressed(KeyEvent e) {}
			};
		}

		public void setupEventDisplay(List<JTextField> temp){
			textFields = temp;
		}
		
		public void updateDisplay(){
			event = data.getCurrentEvent();
			for (JTextField text : textFields){
				text.setText(null);
			}
			textFields.get(0).setText("Name: ");
			textFields.get(1).setText(event.getType());
			if (event instanceof MailDeliveryEvent){
				handleMailUpdate((MailDeliveryEvent) event);
			}
			else if (event instanceof TransportCostUpdateEvent){
				handleCostUpdate((TransportCostUpdateEvent) event);
			}
			else if (event instanceof TransportDiscontinuedEvent){
				handleDiscountinuedUpdate((TransportDiscontinuedEvent) event);
			}
			else if (event instanceof PriceUpdateEvent){
				handlePriceUpdate((PriceUpdateEvent) event);
			}
			textFields.get(12).setText("Date: ");
			textFields.get(13).setText(new Date(event.getTimeLogged()).toString());
		}

		private void handlePriceUpdate(PriceUpdateEvent event) {
			textFields.get(2).setText("Desitination");
			textFields.get(3).setText(event.getDestination());
			textFields.get(4).setText("Volume Price");
			textFields.get(5).setText("$"+event.getVolumePrice());
			textFields.get(6).setText("Gram Price");
			textFields.get(7).setText("$"+event.getGramPrice());
			textFields.get(8).setText("Priority");
			textFields.get(9).setText(""+event.getPriority());
		}

		private void handleDiscountinuedUpdate(TransportDiscontinuedEvent event) {
			textFields.get(2).setText("Desitination");
			textFields.get(3).setText(event.getDestination());
			textFields.get(4).setText("Tansport Firm");
			textFields.get(5).setText(event.getTransportFirm());
			textFields.get(6).setText("Transport Type");
			textFields.get(7).setText(""+event.getTransportType());
			
		}

		private void handleCostUpdate(TransportCostUpdateEvent event) {
			textFields.get(2).setText("Desitination");
			textFields.get(3).setText(event.getDestination());
			textFields.get(4).setText("Volume Price");
			textFields.get(5).setText("$"+event.getVolumePrice());
			textFields.get(6).setText("Gram Price");
			textFields.get(7).setText("$"+event.getGramPrice());
			textFields.get(8).setText("Max Volume");
			textFields.get(9).setText(""+event.getMaxVolume());
			textFields.get(10).setText("Max Weight");
			textFields.get(11).setText(""+event.getMaxWeight());
			textFields.get(12).setText("Mail Transport");
		}

		private void handleMailUpdate(MailDeliveryEvent event) {
			textFields.get(2).setText("Desitination");
			textFields.get(3).setText(event.getDestination());
			textFields.get(4).setText("Volume");
			textFields.get(5).setText(event.getVolume()+"");
			textFields.get(6).setText("Weight");
			textFields.get(7).setText(event.getWeight()+"");
			textFields.get(8).setText("Day");
			textFields.get(9).setText(""+event.getDay());
		}

		public ActionListener getNextLisener() {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					event = data.getNextEvent();
					updateDisplay();
				}
			};
		}

		public ActionListener getPrevLisener() {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					event = data.getPrevEvent();
					updateDisplay();
				}
			};
		}
	}

	private class GraphPanel extends JPanel{
		private LogManager data;
		private Dimension sizeg  = new Dimension(size.width/2, size.height - size.height/4);
		public GraphPanel (String title, LogManager eventLog){
			data = eventLog;
			this.setPreferredSize(sizeg);
			this.setSize(sizeg);
			add(setupGraph(setupDataset()));
		}
		
		private PieDataset setupDataset(){
			DefaultPieDataset dataset = new DefaultPieDataset();
			dataset.setValue("One", new Double(43.2));
			return dataset;
		}
		
		private JPanel setupGraph(PieDataset dataset){
			JFreeChart chart = ChartFactory.createPieChart(
					"Temp", 
					dataset,
					false,
					true,
					false
					);
			PiePlot plot = (PiePlot) chart.getPlot();
			plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
	        plot.setNoDataMessage("No data available");
	        plot.setCircular(false);
	        plot.setLabelGap(0.02);
	        JPanel p = new ChartPanel(chart);
	        p.setPreferredSize(sizeg);
	        return p;
		}
		
	}
	
	private class DisplayPanel extends JPanel{
		private LogManager data;
		private Dimension sized  = new Dimension(size.width/2, size.height - size.height/4);
		private List<JTextField> textFields;
		
		public DisplayPanel (LogManager eventLog){
			data = eventLog;
			textFields = new ArrayList<JTextField>();
			data.setupEventDisplay(textFields);
			this.setPreferredSize(sized);
			this.setSize(sized);
			textFieldSetup();
		}
		
		private void textFieldSetup(){
			this.setLayout(new GridBagLayout());
			GridBagConstraints com = new GridBagConstraints();
			for (int i = 0; i < 14; i++){
				textFields.add(makeTextField(i, com));
			}
			data.updateDisplay();
		}
		
		private JTextField makeTextField(int counter, GridBagConstraints com){
			JTextField field = new JTextField() {
				@Override
				public void setBorder(Border border) {}
			};
			field.setEditable(false);
			com.fill = GridBagConstraints.NONE;
			int temp = counter%2;
			com.anchor = temp == 1 ? GridBagConstraints.NORTHEAST : GridBagConstraints.NORTHWEST;
			com.gridx = counter%2;
			com.gridy = counter/2;
			this.add(field, com);
			field.setMinimumSize(new Dimension(0, size.width/4-10));
			return field;
		}
	}
	
	private class SelectPanel extends JPanel{
		private LogManager data;
		private Dimension sizes  = new Dimension(size.width, size.height/10);
		
		public SelectPanel (LogManager eventLog){
			data = eventLog;
			this.setPreferredSize(sizes);
			this.setSize(sizes);
			buttonSetup();
		}
		
		public void buttonSetup(){
			this.setLayout(new GridBagLayout());
			GridBagConstraints con = new GridBagConstraints();
			JButton button = new JButton("Next");
			con.fill = GridBagConstraints.HORIZONTAL;
			con.gridx = 0;
			con.gridy = 0;
			button.addActionListener(data.getNextLisener());
			this.add(button, con);
			button = new JButton("Prev");
			con.fill = GridBagConstraints.HORIZONTAL;
			con.gridx = 1;
			con.gridy = 0;
			button.addActionListener(data.getPrevLisener());
			this.add(button, con);
		}
	}

	public static void main(String[] arg){
		JFrame frame = new JFrame();
		frame.setSize(1200, 900);
		DecisionSupportPanel support = null;
		try {
			support = new DecisionSupportPanel(new EventLog(KPSParser.parseFile(Main.filename)));
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.add(support, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}


