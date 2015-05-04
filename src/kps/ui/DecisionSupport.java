package kps.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import kps.data.wrappers.EventLog;
import kps.events.BusinessEvent;

/**
 * 
 * @author Shane Brewer
 *
 */
public class DecisionSupport extends JPanel implements MouseListener, KeyListener{
	
	private EventLog data;
	private BusinessEvent event;
	private Button[] buttons; 
	
	/** 
	 * @param data - The Event log of the program.
	 */
	public DecisionSupport(EventLog data){
		this.data = data;
		if (!this.data.eventLogIsEmpty()){
			event = this.data.getCurrentEvent();
		}
		this.setSize(700, 500);
		buttonSetup();
		
	}
	
	@Override
	public void repaint(){
		if(buttons == null)return;
		Graphics g = this.getGraphics();
		Graphics2D g2 = (Graphics2D)g;
		
		
		for (int i = 0; i < buttons.length; i++){
			g.setColor(buttons[i].fill);
			g2.fill(buttons[i]);
			g.setColor(buttons[i].draw);
			g2.draw(buttons[i]);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
	}
	
	/**
	 * Sets up buttons on panel
	 */
	private void buttonSetup(){
		buttons = new Button[2];
		int width = 50;
		int height = 20;
		int base = 20;
		int y = this.getHeight() - base - height;
		int x = this.getWidth() - base;
		buttons[0] = new Button("Up", x - width, y, width, height);
		buttons[1] = new Button("Down", x - width*2-base, y, width, height);
	}
	
	/**
	 * Button - Used in the menu for pressing.
	 * @author Shane Brewer
	 *
	 */
	private class Button extends Rectangle{
		public String name;
		public Color draw = new Color(255,0,0);
		public Color fill = new Color(0,255,0);
		
		public Button(String name, int x, int y, int width, int height){
			super(x,y, width, height);
			this.name = name;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}
	
	//==========================Unused Methods===========================//
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	
	public static void main(String[] arg){
		JFrame frame = new JFrame();
		frame.setSize(700, 500);
		frame.add(new DecisionSupport(new EventLog()));
		frame.setVisible(true);
		frame.repaint();
	}
}

	
