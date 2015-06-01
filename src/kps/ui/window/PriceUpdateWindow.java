package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import kps.data.Node;
import kps.enums.Priority;
import kps.ui.formlistener.PriceUpdateListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

public class PriceUpdateWindow extends AbstractFormWindow {

	private String[] fieldNames = new String[] { "from", "to", "priority", "weight cost", "volume cost" };

	public PriceUpdateWindow(PriceUpdateListener listener, List<Node> locations){

		super("Update route price");
		setLayout(new BorderLayout());

		// add fields
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new SpringLayout());

		makeComboBox(fieldNames[0], locations.toArray(), inputPanel);
		makeComboBox(fieldNames[1], locations.toArray(), inputPanel);
		makeComboBox(fieldNames[2], Priority.values(), inputPanel);
		makeTextField(fieldNames[3], inputPanel);
		makeTextField(fieldNames[4], inputPanel);

		int fieldCount = fieldNames.length;

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
			// check digit fields
			String weightStr = (String)fields.get("weight cost");
			String volStr = (String)fields.get("volume cost");
			if (!UIUtils.isInteger(weightStr, volStr)){
				numberFieldsPrompt("weight and volume should only contain digits");
				return;
			}

			String from = (String) fields.get("from").toString();
			String to = (String) fields.get("to").toString();
			int weightCost = Integer.parseInt(weightStr);
			int volumeCost = Integer.parseInt(volStr);
			Priority priority = (Priority) fields.get("priority");

			listener.onPriceUpdateSubmitted(from, to, priority, weightCost, volumeCost);
			UIUtils.closeWindow(this);
		});

		cancel.addActionListener((ActionEvent e) -> {
			listener.onCancel();
			UIUtils.closeWindow(PriceUpdateWindow.this);
		});

		// open window
		pack();
		setVisible(true);
	}

	@Override
	protected boolean isFormComplete() {
		for (Object value : fields.values()){
			if (value == null)
				return false;
		}
		return true;
	}
}