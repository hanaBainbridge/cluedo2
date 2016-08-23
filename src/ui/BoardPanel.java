package ui;

import game_elements.Board;
import game_elements.Player;
import game_elements.Solution;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cards.Card;
import cards.CharacterCard;
import cards.RoomCard;
import cards.Weapon;

public class BoardPanel extends JPanel {
	private final int BOARDX = 10;
	private final int BOARDY = 10;
	private final int SQUARE_WIDTH = 23; // The width of a square on the board.
	private final int SQUARE_HEIGHT = 12; // The height of a square on the board.
	private GridBagConstraints gbc = new GridBagConstraints(); // Defines properties about component spacing.
	private JPanel actionsPanel = new JPanel(); // The action buttons will be stored here.
	private JButton suggestionBtn = new JButton("Make suggestion");
	private JButton accusationBtn = new JButton("Make Accusation");
	private JButton rollBtn = new JButton("Roll Dice");
	private JButton endTurn = new JButton("End Turn");
	
	private boolean roll = false; // A flag that states if a player has rolled this turn or not.
	private JLabel dice1 = null; // The image of the first dice.
	private JLabel dice2 = null; // The image of the second dice.
	private int[] rolledNums = null; // The numbers of the two dice.
	
	private JPanel cardsPanel = new JPanel(); // This panel shows the player's cards, as well as their name that they entered.
	private JLabel playerName = null; // Displays the player's name.
	private Insets insets = new Insets(0, 0, 0, 0);
	private ArrayList<Player> players = new ArrayList<Player>(); // Stores the players that are in the game. 
	private ArrayList<Object> characters = new ArrayList<Object>(); 
	private ImageIcon boardImage = new ImageIcon("board2.png"); // The image of the board which the game will be played on.
	private JLabel[] currentPlayerCards; // Stores the images of the player cards. 
	private ArrayList<ImageIcon> playerIcons= new ArrayList<ImageIcon>(); // Stores the images representing the players.
	private ArrayList<ImageIcon> diceIcons = new ArrayList<ImageIcon>(); // Stores the images showing the different dice face.
	private ArrayList<Point> startingPos= new ArrayList<Point>(); 
	private Board board; // The board object which hold information about the game being played.
	//felilds to create solution for accuation and suggestion
	private ArrayList<String> roomNames = new ArrayList<String>();
	private ArrayList<String> weaponNames = new ArrayList<String>();
	private ArrayList<String> characterNames = new ArrayList<String>();


