import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State extends AbstractState {
	
	private boolean isTerminal;
	private int nUsages;
	
	public State(String value, boolean isTerminal) {
		this.value = value;
		this.isTerminal = isTerminal;
		this.nextStates = new HashMap<>();
		this.nUsages = 0;
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
	
	// TODO Ameliorier nom
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
	
	public void choose() {
		++nUsages;
	}
	
	public int getNUsages() {
		return nUsages;
	}
}
