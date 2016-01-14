package com.sergiogomeza.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 12/01/2016.
 */
public class databaseHandler extends SQLiteOpenHelper {
    private static String TABLE_NAME="Users";
    final static String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+
            "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" name TEXT, email TEXT, phone TEXT, password TEXT)";
    public databaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
        onCreate(db);
    }
    /*
    public void createContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", contact.get_name());
        values.put("email", contact.get_email());
        values.put("phone", contact.get_phone());
        values.put("password", contact.get_pass());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public Contact getContact(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{"id","name","email","phone","password"},"id=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        return contact;
    }

    public int getContactsCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        return count;
    }*/
}
