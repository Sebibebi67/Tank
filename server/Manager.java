package server;

import java.util.ArrayList;

import message.InitMessage;
import message.SCMessage;

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

    private void createRoom(int roomID, int playerID) {
        Room r = new Room(roomID, playerID);
        rooms.add(r);
        //return r.getId();
    }

    private void play(Room r) {
        
    }

    public InitMessage connect(int roomID, int playerID) {
        if (!isOpen(roomID)) {
            createRoom(roomID, playerID);
            Room newRoom = getRoom(roomID);

            return new InitMessage(
                playerID, 
                newRoom.getMap().getTab(), 
                null
            );
        }
        this.play(getRoom(roomID));
        return null;
    }
}