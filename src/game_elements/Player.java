package game_elements;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import ui.TextUI;

public class Player {

	// Stores the names of the cards that this player has.
	private Set<String> playerCards = new HashSet<String>();
	// Stores the x and y coordinates of the player on the board.
	private int xPos;
	private int yPos;
	// Keeps track of players who have been eliminated.
	private boolean isPlaying;
	// The room that the player is in.
	private Room currentRoom;

	public Player(int x, int y, Set<String> cards) {
		playerCards = cards;
		xPos = x;
		yPos = y;
		isPlaying = true;
	}

	/**
	 * Method that gets the character, weapon and room the player wants for the solution and returns them as a List
	 * @param charaters, the remaining characters
	 * @param weapons, the remaining weapons.
	 * @param rooms, the remaining rooms.
	 * @return List<String>, the user's choices.
	 */
	public List<String> makeAccusation(ArrayList<String> charaters, ArrayList<String> weapons,
			ArrayList<String> rooms) {
		// the player must be in a room to make an auucstion
		if (this.currentRoom != null) {
			// asks the player to select a character from the list (the number).
			System.out.println("possible charater choices are:");
			for (int i = 0; i < charaters.size(); i ++) {
				System.out.println((i+1) + ": " + charaters.get(i));
			}
			System.out.println();
			int charater = TextUI.checkInput(charaters.size()) - 1;

			// selecting a weapon for the accusation
			System.out.println("Possible weapon choices are:");
			for (int i = 0; i < weapons.size(); i ++) {
				System.out.println((i+1) + ": " + weapons.get(i));
			}
			System.out.println();
			int weapon = TextUI.checkInput(weapons.size()) - 1;

			// selecting a room for the accusation
			System.out.println("Possible room choices are:");
			for (int i = 0; i < rooms.size(); i ++) {
				System.out.println((i+1) + ": " + rooms.get(i));
			}
			System.out.println();
			int room = TextUI.checkInput(rooms.size()) - 1;
			List<String> ans = Arrays.asList(charaters.get(charater), weapons.get(weapon), rooms.get(room));
			return ans;
		}
		return null;
	}

	/**
	 * Method that gets the character and weapon the player wishes to suggest.
	 * @param charaters, the remaining characters.
	 * @param weapons, the remaining weapons.
	 * @param currentRoom, the room that the player making the suggestion is in.
	 * @return List<String>, the player's choices.
	 */
	public List<String> makeSuggestion(ArrayList<String> characters, ArrayList<String> weapons, Room currentRoom) {
		if (this.currentRoom != null && this.isPlaying) {
			// asks the player to select a character from the list (the number).
			System.out.println("possible charater choices are:");
			for (int i = 0; i < characters.size(); i++) {
				System.out.println((i+1) + ": " + characters.get(i));
			}
			System.out.println();
			int character = TextUI.checkInput(characters.size()) - 1;

			// selecting a weapon for the accusation
			System.out.println("Possible weapon choices are:");
			for (int i = 0; i < weapons.size(); i ++) {
				System.out.println((i+1) + ": " + weapons.get(i));
			}
			System.out.println();
			int weapon = TextUI.checkInput(weapons.size()) - 1;
			List<String> ans = Arrays.asList(characters.get(character), weapons.get(weapon), currentRoom.toString());
			return ans;

		}
		return null;
	}

	/**
	 * Displays the player's cards that they have been dealt.
	 */
	public void displayHand() {
		System.out.println("Player has the following cards in their hand...");
		for(String card: playerCards) {
			System.out.println(card);
		}
	}
	/**
	 * getter methods
	 */
	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	/**
	 * keeps track of if a selected player is in the game or not
	 */
	public boolean isEliminated() {
		return !isPlaying;
	}
	
	/**
	 * Sets whether the player is playing the game to b.
	 * @param b, the boolean value.
	 */
	public void setPlayingStatus(boolean b) {isPlaying = b;} 

	/**
	 * returns the room the player is currently in or null if the player is not
	 * in a room
	 */
	public Room getRoom() {
		return currentRoom;
	}

	public void setRoom(Room room) {
		currentRoom = room;
	}
	
	public void setPosition(int x, int y) {
		xPos = x;
		yPos = y;
	}

	/**
	 * moves the players cooradinates on the board
	 */
	public void movePlayer(String direction) {
		switch (direction) {
		case ("North"):
			yPos--;
			break;
		case ("East"):
			xPos++;
			break;
		case ("South"):
			yPos++;
			break;
		case ("West"):
			xPos--;
			break;
		default:
			break;
		}
	}
	
	/**
	 * Method checks if the player's hand contains one or more of the cards that another player has suggested and returns them
	 * @param cards, the suggestion cards
	 * @return the suggestion cards that the player has in their hand.
	 */
	public List<String> getMatchingCards(List<String> suggestionCards) {
		List<String> containedCards = new ArrayList<String>();
		for(String card: playerCards) {
			if(suggestionCards.contains(card)) {
				containedCards.add(card); // Adds the card if the player has the card in their hand.
			}
		}
		if(containedCards.size() != 0) {return containedCards;} // Return the cards
		return null; // Return null if player has no cards.
	}
}