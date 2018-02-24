package com.deaenita.popumovi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "film.db";

    private static final int DATABASE_VERSION = 8;

    private static final String TAG = "MovieDbHelper";
    //logt

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                        MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieContract.MovieEntry.COLUMN_ID + " INTEGER NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_JUDUL + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_RATING + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_DATE + " TEXT NOT NULL, " +


                        "UNIQUE (" + MovieContract.MovieEntry.COLUMN_JUDUL + ") ON CONFLICT REPLACE);";

        Log.d(TAG, "onCreate: "+SQL_CREATE_WEATHER_TABLE);

        try {
            sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
        } catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


}