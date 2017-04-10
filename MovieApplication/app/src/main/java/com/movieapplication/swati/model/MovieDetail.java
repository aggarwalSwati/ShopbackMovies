package com.movieapplication.swati.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by aggarwal.swati on 4/3/17.
 */

public class MovieDetail extends MoviesModel implements Parcelable {
	public String overview;
	public String runtime;
	public ArrayList<GenresModel> genres;
	public ArrayList<SpokenLanguage> spoken_languages;

	public MovieDetail(){

	}
	protected MovieDetail(Parcel in) {
		super(in);
		this.overview = in.readString();
		this.runtime = in.readString();
		this.spoken_languages = new ArrayList<SpokenLanguage>();
		in.readList(spoken_languages, (ClassLoader) SpokenLanguage.CREATOR);
		this.genres= new ArrayList<GenresModel>();
		in.readList(genres, (ClassLoader) GenresModel.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.overview);
		dest.writeString(this.runtime);
		dest.writeSerializable(this.spoken_languages);
		dest.writeList(genres);
		super.writeToParcel(dest,flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
		@Override
		public MovieDetail createFromParcel(Parcel in) {
			return new MovieDetail(in);
		}

		@Override
		public MovieDetail[] newArray(int size) {
			return new MovieDetail[size];
		}
	};
}
