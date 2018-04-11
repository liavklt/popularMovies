package com.example.android.popularmoviesstageone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.popularmoviesstageone.data.FavoritesContract.FavoritesEntry;

/**
 * Created by lianavklt on 11/04/2018.
 */

public class FavoritesDbHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "favorites.db";
  private static final int DATABASE_VERSION = 1;

  public FavoritesDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoritesEntry.TABLE_NAME + " (" +
        FavoritesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        FavoritesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
        FavoritesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
        FavoritesEntry.COLUMN_MOVIE_SYNOPSIS + " TEXT, " +
        FavoritesEntry.COLUMN_MOVIE_POSTER + " TEXT, " +
        FavoritesEntry.COLUMN_MOVIE_RATING + " REAL, " +
        FavoritesEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT" +
        "); ";
    db.execSQL(SQL_CREATE_FAVORITES_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + FavoritesEntry.TABLE_NAME);
    onCreate(db);
  }
}
