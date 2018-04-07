package com.example.android.popularmoviesstageone.utils;

import android.content.Context;
import android.os.AsyncTask;
import com.example.android.popularmoviesstageone.model.Movie;
import java.net.URL;
import java.util.List;

/**
 * Created by lianavklt on 07/04/2018.
 */

public class FetchMoviesAsyncTask extends AsyncTask<URL, Void, List<Movie>> {

  private static final String TAG = "FetchMoviesAsyncTask";

  private Context context;
  private AsyncTaskCompleteListener<List<Movie>> asyncTaskCompleteListener;


  public FetchMoviesAsyncTask(Context context,
      AsyncTaskCompleteListener<List<Movie>> asyncTaskCompleteListener) {
    this.context = context;
    this.asyncTaskCompleteListener = asyncTaskCompleteListener;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    asyncTaskCompleteListener.onTaskPreExecute();
  }

  @Override
  protected List<Movie> doInBackground(URL... urls) {
    URL movieRequestUrl = urls[0];
    String jsonString;
    List<Movie> movies;
    try {
      jsonString = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
      movies = JsonUtils.getStringsFromJson(jsonString);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return movies;
  }

  @Override
  protected void onPostExecute(List<Movie> movies) {
    super.onPostExecute(movies);
    asyncTaskCompleteListener.onTaskComplete(movies);

  }
}
