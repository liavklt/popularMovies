package com.example.android.popularmoviesstageone;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by lianavklt on 19/02/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

  private static final String LOG_TAG = RecyclerViewAdapter.class.getSimpleName();
  private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
  private static final String POSTER_SIZE = "w185";
  private Context context;
  private List<Movie> movies;

  public RecyclerViewAdapter(Context context) {
    this.context = context;
  }

  @Override
  public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    int layoutIdForItem = R.layout.recyclerview_item;
    LayoutInflater inflater = LayoutInflater.from(context);
    View thisItemsView = inflater.inflate(layoutIdForItem, parent, false);

    return new ViewHolder(thisItemsView);
  }

  @Override
  public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

    Log.d(LOG_TAG, "#" + position);

    String posterUrl = POSTER_BASE_URL + POSTER_SIZE + movies.get(position).getPosterUrl();
    Picasso.with(context).load(posterUrl).into(holder.listMovieItemView);
  }

  @Override
  public int getItemCount() {
    if (movies == null) {
      return 0;
    }
    return movies.size();
  }

  public void setMovieData(List<Movie> movieData) {
    movies = movieData;
    notifyDataSetChanged();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView listMovieItemView;

    public ViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      listMovieItemView = itemView.findViewById(R.id.iv_movie_item);
    }

    @Override
    public void onClick(View view) {
      Toast.makeText(view.getContext(), "Clicked!", Toast.LENGTH_SHORT).show();
    }

  }
}
