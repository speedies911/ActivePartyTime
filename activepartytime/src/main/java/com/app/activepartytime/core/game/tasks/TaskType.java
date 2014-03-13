package com.app.activepartytime.core.game.tasks;

import com.app.activepartytime.R;

/**
 * Created by Dave on 13.3.14.
 *
 * TODO images
 */
public enum TaskType {

    DRAWING(R.drawable.drawing),
    TALKING(R.drawable.talking),
    PANTOMIME(R.drawable.pantomime),
    MAZE(R.drawable.maze);

    private final String imageLocation;
    private final String name;

    private TaskType(String name, String imageLocation) {
        this.name = name;
        this.imageLocation = imageLocation;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public String getName() {
        return name;
    }
}
