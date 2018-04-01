package com.example.android.popularmoviesstageone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.popularmoviesstageone.model.Video;
import java.util.List;

/**
 * Created by lianavklt on 18/03/2018.
 */

public class TrailerRecyclerViewAdapter extends
    RecyclerView.Adapter<TrailerRecyclerViewAdapter.ViewHolder> {

  private Context context;
  private List<Video> trailers;

  public TrailerRecyclerViewAdapter(Context context) {
    this.context = context;
  }

  public void setVideoData(List<Video> videoData) {
    trailers = videoData;
  }

  @Override
  public TrailerRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    int layoutIdForItem = R.layout.recyclerview_trailer_item;
    LayoutInflater inflater = LayoutInflater.from(context);
    View thisItemsView = inflater.inflate(layoutIdForItem, parent, false);

    return new ViewHolder(thisItemsView);
  }

  @Override
  public void onBindViewHolder(TrailerRecyclerViewAdapter.ViewHolder holder, int position) {
    String videoName = trailers.get(position).getName();

    holder.listTrailerTextView.setText(videoName);
  }


  @Override
  public int getItemCount() {
    if (trailers == null) {
      return 0;
    }
    return trailers.size();
  }


  class ViewHolder extends RecyclerView.ViewHolder {

    TextView listTrailerTextView;

    public ViewHolder(View itemView) {
      super(itemView);
      listTrailerTextView = itemView.findViewById(R.id.tv_trailer_item);
    }

  }
}

