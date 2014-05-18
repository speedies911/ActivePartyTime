package com.app.activepartytime.core.game;

import java.util.List;

/**
 * Created by Dave on 3.4.14.
 */
public class Game {

    private Playground playground;
    private Team[] teams;

    private Team currentTeam;

    public Game(int playgroundLength, Team[] teams) {
        this.playground = new Playground(playgroundLength);
        this.playground.generateTasks();

        this.teams = teams;
        this.currentTeam = null;
    }

    /**
     * TODO
     * @param team
     */
    public void moveTeam(Team team, int points) {
        if ((team.getPlaygroundPosition()+ points) >= playground.getPlaygroundLength()){//FINAL STATE
            team.moveTeam(playground.getPlaygroundLength()-team.getPlaygroundPosition()-1);
        }else{
            team.moveTeam(points);
        }
    }

    public void startGame() {
        currentTeam = teams[0];
    }

    public void nextTeam() {
        int id = currentTeam.getId();
        int next = (id+1) % teams.length;
        currentTeam = teams[next];
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public Playground getPlayground() {
        return playground;
    }

    public Team[] getTeams() {
        return teams;
    }
}
