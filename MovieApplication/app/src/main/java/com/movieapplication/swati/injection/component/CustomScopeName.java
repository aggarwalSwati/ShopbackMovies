package com.movieapplication.swati.injection.component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by aggarwal.swati on 4/4/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomScopeName {
}
