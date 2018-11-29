import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class State {
	
	private String value;
	private boolean isTerminal;
	private Map<Character, State> nextStates;
	private int nUsages;
	
	public State(String value, boolean isTerminal) {
		this.value = value;
		this.isTerminal = isTerminal;
		nextStates = new HashMap<>();
		nUsages = 0;
	}
	
	public void addState(Queue<Character> remainingChars) {
		
		char transition = remainingChars.poll();
		
		if (!nextStates.containsKey(transition)) {
			nextStates.put(transition, new State(value + transition, false));
		}
		
		if (remainingChars.size() == 1) {
			
		}
			
		nextStates.get(transition).addState(nextState.);
	}
}
