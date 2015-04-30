package kps.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogonBox extends JFrame{

	public LogonBox(AuthenticationListener listener, String defaultUN, String defaultPW){
		super("Login");
		//setLayout();
		//set up components
		JLabel usernameLabel = new JLabel("Username:");
		JLabel passwordLabel = new JLabel("Password:");
		JTextField usernameField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		JButton loginButton = new JButton("Login");
		JButton cancelButton = new JButton("Cancel");

		passwordField.setEchoChar('~');

		JPanel unPanel = new JPanel();
		JPanel pwPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		unPanel.add(usernameLabel);
		unPanel.add(usernameField);
		pwPanel.add(passwordLabel);
		pwPanel.add(passwordField);
		buttonPanel.add(loginButton);
		buttonPanel.add(cancelButton);

		add(unPanel);
		add(pwPanel);

	}
	/*public AuthDetails getAuthDetails(){
		setVisible(true);
	}*/

}
