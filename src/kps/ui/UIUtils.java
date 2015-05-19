package kps.ui;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class UIUtils {

	public static void closeWindow(JFrame frame){
		frame.dispatchEvent(
			new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)
		);
	}
	
}
