package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


import kps.ui.formlistener.AuthDetailsListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

/**
 * @author hardwiwill
 *
 * A popup window which prompts a user for a username and password.
 * Form inputs will be sent to the AuthDetailsListener
 */
public class LogonBox extends JFrame {

	public LogonBox(AuthDetailsListener listener, String defaultUN, String defaultPW){
		super("Login");
		setLocationRelativeTo(null); // centers the frame
		setLayout(new BorderLayout());

		//set up components
		JLabel usernameLabel = new JLabel("Username:");
		JLabel passwordLabel = new JLabel("Password:");
		JTextField usernameField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		JButton loginButton = new JButton("Login");
		JButton cancelButton = new JButton("Cancel");
		JPanel fields = new JPanel();
		fields.setLayout(new SpringLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());

		usernameField.setText(defaultUN);
		passwordField.setText(defaultPW);
		usernameLabel.setLabelFor(usernameField);
		passwordLabel.setLabelFor(passwordField);
		passwordField.setEchoChar('~');

		fields.add(usernameLabel);
		fields.add(usernameField);
		fields.add(passwordLabel);
		fields.add(passwordField);
		buttons.add(loginButton);
		buttons.add(cancelButton);

		add(fields, BorderLayout.NORTH);
		add(buttons, BorderLayout.SOUTH);

		// puts inputs in a grid
		SpringUtilities.makeCompactGrid(fields,
										2, 2,	//rows, cols
						                6, 6,	//initX, initY
						                6, 6);	//xPad, yPad)

		// event handling
		loginButton.addActionListener((ActionEvent e) -> {
			boolean result = listener.onReceivedAuthDetails(
				usernameField.getText(),
				new String(passwordField.getPassword())
			);

			if (!result){
				JOptionPane.showMessageDialog(this, "username & password combination was not recognised");
			} else {
				UIUtils.closeWindow(this);
			}
		});
		cancelButton.addActionListener((ActionEvent e) -> {
			listener.onCancel();
			UIUtils.closeWindow(this);
		});
		// display the box
		setSize(new Dimension(300, 120));
		setVisible(true);

		loginButton.requestFocus(); // for quick usage
	}

	public static void main(String[] args){
		new LogonBox(new AuthDetailsListener(){
			public boolean onReceivedAuthDetails(String un, String pw){
				System.out.println(un + ", " + pw);
				return true;
			}
			public void onCancel(){
				System.out.println("Cancelled");
			}
		}, "Will", "pw");
	}
}
