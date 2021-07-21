package com.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.application.database.models.BeachModel;
import com.application.database.models.NewsModel;
import com.application.database.models.OrderModel;
import com.application.database.models.SunbedModel;
import com.application.database.models.UserModel;

import java.sql.SQLInput;

public class DataBaseHelper extends SQLiteOpenHelper {

    // User Table constants
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_E_MAIL = "E_MAIL";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String COLUMN_CITY = "CITY";
    public static final String COLUMN_COUNTRY = "COUNRTY";
    public static final String COLUMN_POSTAL_CODE = "POSTAL_CODE";

    // Beach Table constants
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

    // News Table constants
    public static final String COLUMN_NEWS_TITLE = "NEWS_TITLE";
    public static final String COLUMN_NEWS_TEXT = "NEWS_TEXT";
    public static final String COLUMN_NEWS_DATE = "NEWS_DATE";
    public static final String COLUMN_NEWS_IMAGE = "NEWS_IMAGE";
    public static final String NEWS_TABLE = "NEWS_TABLE";
    public static final String ORDER_TABLE = "ORDER_TABLE";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_SUNBED_ID = "SUNBED_ID";
    public static final String COLUMN_ORDER_DATE = "ORDER_DATE";
    public static final String COLUMN_RESERVATION_DATE_START = "RESERVATION_DATE_START";
    public static final String COLUMN_RESERVATION_DATE_END = "RESERVATION_DATE_END";
    public static final String COLUMN_TOTAL_COST = "TOTAL_COST";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "AppDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create User Table
        String createTableStatement = "CREATE TABLE " + USER_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIRST_NAME + " TEXT, " + COLUMN_LAST_NAME + " TEXT, " + COLUMN_E_MAIL +  " TEXT NOT NULL UNIQUE, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_ADDRESS + " TEXT, " + COLUMN_CITY + " TEXT, " + COLUMN_COUNTRY + " TEXT, " + COLUMN_POSTAL_CODE + " INTEGER)";
        db.execSQL(createTableStatement);

        // Create Beach table
        String createBeachTableStatement = "CREATE TABLE " + BEACH_TABLE + "(" + COLUMN_BEACH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BEACH_NAME + " TEXT, " + COLUMN_BEACH_LOCATION + " TEXT, " + COLUMN_BEACH_INFO + " TEXT)";
        db.execSQL(createBeachTableStatement);

        // Create Sunbed table
        String createSunbedTableStatement = "CREATE TABLE " + SUNBED_TABLE + "(" + COLUMN_SUNBED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BEACH_ID + " INTEGER, " + COLUMN_SECTION + " TEXT, " + COLUMN_NUMBER + " INT, " + COLUMN_DAILY_PRICE_SEASON + " REAL, " + COLUMN_DAILY_PRICE_NOSEASON + " REAL, " + COLUMN_RESERVED + " INT, FOREIGN KEY(BEACH_ID) REFERENCES BEACH_TABLE(BEACH_ID))";
        db.execSQL(createSunbedTableStatement);

        // Create News Ttble
        String createNewsTableStatement = "CREATE TABLE " + NEWS_TABLE + "(NEWS_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NEWS_TITLE + " TEXT, " + COLUMN_NEWS_TEXT + " TEXT, " + COLUMN_NEWS_DATE + " TEXT, " + COLUMN_NEWS_IMAGE + " TEXT)";
        db.execSQL(createNewsTableStatement);

        // Create Order table
        String createOrderTableStatement = "CREATE TABLE " + ORDER_TABLE + " (ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_ID + " INTEGER, " + COLUMN_SUNBED_ID + " INTEGER, " + COLUMN_ORDER_DATE + " TEXT, " + COLUMN_RESERVATION_DATE_START + " TEXT, " + COLUMN_RESERVATION_DATE_END + " TEXT, " + COLUMN_TOTAL_COST + " REAL, FOREIGN KEY (USER_ID) REFERENCES USER_TABLE(USER_ID), FOREIGN KEY (SUNBED_ID) REFERENCES SUNBED_TABLE(SUNBED_ID))";
        db.execSQL(createOrderTableStatement);
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

    public boolean addSunbed(SunbedModel sunbedModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BEACH_ID, sunbedModel.getBeachID());
        cv.put(COLUMN_SECTION, sunbedModel.getSunbedSection());
        cv.put(COLUMN_NUMBER, sunbedModel.getSunbedNumber());
        cv.put(COLUMN_DAILY_PRICE_SEASON, sunbedModel.getDailyPriceSeason());
        cv.put(COLUMN_DAILY_PRICE_NOSEASON, sunbedModel.getGetDailyNonSeason());
        cv.put(COLUMN_RESERVED, sunbedModel.isReserved());

        long insert = db.insert(NEWS_TABLE, null, cv);
        if(insert == -1) return false;
        else return true;
    }

    public boolean addNews(NewsModel newsModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NEWS_TITLE, newsModel.getNewsTitle());
        cv.put(COLUMN_NEWS_TEXT, newsModel.getNewsText());
        cv.put(COLUMN_NEWS_DATE, newsModel.getDate());
        cv.put(COLUMN_NEWS_IMAGE, newsModel.getImage());

        long insert = db.insert(NEWS_TABLE, null, cv);
        if(insert == -1) return false;
        else return true;
    }

    public boolean addOrder(OrderModel orderModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_ID, orderModel.getUserID());
        cv.put(COLUMN_SUNBED_ID, orderModel.getSunbedID());
        cv.put(COLUMN_ORDER_DATE, orderModel.getOrderDate());
        cv.put(COLUMN_RESERVATION_DATE_START, orderModel.getReservationDateStart());
        cv.put(COLUMN_RESERVATION_DATE_END, orderModel.getReservationDateEnd());
        cv.put(COLUMN_TOTAL_COST, orderModel.getTotalCost());

        long insert = db.insert(ORDER_TABLE, null, cv);
        if(insert == -1) return false;
        else return true;
    }




}
