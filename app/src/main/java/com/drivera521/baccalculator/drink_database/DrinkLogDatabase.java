package com.example.daniel.riveradaniel_project1.drink_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DrinkLogDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_FILE = "drinklog.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            DrinkLogContract.DATA_TABLE + " (" +
            DrinkLogContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DrinkLogContract.USERID+ " TEXT, " +
            DrinkLogContract.DRINKID + " TEXT, " +
            DrinkLogContract.DRINKTIME + " TEXT)";

    public DrinkLogDatabase(Context context) {
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
