import java.util.HashMap;

public class Node {
	
	private String value;
	private boolean isTerminal;
	private HashMap<Character, Node> nextStates;
	private int nUsages;
	
	public Node(String value) {
		this.value = value;
	}
}
