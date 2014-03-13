package com.app.activepartytime.core.game;

/**
 * Created by Dave on 13.3.14.
 */
public class Playground {

    private static final int PLAYGROUND_LENGTH = 40;
    private Task[] tasks;

    public Playground(Task[] tasks) {
        this.tasks = new Task[PLAYGROUND_LENGTH];
    }
}
