package com.example.android.popularmoviesstageone.utils;

import android.content.Context;
import android.os.AsyncTask;
import com.example.android.popularmoviesstageone.model.Review;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

/**
 * Created by lianavklt on 09/04/2018.
 */

public class FetchReviewsAsyncTask extends AsyncTask<URL, Void, List<Review>> {

  private WeakReference<Context> context;
  private AsyncTaskListener<List<Review>> asyncTaskListener;


  public FetchReviewsAsyncTask(Context context,
      AsyncTaskListener<List<Review>> asyncTaskListener) {
    this.context = new WeakReference<>(context);
    this.asyncTaskListener = asyncTaskListener;
  }

  @Override
  protected List<Review> doInBackground(URL... urls) {
    URL reviewRequestUrl = urls[0];
    return asyncTaskListener.onTaskGetResult(reviewRequestUrl);
  }

  @Override
  protected void onPostExecute(List<Review> reviews) {
    super.onPostExecute(reviews);
    asyncTaskListener.onTaskComplete(reviews);
  }
}
