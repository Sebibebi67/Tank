package server;

public class Room {

    private final int MAX_PLAYER = 4;

    private static int newRoomId = 0;
    private final int id;

    // Management room object (Static methods)

    public Room() {
        this.id = newRoomId;
        newRoomId++;
    }

    public int getId() {
		return this.id;
	}
}