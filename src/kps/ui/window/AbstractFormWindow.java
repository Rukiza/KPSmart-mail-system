package kps.ui.window;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author hardwiwill
 *
 * Contains boilerplate code for form windows
 */
public abstract class AbstractFormWindow extends JFrame {

	/**
	 * Stores input from the form
	 */
	protected Map<String, Object> fields = new HashMap<>();

	public AbstractFormWindow(String title){
		super(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // centers the frame
	}

	/**
	 * prompt the user to fill number fields correctly
	 */
	protected void numberFieldsPrompt(String message) {
		JOptionPane.showMessageDialog(this, message);

	}

	/**
	 * prompt the user to fill all fields
	 */
	protected void completeFormPrompt() {
		JOptionPane.showMessageDialog(this, "Please fill all fields");
	}

	/**
	 * @param fields
	 * @return whether the form is complete (all fields are filled)
	 */
	protected abstract boolean isFormComplete();

	/**
	 * Boilerplate code for creating a text field and adding it to a container
	 * @param name
	 * @param cont
	 * @return the text field created
	 */
	protected JTextField makeTextField(String name, Container cont) {
		JTextField textField = new JTextField();
		textField.getDocument().addDocumentListener(new DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
					fields.put(name, textField.getText());
                }
				@Override public void changedUpdate(DocumentEvent e) { }
                @Override public void removeUpdate(DocumentEvent e) { }
		});
		// put default text in fields
		fields.put(name, null);
		JLabel label = new JLabel(name);
		label.setLabelFor(textField);
		cont.add(label);
		cont.add(textField);

		return textField;
	}

	/**
	 * Boilerplate code for creating a combo box and adding it to a container
	 * @param name
	 * @param cont
	 * @return the combo box created
	 */
	protected JComboBox<Object> makeComboBox(String name, Object[] items, Container cont) {
		JComboBox<Object> combo = new JComboBox<>(items);
		combo.addActionListener((ActionEvent e) -> {
			comboBoxUpdated(combo, name);
		});
		// put default item in fields
		fields.put(name, combo.getSelectedItem());
		JLabel label = new JLabel(name);
		label.setLabelFor(combo);
		cont.add(label);
		cont.add(combo);
		return combo;
	}

	/**
	 * boilerplate code for when a combobox is updated
	 * @param comboBox
	 * @param name
	 */
	protected void comboBoxUpdated(JComboBox<Object> comboBox, String name){
			fields.put(name, comboBox.getSelectedItem());
	}
}
