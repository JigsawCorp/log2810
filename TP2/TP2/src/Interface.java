import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JTextArea;

public class Interface extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static final String DEFAULT_PATH = "/lexiques/";

	/**
	 * Create the frame.
	 */
	public Interface() {
		//Initialize Lexicon
		Lexicon lexicon = Lexicon.newLexicon(DEFAULT_PATH + "lexique6.txt");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Dubai", Font.PLAIN, 15));
		textField.setBounds(10, 36, 509, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblEntrezUnMot = new JLabel("Entrez un mot :");
		lblEntrezUnMot.setBounds(10, 11, 289, 14);
		contentPane.add(lblEntrezUnMot);
		
		JLabel lblLesMotsPossibles = new JLabel("Les mots possibles :");
		lblLesMotsPossibles.setBounds(10, 82, 289, 14);
		contentPane.add(lblLesMotsPossibles);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 107, 509, 380);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		scrollPane.setViewportView(textArea);
		
		// Text field listener.
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// If the "enter" key is pressed the actionPerformed is fired
				// We put the currentNode of the lexicon to the first state
				boolean success = lexicon.chooseCurrentState();
				if(success) {
					// We delete the content of the textField and the textPane.
					textField.setText("");
					textArea.setText("");
				}
			}
		});
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				char characterPressed = java.lang.Character.toLowerCase((char)event.getKeyCode());
				// If the "backspace" is pressed.
				if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					// We delete the content of the textField and the textPane.
					textField.setText("");
					textArea.setText("");
					// We put the currentNode of the lexicon to the first state
					lexicon.reset();
				} else if (Character.isLetter(characterPressed)){
					List<State> possibleWords = lexicon.nextState(characterPressed);
					String textAreaContent = "";
					textAreaContent += "              \t\tDans le top 5?\t\tNombre d'utilisation\n"; 
					if(possibleWords != null) {
						for (int i = 0; i < possibleWords.size(); i++) {
							State state = possibleWords.get(i);
							String isTop5 = "";
							if(state.isTop5()) {
								isTop5 = "Oui";
							} else {
								isTop5 = "Non";
							}
							textAreaContent += String.format("%-23s %-23s %s\n", state.getValue(),isTop5,state.getNUsages());
						}
						textArea.setText(textAreaContent);
					} else {
						JOptionPane.showMessageDialog(null, "Ce mot n'existe pas dans le lexique!");
						lexicon.reset();
						textField.setText("");
						textArea.setText("");
					}
					textArea.setCaretPosition(0);
					
				} else {
					if(event.getKeyCode() != KeyEvent.VK_ENTER) {
						JOptionPane.showMessageDialog(null, "Veuillez entrer une lettre!");
						// We delete the content of the textField and the textPane.
						textField.setText("");
						textArea.setText("");
						// We put the currentNode of the lexicon to the first state
						lexicon.reset();
					}
				}
			}
		});
	}
}
