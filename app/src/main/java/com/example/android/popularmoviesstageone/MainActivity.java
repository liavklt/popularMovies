package com.example.android.popularmoviesstageone;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {


  private RecyclerView rView;
  private RecyclerViewAdapter rcAdapter;
  private ProgressBar mLoadingIndicator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //TODO ADD SETTINGS - SHAREDPREFERENCES?
    rView = findViewById(R.id.rv_movies);
    rView.setHasFixedSize(true);

    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2,
        LinearLayoutManager.VERTICAL, false);
    rView.setLayoutManager(gridLayoutManager);

    rcAdapter = new RecyclerViewAdapter(this);
    rView.setAdapter(rcAdapter);
    mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

    URL urlPopularMovies = NetworkUtils.buildUrlPopularMovies();

    new FetchMoviesAsyncTask().execute(urlPopularMovies);
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
      return true;
    }
    return super.onOptionsItemSelected(item);
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

      }
    }
  }
}
