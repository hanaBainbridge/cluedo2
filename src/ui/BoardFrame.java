package ui;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JFrame;

public class BoardFrame {
	
	// Creates the window that will house the two panels.
	private JFrame window = new JFrame(); 
	// Creates the two panels that the game uses 
	private StartPanel startPanel = new StartPanel();
	private BoardPanel boardPanel = new BoardPanel();
	
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
		window.getContentPane().add(boardPanel);
		// Make dialog boxes appear here.
		
		window.revalidate();
		window.repaint();
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
