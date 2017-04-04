package com.movieapplication.swati.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.movieapplication.swati.model.MoviesModel;
import com.movieapplication.swati.view.activity.MoviesDetailActivity;

import application.movie.swati.com.movieapplication.R;

/**
 * Created by aggarwal.swati on 4/3/17.
 */

public class MoviesListViewModel extends BaseObservable {

	static Context context;
	MoviesModel mMovie;

	public MoviesListViewModel(Context context, MoviesModel mMovie) {
		this.context = context;
		this.mMovie = mMovie;

	}

	public String getMovieTitle() {
		return mMovie.title;
	}

	public SpannableStringBuilder getMoviePopularity() {
		SpannableStringBuilder longDescription = new SpannableStringBuilder();
		longDescription
				.append(context.getString(R.string.text_popularity) + "  ");

		String popularity = String.format("%.2f", mMovie.popularity);
		int start = longDescription.length();
		longDescription.append(popularity);
		longDescription.setSpan(new ForegroundColorSpan(0xFFCC5500), start,
				longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		longDescription.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
				start, longDescription.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		return longDescription;
	}

	public String getImageUrl() {
		// The URL will usually come from a model
		String url = "";
		if (null == mMovie.backdrop_path) {
			url = mMovie.poster_path == null ? "" : mMovie.poster_path;
		}
		return "https://image.tmdb.org/t/p/w780" + url;
	}

	@BindingAdapter({"bind:imageUrl"})
	public static void loadImage(ImageView view, String imageUrl) {
		Glide.with(context).load(imageUrl).crossFade()
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.movie_placeholder)
				.error(R.drawable.movie_placeholder).into(view);
	}

	public String getMoviePoster() {
		return mMovie.backdrop_path;
	}

	public View.OnClickListener onClickPost() {
		return new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				launchMovieDetailActivity();
			}
		};
	}

	private void launchMovieDetailActivity() {
		context.startActivity(
				MoviesDetailActivity.getStartIntent(context, mMovie));
	}

}
