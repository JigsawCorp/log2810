import java.util.ArrayList;
import java.util.HashMap;


public class Graph {
	
	ArrayList<Clsc> clscArray_;
	ArrayList<Path> pathArray_;
	 
	/**
	 * 
	 * @param start -Summit we're starting from
	 * @param destination -Desired summit
	 * @return Prints: -Vehicle type, -% remaining, -Shortest Path, -Time
	 */
	public String findShortestPath(Clsc start, Clsc destination, Vehicle vehicle) {
		
		StringBuilder builder = new StringBuilder();
				
		// For each CLSC, we have 1) the path to get to that CLSC, starting from "start"
		//					and	2) the time it took us to get to that CLSC (from "start")
		HashMap<Clsc, ClscNode> map = new HashMap<Clsc, ClscNode>();
		
		ArrayList<Clsc> visitedList = new ArrayList<Clsc>();
		// We initialize our Hashmap
		for (Clsc point : clscArray_) {	
			map.put(point, new ClscNode(point));
		}
		// The starting Node is set to 0 and all the rest is set to infinity by default
		map.get(start).setTime_(0);
		map.get(start).addToPath(start);
		visitedList.add(start);	// We first visit the starting CLSC
		
		int bestTime = Dijkstra(map, visitedList, start, destination, vehicle);
		
		if (bestTime != Integer.MAX_VALUE) {	// If no errors
			builder.append("Shortest path to CLSC #" + destination.getId() + " from CLSC #" + start.getId() + " :\n"
					+ "\tVehicle type : NI-NH\n"
					+ "\tBattery remaining : " + vehicle.chargeLeft()
					+ "\tShortest path :\n\t");
			for (Clsc clsc : map.get(destination).getPathToPoint_()) {
				
			}
			
		}
		return shortestPath;
	}
	
	/* 1- We find the shortest CLSC not in visited
	 * 2- Every unvisited neighbor has a) its time changed and b) its path changed
	 */
	// TODO: update the algorithm with Vehicle
	private int Dijkstra(HashMap<Clsc, ClscNode> map, ArrayList<Clsc> visitedList, Clsc start, Clsc destination, Vehicle vehicle) {
				
		int shortestTime = Integer.MAX_VALUE;
		Clsc nextClsc = null;
		
		// For every known (visited) CLSC...
		for (Clsc visited : visitedList) {
			// ...We check every path
			for (Path path : visited.getPaths()) {
				Clsc currentClsc = path.getDestination();
				ClscNode currentNode = map.get(currentClsc);
				// We update all of visited's neighbors
				currentNode.update(map.get(visited), path.getTime());
				// We find the shortest point to a neighbor not visited yet
				if (!visitedList.contains(currentClsc)) {		// We make sure we're not going back to a CLSC we already visited
					if (currentNode.getTime_() < shortestTime) {
						nextClsc = currentClsc;
						shortestTime = currentNode.getTime_();
					}
				}			
			}
		}
		visitedList.add(nextClsc);
		ClscNode nextNode = map.get(nextClsc);
		if (nextClsc == destination) {
			return map.get(nextClsc).getTime_();	// Can also access to the path
		}
		return Dijkstra(map, visitedList, nextClsc, destination);
	}
}