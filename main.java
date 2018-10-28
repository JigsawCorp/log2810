import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.omg.CORBA.DynAnyPackage.Invalid;

public class main {

	public static void main(String[] args){
		
		Graph graph = readTextFile("/Users/samue/Desktop/POLY/AUT_2018/LOG2810/TP1/LOG2810_TP1/centresLocaux.txt");
		System.out.println(graph);
		final String MENU = "(a) Mettre à jour carte.\n"
							+ "(b) Déterminer le plus court chemin sécuritaire.\n"
							+ "(c) Extraire sous-graphe.\n"
							+ "(d) Quitter.\n";
		
		for (;;) {
			String selectedOption = JOptionPane.showInputDialog(MENU);
			selectedOption = selectedOption.toLowerCase();
			switch (selectedOption) {
			case "a" : 
				updateGraph(graph);
				break;
			case "b" : 
				shortestPath(graph);
				break;
			case "c" : 
				extractGraph(graph);
				break;
			case "d" : 
				System.exit(0);
				break;
			default : 
				System.out.println("Ceci n'est pas une option!");
				break;
			}
			
		}
	}
	
	/**
	 * 
	 * @param graph: graph to modify
	 */
	private static void updateGraph(Graph graph) {
		String newGraphFile = JOptionPane.showInputDialog("Entrer le nom du fichier texte de la nouvelle carte avec son chemin.");
		graph = readTextFile(newGraphFile);
		System.out.println(graph);
	}
	
	private static void shortestPath(Graph graph) {
		int startClscID = askStartId(graph.getArray().size());
		int endClscID = askEndId(graph.getArray().size());
		boolean invalidEntry = true;
		String shortestPath = "";
		while(invalidEntry) {
			String transportType = JOptionPane.showInputDialog("Choisir le type de transport. \n"
														 + "(1) Transport à faible risque. \n"
														 + "(2) Transport à moyen risque. \n"
														 + "(3) Trasport à haut risque. \n");
			switch (transportType) {
			case "1":
				shortestPath = graph.findShortestPath(graph.getArray().get(startClscID - 1), graph.getArray().get(endClscID - 1), new Vehicle(1));
				invalidEntry = false;
				break;
			case "2":
				shortestPath = graph.findShortestPath(graph.getArray().get(startClscID - 1), graph.getArray().get(endClscID - 1), new Vehicle(2));
				invalidEntry = false;
				break;
			case "3":
				shortestPath = graph.findShortestPath(graph.getArray().get(startClscID - 1), graph.getArray().get(endClscID - 1), new Vehicle(3));
				invalidEntry = false;
				break;
			default:
				System.out.println("L'entrée pour le type de transport est invalide!");
				break;
			}
			
		}
		System.out.println(shortestPath);
		
	}
	
	
	private static int askStartId(int nbrDeClsc) {
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
	
	private static int askEndId(int nbrDeClsc) {
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
	
	private static void extractGraph(Graph graph) {
		int clscId = askClscId(graph.getArray().size());
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
	/**
	 * Reads a text file associated to a graph.
	 * @return graph:  generated with text file.
	 */
	private static  Graph readTextFile(String nomFichier) {
		
		ArrayList<Clsc> clscArray = new ArrayList<Clsc>();
		ArrayList<Clsc> clscBorne = new ArrayList<Clsc>();
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader(nomFichier);
			br = new BufferedReader(fr);
			
			String currentLine;
			
			// Read first series of numbers that represents the Clsc's id and the presence of an electrical terminal or not.
			while (!((currentLine = br.readLine()).equals(""))) {
				String[] splitLine = currentLine.split(",");
				Clsc clsc = new Clsc(Integer.parseInt(splitLine[0]), splitLine[1] != "0");
				clscArray.add(clsc);
				if(splitLine[1] == "0") 
					clscBorne.add(clsc);
			}
			// Read the second series of numbers that represents a specific path and its crossing time.
			while((currentLine = br.readLine()) != null) {
				String[] splitLine = currentLine.split(",");
				clscArray.get(Integer.parseInt(splitLine[0]) - 1).addPath(new Path(clscArray.get(Integer.parseInt(splitLine[1]) - 1), Integer.parseInt((splitLine[2]))));
				clscArray.get(Integer.parseInt(splitLine[1]) - 1).addPath(new Path(clscArray.get(Integer.parseInt(splitLine[0]) - 1), Integer.parseInt((splitLine[2]))));
			}

		} catch (IOException e) {
			// Pour s'assurer que le fichier n'est pas vide.
			e.printStackTrace();
		}
		Graph graph = new Graph(clscArray,clscBorne);
		return graph;
	}
}