package com.drivera521.baccalculator.drink_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DrinkDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_FILE = "drink.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            DrinkContract.DATA_TABLE + " (" +
            DrinkContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DrinkContract.DRINKNAME+ " TEXT, " +
            DrinkContract.STANDARDDRINK + " TEXT, " +
            DrinkContract.DRINKVOLUME + " TEXT, " +
            DrinkContract.DRINKPERCENTAGE + " TEXT, " +
            DrinkContract.DRINKIMAGE + " TEXT)";

    public DrinkDatabaseHelper(Context context) {
        super(context,DATABASE_FILE,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
