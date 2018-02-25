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
  private static String BACKDROP_PATH = "backdrop_path";
  private static String ORIGINAL_TITLE = "original_title";
  private static String USER_RATING = "vote_average";
  private static String RELEASE_DATE = "release_date";
  private static String PLOT = "overview";

  public static List<Movie> getStringsFromJson(String jsonStr)
      throws JSONException {
    JSONObject moviesJson = new JSONObject(jsonStr);
    JSONArray results = moviesJson.getJSONArray(RESULTS);
    List<Movie> movies = new ArrayList<>();
    for (int i = 0; i < results.length(); i++) {
      Movie movie = new Movie();

      JSONObject movieInfo = results.getJSONObject(i);
      String posterUrl = movieInfo.getString(POSTER_PATH);
      String title = movieInfo.getString(TITLE);
      String backdropPath = movieInfo.getString(BACKDROP_PATH);
      String originalTitle = movieInfo.getString(ORIGINAL_TITLE);
      Double userRating = movieInfo.getDouble(USER_RATING);
      String releaseDate = movieInfo.getString(RELEASE_DATE);
      String plot = movieInfo.getString(PLOT);

      movie.setPosterUrl(posterUrl);
      movie.setTitle(title);
      movie.setBackdropPathUrl(backdropPath);
      movie.setOriginalTitle(originalTitle);
      movie.setUserRating(userRating);
      movie.setReleaseDate(releaseDate);
      movie.setPlot(plot);


      movies.add(movie);
    }
    return movies;
  }
}
