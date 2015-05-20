package kps.ui.window;

import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AbstractFormWindow extends JFrame{

	public AbstractFormWindow(String title){
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // centers the frame
	}

	/**
	 * prompt the user to fill number fields correctly
	 */
	protected void promptNumberFields(String message) {
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
	protected boolean formComplete(Collection<JTextField> fields) {
		for (JTextField f : fields){
			if (f.getText().isEmpty())
				return false;
		}
		return true;
	}

}
