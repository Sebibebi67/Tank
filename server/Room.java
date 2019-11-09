package server;

import entities.*;

import java.awt.Dimension;
import java.util.ArrayList;

public class Room {

    private final int MAX_PLAYER = 4;

    private final int id;

    private Map map;

    private ArrayList<Player> players;

    Dimension size = new Dimension(1600, 830);

    // Management room object

    public Room(int roomID, int playerID) {
        this.id = roomID;

        map = new Map();

        players.add(new Player(500,500, map, playerID));
    } 

    public int getId() {
		return this.id;
    }

    public Map getMap() {
        return this.map;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /*public void setPlayers(Object players) {
        this.players = players;
    }*/

    /*public void setMap(Map map) {
        this.map = map;
    }*/
}