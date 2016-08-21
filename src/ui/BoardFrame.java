package ui;

import game_elements.Player;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	//number of players in the game
	private int numPlayers;
	public BoardFrame() {
	   System.out.println();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(startPanel);
		createMenu();
		window.pack();
		window.setVisible(true);
		window.setResizable(false);
		while(!startPanel.startGame()) {
			try{
				Thread.sleep(50);} // Need the program to sleep so that it can detect the button press.
			catch(InterruptedException e) {throw new Error("Sleep interputed");}
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
	public int getPlayerNum() {
		try {
			Object[] possiblities = new Object[] {"3","4","5","6"};
			Object selectedValue = JOptionPane.showInputDialog(null,
					"Select number of players", "Number of players",
					JOptionPane.INFORMATION_MESSAGE, null,
					possiblities, possiblities[0]); 
		     numPlayers=Integer.parseInt(selectedValue.toString());
			return numPlayers;
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
		MenuItem restart = new MenuItem("Restart");
		fileMenu.add(restart);
		MenuItem e = new MenuItem("Exit");
		fileMenu.add(e);
		MenuItem howToPlay = new MenuItem("How to play");
		helpMenu.add(howToPlay);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		window.setMenuBar(menuBar);
		// setting actions for when a menu item is pressed
				 e.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						window.dispose();
					}
				});
				howToPlay.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String message="To play the game click the roll dice button and"
								+ " move the ammount that the dice tell you."
								+ "You move by clicking right square to the new locaiton.";
						 JOptionPane.showMessageDialog(null, message, "help", 1);
					}
				});
				restart.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
					 	window.getContentPane().remove(boardPanel);
						window.getContentPane().add(startPanel);
						window.revalidate();
						window.repaint();
					}
				});
	}
	
	public static void main(String[] args) {new BoardFrame();}

}