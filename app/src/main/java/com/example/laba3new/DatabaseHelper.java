package com.example.laba3new;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "Одногруппники";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_FIO = "ФИО";
    public static final String COLUMN_DATE = "ВремяДобавления";

    public static final String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FIO + " TEXT, " +
            COLUMN_DATE + " TEXT)";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }

    public void clearAndInsertInitialData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);

        String[] names = {"Иванов Иван Иванович", "Петров Петр Петрович",
                "Сидоров Сидор Сидорович", "Кузнецов Кузьма Кузьмича",
                "Семенов Семен Семенович"};
        for (String name : names) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FIO, name);
            values.put(COLUMN_DATE, String.valueOf(System.currentTimeMillis()));
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }
}
