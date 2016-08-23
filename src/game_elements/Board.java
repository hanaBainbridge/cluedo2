package game_elements;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.ImageIcon;

import cards.Card;
import cards.CharacterCard;
import cards.Weapon;
import cards.RoomCard;

public class Board {
	private final int BOARDX = 10;
	private final int BOARDY = 10;
	private final int SQUARE_WIDTH = 23; // The width of a square on the board.
	private final int SQUARE_HEIGHT = 12; // The height of a square on the board.
	private int[][] board; // Represents the board as a 2D array 
	private ArrayList<Player> players; // Represents the players in the game.
	private int currentPlayerIndex = 0; // Keeps track of the current player's index.
	private Player currentPlayer = null;
	private ArrayList<CharacterCard> characterCards = new ArrayList<CharacterCard>(); // Character cards.
	private ArrayList<Weapon> weaponCards = new ArrayList<Weapon>(); // Weapon cards.
	private ArrayList<RoomCard> roomCards = new ArrayList<RoomCard>(); // Room cards.
	private ArrayList<Room> rooms; // These are the actual Rooms
	private ArrayList<Card> mixedCards = new ArrayList<Card>(); // Used to shuffle the cards
	private Solution solution; // The solution to the game.
	private boolean gameNotWon = true; // Keeps the game playing while true

	public static void main(String[] args) {}	
	
	/**
	 * Constructor takes an ArrayList of players as a parameter.
	 * 
	 * @param p, the ArrayList of players.
	 */
	public Board(ArrayList<Player> p) {
		players = p; // Gets the players for this game.
		currentPlayer = players.get(currentPlayerIndex); // Sets the current player.
		initializeCards();
		initializeBoard();
		solution = createSolution(true); // Creates the starting solution
		// Adds all the cards together after the solution cards have been removed.
		mixedCards.addAll(characterCards); 
		mixedCards.addAll(weaponCards);
		mixedCards.addAll(roomCards);
		Collections.shuffle(mixedCards);
		
		int cardCount = (int) mixedCards.size() / players.size(); // Decides how many cards each player will get.
		int remainder = mixedCards.size() % (cardCount * players.size()); // Gets the number of cards that are left over.

		for (int i = 0; i  < players.size(); i++) {
			List<Card> playerCards = new ArrayList<Card>();
			for (int j = 0; j < cardCount; j++) {
				playerCards.add(mixedCards.remove(0)); // Adds the card to the player's hand but also removes it so no player gets the same card.
			}
			players.get(i).setHand(playerCards); // Gives the player their cards for the game.
		}
				
		// We have to add the solution cards back as we don't want the players to figure out what cards are missing, but we can be sure no players have them.
		// The shuffling is so the players can't tell which card was selected as it will be the last one added.
		characterCards.add(solution.getCharacterCard());
		weaponCards.add(solution.getWeaponCard());
		roomCards.add(solution.getRoomCard());
		Collections.shuffle(roomCards);
	}

	/**
	 * Method that initializes all the cards for the game
	 */
	private void initializeCards() {
		characterCards.add(new CharacterCard("Mrs Scarlett", new ImageIcon("MS.png")));
		characterCards.add(new CharacterCard("Colonel Mustard", new ImageIcon("CM.png")));
		characterCards.add(new CharacterCard("Mr Green", new ImageIcon("MG.png")));
		characterCards.add(new CharacterCard("Professor Plum", new ImageIcon("PP.png")));
		characterCards.add(new CharacterCard("Mrs White", new ImageIcon("MW.png")));
		characterCards.add(new CharacterCard("Mrs Peacock", new ImageIcon("MP.png")));
		
		weaponCards.add(new Weapon("Rope", new ImageIcon("rope.png")));
		weaponCards.add(new Weapon("Dagger", new ImageIcon("knife.png")));
		weaponCards.add(new Weapon("Candle Stick", new ImageIcon("candlestick.png")));
		weaponCards.add(new Weapon("Lead Pipe", new ImageIcon("leadpipe.png")));
		weaponCards.add(new Weapon("Revolver", new ImageIcon("revolver.png")));
		weaponCards.add(new Weapon("Wrench", new ImageIcon("wrench.png")));
		
		roomCards.add(new RoomCard("Kitchen", new ImageIcon("kitchen.png")));
		roomCards.add(new RoomCard("Ball Room", new ImageIcon("ballroom.png")));
		roomCards.add(new RoomCard("Conservatory", new ImageIcon("conservatory.png")));
		roomCards.add(new RoomCard("Billard Room", new ImageIcon("billardroom.png")));
		roomCards.add(new RoomCard("Library", new ImageIcon("library.png")));
		roomCards.add(new RoomCard("Study", new ImageIcon("study.png")));
		roomCards.add(new RoomCard("Hall", new ImageIcon("hall.png")));
		roomCards.add(new RoomCard("Lounge", new ImageIcon("lounge.png")));
		roomCards.add(new RoomCard("Dinning Room", new ImageIcon("dinningroom.png")));
	}
	
