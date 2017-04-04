package com.movieapplication.swati.injection.component;


import com.movieapplication.swati.data.DataManager;
import com.movieapplication.swati.injection.module.DataManagerModule;

import dagger.Component;

/**
 * Created by aggarwal.swati on 4/3/17.
 */

@CustomScopeName
@Component(dependencies = ApplicationComponent.class, modules = DataManagerModule.class)
public interface DataMangerComponent {

	void inject(DataManager dataManager);

}
