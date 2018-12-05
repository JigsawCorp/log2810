import java.util.*;

/**
 * Class that represents the state from the automaton, either a terminal or a non-terminal state.
 * @attributes	-isTerminal : True if the state is a word (read from the lexicon)
 * 				-isTop5 : True if the word has been in the last 5 words used
 * 				-nUsages : Number of times the word has been chosen
 * 				-value : String describing the corresponding state, usually a word
 * 				-nextStates : Map that holds the possible transitions (chars) to existing states
 *
 */
public class State {
	
	private boolean isTerminal;
	private boolean isTop5;
	private int nUsages;
	private String value;
	private HashMap<Character,State> nextStates;
	
	public State(String value, boolean isTerminal) {
		this.value = value;
		this.isTerminal = isTerminal;
		this.nextStates = new HashMap<>();
		this.nUsages = 0;
	}

	/**
	 * Method that goes through our states and creates new ones.
	 * Deconstructs the queue of chars to use as transition and value.
	 * Calls itself but from the next states.
	 * @param remainingChars : Queue of chars with the rest of the transitions to place
	 */
	public void addStatesFrom(Queue<Character> remainingChars) {
		if (remainingChars.size() == 0) {
			System.out.println("test");
		}
		// Get the next character
		char transition = remainingChars.poll();

		// If there is not transition to this character
		if (!nextStates.containsKey(transition)) {
			// If this character is the last one of the word
			if (remainingChars.isEmpty()) {
				// Then this state is a state that holds a complete word
				nextStates.put(transition, new State(value + transition, true));
				return;
			}
			else {
				// Otherwise this state is not a state that holds a complete word
				nextStates.put(transition, new State(value + transition, false));
			}
		}
		// Call the method with the following characters of the word to the next state
		nextStates.get(transition).addStatesFrom(remainingChars);
	}

	/**
	 * Public method used to call the recursive method getNextTerminalStates()
	 * @return terminalStates : ArrayList of all the terminal states
	 */
	public List<State> getAllTerminalStates() {
		// List that will contain all the possible terminal states starting from this state
		List<State> terminalStates = new ArrayList<>();
		
		this.getNextTerminalStates(terminalStates);
		
		return terminalStates;
	}
	
	/**
	 * Recursive method, adds itself first so it displays in alphabetical order
	 * @param terminalStates : list of terminal states
	 */
	private void getNextTerminalStates(List<State> terminalStates) {
		
		// We want to display the current state if it's a terminal state
		if (isTerminal)
			terminalStates.add(this);

		// We iterate through all the next states
		for (Map.Entry<Character, State> entry : nextStates.entrySet()) {
			nextStates.get(entry.getKey()).getNextTerminalStates(terminalStates);
		}
	}
	
	/**
	 * When the user selects the terminal word.
	 * Increments its number of usages and isTop5 is set to true
	 */
	public void choose() {
		++nUsages;
		isTop5 = true;
	}
	
	/**
	 * Getters
	 */
	public int getNUsages() {
		return nUsages;
	}
	public boolean isTerminal() {
		return isTerminal;
	}
	public boolean isTop5() {
		return isTop5;
	}
	public String getValue() {
		return value;
	}
	
	/**
	 * Getters for nextStates
	 * hasState() returns if a transition exists for a state, given a char
	 * getState() returns the state with a given char
	 */
	public boolean hasState(char transition) {
		return nextStates.containsKey(transition);
	}
	public State getState(char transition) {
		return nextStates.get(transition);
	}
	
	/**
	 * Setter
	 */
	public void setIsTop5(boolean top) {
		isTop5 = top;
	}
}
