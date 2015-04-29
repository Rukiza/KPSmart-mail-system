package kps.ui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {

	public Window(){
		final Dimension WINDOW_SIZE = new Dimension(600,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_SIZE);


		setVisible(true);
	}

}
