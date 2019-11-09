package server;

import java.util.ArrayList;

public class Manager {

    private ArrayList<Room> rooms = new ArrayList<>();

    private boolean isOpen(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private Room getRoom(int id){
        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }

    private void createRoom() {
        Room r = new Room();
        rooms.add(r);
        //return r.getId();
    }

    private void play(Room r) {
        
    }

    public void connect(int id) {
        if (!isOpen(id)) {
            createRoom();
        }
        this.play(getRoom(id));
        //play()
    }
}