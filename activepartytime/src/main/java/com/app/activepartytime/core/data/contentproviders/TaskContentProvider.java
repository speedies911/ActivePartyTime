package com.app.activepartytime.core.data.contentproviders;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.app.activepartytime.core.data.TaskDatabaseHelper;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Dave on 12.4.14.
 */
public class TaskContentProvider extends ContentProvider {

    private TaskDatabaseHelper database;

    private static final int TASKS = 10;
    private static final int TASK_ID = 20;

    private static final String AUTHORITY = "com.app.activepartytime.core.data.contentproviders";
    private static final String BASE_PATH = "tasks";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/tasks";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/task";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, TASKS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, TASK_ID);
    }

    @Override
    public boolean onCreate() {
        database = new TaskDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        checkColumns(projection);
        queryBuilder.setTables(TaskDatabaseHelper.TABLE_NAME);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case TASKS:
                break;
            case TASK_ID:
                queryBuilder.appendWhere(TaskDatabaseHelper.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case TASKS:
                id = sqlDB.insert(TaskDatabaseHelper.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case TASKS:
                rowsDeleted = sqlDB.delete(TaskDatabaseHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case TASK_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(TaskDatabaseHelper.TABLE_NAME,
                            TaskDatabaseHelper.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(TaskDatabaseHelper.TABLE_NAME,
                        TaskDatabaseHelper.COLUMN_ID + "=" + id
                        + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case TASKS:
                rowsUpdated = sqlDB.update(TaskDatabaseHelper.TABLE_NAME,
                        values, selection, selectionArgs);
                break;
            case TASK_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(TaskDatabaseHelper.TABLE_NAME,
                            values,
                            TaskDatabaseHelper.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(TaskDatabaseHelper.TABLE_NAME,
                            values,
                            TaskDatabaseHelper.COLUMN_ID + "=" + id
                            + " and "
                            + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = { TaskDatabaseHelper.COLUMN_POINTS,
                TaskDatabaseHelper.COLUMN_TASK_NAME, TaskDatabaseHelper.COLUMN_ID
                };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
