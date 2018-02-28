package com.example.android.popularmoviesstageone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.popularmoviesstageone.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

  private ImageView backdropImageView;
  private ImageView posterImageView;
  private TextView originalTitleTextView;
  private TextView userRatingTextView;
  private TextView releaseDateTextView;
  private TextView plotTextView;
  private Movie movie;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    if (intent == null) {
      closeOnError();
    }
    movie = intent.getParcelableExtra("movie");
    backdropImageView = findViewById(R.id.backdrop_path_poster);
    posterImageView = findViewById(R.id.poster);
    originalTitleTextView = findViewById(R.id.original_title);
    userRatingTextView = findViewById(R.id.user_rating);
    releaseDateTextView = findViewById(R.id.release_date);
    plotTextView = findViewById(R.id.plot);

    populateUI();
  }

  private void closeOnError() {
    finish();
    Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
  }

  private void populateUI() {
    String posterSize = getString(R.string.poster_size);
    loadImage(posterSize, movie.getPosterUrl(), posterImageView);

    String backdropSize = getString(R.string.backdrop_size);
    loadImage(backdropSize, movie.getBackdropPathUrl(), backdropImageView);

    originalTitleTextView.setText(movie.getOriginalTitle());
    String userRatingString = movie.getUserRating().toString();
    userRatingTextView.setText(userRatingString);
    releaseDateTextView.setText(movie.getReleaseDate());
    plotTextView.setText(movie.getPlot());
  }

  private void loadImage(String posterSize, String posterPath, ImageView imageView) {
    String imageBaseUrl = getString(R.string.image_base_url);
    String imageUrl = imageBaseUrl + posterSize + posterPath;
    Picasso.with(this).load(imageUrl).into(imageView);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    if (itemId == android.R.id.home) {
      NavUtils.navigateUpFromSameTask(this);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }


}
