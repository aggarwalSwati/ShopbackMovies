package com.movieapplication.swati.injection.component;

import android.app.Application;

import com.movieapplication.swati.data.DataManager;
import com.movieapplication.swati.injection.module.ApplicationModule;
import com.movieapplication.swati.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by aggarwal.swati on 4/3/17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

	void inject(MainActivity mainActivity);

	Application application();

	DataManager dataManager();

}
