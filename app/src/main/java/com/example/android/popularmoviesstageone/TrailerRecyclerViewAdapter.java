package com.example.android.popularmoviesstageone;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    String key = trailers.get(position).getKey();

    holder.listTrailerTextView.setText(videoName);
    holder.button.setTag(R.id.imageURI, key);
  }


  @Override
  public int getItemCount() {
    if (trailers == null) {
      return 0;
    }
    return trailers.size();
  }


  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView listTrailerTextView;
    Button button;

    public ViewHolder(View itemView) {
      super(itemView);
      listTrailerTextView = itemView.findViewById(R.id.tv_trailer_item);
      button = itemView.findViewById(R.id.btn_trailer);
      button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
      Intent appIntent = new Intent(Intent.ACTION_VIEW,
          Uri.parse("vnd.youtube:" + button.getTag(R.id.imageURI)));
      Intent webIntent = new Intent(Intent.ACTION_VIEW,
          Uri.parse("http://www.youtube.com/watch?v=" + button.getTag(R.id.imageURI)));
      try {
        context.startActivity(appIntent);
      } catch (ActivityNotFoundException ex) {
        context.startActivity(webIntent);
      }

    }
  }
}

