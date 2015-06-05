package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import kps.ui.formlistener.DeleteUserEvent;
import kps.ui.formlistener.DeleteUserListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

/**
 * @author hardwiwill
 *
 * Prompts user for username of a user to be deleted from the system.
 * User input will be sent to DeleteUserListener once the form has been submitted
 */
public class DeleteUserWindow extends AbstractFormWindow {

	private final String USERNAME = "username";

	private final String[] fieldNames = new String[] { USERNAME };

	public DeleteUserWindow(DeleteUserListener listener) {
		super("Delete a user");

		setLayout(new BorderLayout());
		JPanel inputPanel = new JPanel();

		makeTextField(USERNAME, inputPanel);

		int fieldCount = fieldNames.length;

		inputPanel.setLayout(new SpringLayout());
		SpringUtilities.makeCompactGrid(inputPanel,
				fieldCount, 2,	//rows, cols
                6, 6,	//initX, initY
                6, 6);	//xPad, yPad)

		// add buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		JButton OK = new JButton("Delete");
		JButton cancel = new JButton("Cancel");
		buttonPanel.add(OK);
		buttonPanel.add(cancel);

		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// event handling
		OK.addActionListener((ActionEvent e) -> {
			String username = (String)fields.get(USERNAME);

			boolean result = listener.onUserSubmitted(new DeleteUserEvent(username));
			if (result) {
				UIUtils.closeWindow(this);
			}
			else {
				JOptionPane.showMessageDialog(this, "User doesn't exist");
			}
		});

		cancel.addActionListener((ActionEvent e) -> {
			listener.onCancel();
			UIUtils.closeWindow(this);
		});

		// open window
		pack();
		setVisible(true);
	}


	@Override
	protected boolean isFormComplete() {
		// TODO Auto-generated method stub
		return false;
	}

}
