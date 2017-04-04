package com.movieapplication.swati.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aggarwal.swati on 4/3/17.
 */

public class MoviesModel implements Parcelable {
	public String title;
	public String backdrop_path;
	public double popularity;
	public int id;
	public String poster_path;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.backdrop_path);
		dest.writeValue(this.popularity);
		dest.writeString(this.title);
		dest.writeString(this.poster_path);
		dest.writeInt(this.id);
	}
	public static final Parcelable.Creator<MoviesModel> CREATOR = new Parcelable.Creator<MoviesModel>() {
		public MoviesModel createFromParcel(Parcel source) {
			return new MoviesModel(source);
		}

		public MoviesModel[] newArray(int size) {
			return new MoviesModel[size];
		}
	};
	protected MoviesModel(Parcel in) {

		this.backdrop_path = in.readString();
		this.popularity = (double) in.readValue(Double.class.getClassLoader());
		this.title = in.readString();
		this.poster_path=in.readString();
		this.id=in.readInt();

	}

}
