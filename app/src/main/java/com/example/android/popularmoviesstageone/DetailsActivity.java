package com.example.android.popularmoviesstageone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

  private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
  private static final String POSTER_SIZE = "w185";
  private static final String BACKDROP_SIZE = "w500";
  ImageView backdropImageView;
  ImageView posterImageView;
  TextView originalTitleTextView;
  TextView userRatingTextView;
  TextView releaseDateTextView;
  TextView plotTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    Movie movie = intent.getParcelableExtra("movie");
    backdropImageView = findViewById(R.id.backdrop_path_poster);
    posterImageView = findViewById(R.id.poster);
    originalTitleTextView = findViewById(R.id.original_title);
    userRatingTextView = findViewById(R.id.user_rating);
    releaseDateTextView = findViewById(R.id.release_date);
    plotTextView = findViewById(R.id.plot);

    String posterUrl = POSTER_BASE_URL + POSTER_SIZE + movie.getPosterUrl();
    Picasso.with(this).load(posterUrl).into(posterImageView);

    String backdropPosterUrl = POSTER_BASE_URL + BACKDROP_SIZE + movie.getBackdropPathUrl();
    Picasso.with(this).load(backdropPosterUrl).into(backdropImageView);

    originalTitleTextView.setText(movie.getOriginalTitle());
    userRatingTextView.setText(movie.getUserRating().toString());
    releaseDateTextView.setText(movie.getReleaseDate());
    plotTextView.setText(movie.getPlot());
  }
}
