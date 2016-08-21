//hi connor
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
import cards.Character;
import cards.Weapon;
import cards.RoomCard;

public class Board {

	private int[][] board;
	private Square[][] boardSquares; // Represents the board as a 2D array.
	private ArrayList<Player> players; // Represents the players in the game.
	private ArrayList<Character> characterCards = new ArrayList<Character>(); // Character cards.
	private ArrayList<Weapon> weaponCards = new ArrayList<Weapon>(); // Weapon cards.
	private ArrayList<RoomCard> roomCards = new ArrayList<RoomCard>(); // Room cards.
	private ArrayList<Room> rooms; // These are the actual Rooms
	private ArrayList<Card> mixedCards = new ArrayList<Card>();
	private Solution solution;
	private boolean gameNotWon = true;
	private Player currentPlayer = null;

	public static void main(String[] args) {}	
	
	/**
	 * Constructor takes the number of players as a parameter.
	 * 
	 * @param playerNum
	 */
	public Board(ArrayList<Player> p) {
		players = p; // Gets the players for this game.
		initializeCards();
		//solution = createSolution(true);
		Collections.shuffle(mixedCards);
		
		// Shuffles the cards.
		Collections.shuffle(mixedCards); // Randomizes the cards.
		int cardCount = (int) mixedCards.size() / players.size(); // Decides how many cards each player will get.
		int remainder = mixedCards.size() % (cardCount * players.size()); // Gets the number of cards that are left over.

		for (int i = 0; i < players.size(); i++) {
			Set<Card> playerCards = new HashSet<Card>();
			for (int j = 0; j < cardCount; j++) {
				playerCards.add(mixedCards.remove(0)); // Adds the card to the player's hand but also removes it so no player gets the same card.
			}
			players.get(i).setHand(playerCards);
		}
				
		// We have to add the solution cards back as we don't want the players to figure out what cards are missing, but we can be sure no players have them.
		// The shuffling is so the players can't tell which card was selected as it will be the last one added.
		characterCards.add(solution.getCharacter());
		Collections.shuffle(characterCards);
		weaponCards.add(solution.getWeapon());
		Collections.shuffle(weaponCards);
		roomCards.add(solution.getRoom());
		Collections.shuffle(roomCards);
	}

	/**
	 * Fills the card desks with card objects
	 */
	private void initializeCards() {
		characterCards.add(new Character("Mrs Scarlett", new ImageIcon("MS.png")));
		characterCards.add(new Character("Colonel Mustard", new ImageIcon("CM.png")));
		characterCards.add(new Character("Mr Green", new ImageIcon("MG.png")));
		characterCards.add(new Character("Professor Plum", new ImageIcon("PP.png")));
		characterCards.add(new Character("Mrs White", new ImageIcon("MW.png")));
		characterCards.add(new Character("Mrs Peacock", new ImageIcon("MP.png")));
		
		// Note that the images need to be replaced with the actual ones for the weapons and rooms.
		weaponCards.add(new Weapon("Rope", new ImageIcon("MS.png")));
		weaponCards.add(new Weapon("Dagger", new ImageIcon("MS.png")));
		weaponCards.add(new Weapon("Candle Stick", new ImageIcon("MS.png")));
		weaponCards.add(new Weapon("Lead Pipe", new ImageIcon("MS.png")));
		weaponCards.add(new Weapon("Revolver", new ImageIcon("MS.png")));
		weaponCards.add(new Weapon("Wrench", new ImageIcon("MS.png")));
		
		roomCards.add(new RoomCard("Kitchen", new ImageIcon("MS.png")));
		roomCards.add(new RoomCard("Ball Room", new ImageIcon("MS.png")));
		roomCards.add(new RoomCard("Conservatory", new ImageIcon("MS.png")));
		roomCards.add(new RoomCard("Billard Room", new ImageIcon("MS.png")));
		roomCards.add(new RoomCard("Library", new ImageIcon("MS.png")));
		roomCards.add(new RoomCard("Study", new ImageIcon("MS.png")));
		roomCards.add(new RoomCard("Hall", new ImageIcon("MS.png")));
		roomCards.add(new RoomCard("Lounge", new ImageIcon("MS.png")));
		roomCards.add(new RoomCard("Dinning Room", new ImageIcon("MS.png")));
		
		mixedCards.addAll(characterCards);
		mixedCards.addAll(weaponCards);
		mixedCards.addAll(roomCards);
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
		Character character = characterCards.get(randomIndex); // Selects a random character.
		if (startingSol) {
			characterCards.remove(randomIndex);
		} // Removes the solution character.

		randomIndex = (int) (Math.random() * 5) + 1;
		Weapon weapon = weaponCards.get(randomIndex); // Selects a random weapon.
		if (startingSol) {
			weaponCards.remove(randomIndex);
		} // Removes the solution weapon.

		randomIndex = (int) (Math.random() * 8) + 1;
		RoomCard room = roomCards.get(randomIndex); // Selects a random room card.
		if (startingSol) {
			roomCards.remove(randomIndex);
		}

		return null;
	}

	/**
	 * Method that starts playing the cluedo game.
	 */
	public void playGame() {
		/*
		while(true) {
			for(Player p: players) {
				currentPlayer = p; // Sets the current player
				p.playTurn();
			}
		}
		*/
	}
	
	/**
	 * Initializes the board.
	 */
	private void initializeBoard() {
		// Initialises the boardkey
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
		boardSquares = new Square[25][25];
		for(int x = 0; x < board.length; x ++) {
			for(int y = 0; y < board.length; y ++) {
				// Wall
				if(board[x][y] == -1) {
					boardSquares[x][y] = new WallSquare(x, y);
				}
				// Hallway
				else if(board[x][y] == 0) {
					boardSquares[x][y] = new HallwaySquare(x, y);
				}
				// Room
				else if(board[x][y] == 1) {
					boardSquares[x][y] = new RoomSquare(x, y);
				}
				// Doorway
				else {
					boardSquares[x][y] = new DoorSquare(x, y);
				}
			}
		}
	}
	
	/**
	 * Returns the current player.
	 * @return Player, the current player.
	 */
	public Player getCurrentPlayer() {return currentPlayer;}
}
