package server;

import java.util.ArrayList;

public class Manager {

    private ArrayList<Room> rooms = new ArrayList<>();

    public boolean isOpened(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public int createRoom() {
        
    }
}