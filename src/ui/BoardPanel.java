package ui;

import game_elements.Player;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.event.ActionEvent;
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
	private GridBagConstraints gbc = new GridBagConstraints(); // Defines
																// properties
																// about
																// component
																// spacing.
	private JPanel actionsPanel = new JPanel(); // The action buttons will be
												// stored here.
	private JPanel cardsPanel = new JPanel(); // This panel shows the player's
												// cards.
	private JButton suggestionBtn = new JButton("Make suggestion");
	private JButton accusationBtn = new JButton("Make accusation");
	private JButton rollBtn = new JButton("Roll dice");
	private Insets insets = new Insets(0, 0, 0, 0); // Change later to a spacing
													// more appropreite.
	private int playerNum;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Object> characters = new ArrayList<Object>();
	private ImageIcon board = new ImageIcon("board.png");
	// lists to store player and dice images and the players current corrants
	
	private ArrayList<ImageIcon> playerIcons= new ArrayList<ImageIcon>();
	private ArrayList<ImageIcon> diceIcons= new ArrayList<ImageIcon>();
	private ArrayList<Point> playersCoor= new ArrayList<Point>();
	private boolean rollPressed = false;
	
	private int rolledNum;

	
   
	
	/**
	 * The constructor for the Board panel that set up the GUI for the actual
	 * game.
	 * 
	 * @param num,
	 *            the number of players.
	 */
	public BoardPanel(int num) {
		
		intaliseArrays();
		playerIcons.add(new ImageIcon("p1.png"));
		playerIcons.add(new ImageIcon("p2.png"));
		playerIcons.add(new ImageIcon("p3.png"));
		playerIcons.add(new ImageIcon("p4.png"));
		playerIcons.add(new ImageIcon("p5.png"));
		playerIcons.add(new ImageIcon("p6.png"));
		
		playersCoor.add(new Point(225,5));
		playersCoor.add(new Point(365,5));
		playersCoor.add(new Point(575,95));
		playersCoor.add(new Point(575,305));
		playersCoor.add(new Point(175,380));
		playersCoor.add(new Point(11,270));
		
		characters.addAll(Arrays.asList("Miss Scarlet", "Professor Plum", "Colonel Mustard", "Mrs White",
				"Reverend Green", "Mrs Peacock"));
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
		gbc.gridx++;
		this.add(actionsPanel, gbc);

		playerNum = num;
        
		// Creates the players.
		for (int i = 0; i < playerNum; i++) {
			players.add(createNewPlayer(i + 1));
		}
		//determines action when roll button is pressed;
		 rollBtn.addActionListener((ActionEvent e) -> {
			 rollPressed =true;
			 rolledNum=(int)(Math.random()*6)+1;
			 this.repaint();
			 }); 
	}

	private Player createNewPlayer(int index) {
		try {
			String character = (String) JOptionPane.showInputDialog(null,
					"Please chose a character for player " + index, "Character selection",
					JOptionPane.INFORMATION_MESSAGE, null, characters.toArray(), characters.get(0));
			characters.remove(character);
			return new Player(0, 0, character);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Game will now crash you have not entered any information for a field",
					"Fatal error!", JOptionPane.ERROR_MESSAGE);
			throw new Error();
		}
	}

	/**
	 * p
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawLine(0, 400, 650, 400);
		g.drawLine(500, 400, 500, 650);
		g.setColor(Color.GRAY);
		g.fillRect(0, 400, 650, 250);
		board.paintIcon(this, g, 10, 10);
		paintPlayers(g);
		if(rollPressed){
			diceIcons.get((rolledNum-1)).paintIcon(cardsPanel, g, 130, 380);
			rollPressed=false;
		}
	}

	private void paintPlayers(Graphics g) {
		
		for(int i=0; i<playerNum; i++){
			playerIcons.get(i).paintIcon(this, g, (int)playersCoor.get(i).getX(),(int)playersCoor.get(i).getY());
		}		
	}

	public static void main(String[] args) {
		new BoardPanel(3);
	}
 //=================================================
 //============HELPER METHODS=======================
 //=================================================
 
/**
 * used to aid in code readiblity 
 * */	
  public void intaliseArrays(){
	  	
	    // player icons
		playerIcons.add(new ImageIcon("p1.png"));
		playerIcons.add(new ImageIcon("p2.png"));
		playerIcons.add(new ImageIcon("p3.png"));
		playerIcons.add(new ImageIcon("p4.png"));
		playerIcons.add(new ImageIcon("p5.png"));
		playerIcons.add(new ImageIcon("p6.png"));
		
		//player corrants
		playersCoor.add(new Point(225,5));
		playersCoor.add(new Point(365,5));
		playersCoor.add(new Point(575,95));
		playersCoor.add(new Point(575,305));
		playersCoor.add(new Point(175,380));
		playersCoor.add(new Point(11,270));
		
		//dice icons
		diceIcons.add(new ImageIcon("d1.png"));
		diceIcons.add(new ImageIcon("d2.png"));
		diceIcons.add(new ImageIcon("d3.png"));
		diceIcons.add(new ImageIcon("d4.png"));
	    diceIcons.add(new ImageIcon("d5.png"));
		diceIcons.add(new ImageIcon("d6.png"));
  }	
}