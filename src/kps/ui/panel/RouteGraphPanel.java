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

/**
 * @author Nicky van Hulst
 * */
public class RouteGraphPanel extends JPanel implements MouseMotionListener, MouseListener, KeyListener{

	private static final long serialVersionUID = 7116082974955918264L;

	//graph to draw
	private RouteGraph graph;

	//nodes from the graph
	private ArrayList<DrawNode> drawNodes;

	//routes from the graph
	private ArrayList<DrawRoute> drawRoutes;

	//path of the nodes of the selected route
	private List<Node> nodePath;

	//frame the panel is on
	private JFrame frame;

	/**
	 * Created the RouteGraphPanel Object
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

	/**
	 * Creates DrawNodes from the graph and adds them to the list of drawNodes
	 * */
	public void setUpDrawNodes(){
		for(Node n : graph.getNodes()){
			drawNodes.add(new DrawNode(n,(int)(Math.random()*1100), (int)(Math.random()*700)));
		}
		setUpNonRandomDrawNodes();
	}

	public void setUpNonRandomDrawNodes(){
		double sWidth = 1200;
		double sHeight = 800;

		DrawNode connectedNode = null;

		for(DrawNode n : drawNodes){
			if(connectedNode == null)connectedNode = n;
			else if(connectedNode.getNode().getNeighbours().size() < n.getNode().getNeighbours().size())connectedNode = n;
		}

		connectedNode.setX(sWidth/2);
		connectedNode.setY(sHeight/2);


	}


	/**
	 * Creates DrawRoutes from the graph and adds them to the list of drawRoutes
	 * */
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

	/**
	 * The mail the user currently wants to send through the graph
	 * */
	public void setRoute(Mail mail){
		DijkstraSearch dks = new DijkstraSearch(graph);

		Map<List<Node>,Double> path = dks.getShortestPath(mail);

		for(List<Node> list : path.keySet()){
			this.nodePath = list;
		}
		setRoutesTaken();
	}

	/**
	 * Sets the routes taken for the mail
	 * */
	public void setRoutesTaken(){
		for(DrawRoute r : drawRoutes)r.setTaken(false);
		for(DrawNode n : drawNodes)n.setRouteSelected(false);

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

		g2.setColor(new Color(238,238,238));//grey to match rest of the program
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
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

	/**
	 * Returns the node that is on a point
	 *
	 * @param point to check
	 * */
	private DrawNode nodeOnPoint(Point p){
		DrawNode n = null;

		for(int i = 0; i < drawNodes.size(); i++){
			if(drawNodes.get(i).containsPoint(p))n = drawNodes.get(i);
		}
		return n;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//set node or routes selected
		if(setNodeSelected(e.getPoint()))return;
		setRouteSelected(e.getPoint());

	}

	/**
	 * Sets a route to be selected if point p is on it
	 * */
	public boolean setRouteSelected(Point p){
		for(DrawRoute r : drawRoutes)r.setSelected(false);
		for(DrawRoute r : drawRoutes){
			if(r.containsPoint(p.getX(),p.getY())){
				r.setSelected(true);
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets a node to be selected if point p is on it
	 * */
	public boolean setNodeSelected(Point p){
		boolean selected = false;
		for(DrawNode n : drawNodes){
			if(n.containsPoint(p)){
				n.setSelected(true);
				selected = true;
			}
			else n.setSelected(false);
		}
		return selected;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
			setNodeSelected(e.getPoint());
			DrawNode n = nodeOnPoint(e.getPoint());
			if(n!=null){
				n.setSelected(true);
				n.setY(e.getY() - n.getSize()/2);
				n.setX(e.getX() - n.getSize()/2);
		}
	}

	/**
	 * Starts the thread to repaint the frame
	 * */
	public void startThread(){
		new WindowThread(20, frame).start();;
	}

	public class WindowThread extends Thread {
		private final int delay; // delay between refreshes
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

	public static void main(String[] arg){
		RouteGraph g = new RouteGraph();

		List<BusinessEvent> events = new ArrayList<BusinessEvent>();

		try {
			events = KPSParser.parseFile(Main.XML_FILE_PATH+"new_dataset.xml");
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

		BasicRoute route = new BasicRoute("Rome", "Wellington");
		Mail mail = new Mail(route, Day.FRIDAY, 100, 5, Priority.DOMESTIC_AIR);

		support.setRoute(mail);
		support.setRoutesTaken();
	}




	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e){}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}

}