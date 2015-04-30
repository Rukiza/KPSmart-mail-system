package kps.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogonBox extends JFrame{

	public LogonBox(AuthDetailsListener listener, String defaultUN, String defaultPW){
		super("Login");
		setSize(300, 200);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		//set up components
		JLabel usernameLabel = new JLabel("Username:");
		JLabel passwordLabel = new JLabel("Password:");
		JTextField usernameField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		JButton loginButton = new JButton("Login");
		JButton cancelButton = new JButton("Cancel");

		passwordField.setEchoChar('~');

		JPanel unPanel = new JPanel();
		unPanel.setLayout(new BoxLayout(unPanel, BoxLayout.LINE_AXIS));
		JPanel pwPanel = new JPanel();
		pwPanel.setLayout(new BoxLayout(pwPanel, BoxLayout.LINE_AXIS));
		JPanel buttonPanel = new JPanel();

		unPanel.add(usernameLabel);
		unPanel.add(usernameField);
		pwPanel.add(passwordLabel);
		pwPanel.add(passwordField);
		buttonPanel.add(loginButton);
		buttonPanel.add(cancelButton);

		add(unPanel);
		add(pwPanel);
		add(buttonPanel);

		// event handling
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				listener.onReceivedAuthDetails(
						usernameField.getText(),
						new String(passwordField.getPassword()));
			}
		});
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				listener.onReceivedCancel();
			}
		});
		// close window when any of the buttons are pressed
		loginButton.addActionListener(new CloseWindowListener());
		loginButton.addActionListener(new CloseWindowListener());

		// display the box
		setVisible(true);
	}

	public static void main(String[] args){
		new LogonBox(new AuthDetailsListener(){
			public void onReceivedAuthDetails(String un, String pw){
				System.out.println(un + ", " + pw);
			}
			public void onReceivedCancel(){
				System.out.println("Cancelled");
			}
		}, "Will", "pw");
	}
	
	private class CloseWindowListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			LogonBox.this.dispatchEvent(
					new WindowEvent(LogonBox.this, WindowEvent.WINDOW_CLOSING)
					);
		}
	}
	
}
