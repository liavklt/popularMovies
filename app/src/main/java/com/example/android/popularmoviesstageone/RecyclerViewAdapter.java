package com.example.android.popularmoviesstageone;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.android.popularmoviesstageone.model.Movie;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by lianavklt on 19/02/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

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
    String posterUrl =
        context.getString(R.string.image_base_url) + context.getString(R.string.poster_size)
            + movies.get(position).getPosterUrl();
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
      Intent detailsActivityIntent = new Intent(view.getContext(), DetailsActivity.class);
      int clickPosition = getAdapterPosition();

      Movie movie = movies.get(clickPosition);
      detailsActivityIntent.putExtra("movie", movie);
      view.getContext().startActivity(detailsActivityIntent);
    }
  }
}
