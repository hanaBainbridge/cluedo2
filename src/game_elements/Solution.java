package game_elements;

import cards.Card;

public class Solution {
	
	private Card characterCard;
	private Card roomCard;
	private Card weaponCard;

	public static void main(String[] args) {}
	
	/**
	 * Constructor for the solution class, accepts 3 Strings.
	 * @param c, the murderer.
	 * @param w, the murder weapon.
	 * @param r, the murder room.
	 */
	public Solution(Card cCard, Card wCard, Card rCard) {		
		characterCard=cCard;
		roomCard=rCard;
		weaponCard=wCard;
	}
	
	/**
	 * Method overrides equals method in Object class.
	 * @param o, the object to be checked.
	 * @return true if Object is equal to this one.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Solution))
			return false;
		Solution other = (Solution) obj;
		if (characterCard == null) {
			if (other.characterCard != null)
				return false;
		} else if (!characterCard.equals(other.characterCard))
			return false;
		if (roomCard == null) {
			if (other.roomCard != null)
				return false;
		} else if (!roomCard.equals(other.roomCard))
			return false;
		if (weaponCard == null) {
			if (other.weaponCard != null)
				return false;
		} else if (!weaponCard.equals(other.weaponCard))
			return false;
		return true;
	}
	
	/**
	 * getter methods 
	 * */
	public Card getRoomCard() {
		return roomCard;
	}


	public Card getWeaponCard() {
		return weaponCard;
	}

	public Card getCharacterCard() {
		return characterCard;
	}

	
	
	
}