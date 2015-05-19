package kps.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import kps.ui.listener.AuthDetailsListener;
import kps.ui.util.SpringUtilities;

public class LogonBox extends JFrame{

	public LogonBox(AuthDetailsListener listener, String defaultUN, String defaultPW){
		super("Login");
		setLocationRelativeTo(null); // centers the frame
		getContentPane().setLayout(new BorderLayout());

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

		SpringUtilities.makeCompactGrid(fields,
										2, 2,	//rows, cols
						                6, 6,	//initX, initY
						                6, 6);	//xPad, yPad)

		// event handling
		loginButton.addActionListener((ActionEvent e) -> {
			listener.onReceivedAuthDetails(
				usernameField.getText(),
				new String(passwordField.getPassword())
			);
		});
		cancelButton.addActionListener((ActionEvent e) -> {
			listener.onCancel();
		});
		// close window when any of the buttons are pressed
		loginButton.addActionListener(new LogonButtonListener());
		cancelButton.addActionListener(new LogonButtonListener());

		// display the box
		pack();
		setVisible(true);
	}

	public static void main(String[] args){
		new LogonBox(new AuthDetailsListener(){
			public void onReceivedAuthDetails(String un, String pw){
				System.out.println(un + ", " + pw);
			}
			public void onCancel(){
				System.out.println("Cancelled");
			}
		}, "Will", "pw");
	}

	private class LogonButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			UIUtils.closeWindow(LogonBox.this);
		}
	}

}
