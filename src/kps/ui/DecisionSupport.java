package kps.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.layout.Border;

import javax.swing.JFrame;
import javax.swing.JPanel;

import kps.Main;
import kps.data.wrappers.EventLog;
import kps.events.BusinessEvent;
import kps.events.MailDeliveryEvent;
import kps.events.PriceUpdateEvent;
import kps.events.TransportCostUpdateEvent;
import kps.events.TransportDiscontinuedEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;

/**
 * @author Shane Brewer
 *
 */
public class DecisionSupport extends JPanel implements MouseListener, KeyListener{

	private EventLog data;
	private BusinessEvent event;
	private List<Button> buttons;
	private Color textColor = new Color (0, 0, 0);
	private Color backgroundColor = new Color (255,255,255);
	private int progressBarY;
	private Color progressForground = new Color(185, 142, 0);
	private Color progressBackground = new Color(255, 205, 0);
	private Color progressDraw = new Color(255,196,0);



	/**
	 * @param data - The Event log of the program.
	 */
	public DecisionSupport(EventLog data){
		this.data = data;
		if (!this.data.isEmpty()){
			event = this.data.getCurrentEvent();
		}
		addMouseListener(this);
	}

	public void setup(){
		this.validate();
		buttonSetup();
	}

	@Override
	public void paint(Graphics g) {
		if(buttons == null || buttons.isEmpty())return;
		Graphics2D g2 = (Graphics2D)g;

		g.setColor(backgroundColor );
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		for (Button button: buttons){
			g.setColor(button.fill);
			g2.fill(button);
			g.setColor(button.draw);
			g2.draw(button);
		}

		if (event == null && !data.isEmpty()){
			System.out.println("Only here once");
			event = data.getCurrentEvent();
		}

		if (event != null){
			g.setColor(textColor);
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
			g.drawString("Type of Event: "+event.getType(), 100, 80);
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
			g.drawString("Origin: "+event.getOrigin(), 100, 120);
			g.drawString("Destination: "+event.getDestination(), 100, 150);
			g.drawString("Time Stamp: "+new Date(event.getTimeLogged()).toString(), 100, 350);
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
			paintEventByType(g2);
		}
		paintProgressBar(g2);
		buttonSetup();
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
					event = data.getNextEvent();
				}
				else if (button.isAtPoint(e.getPoint()) &&
						button.name.equals("Left")){
					event = data.getPrevEvent();
				}
			}
			repaint();
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED &&
				!data.isEmpty()){
			if (e.getKeyCode() == KeyEvent.VK_RIGHT){
				event = data.getNextEvent();
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT){
				event = data.getPrevEvent();

			}
			repaint();
		}
	}

	//=======================HELPER METHODS==============================//
	private void paintEventByType(Graphics g){
		if (event instanceof MailDeliveryEvent){
			MailDeliveryEvent tempEvent = (MailDeliveryEvent) event;
			g.drawString("Volume: "+tempEvent.getVolume(), 100, 175);
			g.drawString("Weight: "+ tempEvent.getWeight(), 100, 195);
		}
		if (event instanceof PriceUpdateEvent){
			PriceUpdateEvent tempEvent = (PriceUpdateEvent) event;
			g.drawString("Price Per Gram: "+tempEvent.getGramPrice(), 100, 175);
			g.drawString("Price Per Volume: "+ tempEvent.getVolumePrice(), 100, 195);
		}
		if (event instanceof TransportCostUpdateEvent){
			TransportCostUpdateEvent tempEvent = (TransportCostUpdateEvent) event;
			g.drawString("Volume Price: "+tempEvent.getGramPrice(), 100, 175);
			g.drawString("Weight Price: "+ tempEvent.getVolumePrice(), 100, 195);
			g.drawString("Maximum Weight: "+tempEvent.getMaxWeight(), 100, 215);
			g.drawString("Maximum Volume: "+ tempEvent.getMaxVolume(), 100, 235);
			g.drawString("Transport Duration: "+ tempEvent.getTripDuration(), 100, 255);
			g.drawString("Transport Firm: "+ tempEvent.getTransportFirm(), 100, 275);
			g.drawString("Transport Type: "+ tempEvent.getTransportType(), 100, 295);
			g.drawString("Departure Frequency: "+ tempEvent.getDepartureFrequency(), 100, 315);
		}
		if (event instanceof TransportDiscontinuedEvent){
			TransportDiscontinuedEvent tempEvent = (TransportDiscontinuedEvent) event;
			g.drawString("Transport Firm: "+tempEvent.getTransportFirm(), 100, 175);
			g.drawString("Transport Type: "+ tempEvent.getTransportType(), 100, 195);
		}
	}

	private void paintProgressBar(Graphics g){
		int progressBarX = (int)(this.getWidth()*0.10);
		progressBarY = (int)(this.getHeight()*0.90);
		int progressBarWidth = (int)(getWidth()*0.8);
		int progressBarHeight = 20;
		int progress = (int)(progressBarWidth * (data.getPosition()/(data.getSize()-1.0)));
		g.drawRect(progressBarX, progressBarY, progressBarWidth, progressBarHeight);
		g.fillRect(progressBarX, progressBarY, progress, progressBarHeight);
	}
	//=====================HELPER METHODS END============================//

	/**
	 * Sets up buttons on panel
	 */
	private void buttonSetup(){
		buttons = new ArrayList<Button>();
		int width = 100;
		int height = 60;
		int base = 20;
		int y = this.progressBarY - base - height;
		int x = this.getWidth()/2;
		buttons.add(new Button("Right", x - width/2 - base/2, y, width, height));
		buttons.add(new Button("Left", x + width/2  +base/2, y, width, height));
	}

	/**
	 * Button - Used in the menu for pressing.
	 * @author Shane Brewer
	 *
	 */
	private class Button extends Rectangle{
		public String name;
		public Color draw = new Color(255,196,0);
		public Color fill = new Color(255,215,51);

		public Button(String name, int x, int y, int width, int height){
			super(x,y, width, height);
			this.name = name;
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
		frame.setSize(1200, 900);
		DecisionSupport support = null;
		try {
			support = new DecisionSupport(new EventLog(KPSParser.parseFile(Main.filename)));
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.addKeyListener(support);
		frame.add(support, BorderLayout.CENTER);
		frame.setVisible(true);
		support.setup();

	}
}


