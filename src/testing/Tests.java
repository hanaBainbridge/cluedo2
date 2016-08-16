package testing;

import static org.junit.Assert.*;
import game_elements.Board;

import org.junit.Test;

public class Tests {

	/**
	 * Tests whether the dice roll contantly rolls in the expected range, fails if the roll is less than 2 or greater than 12.
	 */
	@Test
	public void diceTest() {
		Board testBoard = new Board(3);
		for(int i = 0; i < 100; i ++) {
			int diceRoll = testBoard.rollDice();
			if(diceRoll > 12 || diceRoll < 2) {
				fail();
			}
		}
	}
	
	@Test
	public void displayBoardTest() {
		System.out.println("Testing 3 player game");
		new Board(3).displayBoard();
		System.out.println("Testing 4 player game");
		new Board(4).displayBoard();
		System.out.println("Testing 5 player game");
		new Board(5).displayBoard();
		System.out.println("Testing 6 player game");
		new Board(6).displayBoard();
	}
	
	public static void main(String[] args) {}

}