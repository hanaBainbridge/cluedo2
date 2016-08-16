package game_elements;

import java.awt.Point;
import java.util.ArrayList;

public class Room {

	private ArrayList<Point> entryPoints = new ArrayList<Point>(); // Holds the points of the entries to the room.
	private Room connectedRoom; // The room that this room is connected to via a stair case, note that this will be null if there is no connecting room.
	private String name;
	
	public static void main(String[] args) {}

	/**
	 * Constructor accepts an ArrayList of Points which are the entrances to the room on the board and the room name.
	 * @param entries, The entrances.
	 * @param n, the name of the room.
	 */
	public Room(ArrayList<Point> entries, String n) {
		entryPoints = entries;
		name = n;
	}
	
	/**
	 * Returns whether or not this room has a room that is connected to it.
	 * @return true if this room is connected to another room, false if not.
	 */
	public boolean hasStairCase() {
		return (connectedRoom != null);
	}
	
	/**
	 * Sets the connected room using the given parameter.
	 * @param room, the room to be set as the connected room.
	 * @return true if room set successfully, false if not.
	 */
	public boolean setConnectedRoom(Room room) {
		// I only want the room to be set once
		if (connectedRoom == null) {
			connectedRoom = room;
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the connected room.
	 * @return connectedRoom, returns the room that this one is connected to, or null if not connected to any room
	 */
	public Room getConnectedRoom(){
		return connectedRoom;
	}
	
	/**
	 * Returns the entry points for the room.
	 * @return entryPoints, returns the ArrayList of Points which are the co-ordinates of entrances on the board.
	 */
	public ArrayList<Point> getEntryPoints(){
		return entryPoints;
	}
	
	/**
	 * Method checks if a given point x,y is an entry point for this room.
	 * @param x, the x co-ordinate.
	 * @param y, the y co-ordinate.
	 * @return true if x,y is an entry point, false if not.
	 */
	public boolean containsEntry(int x, int y) {
		if (entryPoints.contains(new Point(x,y))) {return true;}
		return false;
	}
	
	/**
	 * Returns the name of the room.
	 * @return String, the name of the room.
	 */
	@Override
	public String toString() {
		return name;
	}
}