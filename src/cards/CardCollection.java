package cards;
import java.util.ArrayList;;

/**
 * This is a custom collection for the cluedo game.
 * All methods are the same, except the remove methods which are not supported.
 * This is because we wish to know if the cards are eliminated and hence don't want them
 * removed.
 * @author Connor
 *
 * @param <E>
 */
public class CardCollection<E> extends ArrayList<E> {
	private int count; // The number of elements.
	private Card[] data; // The card objects in the collection

	/**
	 * The constructor that sets up the count and initializes the array.
	 */
	public CardCollection() {
		count = 0;
		data = new Card[6];
	}
	
	/**
	 * remove method is not supported.
	 */
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}
	
	public boolean cardEliminated(E card) {
		if(this.contains(card)) {
			return data[this.indexOf(card)].isEliminated(); // Returns if the Card is eliminated.
		}
		return false;
	}
	
	public static void main(String[] args) {}

}
