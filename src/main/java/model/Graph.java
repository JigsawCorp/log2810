package main.java.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A model class that represents a non oriented weighted graph.
 */
public class Graph {

	// Holds all the CLSCs including those with charging terminals
	private ArrayList<CLSC> fCLSCArray;
	// Holds only the CLSCs with terminals to avoid iterating over fCLSCArray
	private ArrayList<CLSC> fCLSCWithTerminals;

	/**
	 * Default constructor
	 */
	public Graph() {
		fCLSCArray = new ArrayList<CLSC>();
		fCLSCWithTerminals = new ArrayList<>();
	}

	/**
	 * Adds a CLSC to the arrays. If the CLSC has an array, also add it to fCLSCWithTerminals.
	 * @param id The id of the CLSC
	 * @param hasTerminal True if the CLSC has a charging terminal.
	 */
	public void addClsc(int id, boolean hasTerminal) {
		CLSC newCLSC = new CLSC(id, hasTerminal);
		fCLSCArray.add(newCLSC);
		if (hasTerminal) {
			fCLSCWithTerminals.add(newCLSC);
		}
	}

	/**
	 * Adds the paths connecting two CLSCs into their fields. To note: The graph doesn't hold the paths contrary to most implementations.
	 * The CLSC nodes hold the paths to all their neighbors in their own class.
	 * @param first The first CLSC
	 * @param second The second CLSC
	 * @param distance The distance between these two
	 */
	public void addClscPath(int first, int second, int distance) {
		fCLSCArray.get(first - 1).getNeighbors().put(fCLSCArray.get(second - 1), distance);
		fCLSCArray.get(second - 1).getNeighbors().put(fCLSCArray.get(first - 1), distance);
	}


	/**
	 * Getter to fCLSCArray
	 * @return fCLSCArray
	 */
	public List<CLSC> getCLSCs() {
	    return fCLSCArray;
    }

	/**
	 * Getter to fCLSCWithTerminals
	 * @return fCLSCWithTerminals
	 */
	public List<CLSC> getClscWithTerminal() {
		return fCLSCWithTerminals;
	}

	/**
	 * Formats the Graph into a String
	 * @return A formatted graph
	 */
	public String toString() {
		for (int i = 0; i < fCLSCArray.size(); ++i) {
			System.out.print("CLSC_" + i + ", ");
		}
		return null;
	}
}