package com.app.activepartytime.core.data.tasks;

/**
 * Created by Dave on 16.4.14.
 */
public class TaskDB {

    private int _id;
    private String _name;
    private int _points;
    private int _taskType;

    public TaskDB() {}

    public TaskDB(int id, String name, int points, int taskType) {
        this._id = id;
        this._name = name;
        this._points = points;
        this._taskType = taskType;
    }

    public TaskDB(String name, int points, int taskType) {
        this._name = name;
        this._points = points;
        this._taskType = taskType;
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

    public int getTaskType() {
        return _taskType;
    }

    public void setTaskType(int _taskType) {
        this._taskType = _taskType;
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
