package main.java.utility;

import main.java.model.*;

import java.util.*;

public class Dijkstra {

    public static Vehicle getShortestPath(Clsc startingPoint, Clsc finishPoint, Graph graph, Patient.Type patientType) {
        HashMap<Clsc, Path> shortestPathsFromStart = getAllShortestPaths(startingPoint, graph);
        Vehicle vehicle = new Vehicle(patientType);
        vehicle.getShortestPath(startingPoint, finishPoint, graph);
        return vehicle;
    }

    /**
     * Takes in a HashMap<Clsc,Path> and returns a list of all CLCSs to go through in order to reach the destination.
     * @param startNode The starting CLSC
     * @param endNode The destination CLSC
     * @param pathLengths The HashMap<Clsc,Path> that holds all shortest paths
     * @return A List<Clsc> that holds in chronological order how to get from the starting CLSC to the destination CLSC, where
     * the first element is the starting CLSC and the last element is the destination CLSC.
     */
    public static List<Clsc> getShortestPathBetweenTwoNodes(Clsc startNode, Clsc endNode, HashMap<Clsc, Path> pathLengths) {
        Clsc currentNode = endNode;
        List<Clsc> shortestPath = new ArrayList<>();
        shortestPath.add(endNode);
        while (currentNode != startNode) {
            currentNode = pathLengths.get(currentNode).getPreviousNode();
            shortestPath.add(currentNode);
        }
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    public static Integer getDistance(Clsc startNode, Clsc endNode) {
        return startNode.getNeigbourghs().get(endNode);
    }


    public static Clsc getSmallestDistanceNode(Clsc startingNode, Set<Clsc> examinedNodes, HashMap<Clsc, Path> paths) {
        Map.Entry<Clsc, Path> minimum = null;
        for (Map.Entry<Clsc, Path> entry : paths.entrySet()) {
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


    public static void updateNeibourghs(Clsc node, HashMap<Clsc, Path> pathLengths, Set<Clsc> examinedNodes) {
        for (Map.Entry<Clsc, Integer> entry : node.getNeigbourghs().entrySet()) {
            if (!examinedNodes.contains(entry.getKey()) && (pathLengths.get(node).getTime() + node.getNeigbourghs().get(entry.getKey())) < pathLengths.get(entry.getKey()).getTime()) {
                pathLengths.get(entry.getKey()).setTime(node.getNeigbourghs().get(entry.getKey()) + pathLengths.get(node).getTime());
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
    public static HashMap<Clsc, Path> getAllShortestPaths(Clsc startPoint, Graph graph) {
        HashMap<Clsc, Path> clscPathLenghts = new HashMap<>();
        Set<Clsc> examinedNodes = new HashSet<>();
        Set<Clsc> toHandleNodes = new HashSet<>();
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

        updateNeibourghs(startPoint, clscPathLenghts, examinedNodes);
        while(!toHandleNodes.isEmpty()) {
            Clsc minimalPathClsc = getSmallestDistanceNode(startPoint, examinedNodes, clscPathLenghts);
            examinedNodes.add(minimalPathClsc);
            if (minimalPathClsc != null) {
                updateNeibourghs(minimalPathClsc, clscPathLenghts, examinedNodes);
                toHandleNodes.remove(minimalPathClsc);
            }
            toHandleNodes.remove(minimalPathClsc);

        }

        //System.out.println("The Shortest path from the CLSC" + startPoint.getId() + " to the CLSC" + endPoint.getId() + " is " + clscPathLenghts.get(endPoint).getTime());
        return clscPathLenghts;
    }
}
