import java.awt.EventQueue;

public class Main {
    public static void main(String[] args){
    	
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
        new Application().start();
        
    }
}
