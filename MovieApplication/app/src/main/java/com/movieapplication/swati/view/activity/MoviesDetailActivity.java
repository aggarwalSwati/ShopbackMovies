package com.movieapplication.swati.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.movieapplication.swati.model.MoviesModel;
import com.movieapplication.swati.view.fragments.MoviesDetailFragment;

import application.movie.swati.com.movieapplication.R;

/**
 * Created by aggarwal.swati on 4/4/17.
 */

public class MoviesDetailActivity extends AppCompatActivity {
	private static final String EXTRA_MOVIE_OBJ = "movie_obj";

	public static Intent getStartIntent(Context context, MoviesModel movieObj) {
		Intent intent = new Intent(context, MoviesDetailActivity.class);
		intent.putExtra(EXTRA_MOVIE_OBJ, movieObj);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MoviesModel movieObj = (getIntent().getParcelableExtra(EXTRA_MOVIE_OBJ));
		addDetailFragment(movieObj);
	}

	private void addDetailFragment(MoviesModel moviesModel) {
		Fragment detailFragment = MoviesDetailFragment.newInstance(moviesModel);
		getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, detailFragment)
				.commit();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		supportFinishAfterTransition();
	}
}