	/**
	 * Creates a solution, note that if it is a starting solution the chosen
	 * cards are removed so no player can have the solution
	 * cards in their hand. Note that if the solution is the result of an
	 * accusation by a player the cards are not removed.
	 * 
	 * @param startingSol, true if this is the solution that is picked at the start ofthe game, false if it is an accusation by a player.
	 * @return a Solution object containing a character, weapon and room.
	 */
	private Solution createSolution(boolean startingSol) {
		int randomIndex = (int) (Math.random() * 5) + 1; // random() returns number >= 0 and < 1, note that 7 will never be a possiblity and the maximum is 6.
		CharacterCard character = characterCards.get(randomIndex); // Selects a random character.
		if (startingSol) {
			characterCards.remove(randomIndex); // Removes the solution character.
		}

		randomIndex = (int) (Math.random() * 5) + 1;
		Weapon weapon = weaponCards.get(randomIndex); // Selects a random weapon.
		if (startingSol) {
			weaponCards.remove(randomIndex); // Removes the solution weapon.
		} 

		randomIndex = (int) (Math.random() * 5) + 1;
		RoomCard room = roomCards.get(randomIndex); // Selects a random room card.
		if (startingSol) {
			roomCards.remove(randomIndex);
		}   
		return new Solution(character, weapon, room); // Returns the solution object.
	}
	
	/**
	 * Gives the next player their turn.
	 * Moves the index to the next player.
	 */
	public void nextPlayer() {
		// If index is at the last player go back to the first.
		if (currentPlayerIndex == players.size()-1) {
			currentPlayerIndex = 0;
		}
		else {
			currentPlayerIndex ++;
		}
		currentPlayer = players.get(currentPlayerIndex); // Gets the new current player.
	}
	
