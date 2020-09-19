package com.example.daniel.riveradaniel_project1.drink_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileDatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_FILE = "profile.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            ProfileContract.DATA_TABLE + " (" +
            ProfileContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ProfileContract.USERNAME + " TEXT, " +
            ProfileContract.USERWEIGHT + " TEXT, " +
            ProfileContract.GENDER + " TEXT)";

    public ProfileDatabaseHelper(Context context) {
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
