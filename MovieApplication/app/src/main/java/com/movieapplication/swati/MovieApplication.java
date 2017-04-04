package com.movieapplication.swati;

import android.app.Application;
import android.content.Context;

import com.movieapplication.swati.injection.component.ApplicationComponent;
import com.movieapplication.swati.injection.component.DaggerApplicationComponent;
import com.movieapplication.swati.injection.module.ApplicationModule;


/**
 * Created by aggarwal.swati on 4/3/17.
 */

public class MovieApplication extends Application {
	public ApplicationComponent getmApplicationComponent() {
		return mApplicationComponent;
	}

	public void setmApplicationComponent(ApplicationComponent mApplicationComponent) {
		this.mApplicationComponent = mApplicationComponent;
	}

	public static  MovieApplication get(Context context){
		return (MovieApplication)context.getApplicationContext();
	}

	ApplicationComponent mApplicationComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		mApplicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(new ApplicationModule(this))
				.build();
	}
}
