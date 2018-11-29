import com.sun.tools.javac.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Class that does everything
 */
public class Lexicon {
	
	private Map<Character, Node> startStates;
	private Node currentNode;
	private Queue<Node> top5;

	public Lexicon() {
	    top5 = new LinkedList<>();
	    startStates = new HashMap<>();
    }
	
	public static Lexicon newLexicon(String file) {
		Lexicon lexicon = new Lexicon();
        InputStream inputStream = Main.class.getResourceAsStream(file);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String currentLine;

            while (!(currentLine = br.readLine()).isEmpty()) {
                Queue<Character> string = new LinkedList<>();
                for (int i = 0; i < currentLine.length(); ++i) {
                    string.offer(currentLine.charAt(i));
                }
                lexicon.addState(string);
            }


        } catch (IOException e) {
            // Pour s'assurer que le fichier n'est pas vide.
            e.printStackTrace();
	}

	
}
