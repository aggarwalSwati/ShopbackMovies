package com.movieapplication.swati.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movieapplication.swati.model.MoviesModel;
import com.movieapplication.swati.viewmodel.MovieDetailViewModel;

import javax.inject.Inject;

import application.movie.swati.com.movieapplication.R;
import application.movie.swati.com.movieapplication.databinding.LayoutMovieDetailBinding;
import butterknife.Bind;

/**
 * Created by aggarwal.swati on 4/4/17.
 */

public class MoviesDetailFragment extends Fragment {
	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Inject
	MovieDetailViewModel viewDetailModel;

	private MoviesModel selectedMovieObj;
	LayoutMovieDetailBinding databinding;

	public static MoviesDetailFragment newInstance(MoviesModel moviesObj) {
		MoviesDetailFragment moviesDetail = new MoviesDetailFragment();
		Bundle args = new Bundle();
		args.putParcelable("moviesObj", moviesObj);
		moviesDetail.setArguments(args);
		return moviesDetail;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();

		if (bundle != null) {
			selectedMovieObj = bundle.getParcelable("moviesObj");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		viewDetailModel.destroy();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		databinding = LayoutMovieDetailBinding.inflate(inflater, container, false);
		viewDetailModel = new MovieDetailViewModel(selectedMovieObj,getActivity());
		databinding.setViewModel(viewDetailModel);
		setupToolbar();
		return databinding.getRoot();
	}

	private void setupToolbar() {
		((AppCompatActivity) getActivity()).setSupportActionBar(databinding.toolbar);
		ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
		actionBar.setTitle(selectedMovieObj.title);
		if (actionBar != null) {
			actionBar.setDisplayShowTitleEnabled(true);
		}
	}



}
