package server;

import entities.*;
import java.awt.*;

import java.util.ArrayList;

public class Room {

    private final int MAX_PLAYER = 4;

    private static int newRoomId = 0;
    private final int id;

    private Map map;
    private ArrayList<Player> players;

    Dimension size = new Dimension(1600, 830);

    // Management room object

    public Room() {
        this.id = newRoomId;
        newRoomId++;
    }

    public int getId() {
		return this.id;
	}
}