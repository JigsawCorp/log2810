package main.java.utility;

import main.java.model.Clsc;
import main.java.model.Graph;
import main.java.model.Path;
import main.java.model.Patient;

import java.util.*;

public class Dijkstra {

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
                //pathLengths.replace(entry.getKey(), node.getNeigbourghs().get(entry.getKey()) + pathLengths.get(node).getTime());
                pathLengths.get(entry.getKey()).setTime(node.getNeigbourghs().get(entry.getKey()) + pathLengths.get(node).getTime());
            }
        }
    }

    public static void getShortestPath(Clsc startPoint, Patient.Type patientType, Graph graph, Clsc endPoint) {
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
                clscPathLenghts.put(graph.getCLSCs().get(i), new Path(graph.getCLSCs().get(i), Integer.MAX_VALUE));
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

        System.out.println("The Shortest path from the CLSC" + startPoint.getId() + " to the CLSC" + endPoint.getId() + " is " + clscPathLenghts.get(endPoint).getTime());

    }
}
