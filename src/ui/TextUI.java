package ui;
import game_elements.Board;

import java.util.Scanner;

public class TextUI {
	private Board board;
	public static void main(String[] argu) {new TextUI();}
	
	/**
	 * Constructor for the TextUI class, sets up the Board class that the game will be played on.
	 * Explains the rules and sets up the board that the game will be played on.
	 */
	public TextUI() {
		System.out.println("Welcome to Cluedo!\n"
				+ "This is a game where you try to determine the murderer, murder weapon and the murder room.\n"
				+ "Each player takes turns rolling the dice and moving to different rooms on the board.\n"
				+ "Once in a room a player can make a suggestion where the player suggests the murderer, weapon and room\n"
				+ "The other players try and disprove the suggestion using their cards.\n"
				+ "Once a player thinks they know who the character, weapon and room they can make an accusation "
				+ "which consists of a character, weapon and room.\nIf the suggestion is correct that player wins the game"
				+ ", however if incorrect that player is eliminated from the game and cannot make any more suggestions or accusations.");
		System.out.println("Please enter the number of players, between 3 and 6: ");
		Scanner sc = new Scanner(System.in); // Used to get the input from the user.
		String input = sc.next(); // Gets the Users input.
		// If check returns false ask again.
		while (!checkPlayerNum(input)) {
			System.out.println("The input you have given is not correct please enter an integer between 3 and 6");
			input = sc.next();
		}
		board = new Board(Integer.parseInt(input)); // When the input is fine we can create an instance of Board.
		sc.close(); // Good practice to close scanners after use.
	}
	
	/**
	 * Checks that the input the user enters is a number and in the specified range.
	 * @param num, the String to be checked.
	 * @return true if num is a number and in the appropriate range.
	 */
	private boolean checkPlayerNum(String num) {
		try {
			int number = Integer.parseInt(num); // An exception will be thrown here if the String is NAN.
			if (number < 3 || number > 6) {return false;}
		}
		catch(NumberFormatException e) {return false;} // Return false if NAN.
		return true; // Number is between 3 and 6.
	}
	
	/**
	 * Checks that the input the user entered a number.
	 * @param num, the String to be checked.
	 * @return true if num is a number.
	 */
	private static boolean checkNum(String num) {
		try {
			int number = Integer.parseInt(num); // An exception will be thrown here if the String is NAN.
		}
		catch(NumberFormatException e) {return false;} // Return false if NAN.
		return true; // Number is between 3 and 6.
	}
	
	/*=====================
	 *======HELPER METHODS
	 *=====================
	 */
	
	/**
	 * Checks the users input 
	 * @param optionNum, the number of options avaiable to the user.
	 * @return the selected option.
	 */
	public static int checkInput(int optionNum) {
	 
		if (optionNum <= 0) {return 0;}
		System.out.println("Please enter a number between 1 and " + optionNum);
		int inputNum = getInput();
		while(inputNum <= 0 || inputNum > optionNum) {
			System.out.println("That integer is outside the range given please try again!");
			inputNum = getInput();
		}
		return inputNum;
	}
	
	/**
	 * Gets the user's input and checks that the input is actually an integer value.
	 * @return the integer the player entered.
	 */
	private static int getInput() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter an integer to select an option!");
		String input = "";
		// If the scanner has an input.
		if(sc.hasNext()) {
			input = sc.nextLine();
		}
		// No input
		else {
			while(!sc.hasNext()) {} // Wait until there is input.
			input = sc.nextLine();
		}
		
		while(!checkNum(input)) {
			System.out.println("That input is not an integer please try again!");
			input = sc.nextLine();
		}
		return Integer.parseInt(input);
	}
}