	/**
	 * The constructor for the Board panel that set up the GUI for the actual game.
	 * 
	 * @param num, the number of players.
	 */
	public BoardPanel(int num) {
		intaliseArrays(); // Initializes the player icons and the starting positions on the board.
		characters.addAll(Arrays.asList("Miss Scarlet", "Professor Plum", "Colonel Mustard", "Mrs White", "Reverend Green", "Mrs Peacock"));
		gbc.insets = insets;
		gbc.gridheight = 3;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		this.setLayout(new GridBagLayout()); // Sets the layout.
		this.setPreferredSize(new Dimension(600, 600)); // Sets the size.
		this.setBackground(Color.WHITE); // Sets the background colour.
		this.setVisible(true);
		
		// Initializes the dice images.
		diceIcons.add(new ImageIcon("d1.png"));
		diceIcons.add(new ImageIcon("d2.png"));
		diceIcons.add(new ImageIcon("d3.png"));
		diceIcons.add(new ImageIcon("d4.png"));
		diceIcons.add(new ImageIcon("d5.png"));
		diceIcons.add(new ImageIcon("d6.png"));

		this.add(cardsPanel, gbc);
		gbc.gridx++;
		this.add(actionsPanel, gbc);
        
		// Creates the players.
		for (int i = 0; i < num; i++) {
			players.add(createNewPlayer(i + 1));
		}
		
		board = new Board(players); // Creates the board.
		getCurrentPlayerCardImages(); // Gets the current images of the cards that the player has in their hand.
		cardsPanel.setBackground(Color.GRAY);
		cardsPanel.setPreferredSize(new Dimension(500, 210));
		actionsPanel.setPreferredSize(new Dimension(100, 210));
		actionsPanel.setBackground(Color.GRAY);
		actionsPanel.add(suggestionBtn);
		suggestionBtn.addActionListener((ActionEvent e) -> {
			//if(board.getCurrentPlayer().inRoom())
			String character = getCharacterValue("suggestion");
			String weapon = getWeaponValue("suggestion");
			//allow the players to refute the suggestion 
			int index=board.getCurrentIndex(); // Stores the current player's index.
			ArrayList<Player> players= board.getPlayers(); // Gets the players.
			// This needs to be in clockwise order
			for(int i=0; i<players.size(); i++){
				// We don't want the player to refute their own suggestion.
				if(i!=index){
					boolean refutingCharacter=checkRefuting(character,players.get(i));  
					boolean refutingWeapon=checkRefuting(weapon,players.get(i));
					if(refutingWeapon || refutingCharacter){
						String text="a player has refted your suggestion\n"
								+ " it is now the next players turn.";
						JPanel outcome=new JPanel();
						JLabel outcomeText= new JLabel(text);
						outcome.add(outcomeText);
						JOptionPane.showMessageDialog(null, outcome); 
						// set next player turn 
						board.nextPlayer();
						break;
						
					}
					else{
						String text= "player" + players.get(i).toString() +"has "
								+ "not refutedd your seggestion";
						JPanel outcome=new JPanel();
						JLabel outcomeText= new JLabel(text);
						outcome.add(outcomeText);
						JOptionPane.showMessageDialog(null, outcome); 
					}
				}
			}
			
		});
		// Button listener for accusation goes here.
		actionsPanel.add(accusationBtn);
		accusationBtn.addActionListener((ActionEvent e) -> {
			String roomA= getRoomValue("accusation");
			String weaponA= getWeaponValue("accusation");
			String characterA= getCharacterValue("accusation");
			
			Solution ans=board.getSolution();
			if(ans.getRoomCard().toString().equals(roomA) &&
			   ans.getWeaponCard().toString().equals(weaponA) &&	
			   ans.getCharacterCard().toString().equals(characterA)){
				board.setWon(false);
			}
			else{
				Player cPlayer=board.getCurrentPlayer();
				cPlayer.eleimatePlayer();
			}
			

		});
		
		
		actionsPanel.add(rollBtn);
		rollBtn.addActionListener((ActionEvent e) -> {
			// Can only roll if the player has not rolled this turn.
			if (!roll) {
				rolledNums = board.getCurrentPlayer().rollDice(); // Gets the rolled numbers.
				roll = true; // Sets the flag to true.
				this.repaint();
			}
		});
		
		actionsPanel.add(endTurn);
		endTurn.addActionListener((ActionEvent e) -> {
			// Checks if the game has been won.
			if(!board.getGameStatus()){ 
				JFrame endFrame= new JFrame("GAME WON!!!!!!!");
				endFrame.setSize(200,500);
				endFrame.setVisible(true);
				endFrame.setLayout(new GridBagLayout());
				endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JPanel endPanel= new JPanel();
				endFrame.add(endPanel);
				endPanel.setPreferredSize(new Dimension(200,200));
				String text="The game has been won!!!!"
						+ "The winner is" + board.getCurrentPlayer().toString();
				JLabel endText= new JLabel(text);
				endPanel.add(endText);	
			}
			
			board.nextPlayer(); // Move to the next player.
			getCurrentPlayerCardImages(); // Gets the next player's cards
			roll = false; // Allows the next player to roll.
			rolledNums = null; // Resets the numbers rolled for the next turn 
			this.repaint();
		});
		
		
		// mouselistener for moving the plaeyrs 
		addMouseListener(new mouseAdapter());
	}
	
	private boolean checkRefuting(String s, Player player) {
		boolean ans=false;
		List<Card> cards=player.getHand(); // This is a good start but this will only remove the card from the player's hand, we also want to remove it from the overall cards.
		for(int i=0; i<cards.size(); i++){
			if(cards.get(i).toString().equals(s)) {
				ans=true;
				cards.remove(i);
				// Should there be a break statement in here? The player can only refute using one card.
				// Or what about returning after the card is removed.
			}
		}
		return ans;
	}

	private String getCharacterValue(String string) {
		String var= null;
		JPanel panel = new JPanel();
	      JRadioButton scarrlett = new JRadioButton("Miss Scarlett");
	      JRadioButton CM = new JRadioButton("Colonel Mustard");
	      JRadioButton white = new JRadioButton("Mrs White");
	      JRadioButton green = new JRadioButton("Reverend Green");
	      JRadioButton peacock = new JRadioButton("Mrs Peacock");
	      JRadioButton plum = new JRadioButton("Professor Plum");
	      List<CharacterCard> currentCharacterCards = board.getCharacterCards();
	      
	        if(findCard(currentCharacterCards, "Miss Scarlett")) {panel.add(scarrlett);}
	        if(findCard(currentCharacterCards, "Colonel Mustard")) {panel.add(CM);}
	        if(findCard(currentCharacterCards, "Mrs White")) {panel.add(white);}
	        if(findCard(currentCharacterCards, "Reverend Green")) {panel.add(green);}
	        if(findCard(currentCharacterCards, "Mrs Peacock")) {panel.add(peacock);}
	        if(findCard(currentCharacterCards, "Professor Plum")) {panel.add(plum);}
	        
	        JOptionPane.showMessageDialog(null, panel);
	        if(scarrlett.isSelected()){
	        	var="Miss Scarlett";
	        }
	        if(CM.isSelected()){
	        	var="Colonel Mustard";
	        }
	        if(white.isSelected()){
	        	var="Mrs White";
	        }
	        if(green.isSelected()){
	        	var="Reverend Green";
	        }
	        if(peacock.isSelected()){
	        	var="Mrs Peacock";
	        }
	        if(plum.isSelected()){
	        	var="Professor Plum";
	        }
	        return var;
	}
	
