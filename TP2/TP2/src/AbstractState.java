import java.util.HashMap;
import java.util.Queue;

/**
 * Abstract State implemented by Lexicon and State
 */
public abstract class AbstractState {
	
	protected String value;
	protected HashMap<Character,State> nextStates;
	
	/**
	 * Method that goes through our states and creates new ones
	 * @param remainingChars : Queue of chars with the rest of the transitions to place
	 */
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
	
	// has transition
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
