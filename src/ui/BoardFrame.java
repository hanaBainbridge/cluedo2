package ui;

import javax.swing.JFrame;

public class BoardFrame {
	// Creates the window that will house the two panels.
	private JFrame window = new JFrame(); 
	// Creates the two panels that the game uses 
	private StartPanel startPanel = new StartPanel();
	private BoardPanel boardPanel = new BoardPanel();
	
	public BoardFrame() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(startPanel); 
		window.pack();
		window.setVisible(true);
		
	}
	
	public static void main(String[] args) {new BoardFrame();}

}