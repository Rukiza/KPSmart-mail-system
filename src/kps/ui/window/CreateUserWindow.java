package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import kps.enums.Position;
import kps.ui.formlistener.CreateUserEvent;
import kps.ui.formlistener.CreateUserListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

public class CreateUserWindow extends AbstractFormWindow {

	private final String USERNAME = "username"
						, PASSWORD = "password"
						, POSITION = "position";

	private final String[] fieldNames = new String[] { USERNAME, PASSWORD, POSITION};

	public CreateUserWindow(CreateUserListener listener) {
		super("Create a user");

		JPanel inputPanel = new JPanel();

		makeTextField(USERNAME, inputPanel);
		makeTextField(PASSWORD, inputPanel);
		makeComboBox(POSITION, Position.values(), inputPanel);

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
			String username = (String)fields.get(USERNAME);
			int passwordHash = ((String)fields.get(PASSWORD)).hashCode();
			Position position = (Position) fields.get(POSITION);

			boolean result = listener.onUserSubmitted(new CreateUserEvent(username, passwordHash, position));
			if (result) {
				UIUtils.closeWindow(this);
			}
			else {
				JOptionPane.showMessageDialog(this, "User already exists");
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
