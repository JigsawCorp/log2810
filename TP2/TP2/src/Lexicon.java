import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Mediator between the States and the Application/Interface
 * @attributes 	- currentState : Holds the current state from the user's inputs
 * 				- startState : The initial state, used for the automaton's instantiation and on reset
 * 				- top5 : Queue/LinkedList (FIFO) that holds the 5 most recent words used. The add()
 * 						method has been overloaded to have a capacity of 5 and handle duplicates.
 */
public class Lexicon  {
    // The current state which consists of the string the user has entered so far
	private State currentState ;
	// The start state which consists in an empty string
	private State startState;
	// A queue that holds the 5 most recently used states
	private Queue<State> top5;
	public Lexicon() {
	    // Override some methods to have a size of 5
	    top5 = new LinkedList<State>() {
            public boolean add(State state) {
                boolean result;
                if (super.contains(state)) {
            		// If the word we choose is already in the top 5, we
            		// just want to push it in the back of the list. So,
                	// we have to remove it from the list first.
            		super.remove(state);
            	}

                if (this.size() < 5)
                    result = super.add(state);
                else {
                	super.getFirst().setIsTop5(false);
                	super.removeFirst();
                	result = super.add(state);
                }
                return result;
            }
        };

	    // Set the current state the the start state
	    currentState = new State("", false);
	    startState = currentState;
    }

    /**
     * Used to create a new lexicon
     * @param path Path to the lexicon
     * @return A new lexicon
     */
	public static Lexicon newLexicon(String path) {
        Lexicon lexicon = new Lexicon();
        // Get our relative path
        String filePath = new File("").getAbsolutePath();
        // Open the file
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

	/**
	 * Modifies the currentState and returns all the terminal states from it
	 * @param transition : transition from the current state to the new state
	 * @return the list of states from the current state
	 */
	public List<State> nextState(char transition) {
	    // If we have a transition with the character
	    if (currentState.hasState(transition)) {
	        // Set the current state to the state matching the character transition
	        currentState= currentState.getState(transition);
	        // Return all the states that are complete words
	        return currentState.getAllTerminalStates();
        }
        // Return null to let the client know no transition exists
	    return null;
	}

	/**
	 * Adds the currentState to the top5, chooses the current state then resets it.
	 * @return true if terminal state has been chosen, false otherwise
	 */
	public boolean chooseCurrentState() {
	    // If the current state is a complete word
	    if (currentState.isTerminal()) {
	        // Add this word to the top 5 most recently used words
            top5.add(currentState);
            // Select the current state
	    	currentState.choose();
	    	// Set the current state as the empty string
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

    /**
     * Resets the currentState to the startState without doing anything else
     */
    public void reset() {
    	currentState = startState;
    }
}
