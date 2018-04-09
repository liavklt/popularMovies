package com.example.android.popularmoviesstageone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.popularmoviesstageone.model.Movie;
import com.example.android.popularmoviesstageone.model.Review;
import com.example.android.popularmoviesstageone.utils.AsyncTaskListener;
import com.example.android.popularmoviesstageone.utils.FetchReviewsAsyncTask;
import com.example.android.popularmoviesstageone.utils.JsonUtils;
import com.example.android.popularmoviesstageone.utils.NetworkUtils;
import java.net.URL;
import java.util.List;

/**
 * Created by lianavklt on 09/04/2018.
 */

public class ReviewsFragment extends Fragment {

  private LinearLayoutManager linearLayoutManager;
  private ReviewRecyclerViewAdapter reviewRecyclerViewAdapter;
  private RecyclerView reviewRecyclerView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    return inflater.inflate(R.layout.reviews_fragment, container, false);
  }

  public void initializeRecyclerView(RecyclerView recyclerView, Movie movie) {
    reviewRecyclerView = recyclerView;
    reviewRecyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
        false);
    reviewRecyclerView.setLayoutManager(linearLayoutManager);
    reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(getActivity());
    reviewRecyclerView.setAdapter(reviewRecyclerViewAdapter);
    loadReviews(movie);

  }

  private void loadReviews(Movie movie) {
    Long id = movie.getId();
    String getReviewsUrl = "/movie/" + id.toString() + "/reviews";
    URL reviewsUrl = NetworkUtils.buildUrl(getReviewsUrl);
    new FetchReviewsAsyncTask(getContext(), new FetchTrailersTaskListener()).execute(reviewsUrl);

  }

  public class FetchTrailersTaskListener implements AsyncTaskListener<List<Review>> {

    @Override
    public void onTaskPreExecute() {
    }

    @Override
    public List<Review> onTaskGetResult(URL url) {
      String jsonString;
      List<Review> reviews;
      try {
        jsonString = NetworkUtils.getResponseFromHttpUrl(url);
        reviews = JsonUtils.getReviewsFromJson(jsonString);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
      return reviews;
    }

    @Override
    public void onTaskComplete(List<Review> reviews) {
      if (reviews != null) {
        reviewRecyclerView.setVisibility(View.VISIBLE);
        reviewRecyclerViewAdapter.setReviewData(reviews);
        reviewRecyclerViewAdapter.notifyDataSetChanged();
      }
    }
  }
}
