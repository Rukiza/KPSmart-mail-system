package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import kps.data.Node;
import kps.enums.Day;
import kps.enums.Priority;
import kps.enums.TransportType;
import kps.ui.formlistener.DeleteRouteListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

public class DeleteRouteWindow extends AbstractFormWindow{

	private DeleteRouteListener listener;

	String[] fieldNames = new String[] { "company", "from", "to", "type" };

	public DeleteRouteWindow(DeleteRouteListener deleteRouteListener, List<Node> locations) {
		super("Delete a route");

		this.listener = deleteRouteListener;
		setLayout(new BorderLayout());

		// add fields
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new SpringLayout());

		makeTextField(fieldNames[0], inputPanel);
		makeComboBox(fieldNames[1], locations.toArray(), inputPanel);
		makeComboBox(fieldNames[2], locations.toArray(), inputPanel);
		makeComboBox(fieldNames[3], TransportType.values(), inputPanel);

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

			String company = (String) fields.get("company");
			String from = (String) fields.get("from").toString();
			String to = (String) fields.get("to").toString();
			TransportType type = (TransportType) fields.get("type");

			deleteRouteListener.onDeleteFormSubmitted(company, from, to, type);
			UIUtils.closeWindow(this);
		});

		cancel.addActionListener((ActionEvent e) -> {
			deleteRouteListener.onCancel();
			UIUtils.closeWindow(DeleteRouteWindow.this);
		});

		// open window
		pack();
		setVisible(true);

	}

	@Override
	protected void makeTextField(String name, Container cont) {
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
	}

	@Override
	protected void makeComboBox(String name, Object[] items, Container cont){
		super.makeComboBox(name, items, cont);
		// get the combobox as most recently added component
		JComboBox<Object> combobox = (JComboBox<Object>) cont.getComponent(cont.getComponentCount() - 1);
		combobox.addItemListener((ItemEvent e) -> {
                if (isFormComplete()) {
                        fireFormUpdate();
                }
		});
	}

	private void fireFormUpdate(){
		// TODO: reuse this code with form submit code
        String company = (String) fields.get("company");
        String from = (String) fields.get("from").toString();
        String to = (String) fields.get("to").toString();
        TransportType type = (TransportType) fields.get("type");

        listener.onCompletedFormUpdate(company, from, to, type);
	}

	@Override
	protected boolean isFormComplete() {
		for (Object field : fields.values()){
			if (field == null)
				return false;
		}
		return true;
	}

}
