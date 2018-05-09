package com.example.android.popularmoviesstageone.utils;

import java.net.URL;

/**
 * Created by lianavklt on 07/04/2018.
 */

public interface AsyncTaskListener<T> {

  void onTaskPreExecute();

  T onTaskGetResult(URL url);

  void onTaskComplete(T result);
}
