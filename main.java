import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
		
		ArrayList<Clsc> clscArray = new ArrayList<Clsc>();
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader("/Users/client.INS-TEG0U5C6/Documents/LOG2810/TP1/centresLocaux.txt");
			br = new BufferedReader(fr);
			
			String currentLine;
			
			// Read first series of numbers that represents the Clsc's id and the presence of an electrical terminal or not.
			while (!((currentLine = br.readLine()).equals(""))) {
				if(currentLine.indexOf(",") == 1) {
					clscArray.add(new Clsc(currentLine.charAt(0) - 48, (currentLine.charAt(2) - 48) != 0));
				} else {
					clscArray.add(new Clsc(Integer.parseInt(currentLine.substring(0,2)), (currentLine.charAt(3) - 48) != 0));
				}
			}
			
			// Read the second series of numbers that represents a specific path and its crossing time.
			while((currentLine = br.readLine()) != null) {
				switch(currentLine.lastIndexOf(",")) {
				case 3 : 
					clscArray.get(currentLine.charAt(0) - 49).addPath(new Path(clscArray.get(currentLine.charAt(2) - 49), Integer.parseInt(currentLine.substring(4))));
					clscArray.get(currentLine.charAt(2) - 49).addPath(new Path(clscArray.get(currentLine.charAt(0) - 49), Integer.parseInt(currentLine.substring(4))));
					break;
				case 4 : 
					clscArray.get(currentLine.charAt(0) - 49).addPath(new Path(clscArray.get(Integer.parseInt(currentLine.substring(2,4)) - 1), Integer.parseInt(currentLine.substring(5))));
					clscArray.get(Integer.parseInt(currentLine.substring(2,4)) - 1).addPath(new Path(clscArray.get(currentLine.charAt(0) - 49), Integer.parseInt(currentLine.substring(5))));
					break;
				case 5 :	
					clscArray.get(Integer.parseInt(currentLine.substring(0,2)) - 1).addPath(new Path(clscArray.get(Integer.parseInt(currentLine.substring(3,5)) - 1), Integer.parseInt(currentLine.substring(6))));
					clscArray.get(Integer.parseInt(currentLine.substring(3,5)) - 1).addPath(new Path(clscArray.get(Integer.parseInt(currentLine.substring(0,2)) - 1), Integer.parseInt(currentLine.substring(6))));
					break;
				}
			}


		} catch (IOException e) {
			// Pour s'assurer que le fichier n'est pas vide.
			e.printStackTrace();
		}
		Graph graph = new Graph(clscArray);
		return graph;
	}
}