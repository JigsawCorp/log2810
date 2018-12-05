import java.awt.EventQueue;

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
