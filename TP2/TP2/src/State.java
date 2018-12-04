import java.util.*;

public class State {
	
	private boolean isTerminal;
	private boolean isTop5;
	private int nUsages;
	protected String value;
	protected HashMap<Character,State> nextStates;
	
	public State(String value, boolean isTerminal) {
		this.value = value;
		this.isTerminal = isTerminal;
		this.nextStates = new HashMap<>();
		this.nUsages = 0;
	}

	public void addStatesFrom(Queue<Character> remainingChars) {
		if (remainingChars.size() == 0) {
			System.out.println("test");
		}
		char transition = remainingChars.poll();

		if (!nextStates.containsKey(transition)) {
			if (remainingChars.isEmpty()) {
				nextStates.put(transition, new State(value + transition, true));
				return;
			}
			else {
				nextStates.put(transition, new State(value + transition, false));
			}
		}

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
		isTop5 = true;
	}
	
	public int getNUsages() {
		return nUsages;
	}

	public boolean isTerminal() {
		return isTerminal;
	}

	public boolean isTop5() {
		return isTop5;
	}
	
	public void setIsTop5(boolean top) {
		isTop5 = top;
	}
	
	public boolean hasState(char transition) {
		return nextStates.containsKey(transition);
	}

	public State getState(char transition) {
		return nextStates.get(transition);
	}
	public String getValue() {
		return value;
	}
}
