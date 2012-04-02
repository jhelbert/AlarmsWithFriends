package sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import sqlite.MyAlarmDB;

public class MyAlarmDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.TIME };

    public MyAlarmDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public MyAlarmDB createAlarm(int comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TIME, comment);
        long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        MyAlarmDB newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(MyAlarmDB comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<MyAlarmDB> getAllComments() {
        List<MyAlarmDB> comments = new ArrayList<MyAlarmDB>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyAlarmDB comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return comments;
    }

    private MyAlarmDB cursorToComment(Cursor cursor) {
        MyAlarmDB alarm = new MyAlarmDB();
        alarm.setId(cursor.getLong(0));
        alarm.setComment(cursor.getString(1));
        return alarm;
    }
}
