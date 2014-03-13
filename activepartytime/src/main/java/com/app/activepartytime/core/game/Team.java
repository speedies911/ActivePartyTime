package com.app.activepartytime.core.game;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by Dave on 13.3.14.
 */
public class Team {

    private String name;
    private Color color;
    private ArrayList<Player> players;
    private short playgroundPosition;

    public Team(String name, Color color) {
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
}
