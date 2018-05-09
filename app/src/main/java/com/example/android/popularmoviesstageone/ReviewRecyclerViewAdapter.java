package com.example.android.popularmoviesstageone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.android.popularmoviesstageone.model.Review;
import java.util.List;

/**
 * Created by lianavklt on 09/04/2018.
 */

class ReviewRecyclerViewAdapter extends
    RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder> {

  private Context context;
  private List<Review> reviews;

  public ReviewRecyclerViewAdapter(Context context) {
    this.context = context;
  }


  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    int layoutIdForItem = R.layout.recyclerview_review_item;
    LayoutInflater inflater = LayoutInflater.from(context);
    View thisItemsView = inflater.inflate(layoutIdForItem, parent, false);

    return new ViewHolder(thisItemsView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    String reviewContent = reviews.get(position).getContent();
    String url = reviews.get(position).getUrl();

    holder.contentTextView.setText(reviewContent);
    holder.expandButton.setTag(R.id.reviewURI, url);

  }

  @Override
  public int getItemCount() {
    if (reviews == null) {
      return 0;
    }
    return reviews.size();
  }

  public void setReviewData(List<Review> reviewData) {
    this.reviews = reviewData;
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView contentTextView;
    Button expandButton;

    public ViewHolder(View itemView) {
      super(itemView);
      contentTextView = itemView.findViewById(R.id.tv_review_content);
      expandButton = itemView.findViewById(R.id.button_expand);
      expandButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
      Intent webIntent = new Intent(Intent.ACTION_VIEW,
          Uri.parse(expandButton.getTag(R.id.reviewURI).toString()));
      if (webIntent.resolveActivity(context.getPackageManager()) != null) {
        context.startActivity(webIntent);
      }
    }
  }
}
