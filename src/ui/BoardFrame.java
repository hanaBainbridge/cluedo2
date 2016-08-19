package ui;

import game_elements.Player;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BoardFrame {
	
	// Creates the window that will house the two panels.
	private JFrame window = new JFrame(); 
	// Creates the two panels that the game uses 
	private StartPanel startPanel = new StartPanel();
	private BoardPanel boardPanel = null;
	
	public BoardFrame() {
	   System.out.println();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(startPanel);
		createMenu();
		window.pack();
		window.setVisible(true);
		window.setResizable(false);
		
		while(!startPanel.startGame()) {
			System.out.println();
		}
		
		window.getContentPane().remove(startPanel);
		boardPanel = new BoardPanel(getPlayerNum()); // Creates a new BoardPanel with the number of players that the user has entered.
		window.getContentPane().add(boardPanel);
		window.revalidate();
		window.repaint();
	}
	
	/**
	 * Asks the user for the number of players that are playing the game.
	 * @return int, the number of players
	 */
	private int getPlayerNum() {
		try {
			Object[] possiblities = new Object[] {"3","4","5","6"};
			Object selectedValue = JOptionPane.showInputDialog(null,
					"Select number of players", "Number of players",
					JOptionPane.INFORMATION_MESSAGE, null,
					possiblities, possiblities[0]); 
			return Integer.parseInt(selectedValue.toString());
		}
		catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Game will now crash you have not entered any information for a field", "Fatal error!", JOptionPane.ERROR_MESSAGE);
			throw new Error("You crashed the game dumbass");
		}
	}
	
	
	/**
	 * Method that creates a menu for the JFrame.
	 * Note that this menu will be present in both panels.
	 */
	private void createMenu() {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		Menu helpMenu = new Menu("Help");
		MenuItem item = new MenuItem("Restart");
		fileMenu.add(item);
		item = new MenuItem("Exit");
		fileMenu.add(item);
		item = new MenuItem("How to play");
		helpMenu.add(item);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		window.setMenuBar(menuBar);
	}
	
	public static void main(String[] args) {new BoardFrame();}

}