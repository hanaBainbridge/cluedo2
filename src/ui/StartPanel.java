package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartPanel extends JPanel {
	private JLabel title = new JLabel("Welcome to Cluedo");
	private JButton startBtn = new JButton("Start Game!");
	private TextArea instructions = new TextArea("Welcome to Cluedo!\n"
			+ "\nThis is a game where you try to determine the murderer, murder weapon and the \nmurder room."
			+ "Each player takes turns rolling the dice and moving to different rooms on \nthe board.\n"
			+ "\nOnce in a room a player can make a suggestion where the player suggests the \nmurderer, weapon and room\n"
			+ "\nThe other players try and disprove the suggestion using their cards. Once a player \nthinks they know who the character, weapon and room they can make an \naccusation "
			+ "which consists of a character, weapon and room.\n\nIf the suggestion is correct that player wins the game"
			+ ", however if incorrect that player is \neliminated from the game and cannot make any more suggestions or accusations.");
	private boolean startGame = false;
	private GridBagConstraints gbc = new GridBagConstraints();
	private Insets insets = new Insets(0, 0, 100, 0); // The padding between each component.
	
	public StartPanel() {
		setLayout(new GridBagLayout());
		gbc.anchor = GridBagConstraints.CENTER; // All components will be aligned with the centre.
		gbc.insets = insets; // Sets the space between all components.
		gbc.gridx = 1; // States what row we are working in.
		gbc.gridy = 0; // States what column we are working in.
		setPreferredSize(new Dimension(600,600)); // Sets the size of the panel.
		setBackground(Color.GRAY);
		title.setFont(new Font("Arial", Font.BOLD, 32));
		add(title, gbc);
		gbc.gridy ++; // Move down a row.
		instructions.setEditable(false); 
		add(instructions, gbc); 
		gbc.gridy ++; // Move down a row.
		add(startBtn, gbc);
		startBtn.addActionListener((ActionEvent e) -> {startGame = true;}); // Adds the action listener to the panel.
	}
	
	public boolean startGame() {
		return startGame;
	}
	
	public static void main(String[] args) {new StartPanel();}

}
