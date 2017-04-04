package com.movieapplication.swati.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.movieapplication.swati.MovieApplication;
import com.movieapplication.swati.data.DataManager;
import com.movieapplication.swati.model.MoviesListModel;
import com.movieapplication.swati.view.adapter.MoviesListAdapter;

import application.movie.swati.com.movieapplication.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by aggarwal.swati on 4/3/17.
 */

public class MovieListFragment extends Fragment implements OnRefreshListener {

	@Bind(R.id.swipe_container)
	SwipeRefreshLayout mSwipeRefreshLayout;
	@Bind(R.id.recycler_movies)
	RecyclerView recyclerView;
	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	@Bind(R.id.progress_indicator)
	ProgressBar mProgressBar;
	private MoviesListAdapter mMoviesAdapter;
	private DataManager mDataManager;
	private int currentPage = 1;

	private CompositeSubscription mSubscriptions;
	private boolean requestFlag;
	private boolean isRefreshedCalled;

	@Override
	public void onRefresh() {
		currentPage=3;
		isRefreshedCalled=true;
		getTopMoviesList(currentPage,true);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSubscriptions = new CompositeSubscription();
		mDataManager = MovieApplication.get(getActivity()).getmApplicationComponent().dataManager();
		mMoviesAdapter = new MoviesListAdapter(getActivity());

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mSubscriptions.unsubscribe();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.layout_fragment_movies, container, false);
		ButterKnife.bind(this, fragmentView);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
		setupToolbar();
		setupRecyclerView();
		return fragmentView;
	}

	private void setupToolbar() {
		((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
		ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowTitleEnabled(true);
		}
	}

	private void setupRecyclerView() {
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setHasFixedSize(true);
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				int position = recyclerView.getChildLayoutPosition(
						recyclerView.getChildAt(recyclerView.getChildCount() - 1));
				// check last (last_postion-3) position is visible for call nre request

				if (position == (mMoviesAdapter.getItemCount() - 1)) {
					if (requestFlag) {
						requestFlag = false;
						getTopMoviesList(++currentPage,false);
					}
				}
			}
		});
		recyclerView.setAdapter(mMoviesAdapter);
		getTopMoviesList(currentPage,false);
	}

	private void getTopMoviesList(int page, boolean isrefresh) {
		if (!isrefresh)
		mProgressBar.setVisibility(View.VISIBLE);
		mSubscriptions.add(mDataManager.getMoviesFromServer(page)
				.observeOn(AndroidSchedulers.mainThread()).subscribeOn(mDataManager.getScheduler())
				.subscribe(new Subscriber<MoviesListModel>() {

					@Override
					public void onCompleted() {
						requestFlag=true;
					}

					@Override
					public void onError(Throwable e) {
						hideLoadingViews();
						e.printStackTrace();
						Toast.makeText(getActivity(),"Error while loading", Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onNext(MoviesListModel movieslist) {
						hideLoadingViews();
						requestFlag = true;
						if (!isRefreshedCalled) {
							if (currentPage > 1) {
								if (null != movieslist && movieslist.getMoviesModel().size() > 0) {
									for (int i = 0; i < movieslist.getMoviesModel().size(); i++) {
										mMoviesAdapter.addItem(movieslist.getMoviesModel().get(i));
									}
								}

							} else {
								mMoviesAdapter.setItems(movieslist);
							}
						}
						else{
							isRefreshedCalled=false;
							if (null != movieslist && movieslist.getMoviesModel().size() > 0) {
								mMoviesAdapter.setItems(movieslist);
							}
						}
					}

				}));
	}

	private void hideLoadingViews() {
		mProgressBar.setVisibility(View.GONE);
		mSwipeRefreshLayout.setRefreshing(false);
	}


}
