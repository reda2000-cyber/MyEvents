package com.example.myevents;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;

import domain.Event;

public class databaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="MyEvent_dbTest";
    private static final int VERSION =1;
    private static final String TABLE_NAME="Event";
    public databaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATETABLE = "create table " + TABLE_NAME + "(id INTEGER PRIMARY KEY , Name TEXT,"
                + "DateEvent INTEGER , DateCreate INTEGER , Status INTEGER)";
        sqLiteDatabase.execSQL(CREATETABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);


    }
    public boolean addEvent (Event event){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("id",event.getId());
        contentValues.put("Name",event.getName());
        contentValues.put("DateEvent",event.getDateEvent().getTime());
        contentValues.put("DateCreate",event.getDateCreate().getTime());
        contentValues.put("Status",event.getStatus());
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return true;
    }

    public void updateEvent (Event event1,Event event2){

        SQLiteDatabase mDb= this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put("Name",event2.getName());
        args.put("DateEvent",event2.getDateEvent().getTime());
        args.put("DateCreate",event2.getDateCreate().getTime());
        args.put("Status",event2.getStatus());
        mDb.update(TABLE_NAME, args, "id" + "=" + event1.getId(), null);

    }

    public void historicalEvent (Event event){

        SQLiteDatabase mDb= this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put("Name",event.getName());
        args.put("DateEvent",event.getDateEvent().getTime());
        args.put("DateCreate",event.getDateCreate().getTime());
        args.put("Status",0);
        mDb.update(TABLE_NAME, args, "id" + "=" + event.getId(), null);

    }

    public void deleteEvent (Event event){

        SQLiteDatabase mDb= this.getWritableDatabase();
        mDb.delete(TABLE_NAME,"id = "+event.getId(),null);

    }

    @SuppressLint("Range")
    public ArrayList<Event> getAllEvent(){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Event> arrayList = new ArrayList();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+ TABLE_NAME +" WHERE Status = 1",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            arrayList.add(new Event(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("Name")),
                    new Date(cursor.getLong(cursor.getColumnIndex("DateEvent"))),
                            new Date() ,cursor.getInt(cursor.getColumnIndex("Status")))
                            ) ;

            cursor.moveToNext();
        }
        return arrayList;
    }

    @SuppressLint("Range")
    public ArrayList<Event> getAllHistoricalEvent(){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Event> arrayList = new ArrayList();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+ TABLE_NAME +" where Status = 0",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            arrayList.add(new Event(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("Name")),
                    new Date(cursor.getLong(cursor.getColumnIndex("DateEvent"))),
                    new Date() ,cursor.getInt(cursor.getColumnIndex("Status")))
            ) ;

            cursor.moveToNext();
        }
        return arrayList;
    }
}