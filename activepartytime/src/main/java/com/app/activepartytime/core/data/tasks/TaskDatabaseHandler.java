package com.app.activepartytime.core.data.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.activepartytime.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Dave on 16.4.14.
 */
public class TaskDatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_FOLDER = "database";
    private static final String DATABASE_FILENAME = "database.txt";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasksManager";
    private static final String TABLE_TASKS = "tasks";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_POINTS = "points";
    private static final String KEY_TASKTYPE = "tasktype";

    private Context context;

    public TaskDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_NAME + " TEXT,"
                + KEY_POINTS + " INTEGER,"
                + KEY_TASKTYPE + " INTEGER)";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public void addTask(TaskDB task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_POINTS, task.getPoints());
        values.put(KEY_TASKTYPE, task.getTaskType());

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    public TaskDB getTaskDB(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[] {KEY_ID, KEY_NAME, KEY_POINTS, KEY_TASKTYPE},
                KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null );
        if (cursor != null) {
            cursor.moveToFirst();
        }

        TaskDB task = new TaskDB(Integer.parseInt(cursor.getString(0)),
                                cursor.getString(1),
                                Integer.parseInt(cursor.getString(2)),
                                Integer.parseInt(cursor.getString(3)));
        cursor.close();
        return task;
    }

    public List<TaskDB> getAllTasksDB() {
        List<TaskDB> tasksList = new ArrayList<TaskDB>();

        String selectQuery = "SELECT * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TaskDB task = new TaskDB();
                task.setID(Integer.parseInt(cursor.getString(0)));
                task.setName(cursor.getString(1));
                task.setPoints(Integer.parseInt(cursor.getString(2)));
                task.setTaskType(Integer.parseInt(cursor.getString(3)));
                tasksList.add(task);
            } while (cursor.moveToNext());
        }

        return tasksList;
    }

    public int getTasksCount() {
        String countQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int taskCount = cursor.getCount();
        cursor.close();

        return taskCount;
    }

    public int updateTaskDB(TaskDB task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_POINTS, task.getPoints());
        values.put(KEY_TASKTYPE, task.getTaskType());

        return db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[] {String.valueOf(task.getID())});
    }

    public void deleteTaskDB(TaskDB task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getID())});
        db.close();
    }

    public void fillDatabase() {
        InputStream in = context.getResources().openRawResource(R.raw.database);

        BufferedReader bf = new BufferedReader(new InputStreamReader(in));

        try {

            String line = bf.readLine();
            StringTokenizer st;
            while (line != null) {
                st = new StringTokenizer(line,"-");
                int taskType = Integer.parseInt(st.nextToken());
                String name = st.nextToken();
                int points = Integer.parseInt(st.nextToken());

                TaskDB task = new TaskDB(name, points, taskType);
                addTask(task);

                line = bf.readLine();
            }
        } catch (IOException e) {
            System.err.print("Database file access error!");
        }
    }

    public TaskDB getRandomTask() {
        int count = getTasksCount();
        int id = (int)(Math.random() * count + 1);
        return getTaskDB(id);

    }

}
