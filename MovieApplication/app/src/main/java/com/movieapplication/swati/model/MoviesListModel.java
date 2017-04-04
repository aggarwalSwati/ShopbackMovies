package com.movieapplication.swati.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aggarwal.swati on 4/4/17.
 */

public class MoviesListModel {

	public List<MoviesModel> getMoviesModel() {
		return moviesModel;
	}

	public void setMoviesModel(List<MoviesModel> moviesModel) {
		this.moviesModel = moviesModel;
	}

	@SerializedName("results")
	List<MoviesModel> moviesModel;
}
