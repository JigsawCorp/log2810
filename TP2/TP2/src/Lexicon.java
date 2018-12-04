import com.sun.tools.javac.Main;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Class that does everything
 */
public class Lexicon  {

	private State currentState ;
	private State startState;
	private Queue<State> top5;

	public Lexicon() {
	    top5 = new LinkedList<>() {
            public boolean add(State state) {
                boolean result;
                if(this.size() < 5)
                    result = super.add(state);
                else
                {
                    super.removeFirst();
                    result = super.add(state);
                }
                return result;
            }
        };

	    currentState = new State("", false);
	    startState = currentState;
    }

	public static Lexicon newLexicon(String path) {
        Lexicon lexicon = new Lexicon();
        String filePath = new File("").getAbsolutePath();
        File file = new File(filePath + path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            // Holds the current line
            String currentLine;
            // Read every line in the lexicon
            while ((currentLine = br.readLine()) != null) {
                // Create a queue that will act as a string, but we will pop the first characters in addState()
                Queue<Character> string = new LinkedList<>();
                // For every character in the current line
                for (int i = 0; i < currentLine.length(); ++i) {
                    // Add that character in the queue, so that it it will form a list of chars and mimic a string
                    string.offer(Character.toLowerCase(currentLine.charAt(i)));
                }
                // Add the necessary amount of states in our state machine to create a path to our word
                lexicon.startState.addStatesFrom(string);
            }
        } catch (IOException e) {
            // To avoid empty files
            e.printStackTrace();
        }
        return lexicon;
    }

	// TODO nom à élaborer
	/**
	 * Modifies the currentState and returns all the terminal states from it
	 * @param transition : transition from the current state to the new state
	 * @return the list of states from the current state
	 */
	public List<State> nextState(char transition) {
	    if (currentState.hasState(transition)) {
	        currentState= currentState.getState(transition);
	        return currentState.getAllTerminalStates();
        }
	    return null;
	}

	public boolean chooseCurrentState() {
	    if (currentState.isTerminal()) {
            currentState.choose();
            top5.add(currentState);
            currentState = startState;
            return true;
        }
        return false;
    }

    public Queue<State> getTop5() {
	    return top5;
    }


    public State getCurrentState() {
	    return currentState;
    }


	
}
