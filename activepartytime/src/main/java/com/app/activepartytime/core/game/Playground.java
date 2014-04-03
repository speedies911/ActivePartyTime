package com.app.activepartytime.core.game;

/**
 * Created by Dave on 13.3.14.
 */
public class Playground {

    private Task[] playground;
    private int playgroundLength;

    public Playground(int playgroundLength) {
        this.playgroundLength = playgroundLength;
        this.playground = new Task[playgroundLength];
    }

    public void generateTasks() {
        for (int i = 0; i < playgroundLength; i++) {
            playground[i] = Task.getRandomTask();
        }
    }

    public Task getTask(int position) {
        return playground[position];
    }
}
