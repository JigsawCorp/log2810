package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	private ArrayList<Clsc> clscArray_;
	private ArrayList<Path> pathArray_;
	
	public Graph() {
		clscArray_ = new ArrayList<Clsc>();
		pathArray_ = new ArrayList<Path>();
	}
	
	public void addPath (int first,int second, int time) {
		pathArray_.add(new Path(clscArray_.get(first - 1), clscArray_.get(second - 1), time));
		pathArray_.add(new Path(clscArray_.get(second - 1), clscArray_.get(first - 1), time));
	}
	
	public void addClsc(int id, boolean hasTerminal) {
		clscArray_.add(new Clsc(id, hasTerminal));
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
		return clscArray_.toString() + "\n\n" + pathArray_.toString();
	}
	*/

	public List<Clsc> getCLSCs() {
	    return clscArray_;
    }

	public String toString() {
		for (int i = 0; i < clscArray_.size(); ++i) {
			System.out.print("CLSC_" + i + ", ");
		}
		return null;
	}
}