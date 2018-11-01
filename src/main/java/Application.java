package main.java;

import main.java.model.Graph;
import main.java.model.Patient;
import main.java.model.Vehicle;
import main.java.utility.Dijkstra;
import main.java.utility.FileReadingUtility;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

/**
 * Class controlling the logic of the application.
 */
public class Application {
    private Graph fGraph;
    /**
     * Main entry point that starts the logic of the application. Parses the default text file and calls the method to read user inputs and react accordingly.
     */
    public void start() {
        // Parse the default text file into a graph
        fGraph= FileReadingUtility.createGraph(FileReadingUtility.DEFAULT_FILE_PATH);
        System.out.println(Vehicle.getLongestPath(fGraph.getCLSCs().get(2), Vehicle.BatteryType.NI_NH, Patient.Type.MEDIUM_RISK));
        //Vehicle.getShortestPath(graph.getCLSCs().get(22), graph.getCLSCs().get(19), graph, Patient.Type.HIGH_RISK);
        // Call the recursive method the constantly awaits a user input and reacts to it.
        presentChoices();
    }

    /**
     * A recursive method that constantly asks the user what he wants to do and then reacts to the input accordingly.
     */
    private void presentChoices() {
        final String MENU = "(a) Mettre à jour carte.\n"
                + "(b) Déterminer le plus court chemin sécuritaire.\n"
                + "(c) Extraire sous-graphe.\n"
                + "(d) Quitter.\n";
        String selectedOption = JOptionPane.showInputDialog(MENU);
            //System.out.println("Choice is : " + choice);
            switch (selectedOption.toLowerCase()) {
                case "a":
                    System.out.println("Choice is : a");
                    updateGraph();
                    break;
                case "b":
                    System.out.println("Choice is : b");

                    shortestPath();
                    break;
                case "c":
                    System.out.println("Choice is : c");
                    //Dijkstra.getShortestPath()
                    break;
                case "d":
                    System.out.println("ヾ( ´･_ゝ･`)ノ");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Index invalide. Veuillez ressayer.");
            }
        presentChoices();
    }

    private void updateGraph() {
        String newGraphFile = JOptionPane.showInputDialog("Entrer le nom du fichier texte de la nouvelle carte avec son chemin.");
        fGraph = FileReadingUtility.createGraph(newGraphFile);
        //System.out.println(graph);
    }

    private void shortestPath() {
        int startClscID = askStartId(fGraph.getCLSCs().size());
        int endClscID = askEndId(fGraph.getCLSCs().size());
        boolean invalidEntry = true;
        String shortestPath = "";
        while(invalidEntry) {
            String transportType = JOptionPane.showInputDialog("Choisir le type de transport. \n"
                    + "(1) Transport à faible risque. \n"
                    + "(2) Transport à moyen risque. \n"
                    + "(3) Trasport à haut risque. \n");
            switch (transportType) {
                case "1":
                    shortestPath = Vehicle.getShortestPath(fGraph.getCLSCs().get(startClscID - 1), fGraph.getCLSCs().get(endClscID - 1), fGraph, Patient.Type.LOW_RISK).toString();
                    invalidEntry = false;
                    break;
                case "2":
                    shortestPath = Vehicle.getShortestPath(fGraph.getCLSCs().get(startClscID - 1), fGraph.getCLSCs().get(endClscID - 1), fGraph, Patient.Type.MEDIUM_RISK).toString();
                    invalidEntry = false;
                    break;
                case "3":
                    shortestPath = Vehicle.getShortestPath(fGraph.getCLSCs().get(startClscID - 1), fGraph.getCLSCs().get(endClscID - 1), fGraph, Patient.Type.HIGH_RISK).toString();
                    invalidEntry = false;
                    break;
                default:
                    System.out.println("L'entrée pour le type de transport est invalide!");
                    break;
            }

        }
        System.out.println(shortestPath);

    }


    private int askStartId(int nbrDeClsc) {
        int startIdInt = 0;
        String startId = JOptionPane.showInputDialog("Entrer l'id du CLSC de départ.");
        try{
            if(Integer.parseInt(startId) < 0 || Integer.parseInt(startId) > nbrDeClsc) {
                System.out.println("L'id de départ est invalide!");
                askStartId(nbrDeClsc);
            } else {
                startIdInt = Integer.parseInt(startId);
            }
        } catch (NumberFormatException e) {
            System.out.println("L'id de départ est invalide!");
            askStartId(nbrDeClsc);
        }
        return startIdInt;
    }

    private int askEndId(int nbrDeClsc) {
        int endIdInt = 0;
        String endId = JOptionPane.showInputDialog("Entrer l'id du CLSC d'arrivée.");
        try{
            if(Integer.parseInt(endId) < 0 || Integer.parseInt(endId) > nbrDeClsc) {
                System.out.println("L'id de départ est invalide!");
                askEndId(nbrDeClsc);
            } else {
                endIdInt = Integer.parseInt(endId);
            }
        } catch (NumberFormatException e) {
            System.out.println("L'id de départ est invalide!");
            askEndId(nbrDeClsc);
        }
        return endIdInt;
    }

    private void extractGraph(Graph graph) {
        int clscId = askClscId(graph.getCLSCs().size());
        boolean invalidEntry = true;
        while (invalidEntry) {
            String vehicleType = JOptionPane.showInputDialog("Choisir le type de vehicule. \n"
                    + "(1) Véhicule médicalisé NI-NH. \n"
                    + "(2) Véhicule médicalisé LI-ion. \n");
            switch (vehicleType) {
                case "1":
                    //graph.findLongestPath(clscId, NI-NH);
                    invalidEntry = false;
                    break;
                case "2":
                    //graph.findLongestPath(clscId, LI-ion);
                    invalidEntry = false;
                    break;
                default:
                    System.out.println("L'entrée pour le type de véhicule est invalide!");
                    break;
            }
        }

    }

    private static int askClscId(int nbrDeClsc) {
        int clscIdInt = 0;
        String clscId = JOptionPane.showInputDialog("Entrer l'id du sommet.");
        try {
            if(Integer.parseInt(clscId) < 0 || Integer.parseInt(clscId) > nbrDeClsc) {
                System.out.println("L'id du sommet est invalide");
                askClscId(nbrDeClsc);
            } else {
                clscIdInt = Integer.parseInt(clscId);
            }
        } catch (NumberFormatException e) {
            System.out.println("L'id du sommet est invalide");
            askClscId(nbrDeClsc);
        }
        return clscIdInt;
    }

}

