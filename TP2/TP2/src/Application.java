import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Application {
    private static final String DEFAULT_PATH = "/TP2/lexiques/";

    public void start() {
        Lexicon lexicon = Lexicon.newLexicon(DEFAULT_PATH + "lexique6.txt");
        List<State> states = lexicon.getCurrentState().getAllTerminalStates();
        for (int i = 0; i < states.size(); ++i) {
            System.out.println(states.get(i).value);
        }
        while (true) {
            if (lexicon.getCurrentState() != null) {
                System.out.println("Current word is:" + lexicon.getCurrentState().value + ", please enter an other character:");
            }
            else {
                System.out.println("Please enter a character");
            }
            Scanner scanner = new Scanner(System.in);
            String c = "";
            c = scanner.nextLine();
            System.out.println("Next state is:");
            List<State> nextStates = lexicon.nextState(c.charAt(0));
            if (nextStates == null) {
                System.out.println("There are no words matching");
            }
            else {
                for (int i = 0; i < nextStates.size(); ++i) {
                    System.out.println(nextStates.get(i).value);
                }
            }
        }
    }
}
