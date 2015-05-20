package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import kps.ui.listener.PackageFormListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;


public class PackageFormWindow extends AbstractFormWindow{

	public PackageFormWindow(PackageFormListener listener){

		super("enter package details");

		setLayout(new BorderLayout());

		// add fields
		Map<String, JTextField> fields = new HashMap<>();
		String[] names = new String[] { "day", "from", "weight", "volume", "priority" };
		int fieldCount = names.length;

		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new SpringLayout());

		for (String name : names){
			JLabel l = new JLabel(name);
			JTextField field = new JTextField();
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
			String weightStr = fields.get("weight").getText();
			String volStr = fields.get("volume").getText();
			if (!UIUtils.isDouble(weightStr, volStr)){
				promptNumberFields("weight and volume should only contain digits");
				return;
			}

			String day = fields.get("day").getText();
			String from = fields.get("from").getText();
			double weight = Double.parseDouble(weightStr);
			double volume = Double.parseDouble(volStr);
			String priority = fields.get("priority").getText();

			listener.onPackageFormSubmitted(day, from, weight, volume, priority);
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

	public static void main(String args[]){
		new PackageFormWindow(new PackageFormListener(){
			@Override
			public void onPackageFormSubmitted(String day, String from, double weight, double volume, String priority){
				System.out.println("submitted: " + day + ", " + from + "... etc");
			}

			@Override
			public void onCancel(){
				System.out.println("Cancelled");
			}
		});
	}

}
