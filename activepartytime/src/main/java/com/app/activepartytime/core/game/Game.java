package com.app.activepartytime.core.game;

import java.util.List;

/**
 * Created by Dave on 3.4.14.
 */
public class Game {

    private Playground playground;
    private List<Team> teams;

    public Game(int playgroundLength, List<Team> teams) {
        this.playground = new Playground(playgroundLength);
        this.playground.generateTasks();

        this.teams = teams;
    }

    /**
     * TODO
     * @param team
     */
    public void move(Team team) {

        Task task = playground.getTask(team.getPlaygroundPosition());

        // PERFORMING TASK
        // WAITING FOR RESPONSE

        if (DONE) {
            team.moveTeam(task.getPoints());
        }

    }

    /**
     * TODO fix while condition and complete the game progress
     */
    public void play() {
        while(true) {
            for (Team team : teams) {
                move(team);
            }
        }
    }
}
