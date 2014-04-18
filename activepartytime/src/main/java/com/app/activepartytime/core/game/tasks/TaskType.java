package com.app.activepartytime.core.game.tasks;

import android.graphics.drawable.Drawable;

import com.app.activepartytime.R;

/**
 * Created by Dave on 13.3.14.
 */
public enum TaskType {

    DRAWING("Drawing", R.drawable.drawing),
    SPEAKING("Talking", R.drawable.speaking),
    PANTOMIME("Pantomime", R.drawable.pantomime),
    MAZE("Maze", R.drawable.maze);

    private final int imageLocation;
    private final String name;

    private TaskType(String name, int imageLocation) {
        this.name = name;
        this.imageLocation = imageLocation;
    }

    public int getImageLocation() {
        return imageLocation;
    }

    public String getName() {
        return name;
    }

    /**
     * TODO improve if it is necessary
     * @return random tasktype
     */
    public static TaskType getRandomType() {
        int rand = (int)Math.random()*4;
        TaskType[] types = {DRAWING,SPEAKING,PANTOMIME,MAZE};
        return types[rand];
    }
}
