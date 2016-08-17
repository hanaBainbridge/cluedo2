package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	
	public StartPanel() {
		setLayout(null);
		setPreferredSize(new Dimension(600,600));
		setBackground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD, 32));
		setUpComponent(title, 300, 50);
		instructions.setBounds(50 + getInsets().left, 100, 500, 300);
		add(instructions);
		setUpComponent(startBtn, 300, 500);
		startBtn.addActionListener(new BtnListener());
	}
	
	/**
	 * Sets up a component on the JPanel, using absolute positioning.
	 * @param c, the component to be set up.
	 * @param x, the x position that the component is to be placed at.
	 * @param y, the y position that the component is to be placed at.
	 */
	private void setUpComponent(JComponent c, int x, int y) {
		Dimension size = c.getPreferredSize();
		Insets insets = this.getInsets();
		c.setBounds(x + insets.left - size.width/2, y, size.width, size.height);
		add(c);
	}
	
	public boolean startGame() {
		return startGame;
	}

	/**
	 * Class that listens for the start button to be pressed.
	 * @author Connor
	 *
	 */
	private class BtnListener implements ActionListener {

		/**
		 * Method that sets the boolean value when button is pressed, used to start game.
		 */
		public void actionPerformed(ActionEvent arg0) {
			startGame = true;
		}
		
	}
	
	public static void main(String[] args) {new StartPanel();}

}
