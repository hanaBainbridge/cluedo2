package game_elements;

public class DoorSquare extends Square{
	private Room room; // The room that the DoorSquare connects to.
	
	public DoorSquare(int x, int y) {
		super();
		super.xPos = x;
		super.yPos = y;
	}
	
	/**
	 * Method returns the room that the DoorSquare is connected to
	 * @return Room, the connected Room.
	 */
	public Room getRoom() {
		return room;
	}
	
	public static void main(String[] argu) {}
}
