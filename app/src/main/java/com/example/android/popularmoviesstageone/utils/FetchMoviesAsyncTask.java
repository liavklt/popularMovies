package com.example.android.popularmoviesstageone.utils;

import android.content.Context;
import android.os.AsyncTask;
import com.example.android.popularmoviesstageone.model.Movie;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

/**
 * Created by lianavklt on 07/04/2018.
 */

public class FetchMoviesAsyncTask extends AsyncTask<URL, Void, List<Movie>> {

  private static final String TAG = "FetchMoviesAsyncTask";

  private WeakReference<Context> context;
  private AsyncTaskListener<List<Movie>> asyncTaskListener;


  public FetchMoviesAsyncTask(Context context,
      AsyncTaskListener<List<Movie>> asyncTaskListener) {
    this.context = new WeakReference<>(context);
    this.asyncTaskListener = asyncTaskListener;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    asyncTaskListener.onTaskPreExecute();
  }

  @Override
  protected List<Movie> doInBackground(URL... urls) {
    URL movieRequestUrl = urls[0];
    return asyncTaskListener.onTaskGetResult(movieRequestUrl);

  }

  @Override
  protected void onPostExecute(List<Movie> movies) {
    super.onPostExecute(movies);
    asyncTaskListener.onTaskComplete(movies);

  }
}
