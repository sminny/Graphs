package Tutorial3;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;

import Tutorial3.Graph.Vertex;



@SuppressWarnings("serial")
class GraphDisplay extends JComponent
{
	public static void main(String[] args)
	{
		GraphDisplay gd = new GraphDisplay();
		gd.showInWindow(400,400,"A Random Graph");

		//	Add some randomly placed nodes
		int n = 25;
		double p = 0.2;
		Graph graph = gd.getGraph();

		gd.randomGraph(n,p);
		gd.paintGraph();
		//gd.primsAlgorithm(n);
		gd.kruskalAlgorithm(n);
		//	Add a few random edges in slow motion

	}
	protected double minX,maxX,minY,maxY;
	protected HashMap<Object,Node> nodes = new HashMap<Object,Node>();
	protected Vector<Edge> edges = new Vector<Edge>();

	protected int   MARGIN      = 20;
	protected int   NODE_RADIUS = 5;
	protected Color NODE_COLOR  = Color.blue.brighter();
	protected Graph graph = new Graph();

	public Graph getGraph(){
		return graph;
	}
	public void kruskalAlgorithm(int numberOfVertices){
		int pathLength=0;
		DisjointSets ds = new DisjointSets(numberOfVertices);
		PriorityQueue<Tutorial3.Graph.Edge> pq = new PriorityQueue<Tutorial3.Graph.Edge>();
		
		for(Tutorial3.Graph.Edge edge : graph.getEdges()){
			pq.add(edge);
		}
		int edgesAccepted = 0;
		System.out.println(numberOfVertices);
		for(Vertex vert : graph.getVertices()){
			if(vert.isDisjoint())
				numberOfVertices--;
		}
		System.out.println(numberOfVertices);
		while(edgesAccepted<numberOfVertices){
			System.out.println(edgesAccepted +" "+numberOfVertices);
			Tutorial3.Graph.Edge e = pq.poll();
			if(e==null){
				System.out.println("Priority que is empty");
				System.out.println("Shortest path is "+pathLength);
				return;
			}
			int finda = ds.find(e.getStart());
			int findb= ds.find(e.getEnd());

			if(finda != findb){
				ds.union(finda,findb);
				edgesAccepted ++;
				addEdge(e.getStart(), e.getEnd(),Color.RED);
				pathLength+=e.getWeight();
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		

	}
	public void primsAlgorithm(int numberOfVertices){
		int pathLength=0;
		DisjointSets ds = new DisjointSets(numberOfVertices);
		PriorityQueue<Tutorial3.Graph.Edge> pq = new PriorityQueue<Tutorial3.Graph.Edge>();
		int position = new Random().nextInt(numberOfVertices);
		Vertex vert ;
		for(int i=0;i<numberOfVertices;i++){
			vert =graph.getVertex(position);
			ds.setElement(position,0);
			for(Tutorial3.Graph.Edge neighbour : graph.neighbours(position)){
				int value = vert.getValue();
				int weight = neighbour.getWeight();
				if(neighbour.getStart()==value){
					int neighbourEnd = neighbour.getEnd();
					if(ds.getElement(neighbourEnd)==-1 || weight<ds.getElement(value)){
						ds.setElement(neighbourEnd,weight);
						pq.add(neighbour);
					}
				}
				else if(neighbour.getEnd()==value){
					int neighbourStart = neighbour.getStart();
					if(ds.getElement(neighbourStart)==-1 || weight<ds.getElement(value)){
						neighbour.swapValues();	//swaping start/end
						ds.setElement(neighbourStart,weight);
						pq.add(neighbour);

					}
				}

			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Tutorial3.Graph.Edge e = pq.poll();
			if(e== null)
				continue;
			pathLength += e.getWeight();
			addEdge(e.getStart(), e.getEnd(), Color.RED);
			position = e.getEnd(); // the new position
		}
		System.out.println(pathLength);
		/*
		 * checking edge value
		 * int finda= ds.find(verticeA.getValue());
		 * int findb= ds.find(verticeB.getValue());
		 * if(finda != findb){
		 * 		ds.union(finda,findb);
		 * }
		 */
	}
	public void paintGraph(){
		try{
			//			for(int j=0; j<2*n; j++)
			//			{
			//				int a = (int)(Math.random()*n);
			//				int b = (int)(Math.random()*n);
			//				gd.addEdge(a,b,Color.red);
			//				Thread.sleep(500);
			//				gd.addEdge(a,b,Color.black);
			//			}

			for(Graph.Edge element: graph.getEdges()){
				addEdge(element.getStart(),element.getEnd(),Color.red);
				Thread.sleep(100);
				addEdge(element.getStart(),element.getEnd(),Color.black);
			}
		}catch(InterruptedException e) {
		}
	}
	public void randomGraph(int numberOfVertices,double probability){
		Random rand = new Random();
		//create vertices
		for(int i=0;i<numberOfVertices;i++){
			graph.addVertex(i, rand.nextInt(100), rand.nextInt(100));
		}
		//create edges
		//2 possible solutions - check each edge or check sequentially (less iterations)
		//depends on how edges should be created
		for(int j=0;j<numberOfVertices;j++){
			for(int q=j+1;q<numberOfVertices;q++){

				if(Math.random()<probability){
					Vertex vert1 = graph.getVertex(j);
					Vertex vert2 = graph.getVertex(q);
					vert1.setDisjoint(false);
					vert2.setDisjoint(false);
					double deltaX = Math.abs(vert1.getPosition().getX() - vert2.getPosition().getX());
					double deltaY = Math.abs(vert1.getPosition().getY() - vert2.getPosition().getY());
					double weight = Math.hypot(deltaX, deltaY);
					int roundedWeight = (int)Math.round(weight);
					graph.addEdge(graph.getVertex(j).getValue(), graph.getVertex(q).getValue(), roundedWeight);
				}
			}
		}
		for(int i=0;i<numberOfVertices;i++){
			double x = graph.getVertex(i).getPosition().getX()/100;
			double y = graph.getVertex(i).getPosition().getY()/100;
			addNode(i, x, y);
		}
	}
	public GraphDisplay()
	{
		minX = minY = Double.POSITIVE_INFINITY;
		maxX = maxY = Double.NEGATIVE_INFINITY;
	}

	public synchronized void addNode(Object identifier, double x, double y,
			Color col)
	{
		maxX = Math.max(maxX,x);
		maxY = Math.max(maxY,y);
		minX = Math.min(minX,x);
		minY = Math.min(minY,y);
		nodes.put(identifier,new Node(x,y,col));
		repaint();
	}

	public synchronized void addNode(Object identifier, double x, double y)
	{
		maxX = Math.max(maxX,x);
		maxY = Math.max(maxY,y);
		minX = Math.min(minX,x);
		minY = Math.min(minY,y);
		nodes.put(identifier,new Node(x,y,NODE_COLOR));
		repaint();
	}

	public synchronized void addEdge(Object start, Object end, Color c)
	{
		removeEdge(start,end);
		edges.add(new Edge(start,end,c));
		repaint();
	}

	public synchronized boolean removeEdge(Object start, Object end)
	{
		Iterator<Edge> it = edges.iterator();
		while(it.hasNext())
		{
			Edge tmp = it.next();
			if(tmp.joins(start,end))
			{
				it.remove();
				repaint();
				return true;
			}
		}
		return false;
	}

	public void addEdge(Object start, Object end)
	{
		addEdge(start,end,Color.black);
	}


	public JFrame showInWindow(int width, int height, String title)
	{
		JFrame f = new JFrame();
		f.add(this);
		f.setSize(width,height);
		f.setTitle(title);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		f.setVisible(true);
		return f;
	}

	public void paint(Graphics g)
	{
		if(nodes.isEmpty())
			return;

		double xscl = (getSize().width -2*MARGIN) / (maxX-minX);
		double yscl = (getSize().height-2*MARGIN) / (maxY-minY);


		g.translate(+MARGIN,+MARGIN);

		synchronized(this){
			for(Edge e: edges)
				e.paint(g,xscl,yscl,minX,minY);
			for(Node n: nodes.values())
				n.paint(g,xscl,yscl,minX,minY);
		}		

		g.translate(-MARGIN,-MARGIN);
	}







	private class Node
	{
		public Node(double x, double y, Color col)
		{
			this.x = x;
			this.y = y;
			this.col = col;
		}

		public void paint(Graphics g, double xscl, double yscl, double tx, double ty)
		{
			g.setColor(col);
			g.fillOval(
					(int)((x-tx)*xscl - NODE_RADIUS),
					(int)((y-ty)*yscl - NODE_RADIUS),
					2*NODE_RADIUS,
					2*NODE_RADIUS
					);
		}

		protected double x,y;
		protected Color col;

	}

	private class Edge
	{
		public Edge(Object start, Object end, Color col)
		{
			this.start = start;
			this.end = end;
			this.col = col;
		}

		public boolean joins(Object a, Object b)
		{
			return	(start.equals(a) && end.equals(b))
					||	(start.equals(b) && end.equals(a));
		}

		public void paint(Graphics g, double xscl, double yscl, double tx, double ty)
		{
			Node a = nodes.get(start);
			Node b = nodes.get(end);
			g.setColor(col);
			g.drawLine(
					(int)(xscl*(a.x-tx)),
					(int)(yscl*(a.y-ty)),
					(int)(xscl*(b.x-tx)),
					(int)(yscl*(b.y-ty))
					);			
		}

		protected Object start,end;
		protected Color col;
	}
}
