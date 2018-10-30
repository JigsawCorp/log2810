package main.java.model;

import java.util.HashMap;

/**
 * Represents a Graph node.
 */
public class CLSC {

    // Holds the ID of the CLSC. Starting from 1 to infinity.
	private int fID;
	// Is true if the CLSC has a charging terminal.
	private boolean fHasTerminal;
	// Holds all the neighbors of a CLSC node. The key is the neighbor CLSC and the value is the distance in minutes.
	private HashMap<CLSC, Integer> fNeighbors;

    /**
     * Constructor
     * @param id The id of the CLSC
     * @param hasTerminal True if the CLSC has a charging terminal
     */
	CLSC(int id, boolean hasTerminal) {
		fID = id;
		fHasTerminal = hasTerminal;
		fNeighbors = new HashMap<>();
	}

    /**
     * Getter of fHasTerminal
     * @return fHasTerminal
     */
	boolean hasCharge() {
		return fHasTerminal;
	}

    /**
     * Getter of fNeighbors
     * @return fNeighbors
     */
	public HashMap<CLSC, Integer> getNeighbors() {
		return fNeighbors;
	}

    /**
     * Formats a CLSC into a String
     * @return A formatted String
     */
	public String toString() {
		return "CLSC" + fID + ", " + fHasTerminal + "\n";
	}
}