package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;


import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


import kps.enums.Day;
import kps.enums.TransportType;
import kps.ui.formlistener.RouteFormListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

/**
 * @author hardwiwill
 *
 * Prompts the user for route details which will be used to make a new route.
 * Results of the form will be sent to the RouteFormListener
 */
public class RouteFormWindow extends AbstractFormWindow {

	public RouteFormWindow(RouteFormListener listener){
		super("enter package details");
		setLayout(new BorderLayout());

		// add fields
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new SpringLayout());

		makeTextField("company", inputPanel);
		makeTextField("from", inputPanel);
		makeTextField("to", inputPanel);
		makeComboBox("type", TransportType.values(), inputPanel);
		makeTextField("weight cost", inputPanel);
		makeTextField("volume cost",  inputPanel);
		makeTextField("max weight", inputPanel);
		makeTextField("max volume", inputPanel);
		makeTextField("duration", inputPanel);
		makeTextField("frequency", inputPanel);
		makeComboBox("day", Day.values(), inputPanel);

		// TODO: make this variable dynamic
		int fieldCount = 11;

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
			if (!isFormComplete()){
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

			if (!UIUtils.isDouble(weightCostStr, volCostStr)
					|| !UIUtils.isInteger(maxWeightStr, maxVolStr, durStr, freqStr)){
				numberFieldsPrompt("some fields must only have digits");
				return;
			}

			String company = (String)fields.get("company");
			String to = (String)fields.get("to");
			String from = (String)fields.get("from");
			TransportType type = (TransportType)fields.get("type");
			double weightCost = Double.parseDouble(weightCostStr);
			double volCost = Double.parseDouble(volCostStr);
			int maxWeight = Integer.parseInt(maxWeightStr);
			int maxVol = Integer.parseInt(maxVolStr);
			int dur = Integer.parseInt(durStr);
			int freq = Integer.parseInt(freqStr);
			Day day = (Day)fields.get("day");

			listener.onRouteFormSubmitted(company, to, from, type, weightCost, volCost
						, maxWeight, maxVol, dur, freq, day);
			JOptionPane.showMessageDialog(this, "Route successfully added!");
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

	@Override
	public boolean isFormComplete(){
		for (String key : fields.keySet()) {
			Object input = fields.get(key);
			if (input == null)
				return false;
		}
		return true;
	}

	public static void main(String[] args){
		new RouteFormWindow(new RouteFormListener(){
			@Override
			public void onRouteFormSubmitted(String company, String to, String from, TransportType type, double weightCost, double volCost
					, int maxWeight, int maxVol, int dur, int freq, Day day){
				System.out.println("Got form");
			}
			public void onCancel(){
				System.out.println("cancelled");
			}
		});
	}

}
