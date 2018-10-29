package main.java.utility;

import main.java.model.*;

import java.util.*;

/**
 * Utility class that does all calculations associated with Dijkstra's algorithm.
 */
public class Dijkstra {

    /**
     * Takes in a HashMap<CLSC,Path> and returns a list of all CLCSs to go through in order to reach the destination.
     * @param startNode The starting CLSC
     * @param endNode The destination CLSC
     * @param pathLengths The HashMap<CLSC,Path> that holds all shortest paths
     * @return A List<CLSC> that holds in chronological order how to get from the starting CLSC to the destination CLSC, where
     * the first element is the starting CLSC and the last element is the destination CLSC.
     */
    public static List<CLSC> getShortestPathBetweenTwoNodes(CLSC startNode, CLSC endNode, HashMap<CLSC, Path> pathLengths) {
        CLSC currentNode = endNode;
        List<CLSC> shortestPath = new ArrayList<>();
        shortestPath.add(endNode);
        while (currentNode != startNode) {
            currentNode = pathLengths.get(currentNode).getPreviousNode();
            shortestPath.add(currentNode);
        }
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    /**
     * Finds the node not yet examined with the smalled distance to the starting point.
     * @param examinedNodes The nodes that have already been examined
     * @param paths The paths to get to each nodes.
     * @return The node not yet examined with the smallest distance to the starting point.
     */
    private static CLSC getSmallestDistanceNode(Set<CLSC> examinedNodes, HashMap<CLSC, Path> paths) {
        Map.Entry<CLSC, Path> minimum = null;
        for (Map.Entry<CLSC, Path> entry : paths.entrySet()) {
            if (minimum == null) {
                if (!examinedNodes.contains(entry.getKey())) {
                    minimum = entry;
                }
            }
            else {
                if (entry.getValue().getTime() < minimum.getValue().getTime() && !examinedNodes.contains(entry.getKey())) {
                    minimum = entry;
                }
            }
        }

        if (minimum == null) {
            return null;
        }

        return minimum.getKey();
    }

    /**
     * Updates all the neighbors nodes of a specific node to the new distances and paths to reach them.
     * @param node The node which we want to update its neighbors.
     * @param pathLengths The paths to reach all the nodes in our graphs from a starting point.
     * @param examinedNodes The examined nodes in the algorithm.
     */
    private static void updateNeighbors(CLSC node, HashMap<CLSC, Path> pathLengths, Set<CLSC> examinedNodes) {
        for (Map.Entry<CLSC, Integer> entry : node.getNeighbors().entrySet()) {
            if (!examinedNodes.contains(entry.getKey()) && (pathLengths.get(node).getTime() + node.getNeighbors().get(entry.getKey())) < pathLengths.get(entry.getKey()).getTime()) {
                pathLengths.get(entry.getKey()).setTime(node.getNeighbors().get(entry.getKey()) + pathLengths.get(node).getTime());
                pathLengths.get(entry.getKey()).setPreviousNode(node);
            }
        }
    }

    /**
     * Calculates from a starting node and a graph all shortest paths to get to every nodes from the starting point.
     * @param startPoint
     * @param graph
     * @return A HashMap where the key is a certain node and the value is a class that holds the previous node (How to get to the current node) and the cummulative distance to get
     * to this node.
     */
    public static HashMap<CLSC, Path> getAllShortestPaths(CLSC startPoint, Graph graph) {
        HashMap<CLSC, Path> clscPathLenghts = new HashMap<>();
        Set<CLSC> examinedNodes = new HashSet<>();
        Set<CLSC> toHandleNodes = new HashSet<>();
        for (int i = 0; i < graph.getCLSCs().size(); ++i) {
            toHandleNodes.add(graph.getCLSCs().get(i));
            if (startPoint == graph.getCLSCs().get(i))
            {
                clscPathLenghts.put(graph.getCLSCs().get(i), new Path(graph.getCLSCs().get(i), 0));
            }
            else {
                clscPathLenghts.put(graph.getCLSCs().get(i), new Path(null, Integer.MAX_VALUE));
            }
        }

        updateNeighbors(startPoint, clscPathLenghts, examinedNodes);
        while(!toHandleNodes.isEmpty()) {
            CLSC minimalPathCLSC = getSmallestDistanceNode(examinedNodes, clscPathLenghts);
            examinedNodes.add(minimalPathCLSC);
            if (minimalPathCLSC != null) {
                updateNeighbors(minimalPathCLSC, clscPathLenghts, examinedNodes);
                toHandleNodes.remove(minimalPathCLSC);
            }
            toHandleNodes.remove(minimalPathCLSC);

        }

        return clscPathLenghts;
    }
}
