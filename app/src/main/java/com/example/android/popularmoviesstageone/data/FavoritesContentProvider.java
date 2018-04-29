package com.example.android.popularmoviesstageone.data;

import static com.example.android.popularmoviesstageone.data.FavoritesContract.FavoritesEntry.TABLE_NAME;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by lianavklt on 16/04/2018.
 */

public class FavoritesContentProvider extends ContentProvider {

  public static final int FAVORITES = 100;
  public static final int FAVORITE_WITH_ID = 101;
  private static final UriMatcher uriMatcher = buildUriMatcher();
  private FavoritesDbHelper favoritesDbHelper;

  public static UriMatcher buildUriMatcher() {

    // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    uriMatcher.addURI(FavoritesContract.AUTHORITY, FavoritesContract.PATH_FAVORITES, FAVORITES);
    uriMatcher.addURI(FavoritesContract.AUTHORITY, FavoritesContract.PATH_FAVORITES + "/#",
        FAVORITE_WITH_ID);

    return uriMatcher;
  }

  @Override
  public boolean onCreate() {
    Context context = getContext();
    favoritesDbHelper = new FavoritesDbHelper(context);
    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder) {

    final SQLiteDatabase db = favoritesDbHelper.getReadableDatabase();
    int match = uriMatcher.match(uri);
    Cursor retCursor;

    switch (match) {
      case FAVORITES:
        retCursor = db.query(TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder);
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    retCursor.setNotificationUri(getContext().getContentResolver(), uri);

    // Return the desired Cursor
    return retCursor;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    final SQLiteDatabase db = favoritesDbHelper.getWritableDatabase();
    int match = uriMatcher.match(uri);
    Uri returnUri; // URI to be returned

    switch (match) {
      case FAVORITES:
        long id = db.insert(TABLE_NAME, null, values);
        if (id > 0) {
          returnUri = ContentUris.withAppendedId(FavoritesContract.FavoritesEntry.CONTENT_URI, id);
        } else {
          throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);

    return returnUri;

  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
