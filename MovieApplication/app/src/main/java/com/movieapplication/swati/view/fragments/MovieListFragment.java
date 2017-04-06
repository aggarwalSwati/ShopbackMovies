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
import android.widget.Toast;

import com.movieapplication.swati.MovieApplication;
import com.movieapplication.swati.data.DataManager;
import com.movieapplication.swati.model.MoviesListModel;
import com.movieapplication.swati.model.MoviesModel;
import com.movieapplication.swati.utils.EndlessRecyclerViewScrollListener;
import com.movieapplication.swati.view.adapter.MoviesListAdapter;

import java.util.ArrayList;

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

	private MoviesListAdapter mMoviesAdapter;
	private DataManager mDataManager;
	private int currentPage = 1;

	private CompositeSubscription mSubscriptions;
	private EndlessRecyclerViewScrollListener mScrollListener;

	@Override
	public void onRefresh() {
		if (mMoviesAdapter != null) {
			mMoviesAdapter.clearList();
			mMoviesAdapter.notifyDataSetChanged();
		}
		mScrollListener.resetState();
		getTopMoviesList(currentPage, true);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSubscriptions = new CompositeSubscription();
		mDataManager = MovieApplication.get(getActivity())
				.getmApplicationComponent().dataManager();
		mMoviesAdapter = new MoviesListAdapter(getActivity());

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mSubscriptions.unsubscribe();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.layout_fragment_movies,
				container, false);
		ButterKnife.bind(this, fragmentView);
		mSwipeRefreshLayout.setOnRefreshListener(this);

		mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
		setupToolbar();
		setupRecyclerView();
		return fragmentView;
	}

	private void setupToolbar() {
		((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
		ActionBar actionBar = ((AppCompatActivity) getActivity())
				.getSupportActionBar();
		mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

		if (actionBar != null) {
			actionBar.setDisplayShowTitleEnabled(true);
		}
	}

	private void setupRecyclerView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(
				getActivity());
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
		mScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {

			@Override
			public void onLoadMore(int page, int totalItemsCount,
					RecyclerView view) {
				mSwipeRefreshLayout.setRefreshing(true);
				getTopMoviesList(page, false);
			}
		};
		recyclerView.addOnScrollListener(mScrollListener);
		recyclerView.setAdapter(mMoviesAdapter);
		getTopMoviesList(currentPage, false);
	}

	private void getTopMoviesList(int page, boolean isrefresh) {
		mSubscriptions.add(mDataManager.getMoviesFromServer(page)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(mDataManager.getScheduler())
				.subscribe(new Subscriber<MoviesListModel>() {

					@Override
					public void onCompleted() {
					}

					@Override
					public void onError(Throwable e) {
						mSwipeRefreshLayout.setRefreshing(false);
						e.printStackTrace();
						Toast.makeText(getActivity(), "Error while loading",
								Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onNext(MoviesListModel movieslist) {
						mSwipeRefreshLayout.setRefreshing(false);
						ArrayList<MoviesModel> newFilterdArrayList = new ArrayList<MoviesModel>();
						for (int i = 0; i < movieslist.getMoviesModel()
								.size(); i++) {
							MoviesModel model = movieslist.getMoviesModel()
									.get(i);
							if (null != model.backdrop_path
									|| model.poster_path != null) {
								newFilterdArrayList.add(model);
							}
						}
						if (mMoviesAdapter.getAdapterData() == null) {
							movieslist.setMoviesModel(newFilterdArrayList);
							mMoviesAdapter.setItems(movieslist);
						} else {
							int initialPosition = mMoviesAdapter.getItemCount();
							mMoviesAdapter.getAdapterData()
									.addAll(newFilterdArrayList);
							// movieslist.setMoviesModel(mMoviesAdapter.getAdapterData());
							mMoviesAdapter.notifyItemRangeInserted(
									initialPosition,
									newFilterdArrayList.size());
						}

					}

				}));
	}

}
