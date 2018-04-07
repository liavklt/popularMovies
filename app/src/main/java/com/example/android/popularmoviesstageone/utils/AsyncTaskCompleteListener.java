package com.example.android.popularmoviesstageone.utils;

/**
 * Created by lianavklt on 07/04/2018.
 */

public interface AsyncTaskCompleteListener<T> {

  void onTaskPreExecute();

  void onTaskComplete(T result);
}
