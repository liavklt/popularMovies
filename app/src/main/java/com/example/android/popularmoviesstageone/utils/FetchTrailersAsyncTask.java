package com.example.android.popularmoviesstageone.utils;

import android.content.Context;
import android.os.AsyncTask;
import com.example.android.popularmoviesstageone.model.Video;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

/**
 * Created by lianavklt on 09/04/2018.
 */

public class FetchTrailersAsyncTask extends AsyncTask<URL, Void, List<Video>> {

  private static final String TAG = "FetchTrailersAsyncTask";

  private WeakReference<Context> context;
  private AsyncTaskListener<List<Video>> asyncTaskListener;


  public FetchTrailersAsyncTask(Context context,
      AsyncTaskListener<List<Video>> asyncTaskListener) {
    this.context = new WeakReference<>(context);
    this.asyncTaskListener = asyncTaskListener;
  }

  @Override
  protected List<Video> doInBackground(URL... urls) {
    URL trailerRequestUrl = urls[0];
    return asyncTaskListener.onTaskGetResult(trailerRequestUrl);
  }

  @Override
  protected void onPostExecute(List<Video> videos) {
    super.onPostExecute(videos);
    asyncTaskListener.onTaskComplete(videos);
  }
}

