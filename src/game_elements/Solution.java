package game_elements;

public class Solution {
	
	private String character; // The murderer.
	private String weapon; // The murder weapon.
	private String room; // The murder room.

	public static void main(String[] args) {}
	
	/**
	 * Constructor for the solution class, accepts 3 Strings.
	 * @param c, the murderer.
	 * @param w, the murder weapon.
	 * @param r, the murder room.
	 */
	public Solution(String c, String w, String r) {
		character = c;
		weapon = w;
		room = r;
	}
	
	/**
	 * Method overrides equals method in Object class.
	 * @param o, the object to be checked.
	 * @return true if Object is equal to this one.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Solution) {
			Solution s = (Solution)o;
			return (character.equals(s.character) && weapon.equals(s.weapon) && room.equals(s.room)); // returns true if all the Strings are equal.
		}
		return false;
	}
	
	public String getCharacter() {return character;}
	public String getWeapon() {return weapon;}
	public String getRoom() {return room;}
}