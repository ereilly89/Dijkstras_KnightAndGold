package hw4;

public class Vertex {
	int id;
	int distance;
	int predecessor;
	boolean visited;
	
	Vertex(int id){
		this.id = id;
		distance = Integer.MAX_VALUE;
		predecessor = -1;
		visited = false;
	}
	
	//SETS
	public void setID(int id) {
		this.id = id;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public void setPredecessor(int predecessor) {
		this.predecessor = predecessor;
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	//GETS
	public int getID() {
		return id;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public int getPredecessor() {
		return predecessor;
	}
	
	public boolean getVisited() {
		return visited;
	}
}
