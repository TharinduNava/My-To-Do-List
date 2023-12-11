package com.example.lab5;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Lab5.db";
    public static final String TABLE_NAME = "TaskTable";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "TASK";
    public static final String COL_4 = "DATE";
    public static final String COL_5 = "STATUS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT, TASK TEXT, DATE TEXT, STATUS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String name, String task, String date, String status) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, task);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, status);
        long result =  db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM TaskTable", null );
        return result;
    }

    public boolean updateData(String id, String name, String task, String date, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, task);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, status);
        long result =  db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
