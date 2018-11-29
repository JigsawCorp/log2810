import java.util.HashMap;
import java.util.Queue;

public abstract class AbstractState {
	
	private HashMap<Character,Node> nextStates;
	
	public void addState(Queue<Character> remainingChars) {
		char transition = remainingChars.poll();
		
		if (!nextStates.containsKey(transition)) {
			nextStates.put(transition, new Node(value + transition, false));
		}
		
		if (remainingChars.size() == 1) {
			
		}
			
		nextStates.get(transition).addState(nextState.);
	}

	// has transition
	public boolean hasState(char transition) {
		return nextStates.containsKey(transition);
	}
	
	public Node getState(char transition) {
		return nextStates.get(transition);
	}
}
