package kps.ui.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * @author hardwiwill
 *
 * Contains UI utility methods
 */
public class UIUtils {

	/**
	 * Causes a window to close
	 * @param frame
	 */
	public static void closeWindow(JFrame frame){
		frame.dispose();
	}

	/**
	 * @param input
	 * @return true if input is an integer
	 */
	public static boolean isInteger(String... input){
		for(String s : input){
			try {
				Integer.parseInt(s);
			} catch (NumberFormatException | NullPointerException e){
				return false;
			}
		}
		return true;
	}

	/**
	 * @param image
	 * @param newW width of new image
	 * @param newH height of new image
	 * @return the input image resized to newW and newH
	 */
	public static BufferedImage resizeImage(BufferedImage image, int newW, int newH){
		Image tmp = image.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}

	/**
	 * @param input
	 * @return true if input is a double
	 */
	public static boolean isDouble(String... input) {
		for(String s : input){
			try {
				Double.parseDouble(s);
			} catch (NumberFormatException | NullPointerException e){
				return false;
			}
		}
		return true;
	}

}