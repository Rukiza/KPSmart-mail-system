package kps.ui.util;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class UIUtils {

	public static void closeWindow(JFrame frame){
		frame.dispatchEvent(
			new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)
		);
	}

	public static boolean isDouble(String... input){
		for(String s : input){
			try {
				Double.parseDouble(s);
			} catch (NumberFormatException e){
				return false;
			}
		}
		return true;
	}

}
