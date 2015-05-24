package kps.ui.window;

import java.awt.Container;
import java.awt.event.ItemEvent;
import java.util.Collection;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AbstractFormWindow extends JFrame {

	public AbstractFormWindow(String title){
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	protected boolean isFormComplete(Collection<Object> fields) {
		for (Object o : fields){
			if (o == null)
				return false;
		}
		return true;
	}

	protected void makeTextField(String name, Map<String, Object> fields, Container cont) {
		JTextField textField = new JTextField();
		textField.getDocument().addDocumentListener(new DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
					fields.put(name, textField.getText());
                }
				@Override public void changedUpdate(DocumentEvent e) { }
                @Override public void removeUpdate(DocumentEvent e) { }
		});
		JLabel label = new JLabel(name);
		label.setLabelFor(textField);
		cont.add(label);
		cont.add(textField);
	}

	protected void makeComboBox(String name, Object[] values,
			Map<String, Object> fields, Container cont) {
		JComboBox<Object> combo = new JComboBox<>(values);
		combo.addItemListener((ItemEvent e) -> {
			fields.put(name, combo.getSelectedItem());
		});
		JLabel label = new JLabel(name);
		label.setLabelFor(combo);
		cont.add(label);
		cont.add(combo);
	}

}
