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
	private ArrayList<String> characterCards; // Character cards.
	private ArrayList<String> weaponCards; // Weapon cards.
	private ArrayList<String> roomCards; // Room cards.
	private ArrayList<Room> rooms; // These are the actual Rooms
	private ArrayList<String> mixedCards;
	private Solution solution;
	private boolean gameNotWon;
	private Player currentPlayer = null;

	public static void main(String[] args) {}	
	
	/**
	 * Constructor takes the number of players as a parameter.
	 * 
	 * @param playerNum
	 */
	public Board(ArrayList<Player> p) {
		players = p; // Gets the players for this game.
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
	 * Method that starts playing the cluedo game.
	 */
	public void playGame() {
		currentPlayer = players.get(0);
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
	}
	
	/**
	 * Returns the current player.
	 * @return Player, the current player.
	 */
	public Player getCurrentPlayer() {return currentPlayer;}
}
