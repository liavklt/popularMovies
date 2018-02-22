package com.example.android.popularmoviesstageone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.example.android.popularmoviesstageone.utils.JsonUtils;
import com.example.android.popularmoviesstageone.utils.NetworkUtils;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
    SharedPreferences.OnSharedPreferenceChangeListener {


  private RecyclerView rView;
  private RecyclerViewAdapter rcAdapter;
  private ProgressBar mLoadingIndicator;
  private static boolean CHANGED_PREFERENCES = false;
  private static final String POPULAR_MOVIES = "/movie/popular";
  private static final String TOP_RATED_MOVIES = "/movie/top_rated";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    rView = findViewById(R.id.rv_movies);
    rView.setHasFixedSize(true);

    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2,
        LinearLayoutManager.VERTICAL, false);
    rView.setLayoutManager(gridLayoutManager);

    rcAdapter = new RecyclerViewAdapter(this);
    rView.setAdapter(rcAdapter);
    mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

    PreferenceManager
        .getDefaultSharedPreferences(this);

    URL urlPopularMovies = NetworkUtils.buildUrl(POPULAR_MOVIES);

    new FetchMoviesAsyncTask().execute(urlPopularMovies);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    PreferenceManager.getDefaultSharedPreferences(this)
        .unregisterOnSharedPreferenceChangeListener(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      Intent settingsIntent = new Intent(this, SettingsActivity.class);
      startActivity(settingsIntent);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume() {
    super.onResume();
    SharedPreferences sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(this);
    String value = sharedPreferences.getString(getString(R.string.sort_order_key),"");
    if(TOP_RATED_MOVIES.equals(value)){
      URL urlTopRatedMovies = NetworkUtils.buildUrl(TOP_RATED_MOVIES);

      new FetchMoviesAsyncTask().execute(urlTopRatedMovies);
    }else if(POPULAR_MOVIES.equals(value)){
      URL urlPopularMovies = NetworkUtils.buildUrl(POPULAR_MOVIES);

      new FetchMoviesAsyncTask().execute(urlPopularMovies);
    }

  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    String value = sharedPreferences.getString(key, "");


  }

  public class FetchMoviesAsyncTask extends AsyncTask<URL, Void, List<Movie>> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Movie> doInBackground(URL... urls) {
      URL movieRequestUrl = urls[0];
      String jsonString;
      List<Movie> movies;
      try {
        jsonString = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
        movies = JsonUtils.getSimpleWeatherStringsFromJson(jsonString);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }

      return movies;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
      if (movies != null) {
        rView.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        rcAdapter.setMovieData(movies);
        rcAdapter.notifyDataSetChanged();

      }
    }
  }
}
