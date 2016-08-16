//hi connor
package game_elements;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import ui.TextUI;

public class Board {

	private int[][] board; // Represents the board as a 2D array.
	private ArrayList<Player> players; // Represents the players in the game.
	private ArrayList<Point> startingPos; // Represents the co-ordinates of the six starting squares.
	private ArrayList<String> characterCards; // Character cards.
	private ArrayList<String> weaponCards; // Weapon cards.
	private ArrayList<String> roomCards; // Room cards.
	private ArrayList<Room> rooms; // These are the actual Rooms
	private ArrayList<String> mixedCards;
	private Solution solution;
	private boolean gameNotWon;

	public static void main(String[] args) {
	}

	/**
	 * Constructor takes the number of players as a parameter.
	 * 
	 * @param playerNum
	 */
	public Board(int playerNum) {
		players = new ArrayList<Player>();
		gameNotWon = true;

		// Used to make the constructor shorter.
		initializeCharacters();
		initializeWeapons();
		initializeRooms();

		mixedCards = new ArrayList<String>();
		solution = createSolution(true);
		mixedCards.addAll(characterCards);
		mixedCards.addAll(roomCards);
		mixedCards.addAll(weaponCards);

		// Shuffles the cards.
		Collections.shuffle(mixedCards); // Randomizes the cards.
		int cardCount = (int) mixedCards.size() / playerNum; // Decides how many cards each player will get.
		int remainder = mixedCards.size() % (cardCount * playerNum); // Gets the number of cards that are left																		// over.

		for (int i = 0; i < playerNum; i++) {
			Set<String> playerCards = new HashSet<String>();
			for (int j = 0; j < cardCount; j++) {
				playerCards.add(mixedCards.remove(0)); // Adds the card to the player's hand but also removes it so no player gets the same card.
			}
			players.add(new Player(startingPos.get(i).x, startingPos.get(i).y, playerCards)); // Creates a new Player.
			System.out.println();
		}
		
		// We have to add the solution cards back as we don't want the players to figure out what cards are missing, but we can be sure no players have them.
		// The shuffling is so the players can't tell which card was selected as it will be the last one added.
		characterCards.add(solution.getCharacter());
		Collections.shuffle(characterCards);
		weaponCards.add(solution.getWeapon());
		Collections.shuffle(weaponCards);
		roomCards.add(solution.getRoom());
		Collections.shuffle(roomCards);

		initializeBoard();

		// If there are cards left over then they will be shown.
		if (remainder != 0) {
			System.out.println("Because there are cards left over these will be shown now!");
			for (int i = 0; i < remainder; i++) {
				removeCard(mixedCards.get(0)); // Have to remove from both ArrayLists that the card is contained in
				System.out.println(mixedCards.remove(0) + " has been removed.");
				
			}
		}
		playGame();
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
		String character = characterCards.get(randomIndex); // Selects a random character.
		if (startingSol) {
			characterCards.remove(randomIndex);
		} // Removes the solution character.

		randomIndex = (int) (Math.random() * 5) + 1;
		String weapon = weaponCards.get(randomIndex); // Selects a random weapon.
		if (startingSol) {
			weaponCards.remove(randomIndex);
		} // Removes the solution weapon.

		randomIndex = (int) (Math.random() * 8) + 1;
		String room = roomCards.get(randomIndex); // Selects a random room card.
		if (startingSol) {
			roomCards.remove(randomIndex);
		}

		return new Solution(character, weapon, room);
	}

	/**
	 * Method that draws a text representation of the board in the console.
	 */
	public void displayBoard() {
		System.out.println("___________________________________________________");
		// Outer loop goes through inner array.
		for (int i = 0; i < board.length; i++) {
			// Inner loops goes through outer array.
			for (int j = 0; j < board.length; j++) {
				int ele = board[j][i];
				if (board[j][i] == -1) {
					System.out.print("|W");
				} else if (board[j][i] == 1) {
					System.out.print("|R");
				} else if (board[j][i] == 10) {
					System.out.print("|1");
				} else if (board[j][i] == 11) {
					System.out.print("|2");
				} else if (board[j][i] == 12) {
					System.out.print("|3");
				} else if (board[j][i] == 13) {
					System.out.print("|4");
				} else if (board[j][i] == 14) {
					System.out.print("|5");
				} else if (board[j][i] == 15) {
					System.out.print("|6");
				} else {
					System.out.print("| ");
				}
			}
			System.out.print("|\n");
		}
		System.out.println("___________________________________________________");
	}

