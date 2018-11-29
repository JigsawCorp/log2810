import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class State extends AbstractState {
	
	private boolean isTerminal;
	private int nUsages;
	
	public State(String value, boolean isTerminal) {
		this.value = value;
		this.isTerminal = isTerminal;
		this.nextStates = new HashMap<>();
		this.nUsages = 0;
	}
}
