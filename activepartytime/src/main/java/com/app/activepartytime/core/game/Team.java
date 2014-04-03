package com.app.activepartytime.core.game;

import android.graphics.Color;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Dave on 13.3.14.
 */
public class Team {

    private short id;
    private String name;
    private Color color;
    private ArrayList<Player> players;
    private short playgroundPosition;

    private Socket socket;

    public Team(short id) {
        this.id = id;
        this.players = new ArrayList<Player>();
        this.playgroundPosition = 0;
    }

    public Team(short id, String name, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.players = new ArrayList<Player>();
        this.playgroundPosition = 0;
    }

    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    public short getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}
