package com.movieapplication.swati.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aggarwal.swati on 4/4/17.
 */

public class GenresModel implements Parcelable{
	public String id;
	public String name;

	@Override
	public int describeContents() {
		return 0;
	}
	protected GenresModel(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.name);
	}
	public static final Creator<GenresModel> CREATOR = new Creator<GenresModel>() {
		@Override
		public GenresModel createFromParcel(Parcel in) {
			return new GenresModel(in);
		}

		@Override
		public GenresModel[] newArray(int size) {
			return new GenresModel[size];
		}
	};
}
