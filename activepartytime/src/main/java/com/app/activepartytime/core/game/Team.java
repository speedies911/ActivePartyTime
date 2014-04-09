package com.app.activepartytime.core.game;

import android.graphics.Color;
import android.os.Parcelable;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Dave on 13.3.14.
 */
public class Team implements Serializable {

    private short id;
    private String name;
    private int color;
    private ArrayList<Player> players;
    private int playgroundPosition;

    //private Socket socket;

    public Team(short id) {
        this.id = id;
        this.players = new ArrayList<Player>();
        this.playgroundPosition = 0;
    }

    public Team(short id, String name, int color) {
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

    public void moveTeam(int points) {
        this.playgroundPosition += points;
    }

    public short getId() {
        return id;
    }

    public int getPlaygroundPosition() {
        return playgroundPosition;
    }

    public void setPlaygroundPosition(int playgroundPosition) {
        this.playgroundPosition = playgroundPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /*public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }*/

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                '}';
    }
}
