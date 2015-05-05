package kps.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import kps.data.wrappers.EventLog;
import kps.events.BusinessEvent;

/**
 * @author Shane Brewer
 *
 */
public class DecisionSupport extends JPanel implements MouseListener, KeyListener{

	private EventLog data;
	private BusinessEvent event;
	private List<Button> buttons;

	/**
	 * @param data - The Event log of the program.
	 */
	public DecisionSupport(EventLog data){
		this.data = data;
		if (!this.data.isEmpty()){
			event = this.data.getCurrentEvent();
		}
		setPreferredSize(new Dimension(700,500));
		setSize(700, 500);
		buttonSetup();

	}

	@Override
	public void paint(Graphics g) {
		if(buttons == null)return;
		Graphics2D g2 = (Graphics2D)g;


		for (Button button: buttons){
			g.setColor(button.fill);
			g2.fill(button);
			g.setColor(button.draw);
			g2.draw(button);
		}
	};

	@Override
	public void repaint(){
		Graphics g = this.getGraphics();
		paint(g);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getID() == MouseEvent.MOUSE_RELEASED &&
				!data.isEmpty()){
			for (Button button: buttons){
				if (button.isAtPoint(e.getPoint()) &&
						button.name.equals("Right")){
					System.out.println("Click Right");
					event = data.getNextEvent();
				}
				else if (button.isAtPoint(e.getPoint()) &&
						button.name.equals("Left")){
					System.out.println("Click Left");
					event = data.getPrevEvent();
				}
			}
			repaint();
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getID());
		System.out.println(KeyEvent.KEY_PRESSED);
		if (e.getID() == KeyEvent.KEY_PRESSED &&
				!data.isEmpty()){
			if (e.getKeyCode() == KeyEvent.VK_RIGHT){
				System.out.println("Right");
				event = data.getNextEvent();
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT){
				System.out.println("Left");
				event = data.getPrevEvent();
			}
			repaint();
		}
	}

	/**
	 * Sets up buttons on panel
	 */
	private void buttonSetup(){
		buttons = new ArrayList<Button>();
		int width = 50;
		int height = 20;
		int base = 20;
		int y = this.getHeight() - base - height;
		int x = this.getWidth() - base;
		buttons.add(new Button("Up", x - width, y, width, height));
		buttons.add(new Button("Down", x - width*2-base, y, width, height));
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

		public boolean isAtPoint(Point point){
			if (point.x < x || point.x > x + width){
				return false;
			}
			if (point.y < y || point.y > y + height){
				return false;
			}
			return true;
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
		DecisionSupport support = new DecisionSupport(new EventLog());
		frame.addMouseListener(support);
		frame.addKeyListener(support);
		frame.add(support);
		frame.setVisible(true);
	}
}


