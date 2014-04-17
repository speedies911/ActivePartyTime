package com.app.activepartytime.core.game;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Dave on 13.3.14.
 */
public class Player implements Serializable {

    private String name;
    private File avatar;
    private Team team;

    public Player(String name, File avatar, Team team) {
        this.name = name;
        this.avatar = avatar;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getAvatar() {
        return avatar;
    }

    public void setAvatar(File avatar) {
        this.avatar = avatar;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", team=" + team +
                '}';
    }
}
