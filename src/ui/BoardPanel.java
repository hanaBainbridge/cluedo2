package ui;

import game_elements.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuBar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	private GridBagConstraints gbc = new GridBagConstraints(); // Defines properties about component spacing.
	private JPanel actionsPanel = new JPanel(); // The action buttons will be stored here.
	private JPanel cardsPanel = new JPanel(); // This panel shows the player's cards.
	private JButton suggestionBtn = new JButton("Make suggestion");
	private JButton accusationBtn = new JButton("Make accusation");
	private JButton rollBtn = new JButton("Roll dice");
	private Insets insets = new Insets(0,0,0,0); // Change later to a spacing more appropreite.
	private int playerNum;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Object> characters = new ArrayList<Object>();
	private ImageIcon board = new ImageIcon("board.png");
	
	/**
	 * The constructor for the Board panel that set up the GUI for the actual game.
	 * @param num, the number of players.
	 */
	public BoardPanel(int num) {
		characters.addAll(Arrays.asList("Miss Scarlet", "Professor Plum", "Colonel Mustard", "Mrs White", "Reverend Green", "Mrs Peacock"));
		gbc.insets = insets;
		gbc.gridheight = 3;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(600, 600));
		this.setBackground(Color.WHITE);
		
		actionsPanel.setPreferredSize(new Dimension(100, 210));
		actionsPanel.setBackground(Color.GRAY);
		actionsPanel.add(suggestionBtn);
		actionsPanel.add(accusationBtn);
		actionsPanel.add(rollBtn);
		
		cardsPanel.setPreferredSize(new Dimension(500, 210));
		cardsPanel.setBackground(Color.GRAY);
		
		this.add(cardsPanel, gbc);
		gbc.gridx ++;
		this.add(actionsPanel, gbc);
		
		playerNum = num;
		
		// Creates the players.
		for(int i = 0; i < playerNum; i ++) {
			players.add(createNewPlayer(i+1));
		}
		// We still need to draw the board.
	}
	
	private Player createNewPlayer(int index) {
		try {
			String character = (String)JOptionPane.showInputDialog(null, "Please chose a character for player " + index, "Character selection", 
					JOptionPane.INFORMATION_MESSAGE, null, characters.toArray(), characters.get(0));
		characters.remove(character);
		return new Player(0, 0 ,character);
		}
		catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Game will now crash you have not entered any information for a field", "Fatal error!", JOptionPane.ERROR_MESSAGE);
			throw new Error();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawLine(0, 400, 650, 400);
		g.drawLine(500, 400, 500, 650);
		g.setColor(Color.GRAY);
		g.fillRect(0, 400, 650, 250);
		board.paintIcon(this, g, 10, 10);
	}
	

	public static void main(String[] args) {new BoardPanel(3);}

}