package com.movieapplication.swati.injection.module;

/**
 * Created by aggarwal.swati on 4/3/17.
 */

import com.movieapplication.swati.data.MoviesFetchService;
import com.movieapplication.swati.data.RetrofitHelperClass;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Provide dependencies for retrofit and service class
 */

@Module
public class DataManagerModule {
	public DataManagerModule() {
	}

	@Provides
	MoviesFetchService provideMovieService(){
		return new RetrofitHelperClass().newFetchService();
	}

	@Provides
	Scheduler provideScheduleSuscriber(){
		return Schedulers.io();
	}
}
