import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.x500.X500Principal;


/**
 * @author Samuel Saito-Gagné
 * @coauthor nobody
 *
 */
public class Graph {
	
	ArrayList<Clsc> clscArray_;
	ArrayList<Clsc> chargingStations_;
	
	Graph(ArrayList<Clsc> clscArray, ArrayList<Clsc> chargingStations) {
		clscArray_ = clscArray;
		chargingStations_ = chargingStations;
	}
	 
	public Clsc getClscById(int id) {
		return clscArray_.get(id - 1);
	}
	
	public ArrayList<Clsc> getArray() {
		return clscArray_;
	}
	
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
		initialize(map, visitedList, start, vehicle);
		
		int bestTime = Dijkstra(map, visitedList, start, destination, vehicle);
		
		/// TODO: Instead of the current code, put String parts in the main and only return the vehicle? (what to do with map?)
		if (bestTime != Integer.MAX_VALUE) {	// If no errors with the NI-NH vehicle
			builder.append("Shortest path to CLSC #" + destination.getId() + " from CLSC #" + start.getId() + " :\n"
					+ "\tVehicle type : NI-NH\n"
					+ "\tBattery remaining : " + String.format("%.2f", vehicle.chargeLeft()) + "%\n"
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
		initialize(map, visitedList, start, vehicle);
		bestTime = Dijkstra(map, visitedList, start, destination, vehicle);
		
		if (bestTime != Integer.MAX_VALUE) {	// If a path has been found with the LI-ion vehicle
			// Same code as above... Only Vehicle type changes
			builder.append("Shortest path to CLSC #" + destination.getId() + " from CLSC #" + start.getId() + " :\n"
					+ "\tVehicle type : LI-ion\n"
					+ "\tBattery remaining : " + String.format("%.2f", vehicle.chargeLeft()) + "%\n"
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
	
	private void initialize(HashMap<Clsc, ClscNode> mapToInitialize, ArrayList<Clsc> listToInitialize, Clsc start, Vehicle vehicle) {
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
		
		// The battery is fully charged
		vehicle.charge();
	}
	
	/* 1- We find the shortest CLSC not in visited
	 * 2- Every unvisited neighbor has a) its time changed and b) its path changed
	 */
	// TODO: update the algorithm with Vehicle
	private int Dijkstra(HashMap<Clsc, ClscNode> map, ArrayList<Clsc> visitedList, Clsc start, Clsc destination, Vehicle vehicle) {
				
		// Case if our destination is at the same place of our start
		if (start == destination)
			return 0;
		
		int shortestTime = Integer.MAX_VALUE;
		Clsc nextClsc = null;
		
		// For every known (visited) CLSC...
		for (Clsc visited : visitedList) {
			// ...We check every path
			for (Path path : visited.getPaths()) {
				Clsc currentClsc = path.getDestination();
				ClscNode neighbor = map.get(currentClsc);
				// We update all of visited's neighbors
				neighbor.update(map.get(visited), path.getTime());
				// We find the shortest point to a neighbor not visited yet
				if (!visitedList.contains(currentClsc)) {		// We make sure we're not going back to a CLSC we already visited
					// We only save the CLSC if it's a shorter path and if the vehicle can do the move
					if (neighbor.getTime_() < shortestTime) {
						nextClsc = currentClsc;
						shortestTime = neighbor.getTime_();
					}
				}			
			}
		}
		// Handle when no CLSC has been found TODO Probably useless
		if (nextClsc == null) {
			return Integer.MAX_VALUE;
		}
		
		// We add the closest CLSC to the list of visited
		visitedList.add(nextClsc);
		
		// We check if we are at the destination, if we're not we keep going until we are
		if (nextClsc != destination) 
			return Dijkstra(map, visitedList, nextClsc, destination, vehicle);
		
		// If we are, we have to check if the vehicle can move to the CLSC with enough battery
		ClscNode nextNode = map.get(nextClsc);
		if (vehicle.moveIsPossible(nextNode.getTime_())) {	// If we can, there's no problem
			vehicle.travelFor(nextNode.getTime_());
			return nextNode.getTime_();
		}
		
		// If we can't, we need to find a new path to the destination that passes by a charging station		
		ClscNode successfulChargePath = findChargingPath(visitedList.get(0), destination, vehicle);
		
		// If we have found one, we update the map and return the time it took
		if (successfulChargePath != null) {
			map.put(destination, successfulChargePath);
			return successfulChargePath.getTime_();
		}
			
		
		// If a Clsc still hasn't been found, it's not possible and we return MAX_VALUE
		return Integer.MAX_VALUE;

	}
	
	private ClscNode findChargingPath(Clsc start, Clsc destination, Vehicle vehicle) {
		
		// Stores all the possible paths
		ArrayList<ClscNode> pathsFound = new ArrayList<ClscNode>();

		// We check every charging station, and try to find those where it's possible to do start -> charge and charge -> destination
		for (Clsc charge : chargingStations_) {
			HashMap<Clsc, ClscNode> mapStartToCharge = new HashMap<Clsc, ClscNode>();
			ArrayList<Clsc> visitedList = new ArrayList<Clsc>();
			Vehicle vehicleCopy = new Vehicle(vehicle);
			initialize(mapStartToCharge, visitedList, start, vehicleCopy);
			
			// First, from the start to the charge
			int timeFromStartToCharge = Dijkstra(mapStartToCharge, visitedList, start, charge, vehicleCopy);
			
			HashMap<Clsc, ClscNode> mapChargeToDestination = new HashMap<Clsc, ClscNode>();		
			initialize(mapChargeToDestination, visitedList, charge, vehicleCopy);
			
			// Second, from charge to destination
			int timeFromChargeToDestination = Dijkstra(mapChargeToDestination, visitedList, charge, destination, vehicleCopy);
			
			// If a path has been found for both
			if (timeFromStartToCharge != Integer.MAX_VALUE && timeFromChargeToDestination != Integer.MAX_VALUE) {
				ClscNode destinationWithCharge = new ClscNode(destination);
				ClscNode toCharge =  mapStartToCharge.get(charge);
				ClscNode toDestination =  mapStartToCharge.get(destination);
				// We combine both of the paths with each other then add the successful path to the list
				destinationWithCharge = toCharge.combine(toDestination);
				pathsFound.add(destinationWithCharge);
			}
		}
		
		// We compare all the paths we found
		ClscNode best = null;
		int bestTime = Integer.MAX_VALUE;
		for (ClscNode current : pathsFound) {
			if (current.getTime_() < bestTime) {
				best = current;
				bestTime = current.getTime_();
			}
		}
		return best;
		
	}
}