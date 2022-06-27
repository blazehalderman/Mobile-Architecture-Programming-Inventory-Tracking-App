package com.example.cs360blazehaldermanprojecttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.UUID;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT, user_id TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password, String user_id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("user_id", user_id);
        long result = MyDB.insert("users", null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public String checkUsernameGetUserId(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select user_id from users where username = ?", new String[] {username});
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            String userid = cursor.getString(0);
            return userid;
        }
        else {
            return null;
        }
    }

    public String getUsername(String user_id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select username from users where user_id = ?", new String[] {user_id});
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            String username = cursor.getString(0);
            return username;
        }
        else {
            return null;
        }
    }
}
