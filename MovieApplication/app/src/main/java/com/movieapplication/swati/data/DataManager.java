package com.movieapplication.swati.data;

import android.content.Context;

import com.movieapplication.swati.MovieApplication;
import com.movieapplication.swati.injection.component.DaggerDataMangerComponent;
import com.movieapplication.swati.injection.module.DataManagerModule;
import com.movieapplication.swati.model.MovieDetail;
import com.movieapplication.swati.model.MoviesListModel;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by aggarwal.swati on 4/3/17.
 */

public class DataManager {

	@Inject
	protected MoviesFetchService mFetchService;

	@Inject
	protected Scheduler mSubscribeScheduler;

	public DataManager(Context context) {
		injectDependencies(context);
	}

	private void injectDependencies(Context context) {
		DaggerDataMangerComponent.builder()
				.applicationComponent(MovieApplication.get(context)
						.getmApplicationComponent())
				.dataManagerModule(new DataManagerModule()).build()
				.inject(this);


	}

	public Scheduler getScheduler() {
		return mSubscribeScheduler;
	}

	public Observable<MoviesListModel> getMoviesFromServer(int page) {
		return mFetchService.getListOfMovies(page);
	}
	public Observable<MovieDetail> getSelectedMovieDetail(int movieId) {
		return mFetchService.getDetailOfMovie(movieId);
	}
}
