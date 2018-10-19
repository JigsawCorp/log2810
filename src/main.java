import model.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class main {

	public static void main(String[] args){
		Graph graph = readTextFile();
		System.out.println(graph.toString());
	}
	
	/**
	 * 
	 * @return graph:  generated with text file.
	 */
	private static  Graph readTextFile() {
		
		Graph graph = new Graph();
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader("resources/centresLocaux.txt");
			br = new BufferedReader(fr);
			
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