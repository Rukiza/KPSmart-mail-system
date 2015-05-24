package kps.ui.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class UIUtils {

	public static void closeWindow(JFrame frame){
		frame.dispose();
	}

	public static boolean isDouble(String... input){
		for(String s : input){
			try {
				Double.parseDouble(s);
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
	 * @return the input image but resized to newW and newH
	 */
	public static BufferedImage resizeImage(BufferedImage image, int newW, int newH){
		Image tmp = image.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}

}