	/**
	 * Method that starts playing the cluedo game.
	 */
	public void playGame() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			for (Player p : players) {
				// Game goes to the next player if this one has been eliminated.
				if (p.isEliminated()) {
					continue;
				}
				playTurn(p); // Plays the turn for the Player.
				// If the player won the game.
				if (!gameNotWon) {
					System.out.println("Player " + ((players.indexOf(p) + 1) + " won the game!"));
					break; // Breaks out of the for loop and stops the game.
				}
				displayBoard(); // Shows the player the updated board.
				System.out.println("Please type \"1\" to start the next player's turn!");
				TextUI.checkInput(1);
			}
			// Check if game won here to break out of final loop.
			if(!gameNotWon) {
				break;
			}
		}
	}

	/**
	 * Plays a single turn for the player specified as a parameter
	 * 
	 * @param p, the player whos turn it is.
	 */
	private void playTurn(Player p) {
		System.out.println("Player " + (players.indexOf(p) + 1) + " turn!");
		displayBoard();
		// If the player is in a room with a staircase.
		if (p.getRoom() != null && p.getRoom().hasStairCase()) {
			System.out.println("You are in a room with a staircase:\n"
					+ "1. Use staircase to "
					+ p.getRoom().getConnectedRoom().toString() + "\n"
					+ "2. Roll the dice\n");
			int playerInput = TextUI.checkInput(2);
			// Player wants to use the staircase.
			if (playerInput == 1) {
				p.setRoom(p.getRoom().getConnectedRoom());
				// Also need to update player co-ordinates.
			}
			// Player wants to roll the dice.
			else {
				int diceRoll = rollDice();
				System.out.println("Player has rolled a" + diceRoll);
				// Show available exits.
				ArrayList<Point> entryPoints = p.getRoom().getEntryPoints();// for convience.

				for (int i = 0; i < entryPoints.size(); i++) {
					System.out.print("Exit:" + (i + 1) + "\t");
					System.out.print("x:" + (int) entryPoints.get(i).getX() + ":\t");
					System.out.println("Y:" + (int) entryPoints.get(i).getY());
				}
				// Ask the player to select an exit
				int choice = TextUI.checkInput(entryPoints.size()) - 1;
				board[p.getX()][p.getY()] = 0; // Update the position that the player was at.
				// Move the player to the first square outside the room and subtract one from the dice roll.
				p.setPosition(entryPoints.get(choice).x, entryPoints.get(choice).y); // Moves the player out of the room.
				board[p.getX()][p.getY()] = (players.indexOf(p) + 10); // Update the position that the player is now at.
				p.setRoom(null);
				diceRoll--;
				// Call the move method with the dice roll.
				move(diceRoll, p, true);
			}
		}
		// Player is in a room without a staircase.
		else if (p.getRoom() != null) {
			int diceRoll = rollDice();
			System.out.println("The player has rolled a " + diceRoll);
			// Show available exits.
			ArrayList<Point> entryPoints = p.getRoom().getEntryPoints();// for convience.

			for (int i = 0; i < entryPoints.size(); i++) {
				System.out.print("Exit:" + (i + 1) + "\t");
				System.out.print("x:" + (int) entryPoints.get(i).getX() + ":\t");
				System.out.println("Y:" + (int) entryPoints.get(i).getY());
			}
			// Ask the player to select an exit
			int choice = TextUI.checkInput(entryPoints.size()) - 1;
			// Move the player to the first square outside the room and subtract one from the dice roll.
			board[p.getX()][p.getY()] = 0; // Update the position that the player was at.
			p.setPosition(entryPoints.get(choice).x, entryPoints.get(choice).y); // Moves the player out of the room.
			board[p.getX()][p.getY()] = (players.indexOf(p) + 10); // Update the position that the player is now at.
			p.setRoom(null);
			diceRoll--;
			// Call the move method with the dice roll.
			move(diceRoll, p, true);
		}
		// Player is in the hallways.
		else {
			int diceRoll = rollDice(); // Roll the dice.
			System.out.println("Player has rolled a " + diceRoll);
			move(diceRoll, p, false); // Call the move method with the dice roll.
		}
	}

	/**
	 * Moves the player using the given dice roll.
	 * 
	 * @param diceRoll, the number of squares the player can move.
	 * @param p, the player that is being moved.
	 * @param leftRoom, true if the player has just left a room.
	 */
	private void move(int diceRoll, Player p, boolean leftRoom) {
		int currentMoves = 0;
		while (currentMoves < diceRoll) {
			System.out.println("Player " + (players.indexOf(p) + 1) + " turn!");
			System.out.println("" + (diceRoll - currentMoves) + " moves remaining!");
			displayBoard();
			// Get valid directions.
			ArrayList<String> validDir = new ArrayList<String>();
			// North
			if (p.getY() - 1 >= 0 && (board[p.getX()][p.getY() - 1] == 0 || board[p.getX()][p.getY() - 1] == 2)) {
				validDir.add("North");
			}
			// East
			if (p.getX() + 1 < board.length && (board[p.getX() + 1][p.getY()] == 0 || board[p.getX() + 1][p.getY()] == 2)) {
				validDir.add("East");
			}
			// South
			if (p.getY() + 1 < board.length && (board[p.getX()][p.getY() + 1] == 0 || board[p.getX()][p.getY() + 1] == 2)) {
				validDir.add("South");
			}
			// West
			if (p.getX() - 1 >= 0 && (board[p.getX() - 1][p.getY()] == 0 || board[p.getX() - 1][p.getY()] == 2)) {
				validDir.add("West");
			}
			// If the player has no valid moves skip to the next turn.
			if (validDir.size() == 0) {
				System.out.println("Player has no valid moves skipping turn!");
				break;
			}
			System.out.println("Which direction do you want to move in: ");
			for (int i = 0; i < validDir.size(); i++) {
				System.out.println((i + 1) + ": " + validDir.get(i));
			}
			// Ask the player to select an option.
			int choice = TextUI.checkInput(validDir.size()) - 1;
			// If the player has just left a room we need to replace the entrance.
			if (leftRoom) {
				board[p.getX()][p.getY()] = 2; // Updates the player's position that they were at.
				leftRoom = false;
			}
			else {
				board[p.getX()][p.getY()] = 0; // Updates the player's position that they were at.
			}
			p.movePlayer(validDir.get(choice));
			// Check if the player has entered a room.
			if (board[p.getX()][p.getY()] == 2) {
				p.displayHand(); // Displays the player's cards.
				for (Room r : rooms) {
					if (r.containsEntry(p.getX(), p.getY())) {
						p.setRoom(r);
						break;
					}
				}
				// Can only make a suggestion about this room as long as it has not been eliminated.
				if(roomCards.contains(p.getRoom().toString())) {
					System.out.println("What would you like to do:\n1. Make a suggestion.\n2. Make an accusation.\n3. Skip turn.");
					choice = TextUI.checkInput(3);
					// Suggestion
					if (choice == 1) {
						applySuggestionLogic(p);
					} 
					// Accusation
					else if (choice == 2) {
						applyAccusationLogic(p);
					}
					// Update the player's position here if they are inside a room.
					break; // If the player has entered a room their turn is over.
				}
				// If the Player cannot make a suggestion then there are only two options.
				else {
					System.out.println("What would you like to do:\n1. Make a accusation\n2. Skip turn.");
					choice = TextUI.checkInput(2);
					if(choice == 1) {
						applyAccusationLogic(p);
					}
					else {
						break; // Player wants to skip their turn.
					}
				}
			}
			board[p.getX()][p.getY()] = (players.indexOf(p) + 10); // Updates the player's new position.
			currentMoves ++; // Update the while loop condition
		}
	}
	
	/**
	 * Makes a suggestion with a given player and handles the other players refuting the suggestion as well.
	 * @param p, the player making the suggestion.
	 */
	private void applySuggestionLogic(Player p) {
		List<String> choices = p.makeSuggestion(characterCards, weaponCards, p.getRoom()); // Gets the character and weapon the player wishes to use for the suggestion.
		System.out.println("The player has suggested " + choices.get(0) + " is the murder and the weapon is a " + choices.get(1) + " and it took place in the "
				+ choices.get(2));
		// Go through the list of players so they can refute the suggestion.
		for (Player refutingPlayer: players) {
			// Don't want the player who made the suggestion able to refute
			if (p != refutingPlayer) {
				System.out.println("Player " + (players.indexOf(refutingPlayer) + 1) + " turn to refute!");
				List<String> matchingCards = refutingPlayer.getMatchingCards(choices); // Gets the matching cards that this player has.
				// Player has one or more cards that were suggested.
				if(matchingCards != null) {
					System.out.println("Please select a card you wish to refute with...");
					for (int i = 0; i < matchingCards.size(); i ++) {
						System.out.println((i+1) + ": " + matchingCards.get(i));
					}
					int choice = TextUI.checkInput(matchingCards.size()) - 1; // Get the user's input.
					System.out.println("Player has chosen " + matchingCards.get(choice) + " this cards will now be removed.");
					removeCard(matchingCards.get(choice)); // Removes the card from the possible choices.
					break; // If a player refutes we should go to the next player's turn.
				}
				else {
					System.out.println("Player has no matching cards, moving to next player");
					continue; // Goes to the next player.
				}
			}
		}
	}
	
	/**
	 * Makes an accusation with a given player, if the accusation is incorrect then the player is eliminated.
	 * @param p, the player making the accusation.
	 */
	private void applyAccusationLogic(Player p) {
		List<String> choices = p.makeAccusation(characterCards, weaponCards, roomCards);
		Solution posSol = new Solution(choices.get(0), choices.get(1), choices.get(2));
		// If the player guessed correctly.
		if (solution.equals(posSol)) {
			gameNotWon = false;
		}
		// The player was wrong and is eliminated from the game.
		else {
			p.setPlayingStatus(false);
			System.out.println("That accusation was incorrect you are now eliminated from the game!");
		}
	}
	
	/**
	 * Finds the cards in one of the three ArrayLists of cards and removes it so that it is eliminated as a possible choice.
	 * @param card, the card to be removed.
	 */
	private void removeCard(String card) {
		if(characterCards.contains(card)) {
			characterCards.remove(card);
		}
		else if(weaponCards.contains(card)) {
			weaponCards.remove(card);
		}
		else {
			roomCards.remove(card);
		}
	}

	/**
	 * Simulates the rolling of the dice in the game.
	 * 
	 * @return the number rolled.
	 */
	public int rollDice() {
		return (int) ((Math.random() * 6) + 1)
				+ (int) ((Math.random() * 6) + 1);
	}

	/**
	 * Initializes all the characters
	 */
	private void initializeCharacters() {
		characterCards = new ArrayList<String>();
		characterCards.add("Miss Scarlett");
		characterCards.add("Colonel Mustard");
		characterCards.add("Mrs White");
		characterCards.add("Reverend Green");
		characterCards.add("Mrs Peacock");
		characterCards.add("Professor Plum");
	}

	/**
	 * Initializes all the weapons.
	 */
	private void initializeWeapons() {
		weaponCards = new ArrayList<String>();
		weaponCards.add("Candle Stick");
		weaponCards.add("Dagger");
		weaponCards.add("Lead Pipe");
		weaponCards.add("Revolver");
		weaponCards.add("Rope");
		weaponCards.add("Spanner");
	}

	/**
	 * Initializes all the rooms.
	 */
	private void initializeRooms() {
		roomCards = new ArrayList<String>();
		roomCards.add("Ball Room");
		roomCards.add("Conservatory");
		roomCards.add("Billiard Room");
		roomCards.add("Library");
		roomCards.add("Study");
		roomCards.add("Hall");
		roomCards.add("Lounge");
		roomCards.add("Dinning Room");
		roomCards.add("Kitchen");

		ArrayList<Point> entrances = new ArrayList<Point>();
		rooms = new ArrayList<Room>();

		entrances.add(new Point(8, 5));
		entrances.add(new Point(9, 7));
		entrances.add(new Point(15, 7));
		entrances.add(new Point(16, 5));
		rooms.add(new Room(entrances, "Ball Room"));

		entrances = new ArrayList<Point>();
		entrances.add(new Point(19, 4));
		rooms.add(new Room(entrances, "Conservatory"));

		entrances = new ArrayList<Point>();
		entrances.add(new Point(19, 9));
		entrances.add(new Point(23, 12));
		rooms.add(new Room(entrances, "Billiard Room"));

		entrances = new ArrayList<Point>();
		entrances.add(new Point(21, 14));
		entrances.add(new Point(18, 16));
		rooms.add(new Room(entrances, "Library"));

		entrances = new ArrayList<Point>();
		entrances.add(new Point(19, 21));
		rooms.add(new Room(entrances, "Study"));

		entrances = new ArrayList<Point>();
		entrances.add(new Point(15, 20));
		entrances.add(new Point(13, 18));
		entrances.add(new Point(12, 18));
		entrances.add(new Point(11, 18));
		rooms.add(new Room(entrances, "Hall"));

		entrances = new ArrayList<Point>();
		entrances.add(new Point(5, 19));
		rooms.add(new Room(entrances, "Lounge"));

		entrances = new ArrayList<Point>();
		entrances.add(new Point(6, 15));
		entrances.add(new Point(7, 12));
		rooms.add(new Room(entrances, "Dinning Room"));

		entrances = new ArrayList<Point>();
		entrances.add(new Point(5, 4));
		rooms.add(new Room(entrances, "Kitchen"));

		startingPos = new ArrayList<Point>();
		startingPos.add(new Point(9, 0));
		startingPos.add(new Point(15, 0));
		startingPos.add(new Point(24, 6));
		startingPos.add(new Point(24, 19));
		startingPos.add(new Point(7, 24));
		startingPos.add(new Point(0, 17));
		
		// Here is where the connected rooms are set.
		connectRooms("Kitchen", "Study");
		connectRooms("Lounge", "Conservatory");
	}

	/**
	 * Initializes the board.
	 */
	private void initializeBoard() {
		/*
		 * Wall = -1, Room, 1, Hallways 0, Room entrances 2
		 * arrays and y are the numbers inside them
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

		for (int i = 0; i < players.size(); i++) {
			int x = players.get(i).getX();
			int y = players.get(i).getY();
			board[x][y] = i + 10;
		}

	}
	
	/**
	 * Method connects the two rooms given their names.
	 * @param r1, the name of the first room.
	 * @param r2, the name of the second room.
	 * @return true if the rooms have been connected, false if not.
	 */
	private boolean connectRooms(String r1, String r2) {
		Room room1 = null;
		Room room2 = null;
		
		for(Room room: rooms) {
			// If both rooms have been identified.
			if(room1 != null && room2 != null) {
				break;
			}
			if(room.toString().equals(r1)) {
				room1 = room;
			}
			else if(room.toString().equals(r2)) {
				room2 = room;
			}
		}
		
		// Check that we have the rooms.
		if(room1 == null || room2 == null) {
			return false;
		}
		
		// Connects the rooms together.
		room1.setConnectedRoom(room2);
		room2.setConnectedRoom(room1);
		return true;
	}
}
