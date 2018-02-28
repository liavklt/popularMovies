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
import com.example.android.popularmoviesstageone.model.Movie;
import com.example.android.popularmoviesstageone.utils.JsonUtils;
import com.example.android.popularmoviesstageone.utils.NetworkUtils;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
    SharedPreferences.OnSharedPreferenceChangeListener {

  private static final String POPULAR = "/movie/popular";
  private static final String TOP_RATED = "/movie/top_rated";
  private static final int ACTIVITY_CONSTANT = 0;
  private static int index = -1;
  private static int top = -1;
  private static boolean settingsChanged;
  private RecyclerView recyclerView;
  private RecyclerViewAdapter adapter;
  private ProgressBar mLoadingIndicator;
  private GridLayoutManager gridLayoutManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    recyclerView = findViewById(R.id.rv_movies);
    recyclerView.setHasFixedSize(true);

    gridLayoutManager = new GridLayoutManager(this, 2,
        LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(gridLayoutManager);

    adapter = new RecyclerViewAdapter(this);
    recyclerView.setAdapter(adapter);
    mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

    PreferenceManager
        .getDefaultSharedPreferences(this);

    loadMoviePosters();
  }

  private void loadMoviePosters() {
    SharedPreferences sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(this);
    String value = sharedPreferences.getString(getString(R.string.sort_order_key), "");
    URL moviePosterUrl = null;
    if (TOP_RATED.equals(value)) {
      moviePosterUrl = NetworkUtils.buildUrl(TOP_RATED);
    } else if (POPULAR.equals(value)) {
      moviePosterUrl = NetworkUtils.buildUrl(POPULAR);
    }

    new FetchMoviesAsyncTask().execute(moviePosterUrl);
  }

  @Override
  protected void onPause() {
    super.onPause();
    index = gridLayoutManager.findFirstVisibleItemPosition();
    View v = recyclerView.getChildAt(0);
    top = (v == null) ? 0 : (v.getTop() - recyclerView.getPaddingTop());
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
      settingsIntent.putExtra(getString(R.string.changed_settings), false);
      startActivityForResult(settingsIntent, ACTIVITY_CONSTANT);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (settingsChanged) {
      gridLayoutManager.scrollToPosition(0);
    } else if (index != -1) {
      gridLayoutManager.scrollToPositionWithOffset(index, top);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK && requestCode == ACTIVITY_CONSTANT) {
      if (data.hasExtra(getString(R.string.changed_settings))) {
        settingsChanged = data.getBooleanExtra(getString(R.string.changed_settings), false);
        //TODO Investigate: instead of a variable allover class, consider method that does the trick?
        if (settingsChanged) {
          loadMoviePosters();
        }
      }
    }

    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
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
        movies = JsonUtils.getStringsFromJson(jsonString);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
      return movies;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
      if (movies != null) {
        recyclerView.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        adapter.setMovieData(movies);
        adapter.notifyDataSetChanged();
        settingsChanged = false;


      }
    }
  }
}
