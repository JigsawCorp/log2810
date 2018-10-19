package main.java;

import main.java.model.Graph;
import main.java.utility.FileReadingUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
    public void start() {
        Graph graph = new FileReadingUtility().readTextFile();
        System.out.println(graph.toString());
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
                    System.out.println("Choice is : b");
                    break;
                case "c":
                    System.out.println("Choice is : c");
                    break;
                case "d":
                    System.out.println("Choice is : d");
                    System.exit(0);
                    break;
            }
        } catch (IOException e) {

        }
        presentChoices();

    }

}
