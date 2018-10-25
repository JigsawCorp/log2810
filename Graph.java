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
		// We initialize our Hashmap and our ArrayList, and we also put start in the list
		initialize(map, visitedList, start);
		
		int bestTime = Dijkstra(map, visitedList, start, destination, vehicle);
		
		/// TODO: Instead of the current code, put String parts in the main and only return the vehicle? (what to do with map?)
		if (bestTime != Integer.MAX_VALUE) {	// If no errors with the NI-NH vehicle
			builder.append("Shortest path to CLSC #" + destination.getId() + " from CLSC #" + start.getId() + " :\n"
					+ "\tVehicle type : NI-NH\n"
					+ "\tBattery remaining : " + String.format("%.2f", vehicle.chargeLeft()) + "%"
					+ "\tShortest path :\n\t");
			for (Clsc clsc : map.get(destination).getPathToPoint_()) {
				builder.append(" -> " + clsc.getId());
			}
			builder.append("\n\t\t(in " + bestTime + " minutes)");
			return builder.toString();
		}
		// If the NI-NH vehicle failed, we must try with the LI-ion vehicle
		vehicle.switchType();
		// We also reset our containers and try again
		initialize(map, visitedList, start);
		bestTime = Dijkstra(map, visitedList, start, destination, vehicle);
		
		if (bestTime != Integer.MAX_VALUE) {	// If a path has been found with the LI-ion vehicle
			// Same code as above... Only Vehicle type changes
			builder.append("Shortest path to CLSC #" + destination.getId() + " from CLSC #" + start.getId() + " :\n"
					+ "\tVehicle type : LI-ion\n"
					+ "\tBattery remaining : " + String.format("%.2f", vehicle.chargeLeft()) + "%"
					+ "\tShortest path :\n\t");
			for (Clsc clsc : map.get(destination).getPathToPoint_()) {
				builder.append(" -> " + clsc.getId());
			}
			builder.append("\n\t\t(in " + bestTime + " minutes)");
			return builder.toString();
		}
		// If both of the vehicles failed, then the move is impossible
		builder.append("ERROR : The move from CLSC #" + start.getId() + " to CLSC #" + destination.getId()
		+ " is impossible with the NI-NH vehicle nor the LI-ion vehicle because of :"
		+ "\t1) Not enough battery charge and/or no charging stations available or\n"
		+ "\t2) Invalid CLSC");	// TODO: Elaborate
		return builder.toString();
	}
	
	private void initialize(HashMap<Clsc, ClscNode> mapToInitialize, ArrayList<Clsc> listToInitialize, Clsc start) {
		// The content is reset
		mapToInitialize.clear();
		listToInitialize.clear();
		
		// The keys are initialized and the corresponding node as well
		for (Clsc point : clscArray_) {	
			mapToInitialize.put(point, new ClscNode(point));
		}
		// The starting Node is set to 0 and all the rest is set to infinity by default
		mapToInitialize.get(start).setTime_(0);
		mapToInitialize.get(start).addToPath(start);
		listToInitialize.add(start);	// We first visit the starting CLSC
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
		int minutesTaken;
		while (vehicle.chargeLeft())
		minutesTaken = Dijkstra(map, visitedList, nextClsc, destination, vehicle);
		
		return minutesTaken;
	}
}