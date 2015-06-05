package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import kps.enums.Position;
import kps.ui.formlistener.CreateUserEvent;
import kps.ui.formlistener.CreateUserListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

/**
 * @author hardwiwill
 *
 * A popup window to prompt the user for details to give to a new user
 * When the users clicks on confirm button, the form details will be sent
 * to to CreateUserListener
 *
 */
public class CreateUserWindow extends AbstractFormWindow {

	private final String USERNAME = "username"
						, PASSWORD = "password"
						, POSITION = "position";

	private final String[] fieldNames = new String[] { USERNAME, PASSWORD, POSITION};

	public CreateUserWindow(CreateUserListener listener) {
		super("Create a user");

		setLayout(new BorderLayout());
		JPanel inputPanel = new JPanel();

		makeTextField(USERNAME, inputPanel);
		makePasswordField(PASSWORD, inputPanel);
		makeComboBox(POSITION, Position.values(), inputPanel);

		int fieldCount = fieldNames.length;

		inputPanel.setLayout(new SpringLayout());
		SpringUtilities.makeCompactGrid(inputPanel,
				fieldCount, 2,	//rows, cols
                6, 6,	//initX, initY
                6, 6);	//xPad, yPad)

		// add buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		JButton OK = new JButton("Create user");
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

	protected JPasswordField makePasswordField(String name, Container cont) {
		JPasswordField passwordField = new JPasswordField();
		passwordField.getDocument().addDocumentListener(new DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
					fields.put(name, passwordField.getPassword());
                }
				@Override public void changedUpdate(DocumentEvent e) { }
                @Override public void removeUpdate(DocumentEvent e) { }
		});
		passwordField.setEchoChar('~');

		// put default text in fields
		fields.put(name, null);
		JLabel label = new JLabel(name);
		label.setLabelFor(passwordField);
		cont.add(label);
		cont.add(passwordField);

		return passwordField;
	}

	@Override
	protected boolean isFormComplete() {
		// TODO Auto-generated method stub
		return false;
	}

}