	/**
	 * Initializes the board.
	 */
	private void initializeBoard() {
		// Initializes the board.
		/*
		 * Key:
		 * -1 = Wall
		 * 0 = Hallway
		 * 1 = Room
		 * 2 = Door
		 */
		board = new int[][] {
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // x=0
				{ -1, -1, 1, 1, 1, 1, -1, 0, 0, -1, 1, 1, 1, 1, 1, -1, 0, 0, 0,-1, 1, 1, 1, 1, -1 }, // x1
				{ -1, -1, 1, 1, 1, 1, -1, 0, 0, -1, 1, 1, 1, 1, 1, -1, 0, 0, 0,-1, 1, 1, 1, 1, -1 }, // x2
				{ -1, -1, 1, 1, 1, 1, -1, 0, 0, -1, 1, 1, 1, 1, 1, -1, 0, 0, 0,-1, 1, 1, 1, 1, -1 }, // x3
				{ -1, -1, 1, 1, 1, 1, 1, 0, 0, -1, 1, 1, 1, 1, 1, -1, 0, 0, 0,-1, 1, 1, 1, 1, -1 }, // x4
				{ -1, -1, -1, -1, 2, -1, -1, 0, 0, 0, -1, 1, 1, 1, 1, -1, 0,0, 0, 2, 1, 1, 1, 1, -1 }, // x5
				{ -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 1, 1, 1, 2, 0, 0, 0,-1, -1, -1, -1, -1, -1 }, // x6
				{ -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, 2, -1, -1, -1, 0, 0,0, 0, 0, 0, 0, 0, -1 }, // x7
				{ -1, 0, -1, -1, -1, 2, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, -1 }, // x8
				{ -1, 0, -1, 1, 1, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1,-1, -1, -1, -1, -1, -1 }, // x9
				{ -1, -1, 1, 1, 1, 1, 1, -1, 0, 0, -1, -1, -1, -1, -1, -1, -1,0, -1, 1, 1, 1, 1, 1, -1 }, // x10
				{ -1, -1, 1, 1, 1, 1, 1, -1, 0, 0, -1, -1, -1, -1, -1, -1, -1,0, 2, 1, 1, 1, 1, 1, -1 }, // x11
				{ -1, -1, 1, 1, 1, 1, 1, -1, 0, 0, -1, -1, -1, -1, -1, -1, -1,0, 2, 1, 1, 1, 1, 1, -1 }, // x12
				{ -1, -1, 1, 1, 1, 1, 1, -1, 0, 0, -1, -1, -1, -1, -1, -1, -1,0, 2, 1, 1, 1, 1, 1, -1 }, // x13
				{ -1, -1, 1, 1, 1, 1, 1, -1, 0, 0, -1, -1, -1, -1, -1, -1, -1,0, -1, 1, 1, 1, 1, 1, -1 }, // x14
				{ -1, 0, -1, 1, 1, 1, 1, 2, 0, 0, -1, -1, -1, -1, -1, -1, -1,0, -1, -1, 2, -1, -1, -1, -1 }, // x15
				{ -1, 0, -1, -1, -1, 2, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, -1 }, // x16
				{ -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0 }, // x17
				{ -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 2, -1, 0,0, 0, -1, -1, -1, -1 }, // x18
				{ -1, -1, -1, -1, 2, -1, 0, 0, -1, 2, -1, -1, -1, 0, -1, 1, 1,1, -1, 0, 0, 2, 1, 1, -1 }, // x19
				{ -1, -1, 1, 1, 1, -1, 0, 0, -1, 1, 1, -1, -1, 0, -1, 1, 1, 1,-1, 0, 0, -1, 1, 1, -1 }, // x20
				{ -1, -1, 1, 1, 1, -1, 0, 0, -1, 1, 1, 1, -1, 0, 2, 1, 1, 1,-1, 0, 0, -1, 1, 1, -1 }, // x21
				{ -1, -1, 1, 1, 1, -1, 0, 0, -1, 1, 1, 1, -1, 0, -1, 1, 1, 1,-1, 0, 0, -1, 1, 1, -1 }, // x22
				{ -1, -1, 1, 1, 1, -1, 0, 0, -1, 1, 1, 1, 2, 0, -1, 1, 1, 1,-1, 0, 0, -1, 1, 1, -1 }, // x23
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1 } // x24
		};
	}
	
	/**
	 * Method returns the valid points that the player can move to.
	 * Note that the player can only move one square at a time so the method only checks the 4 surrounding squares.
	 * @return ArrayList<Point>, the valid points the player can move to.
	 */
	public ArrayList<Point> getValidMoves() {
		ArrayList<Point> validPoints = new ArrayList<Point>(); // Note that these points are the position of the squares in pixels 
		Point playerPos = currentPlayer.getPlayerCoor();
		Point playerBoardPos = new Point((playerPos.x/SQUARE_WIDTH), (playerPos.y/SQUARE_HEIGHT)); // This is the player's position on the board (square number)
		// Check if North is valid
		if(playerBoardPos.y - 1 > 0 && (board[playerBoardPos.x][playerBoardPos.y-1] == 0 || board[playerBoardPos.x][playerBoardPos.y-1] == 2)) {
			validPoints.add(new Point(playerBoardPos.x * SQUARE_WIDTH, (playerBoardPos.y - 1) * SQUARE_HEIGHT));
		}
		// Check if East is valid
		if(playerBoardPos.x + 1 < board.length && (board[playerBoardPos.x + 1][playerBoardPos.y] == 0 || board[playerBoardPos.x + 1][playerBoardPos.y] == 2)) {
			validPoints.add(new Point((playerBoardPos.x + 1) * SQUARE_WIDTH, playerBoardPos.y * SQUARE_HEIGHT));
		}
		// Check if South is valid
		if(playerBoardPos.y + 1 < board.length && (board[playerBoardPos.x][playerBoardPos.y + 1] == 0 || board[playerBoardPos.x][playerBoardPos.y + 1] == 2)) {
			validPoints.add(new Point(playerBoardPos.x * SQUARE_WIDTH, (playerBoardPos.y + 1) * SQUARE_HEIGHT));
		}
		// Check if West is valid
		if(playerBoardPos.x - -1 > 0 && (board[playerBoardPos.x - 1][playerBoardPos.y] == 0 || board[playerBoardPos.x - 1][playerBoardPos.y] == 2)) {
			validPoints.add(new Point((playerBoardPos.x - 1) * SQUARE_WIDTH, playerBoardPos.y * SQUARE_HEIGHT));
		}
		System.out.println("hello");
		return validPoints; // Returns the valid points.
	}
	
	/**
	 * Returns the current player.
	 * @return Player, the current player.
	 */
	public Player getCurrentPlayer() {return currentPlayer;}
	
	/**
	 * Method that returns the solution to the game.
	 * @return Solution, the solution to the game.
	 */
	public Solution getSolution(){
		return solution;
	}
	
	
	/**
	 * Sets the game not won flag to the parameter. 
	 * @param b, the boolean that the flag is to be set to.
	 */
	public void setWon(boolean b){
		gameNotWon=b;
	}
	
	/**
	 * Method returns whether the game is won or not.
	 * @return true if the game is not won, false if it is.
	 */
	public boolean getGameStatus() {
		return gameNotWon;
	}
	
	/**
	 * Method returns the current character cards that are still possible solutions to the game.
	 * @return, the List of suspects.
	 */
	public List<CharacterCard> getCharacterCards() {return characterCards;}
	
	/**
	 * Method returns the weapon cards that are still possible murder weapons.
	 * @return, the List of possible murder weapons.
	 */
	public List<Weapon> getWeaponCards() {return weaponCards;}
	
	/**
	 * Method returns the room cards that are still possible murder rooms. 
	 * @return List<RoomCard>, the List of possible murder rooms.
	 */
	public List<RoomCard> getRoomCards() {return roomCards;}

	/**
	 * Method returns the index of the current player whos turn it is. 
	 * @return int, the current player's index.
	 */
	public int getCurrentIndex() {
			return currentPlayerIndex;
	}
	
	/**
	 * Method returns an ArrayList of the players that are playing the game (eliminated included).
	 * @return ArrayList<Player>, the ArrayList of players in the game.
	 */
	public ArrayList<Player> getPlayers(){
		return players;
	}	
}