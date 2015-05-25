package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import kps.data.Node;
import kps.enums.Day;
import kps.enums.Priority;
import kps.ui.listener.PackageFormListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

public class PackageFormWindow extends AbstractFormWindow {

	public PackageFormWindow(PackageFormListener listener, List<Node> locations){
		super("enter package details");
		setLayout(new BorderLayout());

		// add fields
		Map<String, Object> fields = new HashMap<>();

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new SpringLayout());

		makeComboBox("day", Day.values(), fields, inputPanel);
		makeComboBox("from", locations.toArray(), fields, inputPanel);
		makeComboBox("to", locations.toArray(), fields, inputPanel);
		makeTextField("weight", fields, inputPanel);
		makeTextField("volume", fields, inputPanel);
		makeComboBox("priority", Priority.values(), fields, inputPanel);

		int fieldCount = fields.size();

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
			String weightStr = (String)fields.get("weight");
			String volStr = (String)fields.get("volume");
			if (!UIUtils.isDouble(weightStr, volStr)){
				numberFieldsPrompt("weight and volume should only contain digits");
				return;
			}

			Day day = (Day)fields.get("day");
			Node from = (Node)fields.get("from");
			double weight = Double.parseDouble(weightStr);
			double volume = Double.parseDouble(volStr);
			Priority priority = (Priority) fields.get("priority");

			listener.onPackageFormSubmitted(day, from, null, weight, volume, priority);
			UIUtils.closeWindow(this);
		});

		cancel.addActionListener((ActionEvent e) -> {
			listener.onCancel();
			UIUtils.closeWindow(PackageFormWindow.this);
		});

		// open window
		pack();
		setVisible(true);
	}

	public static void main(String args[]){
		new PackageFormWindow(new PackageFormListener(){
			@Override
			public void onPackageFormSubmitted(Day day, Node from, Node to, double weight, double volume, Priority priority){
				System.out.println("submitted: " + day + ", " + from + "... etc");
			}

			@Override
			public void onCancel(){
				System.out.println("Cancelled");
			}
		}, Arrays.asList(new Node[]{null, null}));
	}

}
