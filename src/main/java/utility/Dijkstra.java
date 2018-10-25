package main.java.utility;

import main.java.model.Clsc;
import main.java.model.Graph;
import main.java.model.Patient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Dijkstra {
    public static Integer getDistance(Clsc startPoint, Clsc endPoint) {

    }
    public static void getShortestPath(Clsc startPoint, Clsc endPoint, Patient.Type patientType, Graph graph) {
        HashMap<Clsc, Integer> clscPathLenghts = new HashMap<>();
        Set<Clsc> examinedNodes = new HashSet<>();
        Set<Clsc> toHandleNodes = new HashSet<>();
        for (int i = 0; i < graph.getCLSCs().size(); ++i) {
            toHandleNodes.add(graph.getCLSCs().get(i));
            if (startPoint == graph.getCLSCs().get(i)) {
                clscPathLenghts.put(graph.getCLSCs().get(i), 0);
            }
            else {
                clscPathLenghts.put(graph.getCLSCs().get(i), -1);
            }
        }

        while (!examinedNodes.contains(endPoint)) {
            Iterator<Clsc> iterator = toHandleNodes.iterator();
            Clsc minimum = toHandleNodes.iterator().next();
            while (iterator.hasNext()) {
                Clsc currentClsc = iterator.next();
                if (clscPathLenghts.get(currentClsc) < clscPathLenghts.get(minimum)) {
                    minimum = currentClsc;
                }
            }
            examinedNodes.add(minimum);
            iterator = toHandleNodes.iterator();
            while (iterator.hasNext()) {
                Clsc currentClsc = iterator.next();
                if (clscPathLenghts.get(minimum) + 1 < clscPathLenghts.get(currentClsc))
            }
        }
    }
}
