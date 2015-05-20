package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import kps.ui.listener.PackageFormListener;
import kps.ui.listener.RouteFormListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

public class RouteFormWindow extends AbstractFormWindow{

	public RouteFormWindow(RouteFormListener listener){
		super("enter package details");
		setLayout(new BorderLayout());

		// add fields
		Map<String, JTextField> fields = new HashMap<>();
		String[] names = new String[] { "company", "to", "from", "type", "weight cost", "volume cost", "max weight",
				"max volume", "duration", "frequency", "priority", "day"};
		int fieldCount = names.length;

		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new SpringLayout());

		for (String name : names){
			JLabel l = new JLabel(name);
			JTextField field = new JTextField();
			field.setPreferredSize(new Dimension(150, 15));
			l.setLabelFor(field);
			fields.put(name, field);
			fieldPanel.add(l);
			fieldPanel.add(field);
		}

		SpringUtilities.makeCompactGrid(fieldPanel,
				fieldCount, 2,	//rows, cols
                6, 6,	//initX, initY
                6, 6);	//xPad, yPad)

		// add buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		JButton OK = new JButton("OK");
		JButton cancel = new JButton("Cancel");
		buttonPanel.add(OK);
		buttonPanel.add(cancel);

		add(fieldPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// event handling
		OK.addActionListener((ActionEvent e) -> {
			if (!formComplete(fields.values())){
				completeFormPrompt();
				return;
			}
			// assumes form has been filled

			// check digit fields
			String weightCostStr = fields.get("weight cost").getText();
			String volCostStr = fields.get("volume cost").getText();
			String maxWeightStr = fields.get("max weight").getText();
			String maxVolStr = fields.get("max volume").getText();
			String durStr = fields.get("duration").getText();
			String freqStr = fields.get("frequency").getText();

			if (!UIUtils.isDouble(weightCostStr, volCostStr, maxWeightStr, maxVolStr, durStr, freqStr)){
				promptNumberFields("some fields must only have digits");
				return;
			}

			String company = fields.get("company").getText();
			String to = fields.get("to").getText();
			String from = fields.get("from").getText();
			String type = fields.get("type").getText();
			double weightCost = Double.parseDouble(weightCostStr);
			double volCost = Double.parseDouble(volCostStr);
			double maxWeight = Double.parseDouble(maxWeightStr);
			double maxVol = Double.parseDouble(maxVolStr);
			double dur = Double.parseDouble(durStr);
			double freq = Double.parseDouble(freqStr);
			String priority = fields.get("priority").getText();
			String day = fields.get("day").getText();

			listener.onRouteFormSubmitted(company, to, from, type, weightCost, volCost
						, maxWeight, maxVol, dur, freq, priority, day);
			UIUtils.closeWindow(this);
		});

		cancel.addActionListener((ActionEvent e) -> {
			UIUtils.closeWindow(this);
			listener.onCancel();
		});

		// open window
		pack();
		setVisible(true);
	}

	public static void main(String[] args){
		new RouteFormWindow(new RouteFormListener(){
			public void onRouteFormSubmitted(String company, String to, String from, String type, double weightCost, double volCost
					, double maxWeight, double maxVol, double dur, double freq, String priority, String day){
				System.out.println("Got form");
			}
			public void onCancel(){
				System.out.println("cancelled");
			}
		});
	}

}
