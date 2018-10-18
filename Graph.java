import java.util.ArrayList;

public class Graph {
	
	ArrayList<Clsc> clscArray_;
	ArrayList<Path> pathArray_;
	
	public Graph() {
		updateGraph();
	}
	
	public void updateGraph(String fileName) {
		
	}
	
	/**
	 * 
	 * @param start -Summit we're starting from
	 * @param destination -Desired summit
	 * @return Prints: -Vehicle type, -% remaining, -Shortest Path, -Time
	 */
	public ArrayList<Clsc> findShortestPath(Clsc start, Clsc destination) {
		ArrayList<Clsc> shortestPath = new ArrayList<Clsc>();
		int time = Dijkstra(shortestPath, start, destination);
		return shortestPath;
	}
	
	
	private int Dijkstra(ArrayList<Clsc> path, Clsc start, Clsc destination) {
		int time;
		
		return time;
	}
}