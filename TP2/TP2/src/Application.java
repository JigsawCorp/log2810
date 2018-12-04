import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class Application {
    private static final String DEFAULT_PATH = "/lexiques/";

    public void start() {
        Lexicon lexicon = Lexicon.newLexicon(DEFAULT_PATH + "lexique 1.txt");
        for (Map.Entry<Character, State> entry :  lexicon.getNextStates().entrySet()) {
            List<State> states = entry.getValue().getAllTerminalStates();
            System.out.println("The terminal states for " + entry.getValue().value + " are:");
            for (int i = 0; i < states.size(); ++i) {
                System.out.println(states.get(i).value);
            }
        }
        BufferedReader reader =
        		new BufferedReader(new InputStreamReader(System.in));
        String name = "";
        do {
        	try {
        		name = reader.readLine();
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	
        	System.out.println("==Words found==");
        	List<State> list = lexicon.nextState(name.charAt(0));
        	if (list == null) {System.out.println("No words found..."); lexicon.resetCurrent();}
        	else {
        		for (int i = 0; i < list.size(); ++i) {
        			State word = list.get(i);
        			word.choose();
        			System.out.println(word.getValue() + "\t\t" + word.getNUsages()); 
        		}
        		System.out.println("=CurrentState=\n" + lexicon.getCurrentState().getValue());
        	}
        } while (name != "" || name.length() == 1);
    }
}
