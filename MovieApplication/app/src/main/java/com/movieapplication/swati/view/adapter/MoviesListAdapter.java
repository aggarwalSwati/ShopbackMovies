package com.movieapplication.swati.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.movieapplication.swati.model.MoviesListModel;
import com.movieapplication.swati.model.MoviesModel;
import com.movieapplication.swati.viewmodel.MoviesListViewModel;

import java.util.List;

import application.movie.swati.com.movieapplication.R;
import application.movie.swati.com.movieapplication.databinding.LayoutItemMovieBinding;

/**
 * Created by aggarwal.swati on 4/3/17.
 */

public class MoviesListAdapter
		extends
			RecyclerView.Adapter<MoviesListAdapter.AdapterViewHolder> {

	MoviesListModel moviesList;

	Context mContext;

	public MoviesListAdapter(Context context) {
		mContext = context;
	}

	@Override
	public AdapterViewHolder onCreateViewHolder(ViewGroup parent,
			int viewType) {
		LayoutItemMovieBinding databinding = DataBindingUtil.inflate(
				LayoutInflater.from(parent.getContext()),
				R.layout.layout_item_movie, parent, false);
		return new AdapterViewHolder(databinding);
	}

	@Override
	public void onBindViewHolder(AdapterViewHolder holder, int position) {
		LayoutItemMovieBinding postBinding = holder.itemPostbinding;
		postBinding.setViewModel(new MoviesListViewModel(mContext,
				moviesList.getMoviesModel().get(position)));
	}

	@Override
	public int getItemCount() {
		if (null != moviesList && moviesList.getMoviesModel().size() > 0) {
			return moviesList.getMoviesModel().size();
		}
		return 0;
	}

	public void setItems(MoviesListModel list) {
		moviesList = list;
		notifyDataSetChanged();
	}

	public void addItem(MoviesModel list) {
		if (null != moviesList.getMoviesModel()) {
			moviesList.getMoviesModel().add(list);
			notifyItemInserted(moviesList.getMoviesModel().size() - 1);
		}

	}

	public void clearList() {
		if (null != moviesList && null!=moviesList.getMoviesModel())
			moviesList.getMoviesModel().clear();
	}

	public List<MoviesModel> getAdapterData() {
		if (null != moviesList) {
			return moviesList.getMoviesModel();
		} else {
			return null;
		}
	}

	public static class AdapterViewHolder extends RecyclerView.ViewHolder {

		private LayoutItemMovieBinding itemPostbinding;

		public AdapterViewHolder(LayoutItemMovieBinding itemView) {
			super(itemView.cardView);
			this.itemPostbinding = itemView;

		}
	}
}
