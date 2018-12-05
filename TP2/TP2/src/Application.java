import java.awt.EventQueue;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Class representing our application. Used as an entry point
 */
public class Application {
    public void start() {
    	/**
    	 * Launch the application.
    	 */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
