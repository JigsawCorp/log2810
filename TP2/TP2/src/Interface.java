import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JTextArea;

public class Interface extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private Lexicon lexicon;


	/**
	 * Create the frame.
	 */
	public Interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 325, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Dubai", Font.PLAIN, 15));
		textField.setBounds(10, 36, 289, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblEntrezUnMot = new JLabel("Entrez un mot :");
		lblEntrezUnMot.setBounds(10, 11, 289, 14);
		contentPane.add(lblEntrezUnMot);
		
		JLabel lblLesMotsPossibles = new JLabel("Les mots possibles :");
		lblLesMotsPossibles.setBounds(10, 82, 289, 14);
		contentPane.add(lblLesMotsPossibles);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 107, 289, 493);
		contentPane.add(textArea);
		
		// Text field listener.
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// If the "enter" key is pressed the actionPerformed is fired
				// We delete the content of the textField and the textPane.
				textField.setText("");
				textArea.setText("");
				// We put the currentNode of the lexicon to the first state
				// lexicon.setCurrentNodeBegin();
			}
		});
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				char characterPressed = (char) event.getKeyCode();
				String letterPressed = Character.toString(characterPressed).toLowerCase();
				// If the "backspace" is pressed.
				if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					// We delete the content of the textField and the textPane.
					textField.setText("");
					textArea.setText("");
					// We put the currentNode of the lexicon to the first state
					//lexicon.setCurrentNodeBegin();
				} else if (Character.isLetter(characterPressed)){
					/*String[] possibleWords = lexicon.addCharachter((Character.toString((char) event.getKeyCode())).toLowerCase()));
					String textPaneContent = "";
					for (int i = 0; i < possibleWords.length; i++) {
						textPaneContent += possibleWords[i] + "\n";
					}
					textPane.setText(textPaneContent);
					*/
					textArea.setText(textArea.getText() + letterPressed + "\n");
				} else {
					JOptionPane.showMessageDialog(null, "Veuillez entrer une lettre!");
					// We delete the content of the textField and the textPane.
					textField.setText("");
					textArea.setText("");
					// We put the currentNode of the lexicon to the first state
					//lexicon.setCurrentNodeBegin();
				}
			}
		});
	}
}
