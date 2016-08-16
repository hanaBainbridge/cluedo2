package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartPanel extends JPanel {
	private JLabel title = new JLabel("Welcome to Cluedo");
	private JButton startBtn = new JButton("Start Game!");
	private boolean startGame = false;
	
	public StartPanel() {
		this.setLayout(new BorderLayout()); // Sets the layout to Boarder
		this.setPreferredSize(new Dimension(600,600));
		setFontProperties(title);
		this.setBackground(Color.WHITE);
		this.add(title, BorderLayout.NORTH);
		startBtn.addActionListener(new BtnListener());
		this.add(startBtn, BorderLayout.SOUTH);
		
	}
	
	public boolean startGame() {
		return startGame;
	}
	
	private void setFontProperties(JLabel text) {
		title.setFont(new Font("Arial", Font.BOLD, 32));
		title.setForeground(Color.BLACK);	
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
