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
public class Lexicon extends AbstractState {
	
	private State currentNode;
	private Queue<State> top5;

	public Lexicon() {
	    top5 = new LinkedList<>();
	    value = "";
    }
	
	public static Lexicon newLexicon(String file) {
        Lexicon lexicon = new Lexicon();
        InputStream inputStream = Main.class.getResourceAsStream(file);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            // Holds the current line
            String currentLine;
            // Read every line in the lexicon
            while (!(currentLine = br.readLine()).isEmpty()) {
                // Create a queue that will act as a string, but we will pop the first characters in addState()
                Queue<Character> string = new LinkedList<>();
                // For every character in the current line
                for (int i = 0; i < currentLine.length(); ++i) {
                    // Add that character in the queue, so that it it will form a list of chars and mimic a string
                    string.offer(currentLine.charAt(i));
                }
                // Add the necessary amount of states in our state machine to create a path to our word
                lexicon.addStatesFrom(string);
            }
        } catch (IOException e) {
            // To avoid empty files
            e.printStackTrace();
        }
        return lexicon;
    }
	
}
