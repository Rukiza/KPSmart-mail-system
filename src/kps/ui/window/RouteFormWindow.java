package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import kps.enums.Day;
import kps.enums.Priority;
import kps.enums.TransportType;
import kps.ui.form.RouteFormListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

public class RouteFormWindow extends AbstractFormWindow {

	public RouteFormWindow(RouteFormListener listener){
		super("enter package details");
		setLayout(new BorderLayout());

		// add fields
		Map<String, Object> fields = new HashMap<>();
		String[] names = new String[] { "company", "to", "from", "type", "weight cost", "volume cost", "max weight",
				"max volume", "duration", "frequency", "priority", "day"};
		int fieldCount = names.length;

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new SpringLayout());

		makeTextField("company", fields, inputPanel);
		makeTextField("from", fields, inputPanel);
		makeTextField("to", fields, inputPanel);
		makeComboBox("type", TransportType.values(), fields, inputPanel);
		makeTextField("weight cost", fields, inputPanel);
		makeTextField("volume cost", fields, inputPanel);
		makeTextField("max weight", fields, inputPanel);
		makeTextField("max volume", fields, inputPanel);
		makeTextField("duration", fields, inputPanel);
		makeTextField("frequency", fields, inputPanel);
		makeComboBox("priority", Priority.values(), fields, inputPanel);
		makeComboBox("day", Day.values(), fields, inputPanel);

		SpringUtilities.makeCompactGrid(inputPanel,
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

		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// event handling
		OK.addActionListener((ActionEvent e) -> {
			if (!isFormComplete(fields.values())){
				completeFormPrompt();
				return;
			}
			// assumes form has been filled

			// check digit fields
			String weightCostStr = (String)fields.get("weight cost");
			String volCostStr = (String)fields.get("volume cost");
			String maxWeightStr = (String)fields.get("max weight");
			String maxVolStr = (String)fields.get("max volume");
			String durStr = (String)fields.get("duration");
			String freqStr = (String)fields.get("frequency");

			if (!UIUtils.isDouble(weightCostStr, volCostStr, maxWeightStr, maxVolStr, durStr, freqStr)){
				numberFieldsPrompt("some fields must only have digits");
				return;
			}

			String company = (String)fields.get("company");
			String to = (String)fields.get("to");
			String from = (String)fields.get("from");
			TransportType type = (TransportType)fields.get("type");
			double weightCost = Double.parseDouble(weightCostStr);
			double volCost = Double.parseDouble(volCostStr);
			double maxWeight = Double.parseDouble(maxWeightStr);
			double maxVol = Double.parseDouble(maxVolStr);
			double dur = Double.parseDouble(durStr);
			double freq = Double.parseDouble(freqStr);
			Priority priority = (Priority)fields.get("priority");
			Day day = (Day)fields.get("day");

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
			@Override
			public void onRouteFormSubmitted(String company, String to, String from, TransportType type, double weightCost, double volCost
					, double maxWeight, double maxVol, double dur, double freq, Priority priority, Day day){
				System.out.println("Got form");
			}
			public void onCancel(){
				System.out.println("cancelled");
			}
		});
	}

}
