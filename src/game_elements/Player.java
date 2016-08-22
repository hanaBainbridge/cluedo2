package game_elements;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.ImageIcon;

import cards.Card;

public class Player{
	
	private List<Card> playerCards = new ArrayList<Card>(); // Stores the cards that the player has in their hand.
	
	private ImageIcon playerImage; // Stores the ImageIcon that represents the player on the board.
	private Point playerCoor; // Stores the x and y position that the player is currently at.
	private String character; // Stores the name of the character that the player has chosen.
	private String name; // Stores the name of the player.
	private boolean isPlaying; // Stores if the player is playing the game or not.
	private Room currentRoom = null; // Stores the room that the player is in (null if not in room)
	private boolean endTurn = false;

	/**
	 * Constructor for the Player that sets up the object representing the player in the game.
	 * @param imageIcon, the image that shows the player's position on the board.
	 * @param point, the starting point of the player on the board.
	 * @param c, the in game character that the player has chosen.
	 * @param n, the player's name.
	 */
	public Player(ImageIcon imageIcon, Point point, String c, String n) {
		character = c;
		name = n;
		playerImage = imageIcon;
		playerCoor = point;
		isPlaying = true;
	}
	
	/**
	 * Method that waits for the 
	 */
	public void playTurn() {
		while(!endTurn) {
			
			// The user will be pressing buttons here.
			try {
				Thread.sleep(50); // I think that the thread needs to sleep in order to see changes to endTurn (remove if not needed)
			}
			catch(InterruptedException e) {}
		}
		endTurn = false; // resets the flag
	}

	/**
	 * rolls the dice and returns two random numbers as an arraylist 
	 *
	 * */
	public int[] rollDice(){
		int[] values= new int[2];
		values[0]=(int)(Math.random()*6)+1;
		values[1]=(int)(Math.random()*6)+1;
		return values;
	}

	/**
	 * keeps track of if a selected player is in the game or not
	 */
	public boolean isEliminated() {
		return !isPlaying;
	}
	public void eleimatePlayer(){
		isPlaying=false;
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
	
	/**
	 * Method that sets the endTurn flag to true.
	 */
	public void setEndStatus() {
		endTurn = true;
	}
	/**
	 * @return the players current hand 
	 * */
	public List<Card> getHand() {return playerCards;}
	
	public void setHand(List<Card> cards) {playerCards = cards;}
	
	/**
	 * getters for player image and player cooranates
	 * */
	public ImageIcon getPlayerImage() {
		return playerImage;
	}

	/**
	 * Method sets the Position of the player.
	 * @param p, the point that the player will now be at.
	 */
	public void setPoint(Point p) {
		playerCoor = p;
	}
	
	public Point getPlayerCoor() {
		return playerCoor;
	}
	
	/**
	 * Method that returns the name of the player.
	 * @return String, the name of the player.
	 */
	@Override
	public String toString() {
		return name;
	}
}