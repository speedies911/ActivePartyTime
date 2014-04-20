package com.app.activepartytime.core.game;

import com.app.activepartytime.core.game.tasks.TaskType;

/**
 * Created by Dave on 13.3.14.
 */
public class Playground {

    private TaskType[] playground;
    private int playgroundLength;

    public Playground(int playgroundLength) {
        this.playgroundLength = playgroundLength;
        this.playground = new TaskType[playgroundLength];
    }

    public void generateTasks() {
        for (int i = 0; i < playgroundLength; i++) {
            playground[i] = TaskType.getRandomType();
        }
    }

    public TaskType getTask(int position) {
        return playground[position];
    }
}
