package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import kps.data.Node;
import kps.enums.Day;
import kps.enums.Priority;
import kps.ui.formlistener.PackageFormListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

/**
 * @author hardwiwill
 *
 * A popup window to prompt the user for package details, to submit a package
 * into the system.
 * When the users clicks on confirm button, the form details will be sent
 * to a PackageFormListener
 *
 */
public class PackageFormWindow extends AbstractFormWindow {

	private PackageFormListener listener;

	private String[] fieldNames = new String[] { "day", "from", "to", "priority", "weight", "volume" };

	public PackageFormWindow(PackageFormListener packageFormListener, List<Node> locations){
		super("Enter package details");
		this.listener = packageFormListener;
		setLayout(new BorderLayout());

		// add fields
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new SpringLayout());

		makeComboBox(fieldNames[0], Day.values(), inputPanel);
		makeComboBox(fieldNames[1], locations.toArray(), inputPanel);
		makeComboBox(fieldNames[2], locations.toArray(), inputPanel);
		makeComboBox(fieldNames[3], Priority.values(), inputPanel);
		makeTextField(fieldNames[4], inputPanel);
		makeTextField(fieldNames[5], inputPanel);

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
			String weightStr = (String)fields.get("weight");
			String volStr = (String)fields.get("volume");
			if (!UIUtils.isInteger(weightStr, volStr)){
				numberFieldsPrompt("weight and volume should only contain digits");
				return;
			}

			Day day = (Day)fields.get("day");
			String from = (String) fields.get("from").toString();
			String to = (String) fields.get("to").toString();
			int weight = Integer.parseInt(weightStr);
			int volume = Integer.parseInt(volStr);
			Priority priority = (Priority) fields.get("priority");

			String message = packageFormListener.onPackageFormSubmitted(day, from, to, weight, volume, priority);
			if (message == null){
                UIUtils.closeWindow(this);
				JOptionPane.showMessageDialog(this, "Package submitted successfully");
			} else {
				JOptionPane.showMessageDialog(this, message);
			}
		});

		cancel.addActionListener((ActionEvent e) -> {
			packageFormListener.onCancel();
			UIUtils.closeWindow(PackageFormWindow.this);
		});

		// open window
		pack();
		setVisible(true);
	}

	@Override
	protected JTextField makeTextField(String name, Container cont) {
		super.makeTextField(name, cont);
		// get the text field that was just added by the super method
		JTextField textField = (JTextField) cont.getComponent(cont.getComponentCount() - 1);
		textField.getDocument().addDocumentListener(new DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
					fields.put(name, textField.getText());
					// listen if all fields are complete
					if (isFormComplete()) {
						fireFormUpdate();
					}
                }
				@Override public void changedUpdate(DocumentEvent e) { }
                @Override public void removeUpdate(DocumentEvent e) {
                	fields.put(name, textField.getText());
					if (isFormComplete()) {
						fireFormUpdate();
					}
                }
		});
		return textField;
	}

	@Override
	protected JComboBox<Object> makeComboBox(String name, Object[] items, Container cont){
		JComboBox<Object> combobox = super.makeComboBox(name, items, cont);
		// get the combobox as most recently added component
		combobox.addItemListener((ItemEvent e) -> {
                if (isFormComplete()) {
                        fireFormUpdate();
                }
		});
		return combobox;
	}

	private void fireFormUpdate(){
		// TODO: reuse this code with form submit code
        // check digit fields
        String weightStr = (String)fields.get("weight");
        String volStr = (String)fields.get("volume");
        if (!UIUtils.isInteger(weightStr, volStr)){
                return;
        }

        Day day = (Day)fields.get("day");

        String from = (String)fields.get("from").toString();
        String to = (String) fields.get("to").toString();
        int weight = Integer.parseInt(weightStr);
        int volume = Integer.parseInt(volStr);
        Priority priority = (Priority) fields.get("priority");

        listener.onCompletedFormUpdate(day, from, to, priority, weight, volume);
	}

	protected boolean isFormComplete(){
		for (String fieldName : fieldNames){
			if (fields.get(fieldName) == null){
				return false;
			}
		}
		return true;
	}

	public static void main(String args[]){
		new PackageFormWindow(new PackageFormListener(){
			@Override
			public String onPackageFormSubmitted(Day day, String from, String to, int weight, int volume, Priority priority){
				System.out.println("submitted: " + day + ", " + from + "... etc");
				return "success";
			}
			@Override public void onCompletedFormUpdate(Day day, String from, String to, Priority priority, int weight, int volume){
				System.out.println("updated: " + day + ", " + from + "... etc");
			}

			@Override
			public void onCancel(){
				System.out.println("Cancelled");
			}
		}, Arrays.asList(new Node[]{null, null}));
	}
}
