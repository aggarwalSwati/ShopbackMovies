package com.movieapplication.swati.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aggarwal.swati on 4/4/17.
 */

public class SpokenLanguage implements Parcelable

{

	public String iso_639_1;
	public String name;

	@Override
	public int describeContents() {
		return 0;
	}

	protected SpokenLanguage(Parcel in) {
		this.iso_639_1 = in.readString();
		this.name = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.iso_639_1);
		dest.writeString(this.name);
	}

	public static final Creator<SpokenLanguage> CREATOR = new Creator<SpokenLanguage>() {

		@Override
		public SpokenLanguage createFromParcel(Parcel in) {
			return new SpokenLanguage(in);
		}

		@Override
		public SpokenLanguage[] newArray(int size) {
			return new SpokenLanguage[size];
		}
	};
}
