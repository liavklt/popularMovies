package com.example.android.popularmoviesstageone;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.android.popularmoviesstageone.data.FavoritesContract.FavoritesEntry;
import com.squareup.picasso.Picasso;

/**
 * Created by lianavklt on 27/04/2018.
 */

class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.FavoritesViewHolder> {

  private Cursor mCursor;
  private Context mContext;

  public CustomCursorAdapter(Context mContext) {
    this.mContext = mContext;
  }

  @Override
  public CustomCursorAdapter.FavoritesViewHolder onCreateViewHolder(ViewGroup parent,
      int viewType) {
    int layoutIdForItem = R.layout.recyclerview_item;
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View thisItemsView = inflater.inflate(layoutIdForItem, parent, false);

    return new FavoritesViewHolder(thisItemsView);
  }

  @Override
  public void onBindViewHolder(CustomCursorAdapter.FavoritesViewHolder holder, int position) {
    int idIndex = mCursor.getColumnIndex(FavoritesEntry.COLUMN_MOVIE_ID);
    int posterIndex = mCursor.getColumnIndex(FavoritesEntry.COLUMN_MOVIE_POSTER);
    mCursor.moveToPosition(position);
    final int id = mCursor.getInt(idIndex);
    String posterUrl =
        mContext.getString(R.string.image_base_url) + mContext.getString(R.string.poster_size)
            + mCursor.getString(posterIndex);

    Picasso.with(mContext).load(posterUrl).into(holder.listMovieItemView);
  }

  @Override
  public int getItemCount() {
    if (mCursor == null) {
      return 0;
    }
    int count = mCursor.getCount();
    return mCursor.getCount();
  }

  public Cursor swapCursor(Cursor cursor) {
    if (mCursor == cursor) {
      return null;
    }
    Cursor temp = mCursor;
    this.mCursor = cursor;

    if (cursor != null) {
      this.notifyDataSetChanged();
    }
    return temp;
  }

  class FavoritesViewHolder extends ViewHolder {

    ImageView listMovieItemView;


    public FavoritesViewHolder(View itemView) {
      super(itemView);
      listMovieItemView = itemView.findViewById(R.id.iv_movie_item);

    }
  }
}
