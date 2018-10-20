package main.java.utility;

import main.java.model.Clsc;
import main.java.model.Graph;
import main.java.model.Patient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Dijkstra {
    public static void getShortestPath(Clsc startPoint, Clsc endPoint, Patient.Type patientType, Graph graph) {
        HashMap<Clsc, Integer> clscPathLenghts = new HashMap<>();
        Set<Clsc> examinedNodes = new HashSet<>();
        for (int i = 0; i < graph.getCLSCs().size(); ++i) {
            if (startPoint == graph.getCLSCs().get(i)) {
                clscPathLenghts.put(graph.getCLSCs().get(i), 0);
            }
            else {
                clscPathLenghts.put(graph.getCLSCs().get(i), -1);
            }
        }

        while (!examinedNodes.contains(endPoint)) {
         //   for (int i = 0; )
        }
    }
}
