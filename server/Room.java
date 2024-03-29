package server;

import entities.*;
import message.CSMessage;

import java.awt.Dimension;
import java.util.ArrayList;

public class Room {

    //private final int MAX_PLAYER = 4;

    private final int id;

    private Map map;

    private ArrayList<Player> players;

    Dimension size = new Dimension(1600, 830);

    // Management room object

    public Room(int roomID, int playerID) {
        this.id = roomID;

        this.map = new Map();

        this.players = new ArrayList<>();
        this.players.add(new Player(500,500, map, playerID));
    }
    
    private Player getPlayer(int id){
        for (Player player : players) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

    public void addNewPlayer(int playerID) {
        this.players.add(new Player(500+50*playerID,500+50*playerID, map, playerID));
    }

    public void update(int playerID, CSMessage msg) {
        Player player = getPlayer(playerID);
        player.update(msg.getKeys(), msg.getDiff());
        player.setAlphaCanon2(msg.getXMouse(), msg.getYMouse());
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