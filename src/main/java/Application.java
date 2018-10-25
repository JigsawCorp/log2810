package main.java;

import main.java.model.Graph;
import main.java.model.Patient;
import main.java.utility.Dijkstra;
import main.java.utility.FileReadingUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

public class Application {
    public void start() {
        Graph graph = new FileReadingUtility().createGraph(FileReadingUtility.DEFAULT_FILE_PATH);
        System.out.println(graph.toString());
        //Dijkstra.getShortestPath(graph.getCLSCs().get(1), Patient.Type.HIGH_RISK, graph, graph.getCLSCs().get(3));
        Dijkstra.getShortestPath(graph.getCLSCs().get(1), Patient.Type.HIGH_RISK, graph, graph.getCLSCs().get(22));
        presentChoices();
    }

    private void presentChoices() {
        System.out.println("(a) Mettre a jour la carte. \n(b) Determiner le plus court chemin securitaire. \n(c) Extraire un sous-graphe. \n(d) Quitter.");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String choice = br.readLine();
            //System.out.println("Choice is : " + choice);
            switch (choice) {
                case "a":
                    System.out.println("Choice is : a");
                    break;
                case "b":
                    String startChoice, endChoice, patientChoice;
                    System.out.println("Quel est votre point de depart?");
                    startChoice = br.readLine();
                    System.out.println("Quel est votre destination?");
                    endChoice = br.readLine();
                    System.out.println("Quel type de patient voulez-vous transporter?");
                    patientChoice = br.readLine();
                   // Dijkstra.getShortestPath(Integer.parseInt(startChoice), Patient.Type.HIGH_RISK, );
                    //Dijkstra.getShortestPath(Integer.parseInt(startChoice), Integer.parseInt(endChoice), );
                    break;
                case "c":
                    System.out.println("Choice is : c");
                    break;
                case "d":
                    try {
                        System.setProperty("file.encoding", "UTF-8");
                        Field charset = Charset.class.getDeclaredField("defaultCharset");
                        charset.setAccessible(true);
                        charset.set(null, null);
                    } catch (IllegalAccessException | NoSuchFieldException d) {

                }
                    System.out.println("ヾ( ´･_ゝ･`)ノ");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Index invalide. Veuillez ressayer.");
            }
        } catch (IOException e) {

        }
        presentChoices();

    }

    private void readGraph() {

    }

}
