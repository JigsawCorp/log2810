package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	private ArrayList<Clsc> fClscArray;
	private ArrayList<Clsc> fClscWithTerminals;
	
	public Graph() {
		fClscArray = new ArrayList<Clsc>();
		fClscWithTerminals = new ArrayList<>();
	}
	
	public void addClsc(int id, boolean hasTerminal) {
		Clsc newClsc = new Clsc(id, hasTerminal);
		fClscArray.add(newClsc);
		if (hasTerminal) {
			fClscWithTerminals.add(newClsc);
		}
	}

	public void addClscPath(int first, int second, int distance) {
		fClscArray.get(first - 1).getNeigbourghs().put(fClscArray.get(second - 1), distance);
		fClscArray.get(second - 1).getNeigbourghs().put(fClscArray.get(first - 1), distance);
	}
	/**
	 * 
	 * @param start -Summit we're starting from
	 * @param destination -Desired summit
	 * @return Prints: -Vehicle type, -% remaining, -Shortest Path, -Time
	 */
	public ArrayList<Clsc> findShortestPath(Clsc start, Clsc destination) {
		ArrayList<Clsc> shortestPath = new ArrayList<Clsc>();
		//int time = Dijkstra(shortestPath, start, destination);
		return shortestPath;
	}
	
	/*
	private int Dijkstra(ArrayList<Clsc> path, Clsc start, Clsc destination) {
		int time;
		
		return time;
	}*/
	/*public String toString() {
		return fClscArray.toString() + "\n\n" + pathArray_.toString();
	}
	*/

	public List<Clsc> getCLSCs() {
	    return fClscArray;
    }

    public List<Clsc> getClscWithTerminal() {
		return fClscWithTerminals;
	}
	public String toString() {
		for (int i = 0; i < fClscArray.size(); ++i) {
			System.out.print("CLSC_" + i + ", ");
		}
		return null;
	}
}