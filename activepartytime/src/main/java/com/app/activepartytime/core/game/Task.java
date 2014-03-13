package com.app.activepartytime.core.game;

import com.app.activepartytime.core.game.tasks.TaskType;

/**
 * Created by Dave on 13.3.14.
 */
public class Task {

    private TaskType type;
    private short points;

    public Task(TaskType type, short points) {
        this.type = type;
        this.points = points;
    }

}
