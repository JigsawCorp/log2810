import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Mediator between the States and the Application/Interface
 * @attributes 	- currentState : Holds the current state from the user's inputs
 * 				- startState : The initial state, used for the automaton's instanciation and on reset
 * 				- top5 : Queue/LinkedList (FIFO) that holds the 5 most recent words used. The add()
 * 						method has been overloaded to have a capacity of 5 and handle duplicates.
 */
public class Lexicon  {

	private State currentState ;
	private State startState;
	private Queue<State> top5;

	public Lexicon() {
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
                    string.offer(currentLine.charAt(i));
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
	    if (currentState.hasState(transition)) {
	        currentState= currentState.getState(transition);
	        return currentState.getAllTerminalStates();
        }
	    return null;
	}

	/**
	 * Adds the currentState to the top5, chooses the current state then resets it.
	 * @return true if terminal state has been chosen, false otherwise
	 */
	public boolean chooseCurrentState() {
	    if (currentState.isTerminal()) {
            top5.add(currentState);
	    	currentState.choose();
            currentState = startState;
            return true;
        }
        return false;
    }

	/**
	 * Getters
	 */
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
