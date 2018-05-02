package com.example.android.popularmoviesstageone.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lianavklt on 11/04/2018.
 */

public class FavoritesContract {

  public static final String AUTHORITY = "com.example.android.popularmoviesstageone";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
  public static final String PATH_FAVORITES = "favorites";

  public static final class FavoritesEntry implements BaseColumns {

    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

    public static final String TABLE_NAME = "favorites";
    public static final String COLUMN_MOVIE_ID = "id";
    public static final String COLUMN_MOVIE_TITLE = "title";
    public static final String COLUMN_MOVIE_SYNOPSIS = "synopsis";
    public static final String COLUMN_MOVIE_POSTER = "poster";
    public static final String COLUMN_MOVIE_RATING = "rating";
    public static final String COLUMN_MOVIE_RELEASE_DATE = "releaseDate";
    public static final String COLUMN_MOVIE_BACKDROP_IMAGE = "backdropPathUrl";
  }
}
