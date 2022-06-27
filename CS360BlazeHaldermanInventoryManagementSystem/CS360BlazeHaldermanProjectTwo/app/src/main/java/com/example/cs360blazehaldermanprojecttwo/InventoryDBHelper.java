package com.example.cs360blazehaldermanprojecttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InventoryDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "InventoryItems.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_inventory";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_ITEM_DATE = "item_date";
    private static final String COLUMN_ITEM_QUANTITY = "item_quantity";
    private static final String COLUMN_USER_ID = "user_id";

    InventoryDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ITEM_NAME + " TEXT, " +
                        COLUMN_ITEM_DATE + " DATE, " +
                        COLUMN_ITEM_QUANTITY + " INTEGER, " +
                        COLUMN_USER_ID + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    void addInventoryItem(String item_name, int item_quantity, String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

        cv.put(COLUMN_ITEM_NAME, item_name);
        cv.put(COLUMN_ITEM_DATE, dateFormat.format(new Date()));
        cv.put(COLUMN_ITEM_QUANTITY, item_quantity);
        cv.put(COLUMN_USER_ID, user_id);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to insert data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added data Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(String user_id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[] {user_id});
        }
        return cursor;
    }

    void updateData(String row_id, String item_name, String item_quantity, String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

        cv.put(COLUMN_ITEM_NAME, item_name);
        cv.put(COLUMN_ITEM_DATE, dateFormat.format(new Date()));
        cv.put(COLUMN_ITEM_QUANTITY, item_quantity);
        cv.put(COLUMN_USER_ID, user_id);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == - 1) {
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        } else  {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[] {row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
}
