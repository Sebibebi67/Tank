package server;

import java.util.ArrayList;

public class Manager {

    private ArrayList<Room> rooms = new ArrayList<>();

    private boolean isOpened(int id) {
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

    public void createRoom() {
        Room r = new Room();
        rooms.add(r);
        //return r.getId();
    }

    /*public int play() {

    }*/

    public void connect(int id) {
        if (!isOpened(id)) {
            createRoom();
        }
        
        //play()
    }
}