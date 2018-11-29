import java.util.HashMap;
import java.util.Queue;

public abstract class AbstractState {
	
	protected String value;
	protected HashMap<Character,State> nextStates;
	
	public void addStatesFrom(Queue<Character> remainingChars) {
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
}
