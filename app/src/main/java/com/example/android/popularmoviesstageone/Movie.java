package com.example.android.popularmoviesstageone;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lianavklt on 20/02/2018.
 */

public class Movie implements Parcelable {

  public static final Parcelable.Creator<Movie> CREATOR =
      new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
          return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
          return new Movie[size];
        }
      };
  String posterUrl;
  String title;
  String originalTitle;
  String plot;
  Double userRating;
  String releaseDate;
  String backdropPathUrl;

  public Movie(Parcel source) {
    posterUrl = source.readString();
    backdropPathUrl = source.readString();
    plot = source.readString();
    originalTitle = source.readString();
    releaseDate = source.readString();
    userRating = source.readDouble();
  }

  public Movie() {
  }

  public Double getUserRating() {
    return userRating;
  }

  public void setUserRating(Double userRating) {
    this.userRating = userRating;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getPlot() {
    return plot;
  }

  public void setPlot(String plot) {
    this.plot = plot;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getBackdropPathUrl() {
    return backdropPathUrl;
  }

  public void setBackdropPathUrl(String backdropPathUrl) {
    this.backdropPathUrl = backdropPathUrl;
  }

  public String getPosterUrl() {
    return posterUrl;
  }

  public void setPosterUrl(String posterUrl) {
    this.posterUrl = posterUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(posterUrl);
    dest.writeString(backdropPathUrl);
    dest.writeString(plot);
    dest.writeString(originalTitle);
    dest.writeString(releaseDate);
    dest.writeDouble(userRating);

  }
}
