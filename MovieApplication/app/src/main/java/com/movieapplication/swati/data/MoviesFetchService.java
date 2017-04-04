package com.movieapplication.swati.data;

import com.movieapplication.swati.model.MovieDetail;
import com.movieapplication.swati.model.MoviesListModel;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by aggarwal.swati on 4/3/17.
 */

public interface MoviesFetchService {

		String ENDPOINT = "http://api.themoviedb.org/3/";

	/*
	Returns list of moviees
	 */
		@GET("/discover/movie?api_key=328c283cd27bd1877d9080ccb1604c91&primary_release_date.lte=2016-12-31&sort_by=release_date.desc")
		Observable<MoviesListModel> getListOfMovies(@Query("page") int page);

	/*
	Returns detail of movie item clicked
	 */
	@GET("/movie/{movieId}?api_key=328c283cd27bd1877d9080ccb1604c91")
	Observable<MovieDetail> getDetailOfMovie(@Path("movieId") int movieId);

}
