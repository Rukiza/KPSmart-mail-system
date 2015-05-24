package kps.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import kps.Main;
import kps.data.DijkstraSearch;
import kps.data.Mail;
import kps.data.Node;
import kps.data.Route;
import kps.data.RouteGraph;
import kps.data.wrappers.BasicRoute;
import kps.enums.Day;
import kps.enums.Priority;
import kps.events.BusinessEvent;
import kps.events.TransportCostUpdateEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;
import kps.ui.graph.DrawNode;
import kps.ui.graph.DrawRoute;

public class RouteGraphPanel extends JPanel implements MouseMotionListener, MouseListener, KeyListener{


	private Color textColor = new Color (0, 0, 0);
	private Color backgroundColor = new Color (255,255,255);

	private RouteGraph graph;
	private ArrayList<DrawNode> drawNodes;
	private ArrayList<DrawRoute> drawRoutes;
	
	private List<Node> nodePath;
	
	private double NODE_SIZE = 80;
	
	private JFrame frame;

	/**
	 * @param data - The Event log of the program.
	 */
	public RouteGraphPanel(RouteGraph g, JFrame frame){
		this.nodePath = new ArrayList<Node>();
		this.drawNodes = new ArrayList<DrawNode>();
		this.drawRoutes = new ArrayList<DrawRoute>();
		this.graph = g;
		this.frame = frame;
		setUpDrawNodes();
		setUpDrawRoutes();
		addMouseListener(this);
		startThread();
		setup();
	}

	public void setUpDrawNodes(){
		for(Node n : graph.getNodes()){
			drawNodes.add(new DrawNode(n,(int)(Math.random()*1200), (int)(Math.random()*900)));
		}
	}
	
	
	
	public void setUpDrawRoutes(){
		for(DrawNode n : drawNodes ){
			for(Route r : n.getNode().getNeighbours()){
				
				DrawNode src = null;
				DrawNode dest = null;
				for(int i = 0; i<drawNodes.size(); i++){
					if(drawNodes.get(i).getNode().getName().equals(r.getSrc()))src = drawNodes.get(i);
					if(drawNodes.get(i).getNode().getName().equals(r.getDest()))dest = drawNodes.get(i);
				}
				boolean added = false;
				for(DrawRoute dr : drawRoutes){
					 if(dr.addRouteCheck(r)){
						 dr.addRoute(r);
						 added = true;
					 }
				}				
				if(!added)drawRoutes.add(new DrawRoute(r,src,dest));
			}

		}
	}

	public void setup(){
		addMouseMotionListener(this);
		this.validate();
	}
	
	public void setRoute(Mail mail){
		DijkstraSearch dks = new DijkstraSearch(graph);
		
		Map<List<Node>,Double> path = dks.getShortestPath(mail);
		
		for(List<Node> list : path.keySet()){
			this.nodePath = list;
		}
	}
	
	public void setRoutesTaken(){
		for(DrawRoute r : drawRoutes)r.setTaken(false);
		for(DrawNode n : drawNodes)n.setSelected(false);
		
		for(DrawNode n : drawNodes){
			for(int i = 0; i < nodePath.size(); i++){
				if(nodePath.get(i).getName().equals(n.getNode().getName()))n.setRouteSelected(true);
			}
		}
		
		
		
		for(int i = 0; i < nodePath.size() - 1; i++){
			for(DrawRoute r : drawRoutes){
				if(r.getNode1Name().equals(nodePath.get(i).getName()) && r.getNode2Name().equals(nodePath.get(i+1).getName())
				|| r.getNode2Name().equals(nodePath.get(i).getName()) && r.getNode1Name().equals(nodePath.get(i+1).getName()))r.setTaken(true);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		if(g == null )return;
		Graphics2D g2 = (Graphics2D)g;
		 g2.setRenderingHint(
		            RenderingHints.KEY_ANTIALIASING,
		            RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(backgroundColor );
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		g2.setColor(Color.BLACK);
		drawRoutes(g2);

		for(DrawNode n : drawNodes)n.draw(g2);
	};
	
	/**
	 * Simple way of drawing the routes 
	 * */
	public void drawRoutes(Graphics2D g){
		for(DrawRoute r : drawRoutes){
			r.draw(g);
		}	
	}
	

	
	

	@Override
	public void repaint(){
		Graphics g = this.getGraphics();
		paint(g);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}


	
	private DrawNode nodeOnPoint(Point p){
		DrawNode n = null;

		for(int i = 0; i < drawNodes.size(); i++){
			if(drawNodes.get(i).containsPoint(p))n = drawNodes.get(i);
		}
		return n;
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getID() == MouseEvent.MOUSE_RELEASED ){
			for(DrawNode n : drawNodes){
				if(n.containsPoint(e.getPoint())){
				//	n.selected = true;
				}
				else n.setSelected(false);
			}
			repaint();
		}
	}
	
	public void mouseMoved(MouseEvent e){
		for(DrawNode n : drawNodes){
			if(n.containsPoint(e.getPoint())){
				n.setSelected(true);
			}
			else n.setSelected(false);
		}
	}


	@Override
	public void mouseDragged(MouseEvent e) {
			DrawNode n = nodeOnPoint(e.getPoint());
			if(n!=null){
				n.setSelected(true);
				n.setY(e.getY() - n.getSize()/2);
				n.setX(e.getX() - n.getSize()/2);
		}
	}
	
	public static void main(String[] arg){
		RouteGraph g = new RouteGraph();

		List<BusinessEvent> events = new ArrayList<BusinessEvent>();

		try {
			events = KPSParser.parseFile(Main.XML_FILE_PATH+"graph.xml");
		} catch (ParserException e) {
			e.printStackTrace();
		}



		for(BusinessEvent e : events){
			if(e instanceof TransportCostUpdateEvent){
				g.addRoute(new Route((TransportCostUpdateEvent)e));
			}
		}

		JFrame frame = new JFrame();
		frame.setSize(1200, 900);
		RouteGraphPanel support = new RouteGraphPanel(g,frame);

		frame.addKeyListener(support);
		frame.add(support, BorderLayout.CENTER);
		frame.setVisible(true);
		support.setup();
		
		BasicRoute route = new BasicRoute("Wellington", "Rome");
		Mail mail = new Mail(route, Day.FRIDAY, 100, 5, Priority.DOMESTIC_AIR);
		
		support.setRoute(mail);
		support.setRoutesTaken();
	}
	
	public void startThread(){
		new WindowThread(40, frame).start();;
	}
	
	public class WindowThread extends Thread {
		private final int delay; // delay between pulses
		private final JFrame display;

		public WindowThread(int delay, JFrame display) {
			this.delay = delay;
			this.display = display;
		}

		public void run() {
			while(true) {
				// Loop forever
				try {
					Thread.sleep(delay);
					if(display != null) {
						display.repaint();
						
					}
				} catch(InterruptedException e) {
					// should never happen
				}
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}

}