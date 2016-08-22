package cards;

import javax.swing.ImageIcon;

/**
 * Note that this class is a base for all the subclasses of card in Cluedo.
 * @author Connor 
 *
 */
public abstract class Card {
	protected String name;
	protected boolean eliminated;
	protected ImageIcon image;
	
	/**
	 * Returns if the card has been eliminated from the possible choices
	 * @return true if eliminated, false if not.
	 */
	public boolean isEliminated() {return eliminated;}
	
	/**
	 * Sets the eliminated field to b.
	 * This is only one way, that is if the card has been eliminated it cannot be reversed.
	 * @param b, the boolean value.
	 */
	public void setEliminated(boolean b) {
		if(!eliminated) {
			// hello
			eliminated = b;
		}
	}
	
	/**
	 * Returns the ImageIcon that represents the card.
	 * This is used to draw the card on the board.
	 * @return ImageIcon, the image that represents the card.
	 */
	public ImageIcon getImage() {return image;}
	
	/**
	 * Overrides the toString method in Object to return the name.
	 * @return String, the name of the card.
	 */
	@Override
	public String toString() {
		return name;
	}
}
