package com.app.activepartytime.core.game.tasks;

import com.app.activepartytime.core.game.Task;

/**
 * Created by Dave on 13.3.14.
 */
public class Basic extends Task {

    private String word;

    public Basic(TaskType type, short points) {
        super(type, points);
        generateWord();
    }

    /**
     * TODO generate word from dictionary
     */
    private void generateWord() {

    }

}