	private boolean findCard(List<? extends Card> cards, String name) {
		for(Card c: cards) {
			if(c.toString().equals(name)) {
				return true;
			}
		}
		return false;
	}
	private String getWeaponValue(String string) {
		String var= null;
		
		JPanel panel = new JPanel();
	      JRadioButton candle = new JRadioButton("Candle Stick");
	      JRadioButton dagger = new JRadioButton("Dagger");
	      JRadioButton lead = new JRadioButton("Lead Pipe");
	      JRadioButton revolver = new JRadioButton("Revolver");
	      JRadioButton rope = new JRadioButton("Rope");
	      JRadioButton spanner = new JRadioButton("Spanner");
	      List<Weapon> currentWeaponCards = board.getWeaponCards();
	      
	        if(findCard(currentWeaponCards, "Candle Stick")) {panel.add(candle);}
	        if(findCard(currentWeaponCards, "Dagger")) {panel.add(dagger);}
	        if(findCard(currentWeaponCards, "Lead Pipe")) {panel.add(lead);}
	        if(findCard(currentWeaponCards, "Revolver")) {panel.add(revolver);}
	        if(findCard(currentWeaponCards, "Rope")) {panel.add(rope);}
	        if(findCard(currentWeaponCards, "Spanner")) {panel.add(spanner);}

	        JOptionPane.showMessageDialog(null, panel);
	        if(candle.isSelected()){
	        	var="Candle Stick";
	        }
	        if(dagger.isSelected()){
	        	var="Dagger";
	        }
	        if(lead.isSelected()){
	        	var="Lead Pipe";
	        }
	        if(revolver.isSelected()){
	        	var="Revolver";
	        }
	        if(rope.isSelected()){
	        	var="Rope";
	        }
	        if(spanner.isSelected()){
	        	var="Spanner";
	        }
	                return var;
	}
	private String getRoomValue(String s) {
		String ans = null;
		JPanel panel = new JPanel();
		JRadioButton ballRoom = new JRadioButton("Ball Room");
		JRadioButton con = new JRadioButton("Conservatory");
		JRadioButton billard = new JRadioButton("Billiard Room");
		JRadioButton lib = new JRadioButton("Library");
		JRadioButton study = new JRadioButton("Study");
		JRadioButton hall = new JRadioButton("Hall");
		JRadioButton kitchen = new JRadioButton("Kitchen");
		JRadioButton dinning = new JRadioButton("Dinning Room");
		JRadioButton lounge = new JRadioButton("Lounge");
		List<RoomCard> currentRoomCards = board.getRoomCards();
		
		if(findCard(currentRoomCards, "Ball Room")) {panel.add(ballRoom);}
		if(findCard(currentRoomCards, "Billiard Room")) {panel.add(billard);}
		if(findCard(currentRoomCards, "Library")) {panel.add(lib);}
		if(findCard(currentRoomCards, "Study")) {panel.add(study);}
		if(findCard(currentRoomCards, "Hall")) {panel.add(hall);}
		if(findCard(currentRoomCards, "Kitchen")) {panel.add(kitchen);}
		if(findCard(currentRoomCards, "Dinning Room")) {panel.add(dinning);}
		if(findCard(currentRoomCards, "Lounge")) {panel.add(lounge);}
		JOptionPane.showMessageDialog(null, panel);

		if (ballRoom.isSelected()) {
			ans = "Ball Room";
		}
		if (billard.isSelected()) {
			ans = "Billard Room";
		}
		if (lib.isSelected()) {
			ans = "Libaray";
		}
		if (hall.isSelected()) {
			ans = "Hall";
		}
		if (dinning.isSelected()) {
			ans = "Dinning hall";
		}
		if (lounge.isSelected()) {
			ans = "Lounge";
		}
		if (study.isSelected()) {
			ans = "Study";
		}
		if (kitchen.isSelected()) {
			ans = "Kitchen";
		}
		return ans;
	}
	
