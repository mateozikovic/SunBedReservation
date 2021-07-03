package com.application.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_E_MAIL = "E_MAIL";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String COLUMN_CITY = "CITY";
    public static final String COLUMN_COUNRTY = "COUNRTY";
    public static final String COLUMN_POSTAL_CODE = "POSTAL_CODE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "AppDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIRST_NAME + " TEXT, " + COLUMN_LAST_NAME + " TEXT, " + COLUMN_E_MAIL + " TEXT NOT NULL UNIQUE, " + COLUMN_ADDRESS + " TEXT, " + COLUMN_CITY + " TEXT, " + COLUMN_COUNRTY + " TEXT, " + COLUMN_POSTAL_CODE + " INTEGER)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
