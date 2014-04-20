package com.app.activepartytime.core.game.tasks;

import android.graphics.drawable.Drawable;

import com.app.activepartytime.R;

import java.util.Random;

/**
 * Created by Dave on 13.3.14.
 */
public enum TaskType {

    DRAWING("Drawing", R.drawable.drawing),
    SPEAKING("Talking", R.drawable.speaking),
    PANTOMIME("Pantomime", R.drawable.pantomime);

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
        Random r = new Random();
        int rand = r.nextInt(3);

        TaskType[] types = {DRAWING,SPEAKING,PANTOMIME};
        return types[rand];
    }
}