	/**
	 * Method that gets the cards of the current player.
	 */
	private void getCurrentPlayerCardImages() {
		currentPlayerCards = new JLabel[board.getCurrentPlayer().getHand().size()]; // Creates a new array.
		for (int i = 0; i < board.getCurrentPlayer().getHand().size(); i ++) {
			currentPlayerCards[i] = new JLabel(board.getCurrentPlayer().getHand().get(i).getImage()); // Gets the card image.
		}
	}

	private Player createNewPlayer(int index) {
		try {
			String name = (String) JOptionPane.showInputDialog(null, "Please a name for Player " + (index));
			while(name.equals("")) {
				JOptionPane.showMessageDialog(null, "No name entered", "Invaid input", JOptionPane.ERROR_MESSAGE);
				name = (String) JOptionPane.showInputDialog(null, "Please a name for Player " + (index));
			}
			String character = (String) JOptionPane.showInputDialog(null,
					"Please chose a character for player " + index, "Character selection",
					JOptionPane.INFORMATION_MESSAGE, null, characters.toArray(), characters.get(0));
			characters.remove(character);
			return new Player(playerIcons.get(index - 1), startingPos.get(index - 1), character, name);
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
		actionsPanel.repaint();
		g.setColor(Color.GRAY);
		g.fillRect(0, 400, 650, 250);
		boardImage.paintIcon(this, g, 10, 10);
		paintPlayers(g);
		paintDice(g);
	}
	
	private void paintDice(Graphics g) {
		if (roll) {
			//System.out.println("F");
			if(dice1 != null) {actionsPanel.remove(dice1);}
			if(dice2 != null) {actionsPanel.remove(dice2);}
			dice1 = new JLabel(diceIcons.get(rolledNums[0]-1));
			dice2 = new JLabel(diceIcons.get(rolledNums[1]-1));
			actionsPanel.add(dice1);
			actionsPanel.add(dice2);
			this.revalidate();
		}
	}
	
	private void paintPlayers(Graphics g) {
		cardsPanel.removeAll();
		playerName = new JLabel("Current Player: " + board.getCurrentPlayer().toString());
		playerName.setPreferredSize(new Dimension(500, playerName.getPreferredSize().height));
		cardsPanel.add(playerName);
		for (int i = 0; i < board.getCurrentPlayer().getHand().size(); i++) {
			cardsPanel.add(currentPlayerCards[i]);
		}
		for(int i=0; i<players.size(); i++){
			playerIcons.get(i).paintIcon(this, g, (int)players.get(i).getPlayerCoor().getX(),(int)players.get(i).getPlayerCoor().getY());
		}		
		
		this.revalidate();
	}
	
	public static void main(String[] args) {
		new BoardPanel(3);
	}
	
	private class mouseAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			// The player has to rolled before moving.
			if (rolledNums != null) {
				Point mousePoint = e.getPoint(); // Gets the point that the mouse was clicked at.
				ArrayList<Point> validPoints = board.getValidMoves(); // Gets the valid points on the board that we can move to.
				System.out.println(validPoints.toString());
				// Check if the mouse click was in any of the valid squares.
				for(Point p: validPoints) {
					// Point valid if inside the square
					if((mousePoint.x >= p.x && mousePoint.x < p.x + 2*SQUARE_WIDTH) && (mousePoint.y >= p.y && mousePoint.y < p.y + 2*SQUARE_HEIGHT)) {
						board.getCurrentPlayer().setPoint(p); // Sets the player's new position
						BoardPanel.this.repaint();
						break;
					}
				}
			}
			
			else {
				JOptionPane.showMessageDialog(null, "You must roll the dice first!", "Roll first", JOptionPane.ERROR_MESSAGE);
			}
		}
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
		playerIcons.add(new ImageIcon("p2.jpg"));
		playerIcons.add(new ImageIcon("p3.jpg"));
		playerIcons.add(new ImageIcon("p4.jpg"));
		playerIcons.add(new ImageIcon("p5.jpg"));
		playerIcons.add(new ImageIcon("p6.jpg"));
		
		//player starting co-ordinates.
		startingPos.add(new Point(BOARDX + 9*SQUARE_WIDTH, BOARDY + 0*SQUARE_HEIGHT));
		startingPos.add(new Point(BOARDX + 15*SQUARE_WIDTH, BOARDY + 0*SQUARE_HEIGHT));
		startingPos.add(new Point(BOARDX + 24*SQUARE_WIDTH, BOARDY + 6*SQUARE_HEIGHT));
		startingPos.add(new Point(BOARDX + 24*SQUARE_WIDTH, BOARDY + 19*SQUARE_HEIGHT));
		startingPos.add(new Point(BOARDX + 7*SQUARE_WIDTH, BOARDY + 24*SQUARE_HEIGHT));
		startingPos.add(new Point(BOARDX + 0*SQUARE_WIDTH, BOARDY + 17*SQUARE_HEIGHT));
  }
}
