package main.java.utility;

import main.java.Main;
import main.java.model.Graph;

import java.io.*;
import java.net.URISyntaxException;

/**
 * Utility class to parse text files into graphs
 */
public class FileReadingUtility {

    // Path to the default text file
    public static final String DEFAULT_FILE_PATH = "/centresLocaux.txt";

    /**
     * Parses a text file into a graph
     * @param fileName Path to a text file to parse into a graph
     * @return A graph made from the text file
     */
    public Graph createGraph(String fileName) {
        Graph graph = new Graph();

        InputStream inputStream = Main.class.getResourceAsStream(fileName);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String currentLine;

            // Read first series of numbers that represents the CLSC's id and the presence of an electrical terminal or not.
            while (!((currentLine = br.readLine()).isEmpty())) {
                // The CLSCs ids and terminals are seperated by a comma
                String[] splitString = currentLine.split(",");
                graph.addClsc(Integer.parseInt(splitString[0]), splitString[1].equals("1"));
            }

            // Read the second series of numbers that represents a specific path and its crossing time.
            while((currentLine = br.readLine()) != null) {
                // The two CLSCs and the path length are seperated by commas
                String[] splitString = currentLine.split(",");
                // First String is the first CLSC, second is the second CLSC, and the last is the path length
                graph.addClscPath(Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]), Integer.parseInt(splitString[2]));
            }


        } catch (IOException e) {
            // Make sure the file is not empty
            e.printStackTrace();
        }
        return graph;
    }
}
