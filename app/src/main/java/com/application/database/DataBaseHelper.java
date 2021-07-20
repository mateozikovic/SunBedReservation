package com.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.application.database.models.BeachModel;
import com.application.database.models.UserModel;

public class DataBaseHelper extends SQLiteOpenHelper {

    // User Table Constants
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_E_MAIL = "E_MAIL";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String COLUMN_CITY = "CITY";
    public static final String COLUMN_COUNTRY = "COUNRTY";
    public static final String COLUMN_POSTAL_CODE = "POSTAL_CODE";

    // Beach Table Constants
    public static final String BEACH_TABLE = "BEACH_TABLE";
    public static final String COLUMN_BEACH_NAME = "BEACH_NAME";
    public static final String COLUMN_BEACH_LOCATION = "BEACH_LOCATION";
    public static final String COLUMN_BEACH_INFO = "BEACH_INFO";

    // Sunbed Table constants
    public static final String SUNBED_TABLE = "SUNBED_TABLE";
    public static final String COLUMN_BEACH_ID = "BEACH_ID";
    public static final String COLUMN_SECTION = "SECTION";
    public static final String COLUMN_NUMBER = "NUMBER";
    public static final String COLUMN_DAILY_PRICE_SEASON = "DAILY_PRICE_SEASON";
    public static final String COLUMN_DAILY_PRICE_NOSEASON = "DAILY_PRICE_NOSEASON";
    public static final String COLUMN_RESERVED = "RESERVED";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "AppDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // CREATE USER TABLE
        String createTableStatement = "CREATE TABLE " + USER_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIRST_NAME + " TEXT, " + COLUMN_LAST_NAME + " TEXT, " + COLUMN_E_MAIL +  " TEXT NOT NULL UNIQUE, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_ADDRESS + " TEXT, " + COLUMN_CITY + " TEXT, " + COLUMN_COUNTRY + " TEXT, " + COLUMN_POSTAL_CODE + " INTEGER)";

        db.execSQL(createTableStatement);

        // Create Beach table
        String createBeachTableStatement = "CREATE TABLE " + BEACH_TABLE + "(" + COLUMN_BEACH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BEACH_NAME + " TEXT, " + COLUMN_BEACH_LOCATION + " TEXT, " + COLUMN_BEACH_INFO + " TEXT)";

        db.execSQL(createBeachTableStatement);

        String createSunbedTableStatement = "CREATE TABLE " + SUNBED_TABLE + "(SUNBED_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BEACH_ID + " INTEGER, " + COLUMN_SECTION + " TEXT, " + COLUMN_NUMBER + " INT, " + COLUMN_DAILY_PRICE_SEASON + " REAL, " + COLUMN_DAILY_PRICE_NOSEASON + " REAL, " + COLUMN_RESERVED + " INT, FOREIGN KEY(BEACH_ID) REFERENCES BEACH_TABLE(BEACH_ID)";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Function for adding one user in user table
    public boolean addOne (UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

         cv.put(COLUMN_FIRST_NAME, userModel.getFirstName());
         cv.put(COLUMN_LAST_NAME, userModel.getLastName());
         cv.put(COLUMN_E_MAIL, userModel.getEMail());
         cv.put(COLUMN_PASSWORD, userModel.getPassword());
         cv.put(COLUMN_ADDRESS, userModel.getAddress());
         cv.put(COLUMN_CITY, userModel.getCity());
         cv.put(COLUMN_COUNTRY, userModel.getCountry());
         cv.put(COLUMN_POSTAL_CODE, userModel.getPostalCode());

        long insert = db.insert(USER_TABLE, null, cv);
        if(insert == -1) return false;
        else return true;
    }

    // Function for adding beaches into beach table
    public boolean addBeach(BeachModel beachModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRST_NAME, beachModel.getBeachName());
        cv.put(COLUMN_BEACH_LOCATION, beachModel.getBeachLocation());
        cv.put(COLUMN_BEACH_INFO, beachModel.getBeachInfo());

        long insert = db.insert(BEACH_TABLE, null, cv);
        if(insert == -1) return false;
        else return true;

    }

}
