package com.movieapplication.swati.injection.module;

import android.app.Application;

import com.movieapplication.swati.data.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aggarwal.swati on 4/3/17.
 */

@Module
public class ApplicationModule {
	protected final Application application;
	public ApplicationModule(Application application) {
		this.application = application;
	}

	@Provides
	@Singleton
	Application provideApplication() {
		return application;
	}

	@Provides
	@Singleton
	DataManager provideDataManager() {
		return new DataManager(application);
	}
}
