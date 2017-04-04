package com.movieapplication.swati.data;

import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by aggarwal.swati on 4/3/17.
 */

public class RetrofitHelperClass {
	public MoviesFetchService newFetchService(){
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(MoviesFetchService.ENDPOINT)
				.setLogLevel(RestAdapter.LogLevel.FULL)

				.setConverter(new GsonConverter(new GsonBuilder().create()))
				.build();
		return restAdapter.create(MoviesFetchService.class);
	}
}
