package com.example.android.popularmoviesstageone.utils;

import com.example.android.popularmoviesstageone.Movie;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lianavklt on 20/02/2018.
 */

public class JsonUtils {

  private static String RESULTS = "results";
  private static String POSTER_PATH = "poster_path";
  private static String TITLE = "title";

  public static List<Movie> getSimpleWeatherStringsFromJson(String jsonStr)
      throws JSONException {
    JSONObject moviesJson = new JSONObject(jsonStr);
    JSONArray results = moviesJson.getJSONArray(RESULTS);
    List<Movie> movies = new ArrayList<>();
    for (int i = 0; i < results.length(); i++) {
      Movie movie = new Movie();

      JSONObject movieInfo = results.getJSONObject(i);
      String posterUrl = movieInfo.getString(POSTER_PATH);
      String title = movieInfo.getString(TITLE);
      movie.setPosterUrl(posterUrl);
      movie.setTitle(title);

      movies.add(movie);
    }
    return movies;
  }
}
