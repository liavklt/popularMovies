package com.example.android.popularmoviesstageone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.android.popularmoviesstageone.data.FavoritesContract;
import com.example.android.popularmoviesstageone.data.FavoritesDbHelper;
import com.example.android.popularmoviesstageone.model.Movie;
import com.example.android.popularmoviesstageone.utils.AsyncTaskListener;
import com.example.android.popularmoviesstageone.utils.FetchMoviesAsyncTask;
import com.example.android.popularmoviesstageone.utils.JsonUtils;
import com.example.android.popularmoviesstageone.utils.NetworkUtils;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
    SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<Cursor> {

  private final static String TAG = MainActivity.class.getSimpleName();
  private static final int TASK_LOADER_ID = 0;

  private static final String POPULAR = "/movie/popular";
  private static final String TOP_RATED = "/movie/top_rated";
  private static final String FAVORITES = "favorites";
  private static int index = -1;
  private static int top = -1;
  private static boolean SETTINGS_CHANGED = false;
  private RecyclerView recyclerView;
  private RecyclerViewAdapter adapter;
  private CustomCursorAdapter cursorAdapter;
  private ProgressBar mLoadingIndicator;
  private SQLiteDatabase mDb;

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
    mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

    PreferenceManager.getDefaultSharedPreferences(this)
        .registerOnSharedPreferenceChangeListener(this);
    FavoritesDbHelper favoritesDbHelper = new FavoritesDbHelper(this);
    mDb = favoritesDbHelper.getWritableDatabase();

    if (NetworkUtils.isConnected(this)) {
      loadMoviePosters();
    } else {
      Toast.makeText(this, "No connection. Try again later.", Toast.LENGTH_SHORT).show();
    }
  }


  private void loadMoviePosters() {
    SharedPreferences sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(this);
    String value = sharedPreferences.getString(getString(R.string.sort_order_key), "");
    URL moviePosterUrl = null;
    if (TOP_RATED.equals(value)) {
      moviePosterUrl = NetworkUtils.buildUrl(TOP_RATED);
      adapter = new RecyclerViewAdapter(this);
      recyclerView.setAdapter(adapter);
      new FetchMoviesAsyncTask(this, new FetchMoviesTaskListener()).execute(moviePosterUrl);

    } else if (POPULAR.equals(value)) {
      moviePosterUrl = NetworkUtils.buildUrl(POPULAR);
      adapter = new RecyclerViewAdapter(this);
      recyclerView.setAdapter(adapter);
      new FetchMoviesAsyncTask(this, new FetchMoviesTaskListener()).execute(moviePosterUrl);

    } else if (FAVORITES.equals(value)) {
      cursorAdapter = new CustomCursorAdapter(this);
      recyclerView.setAdapter(cursorAdapter);
      getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);


    }
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
      startActivity(settingsIntent);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (SETTINGS_CHANGED) {
      loadMoviePosters();
      gridLayoutManager.scrollToPosition(0);
      SETTINGS_CHANGED = false;
    } else if (index != -1) {
      gridLayoutManager.scrollToPositionWithOffset(index, top);
    }
    SharedPreferences sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(this);
    String value = sharedPreferences.getString(getString(R.string.sort_order_key), "");

    if (FAVORITES.equals(value)) {
      getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    SETTINGS_CHANGED = true;
  }


  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new AsyncTaskLoader<Cursor>(this) {

      // Initialize a Cursor, this will hold all the task data
      Cursor mTaskData = null;

      // onStartLoading() is called when a loader first starts loading data
      @Override
      protected void onStartLoading() {
        if (mTaskData != null) {
          // Delivers any previously loaded data immediately
          deliverResult(mTaskData);
        } else {
          // Force a new load
          forceLoad();
        }
      }

      // loadInBackground() performs asynchronous loading of data
      @Override
      public Cursor loadInBackground() {
        // Will implement to load data

        try {
          return getContentResolver().query(FavoritesContract.FavoritesEntry.CONTENT_URI,
              null,
              null,
              null,
              FavoritesContract.FavoritesEntry.COLUMN_MOVIE_TITLE);

        } catch (Exception e) {
          System.out.println("Failed to asynchronously load data.");
          System.out.println(e.getMessage());
          return null;
        }
      }

      // deliverResult sends the result of the load, a Cursor, to the registered listener
      public void deliverResult(Cursor data) {
        mTaskData = data;
        super.deliverResult(data);
      }
    };
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    cursorAdapter.swapCursor(data);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {

    cursorAdapter.swapCursor(null);
  }

  public class FetchMoviesTaskListener implements AsyncTaskListener<List<Movie>> {

    @Override
    public void onTaskPreExecute() {
      mLoadingIndicator.setVisibility(View.VISIBLE);

    }

    @Override
    public List<Movie> onTaskGetResult(URL url) {
      String jsonString;
      List<Movie> movies;
      try {
        jsonString = NetworkUtils.getResponseFromHttpUrl(url);
        movies = JsonUtils.getStringsFromJson(jsonString);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
      return movies;
    }

    @Override
    public void onTaskComplete(List<Movie> movies) {
      if (movies != null) {
        recyclerView.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        adapter.setMovieData(movies);
        adapter.notifyDataSetChanged();
      }
    }
  }

}
