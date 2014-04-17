package com.app.activepartytime.core.game;

import com.app.activepartytime.core.game.tasks.TaskType;

/**
 * Created by Dave on 13.3.14.
 */
public class Task {

    private TaskType type;
    private short points;

    private static final int MAX_POINTS = 6;
    private static final int MIN_POINTS = 3;

    public Task(TaskType type, short points) {
        this.type = type;
        this.points = points;
    }

    public static Task getRandomTask() {
        TaskType type = TaskType.getRandomType();
        short points = (short)(Math.random()*(MAX_POINTS-MIN_POINTS)+MIN_POINTS);
        return new Task(type, points);
    }

    public short getPoints() {
        return points;
    }

    public TaskType getType() {
        return type;
    }
}
