package main.java.utility;

import main.java.Main;
import main.java.model.Graph;

import java.io.*;
import java.net.URISyntaxException;

public class FileReadingUtility {
    public static final String DEFAULT_FILE_PATH = "/centresLocaux.txt";

    public Graph createGraph(String fileName) {
        Graph graph = new Graph();

        InputStream inputStream = Main.class.getResourceAsStream(fileName);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String currentLine;

            // Read first series of numbers that represents the Clsc's id and the presence of an electrical terminal or not.
            while (!((currentLine = br.readLine()).isEmpty())) {
                String[] splitString = currentLine.split(",");
                graph.addClsc(Integer.parseInt(splitString[0]), splitString[1].equals("1"));
                //graph.addClsc(Integer.parseInt(currentLine.substring(0,2)), (currentLine.charAt(3) - 48) != 0);
            }

            // Read the second series of numbers that represents a specific path and its crossing time.
            while((currentLine = br.readLine()) != null) {
                String[] splitString = currentLine.split(",");
                graph.addPath(Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]), Integer.parseInt(splitString[2]));
            }


        } catch (IOException e) {
            // Pour s'assurer que le fichier n'est pas vide.
            e.printStackTrace();
        }
        return graph;
    }
}
