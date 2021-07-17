package com.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.application.database.models.BeachModel;
import com.application.database.models.UserModel;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_E_MAIL = "E_MAIL";
    public static final String COLUMN_PASSWORD = "PASSWORD";
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
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIRST_NAME + " TEXT, " + COLUMN_LAST_NAME + " TEXT, " + COLUMN_E_MAIL +  " TEXT NOT NULL UNIQUE, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_ADDRESS + " TEXT, " + COLUMN_CITY + " TEXT, " + COLUMN_COUNRTY + " TEXT, " + COLUMN_POSTAL_CODE + " INTEGER)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne (UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();



         cv.put(COLUMN_FIRST_NAME, userModel.getFirstName());
         cv.put(COLUMN_LAST_NAME, userModel.getLastName());
         cv.put(COLUMN_E_MAIL, userModel.getEMail());
         cv.put(COLUMN_PASSWORD, userModel.getPassword());
         cv.put(COLUMN_ADDRESS, userModel.getAddress());
         cv.put(COLUMN_CITY, userModel.getCity());
         cv.put(COLUMN_COUNRTY, userModel.getCountry());
         cv.put(COLUMN_POSTAL_CODE, userModel.getPostalCode());

        long insert = db.insert(USER_TABLE, null, cv);
        if(insert == -1) return false;
        else return true;
    }

    public boolean addBeach(BeachModel beachModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //todo finish the function

        return true;
    }

}
