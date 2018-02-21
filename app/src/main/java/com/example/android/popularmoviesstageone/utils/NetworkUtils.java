package com.example.android.popularmoviesstageone.utils;

import android.net.Uri;
import android.util.Log;
import com.example.android.popularmoviesstageone.BuildConfig;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by lianavklt on 20/02/2018.
 */

public class NetworkUtils {

  private static final String LOG_TAG = NetworkUtils.class.getSimpleName();


  private static final String POPULAR_MOVIES = "/movie/popular";
  private static final String TOP_RATED_MOVIES = "/movie/top_rated";
  private static final String BASE_URL = "https://api.themoviedb.org/3";
  private static final String language = "en-US";
  private static final String page = "1";

  final static String API_KEY_PARAM = "api_key";
  final static String LANGUAGE_PARAM = "language";
  final static String PAGE_PARAM = "page";


  public static URL buildUrlPopularMovies() {
    String apiKey = BuildConfig.API_KEY;

    Uri builtUri = Uri.parse(BASE_URL + POPULAR_MOVIES).buildUpon()
        .appendQueryParameter(API_KEY_PARAM, apiKey)
        .appendQueryParameter(LANGUAGE_PARAM, language)
        .appendQueryParameter(PAGE_PARAM, page)
        .build();

    URL url = null;
    try {
      url = new URL(builtUri.toString());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    Log.v(LOG_TAG, "Built URI " + url);

    return url;
  }

  public static String getResponseFromHttpUrl(URL url) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    try {
      InputStream in = urlConnection.getInputStream();

      Scanner scanner = new Scanner(in);
      scanner.useDelimiter("\\A");

      boolean hasInput = scanner.hasNext();
      if (hasInput) {
        return scanner.next();
      } else {
        return null;
      }
    } finally {
      urlConnection.disconnect();
    }
  }
}
