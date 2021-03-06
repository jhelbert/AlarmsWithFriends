package com.main;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteAdapter {
    public HashMap<String,String> getInfo(String time) {
        HashMap<String,String> info = new HashMap<String,String>();
        String[] columns = new String[]{SETTER, TIME, ALARM_DESC, SNOOZE};
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns, 
                null, null, null, null, null);
        int index_SETTER = cursor.getColumnIndex(SETTER);
        int index_DESC = cursor.getColumnIndex(ALARM_DESC);
        int index_TIME = cursor.getColumnIndex(TIME);
        int index_SNOOZE = cursor.getColumnIndex(SNOOZE);
        ArrayList<String> not = new ArrayList<String>();
        for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
            if (cursor.getString(index_TIME).equals(time)) {
            info.put("setter", cursor.getString(index_SETTER));
            info.put("desc", cursor.getString(index_DESC));
            info.put("snooze", cursor.getString(index_SNOOZE));
            }
            else {
                not.add(cursor.getString(index_TIME));
            }
           }
        return info;
    }
    
    public Boolean alarmActive(String time) {
        boolean found = false;
        String[] columns = new String[]{SETTER, TIME, ALARM_DESC, SNOOZE};
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns, 
                null, null, null, null, null);
        int index_SETTER = cursor.getColumnIndex(SETTER);
        int index_DESC = cursor.getColumnIndex(ALARM_DESC);
        int index_TIME = cursor.getColumnIndex(TIME);
        
        for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
            if (cursor.getString(index_TIME).equals(time)) {
                found = true;
            }
            
           }
        return found;
    }

 public static final String MYDATABASE_NAME = "MY_Daat178214";
 public static final String MYDATABASE_TABLE = "MY_TABLE";
 public static final int MYDATABASE_VERSION = 1;
 public static final String SETTER = "Setter";
 public static final String TIME = "Time";
 public static final String DATE = "Date";
 public static final String ALARM_DESC = "Desc";
 public static final String SNOOZE = "Snooze";
 
 public static final String LIST_TABLE = "LIST_TABLE";
 public static final String LIST_NAME = "List_name";
 public static final String NUMBERS = "numbers";
 
 //create table MY_DATABASE (ID integer primary key, Content text not null);
 private static final String SCRIPT_CREATE_DATABASE =
  "create table " + MYDATABASE_TABLE + " ("
  + SETTER + " text not null, " + ALARM_DESC + " text not null, " + TIME + " text not null, " + DATE + " text not null, "+ SNOOZE + " text not null);";
 
 private static final String SCRIPT_CREATE_LISTS =
         "create table " + LIST_TABLE + " ("
         + LIST_NAME + " text not null, " + NUMBERS + " text not null);";
 
 private SQLiteHelper sqLiteHelper;
 private SQLiteDatabase sqLiteDatabase;

 private Context context;
 
 public SQLiteAdapter(Context c){
  context = c;
 }
 
 public SQLiteAdapter openToRead() throws android.database.SQLException {
  sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
  sqLiteDatabase = sqLiteHelper.getReadableDatabase();
  return this; 
 }
 
 public SQLiteAdapter openToWrite() throws android.database.SQLException {
  sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
  sqLiteDatabase = sqLiteHelper.getWritableDatabase();
  return this; 
 }
 
 public void close(){
  sqLiteHelper.close();
 }
 
 public long insertAlarm(String setter, String description, String time){
  
  ContentValues contentValues = new ContentValues();
  contentValues.put(SETTER, setter);
  contentValues.put(ALARM_DESC, description);
  contentValues.put(TIME, time);
  contentValues.put(SNOOZE, "3");
  return sqLiteDatabase.insert(MYDATABASE_TABLE, null, contentValues);
 }
 
 public long insertAlarm(String setter, String description, String time, String snooze, String date){
     
     ContentValues contentValues = new ContentValues();
     contentValues.put(SETTER, setter);
     contentValues.put(ALARM_DESC, description);
     contentValues.put(TIME, time);
     contentValues.put(SNOOZE, snooze);
     contentValues.put(DATE, date);
     return sqLiteDatabase.insert(MYDATABASE_TABLE, null, contentValues);
    }
 
 public long insertList(String list, String numbers){
     
     ContentValues contentValues = new ContentValues();
     contentValues.put(LIST_NAME, list);
     contentValues.put(NUMBERS, numbers);
     return sqLiteDatabase.insert(LIST_TABLE, null, contentValues);
    }
 
 public int deleteAll(){
  return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);
 }
 
 public int deleteLists(){
     return sqLiteDatabase.delete(LIST_TABLE, null, null);
    }
 
 public int deleteAlarm(String time) {
     return sqLiteDatabase.delete(MYDATABASE_TABLE, TIME+" = '"+time + "'", null);
 }
 
 
 
 public ArrayList<String> queueAllMain(){
     String[] columns = new String[]{SETTER,TIME, DATE,ALARM_DESC};
     Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns, 
       null, null, null, null, null);
     ArrayList<String> result = new ArrayList<String>();
     
     int index_CONTENT = cursor.getColumnIndex(SETTER);
     int index_TIME = cursor.getColumnIndex(TIME);
     int index_DATE = cursor.getColumnIndex(DATE);
     int index_DESC = cursor.getColumnIndex(ALARM_DESC);
     int index = 0;
     for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
         if (cursor.getString(index_CONTENT).equals("")) {
             result.add(cursor.getString(index_TIME) + "  " + cursor.getString(index_DATE) + "  " + cursor.getString(index_DESC));
         }
         else {
             result.add(cursor.getString(index_TIME) + "  " + cursor.getString(index_DATE) + "set by " + cursor.getString(index_CONTENT));
         }
         index ++;
     }
     for (String n : result) {
         if (n == null) {
             result.remove(n);
         }
     }
     return result;
    }
 
 public ArrayList<String> getGroups() {
     String[] columns = new String[]{LIST_NAME};
     Cursor cursor = sqLiteDatabase.query(LIST_TABLE, columns, 
             null, null, null, null, null);
     ArrayList<String> groups = new ArrayList<String>();
     int index_NUMBERS = cursor.getColumnIndex(LIST_NAME);
     for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
         groups.add(cursor.getString(index_NUMBERS));
        }
     return groups;
 }
 
 public String getNumbers(String list) {
     String[] columns = new String[]{LIST_NAME, NUMBERS};
     Cursor cursor = sqLiteDatabase.query(LIST_TABLE, columns, 
             LIST_NAME+" = '"+list + "'", null, null, null, null);
     int index_NUMBERS = cursor.getColumnIndex(NUMBERS);
     String result = "";
     for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
         result = cursor.getString(index_NUMBERS) + "\n";
        }
     return result;
 }
 
 
 
 
 public String queueAllLists(){
     String[] columns = new String[]{LIST_NAME, NUMBERS};
     Cursor cursor = sqLiteDatabase.query(LIST_TABLE, columns, 
       null, null, null, null, null);
     String result = "";
     int index_CONTENT = cursor.getColumnIndex(LIST_NAME);
     int index_TIME = cursor.getColumnIndex(NUMBERS);
     for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
      result = result + cursor.getString(index_CONTENT) + cursor.getString(index_TIME) + "\n";
     }
     
     return result;
 }
 
 public class SQLiteHelper extends SQLiteOpenHelper {

  public SQLiteHelper(Context context, String name,
    CursorFactory factory, int version) {
   super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
   // TODO Auto-generated method stub
      db.execSQL(SCRIPT_CREATE_LISTS);
      db.execSQL(SCRIPT_CREATE_DATABASE);
   
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
   // TODO Auto-generated method stub

  }

 }
 
}