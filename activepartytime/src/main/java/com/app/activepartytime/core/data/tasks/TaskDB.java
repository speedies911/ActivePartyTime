package com.app.activepartytime.core.data.tasks;

import com.app.activepartytime.core.game.tasks.TaskType;

/**
 * Created by Dave on 16.4.14.
 */
public class TaskDB {

    private int _id;
    private String _name;
    private int _points;
    private TaskType _taskType;

    public TaskDB() {}

    public TaskDB(int id, String name, int points, int taskType) {
        this._id = id;
        this._name = name;
        this._points = points;
        switch (taskType){
            case 0: _taskType = TaskType.DRAWING; break;
            case 1: _taskType = TaskType.SPEAKING; break;
            case 2: _taskType = TaskType.PANTOMIME; break;
        }

    }

    public TaskDB(String name, int points, int taskType) {
        this._name = name;
        this._points = points;
        switch (taskType){
            case 0: _taskType = TaskType.DRAWING; break;
            case 1: _taskType = TaskType.SPEAKING; break;
            case 2: _taskType = TaskType.PANTOMIME; break;
        }
    }

    public int getID() {
        return _id;
    }

    public void setID(int _id) {
        this._id = _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public int getPoints() {
        return _points;
    }

    public void setPoints(int _points) {
        this._points = _points;
    }

    public TaskType getTaskType() {
        return _taskType;
    }

    public int getTaskTypeID() {
        switch (_taskType){
            case DRAWING: return 0;
            case SPEAKING: return 1;
            case PANTOMIME: return 2;
        }
        return  -1;
    }

    public void setTaskType(int taskType) {
        switch (taskType){
            case 0: _taskType = TaskType.DRAWING; break;
            case 1: _taskType = TaskType.SPEAKING; break;
            case 2: _taskType = TaskType.PANTOMIME; break;
        }
    }

    @Override
    public String toString() {
        return "TaskDB{" +
                "_id=" + _id +
                ", _name='" + _name + '\'' +
                ", _points=" + _points +
                ", _taskType=" + _taskType +
                '}';
    }
}
