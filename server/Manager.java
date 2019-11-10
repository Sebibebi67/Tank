package server;

import entities.*;
import message.*;

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

    private void createRoom(int roomID, int playerID) {
        Room r = new Room(roomID, playerID);
        rooms.add(r);
        //return r.getId();
    }

    public ArrayList<SCMessage> play(int roomID, int playerID, CSMessage msg) {
        Room room = getRoom(roomID);

        room.update(playerID, msg);

        ArrayList<Player> players = room.getPlayers();
        ArrayList<Double[]> shots = new ArrayList<>();

        ArrayList<SCMessage> messages = new ArrayList<>();

        for (Player p : players) {
            messages.add(new SCMessage(
                p.getAlphaCanon(),
                p.getAlphaMove(),
                p.getX(),
                p.getY(),
                shots,
                p.getId()
            ));
        }

        return messages;
    }

    public char[][] getMap(int roomID){
        return getRoom(roomID).getMap().getTab();
    }

    public ArrayList<SCMessage> connect(int roomID, int playerID) {
        if (!isOpen(roomID)) {
            createRoom(roomID, playerID);
        } else {
            //this.play(getRoom(roomID), playerID);
            getRoom(roomID).addNewPlayer(playerID);
        }

        Room room = getRoom(roomID);
        ArrayList<Player> players = room.getPlayers();
        ArrayList<Double[]> shots = new ArrayList<>();

        ArrayList<SCMessage> messages = new ArrayList<>();

        for (Player p : players) {
            messages.add(new SCMessage(
                p.getAlphaCanon(),
                p.getAlphaMove(),
                p.getX(),
                p.getY(),
                shots,
                p.getId()
            ));
        }

        return messages;
    }
}