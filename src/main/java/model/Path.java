package main.java.model;

/**
 * Represents the path between two CLSCs. Be careful, a path is a path a vehicle takes to get from Point A to Point B, it is not a
 * regular graph edge.
 */
public class Path {

    // Holds the previous CLSC a vehicle was at before coming to the current node.
	private CLSC fPreviousNode;
	// Holds the cumulative time in a journey that a vehicle has taken to get to this point. For instance, if a vehicle has
    // travelled between 5 nodes before getting to the current one, the time of all these nodes is included here.
	private int fTime;

    /**
     * Constructor
     * @param previousNode The previous node the vehicle was at before coming to this one.
     * @param time The cumulative time to come to this node.
     */
	public Path(CLSC previousNode, int time) {
		fPreviousNode = previousNode;
		fTime = time;
	}

    /**
     * Getter of fTime
     * @return fTime
     */
	public int getTime() {
		return fTime;
	}

    /**
     * Setter of fTime
     * @param time The time to set fTime
     */
	public void setTime(int time) {
		fTime = time;
	}

    /**
     * Setter of fPreviousNode
     * @param previousNode The node to set fPreviousNode
     */
	public void setPreviousNode(CLSC previousNode) {
		fPreviousNode = previousNode;
	}

    /**
     * Getter to fPreviousNode
     * @return fPreviousNode
     */
	public CLSC getPreviousNode() {
		return fPreviousNode;
	}

    /**
     * Calculates the distance between two neighbor CLSCs.
     * @param firstNode The first CLSC
     * @param secondNode The second CLSC
     * @return The distance between these two nodes or null if they are not neighbors
     */
	static Integer getDistanceBetweenTwoNodes(CLSC firstNode, CLSC secondNode) {
		return firstNode.getNeighbors().get(secondNode);
	}


}
