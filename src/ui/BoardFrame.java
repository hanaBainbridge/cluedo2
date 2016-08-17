package ui;

import javax.swing.JFrame;
//hi hi hi hi hi hi hi hi hi hi hi hi 

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
		window.pack();
		window.setVisible(true);
		window.setResizable(false);
		
		while(!startPanel.startGame()) {
			System.out.println();
		}
		
		window.getContentPane().remove(startPanel);
		window.getContentPane().add(boardPanel);
		window.revalidate();
		window.repaint();
	}
	
	public static void main(String[] args) {new BoardFrame();}

}
