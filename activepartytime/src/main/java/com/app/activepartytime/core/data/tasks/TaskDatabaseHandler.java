package com.app.activepartytime.core.data.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.FormatException;

import com.app.activepartytime.R;
import com.app.activepartytime.core.game.Task;
import com.app.activepartytime.core.game.tasks.TaskType;

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
    private static final String KEY_USED = "used";

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
                + KEY_TASKTYPE + " INTEGER,"
                + KEY_USED + " INTEGER)";
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
        values.put(KEY_TASKTYPE, task.getTaskTypeID());
        values.put(KEY_USED, 0);


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
        String countQuery = "SELECT count(*) FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int taskCount = cursor.getInt(0);
        cursor.close();

        return taskCount;
    }



    public int updateTaskDB(TaskDB task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_POINTS, task.getPoints());
        values.put(KEY_TASKTYPE, task.getTaskTypeID());

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
          //  System.out.println(line);
            int row =0;
            StringTokenizer st;
            while (line != null) {
                row++;
                try {


                st = new StringTokenizer(line,"*");
                int taskType = Integer.parseInt(st.nextToken().replaceAll(" ",""));
                String name = st.nextToken();
                int points = Integer.parseInt(st.nextToken().replaceAll(" ", ""));

                TaskDB task = new TaskDB(name, points, taskType);
                addTask(task);

                }catch (NumberFormatException e){
                    System.out.println("bad row " + row);
                }
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

    public TaskDB getTask(TaskType taskType, int points, boolean used) {

        int finalPoints = (int)(Math.random() * 7 + 1);//numbers: 1 2 3 4 5 6 7  - I hope :-)
        finalPoints = (finalPoints % 4)+3 -1;
        if (finalPoints == 2){//task for all
            finalPoints = 6;

        }else{
            if (points != -1){//points were selected by player

                finalPoints = points;
            }

        }

        String countQuery = "SELECT * FROM " + TABLE_TASKS + " where " + KEY_TASKTYPE +"=" + taskType.getTypeID();
        if (points != -1){
            countQuery += "AND " + KEY_POINTS + "=" + finalPoints;

        }
        if (used){
            countQuery += "AND " + KEY_USED + "=" + used;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int count = cursor.getCount();
        int id = (int)(Math.random() * count );

        cursor.moveToPosition(id);
        TaskDB task = new TaskDB(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)));
        cursor.close();

        return task;
    }

    public TaskDB[] getFinalTasks(){
        TaskDB [] tasks = new TaskDB[6];

        /*
        drawing
         */

        String query = "SELECT * FROM " + TABLE_TASKS + " where " + KEY_TASKTYPE +"=" + TaskType.DRAWING.getTypeID();
        TaskDB[] twoTasks = getTwoTasks(query);
        tasks[0] = twoTasks[0];
        tasks[3] = twoTasks[1];


        /*
        speaking
         */
        query = "SELECT * FROM " + TABLE_TASKS + " where " + KEY_TASKTYPE +"=" + TaskType.SPEAKING.getTypeID();
        twoTasks = getTwoTasks(query);
        tasks[1] = twoTasks[0];
        tasks[4] = twoTasks[1];

        /*
        pantomime
         */
        query = "SELECT * FROM " + TABLE_TASKS + " where " + KEY_TASKTYPE +"=" + TaskType.PANTOMIME.getTypeID();
        twoTasks = getTwoTasks(query);
        tasks[2] = twoTasks[0];
        tasks[5] = twoTasks[1];

        return tasks;
    }

    private TaskDB[] getTwoTasks(String query){
        TaskDB[] tasks = new TaskDB[2];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int count = cursor.getCount();
        int id = (int)(Math.random() * count );
        int id2 = (int)(Math.random() * count );
        while(id == id2){
            id2 = (int)(Math.random() * count );
        }
        cursor.moveToPosition(id);
        tasks[0] = new TaskDB(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)));
        cursor.moveToPosition(id2);
        tasks[1] = new TaskDB(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)));
        cursor.close();
        return tasks;
    }
}
