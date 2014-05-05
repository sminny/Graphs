package Tutorial3;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Graph {

	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	public Graph(){

	}
	public ArrayList<Edge> getEdges(){
		return edges;
	}
	public Position position(int vertex){
		for(Vertex element: vertices){
			if (element.getValue()==vertex)
				return element.getPosition();
		}
		return null;
	}
	public ArrayList<Vertex> getVertices(){
		return vertices;
	}
	public Edge getEdge(int position){
		return edges.get(position);
	}
	public Vertex getVertex(int position){
		
		return vertices.get(position);
	}

	public void addVertex(int value,double d, double e){
		vertices.add(new Vertex(new Position(d,e),value));
	}
	public void addEdge(int start, int end, int weight){
		edges.add(new Edge(start,end,weight));
	}
	public boolean edgeExists(int vertOne, int vertTwo){
		for(Edge element: edges){
			if((element.getStart()==vertOne && element.getEnd()==vertTwo)
					||(element.getStart()==vertTwo && element.getEnd() == vertOne))
				return true;
		}
		return false;
	}
	public double weight(int vertexOne,int vertexTwo){
		for(Edge element: edges){
			if(element.getStart()==vertexOne && element.getEnd() == vertexTwo)
				return element.getWeight();
		}
		return 0;
	}
	public List<Edge> neighbours(int vertex){
		ArrayList<Edge> ar = new ArrayList<Edge>();

		for(Edge element: edges){
			if(element.getStart()==vertex)
				ar.add(element);
			else if( element.getEnd() == vertex)
				ar.add(element);
		}
		return ar;
	}
	static class Position{
		private double x,y;
		public Position(double x, double y){
			this.x= x;
			this.y= y;
		}
		public double getX(){
			return x;
		}
		public double getY(){
			return y;
		}
	}
	static class Vertex{
		private int value;
		private Position position;
		private boolean disjoint = true;
		
		public Vertex(Position position,int value){
			this.position = position;
			this.value = value;
		}
		public boolean isDisjoint(){
			return disjoint;
		}
		public void setDisjoint(boolean disjoint){
			this.disjoint= disjoint;
		}
		public Position getPosition(){
			return position;
		}
		public int getValue(){
			return value;
		}
	}
	static class Edge implements Comparable{
		private int start,end,weight;
		
		public Edge(int start,int end,int weight) {
			this.start = start;
			this.end = end;
			this.weight = weight;
		}
		public int getStart(){
			return start;
		}
		public int getEnd(){
			return end;
		}
		public int getWeight(){
			return weight;
		}
		public void swapValues(){
			int tmp=0;
			tmp=start;
			start=end;
			end=tmp;
		}
		@Override
		public int compareTo(Object o) {
			if(this.getWeight() > ((Edge) o).getWeight()){
				return 1;
			}else if(this.getWeight() < ((Edge) o).getWeight())
				return -1;
			else 
				return 0;
		}
	}
}
