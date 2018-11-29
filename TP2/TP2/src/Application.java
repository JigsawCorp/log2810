import java.util.List;
import java.util.Map;

public class Application {
    private static final String DEFAULT_PATH = "/TP2/lexiques/";

    public void start() {
        Lexicon lexicon = Lexicon.newLexicon(DEFAULT_PATH + "lexique6.txt");
        for (Map.Entry<Character, State> entry :  lexicon.getNextStates().entrySet()) {
            List<State> states = entry.getValue().getAllTerminalStates();
            System.out.println("The terminal states for " + entry.getValue().value + " are:");
            for (int i = 0; i < states.size(); ++i) {
                System.out.println(states.get(i).value);
            }
        }
    }
}
