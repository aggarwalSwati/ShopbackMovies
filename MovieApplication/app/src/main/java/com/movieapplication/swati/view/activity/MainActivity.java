package com.movieapplication.swati.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.movieapplication.swati.view.fragments.MovieListFragment;

import application.movie.swati.com.movieapplication.R;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addMoviesFragment();
	}

	private void addMoviesFragment() {
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.activity_main, new MovieListFragment())
				.commit();
	}
}
