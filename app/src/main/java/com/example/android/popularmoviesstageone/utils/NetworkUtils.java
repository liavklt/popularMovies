package com.example.android.popularmoviesstageone.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

  private static final String BASE_URL = "https://api.themoviedb.org/3";
  private static final String language = "en-US";

  private final static String API_KEY_PARAM = "api_key";
  private final static String LANGUAGE_PARAM = "language";

  private final static int CONNECT_TIMEOUT = 5000;
  private final static int READ_TIMEOUT = 10000;

  public static URL buildUrl(String param) {
    String apiKey = BuildConfig.API_KEY;

    Uri builtUri = Uri.parse(BASE_URL + param).buildUpon()
        .appendQueryParameter(API_KEY_PARAM, apiKey)
        .appendQueryParameter(LANGUAGE_PARAM, language)
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
      urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
      urlConnection.setReadTimeout(READ_TIMEOUT);
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

  public static boolean isConnected(Context context) {
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = null;
    if (cm != null) {
      activeNetwork = cm.getActiveNetworkInfo();
    }
    return activeNetwork != null &&
        activeNetwork.isConnectedOrConnecting();

  }
}
