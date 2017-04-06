package com.movieapplication.swati.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.movieapplication.swati.MovieApplication;
import com.movieapplication.swati.data.DataManager;
import com.movieapplication.swati.model.MovieDetail;
import com.movieapplication.swati.model.MoviesModel;
import com.movieapplication.swati.view.activity.WebViewBookNowActivity;

import javax.inject.Inject;

import application.movie.swati.com.movieapplication.R;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by aggarwal.swati on 4/4/17.
 */

public class MovieDetailViewModel extends BaseObservable implements BaseViewModel {

	static Context context;
	private final int id;

	MoviesModel moviesObj;

	public void setMovieDetail(MovieDetail movieDetail) {
		this.movieDetail.set(movieDetail);
	}

	public MovieDetail getMovieDetail() {
		return this.movieDetail.get();
	}

	private ObservableField<MovieDetail> movieDetail = new ObservableField<>();
	CompositeSubscription mSubscriptions;

	public String getMovieTitle() {
		return moviesObj.title;
	}

	public String getMovieOverview() {
		return null == movieDetail.get() ? "" : movieDetail.get().overview;
	}

	public String getMovieDuration() {
		return null == movieDetail.get() ? "" : movieDetail.get().runtime + " mins";
	}

	public SpannableStringBuilder getMovieGenres() {
		SpannableStringBuilder longDescription = new SpannableStringBuilder();
		if (null != movieDetail.get()) {
			if (null != movieDetail.get().genres && movieDetail.get().genres.size() > 0) {
				for (int i = 0; i < movieDetail.get().genres.size(); i++) {
					if (i > 0) {
						longDescription.append(" | ");

					}
					longDescription.append(movieDetail.get().genres.get(i).name);


				}
			}
		}
		return longDescription;
	}

	public SpannableStringBuilder getMovieSpokenLanguages() {
		SpannableStringBuilder longDescription = new SpannableStringBuilder();
		if (null != movieDetail.get()) {
			if (null != movieDetail.get().spoken_languages &&
					movieDetail.get().spoken_languages.size() > 0) {
				for (int i = 0; i < movieDetail.get().spoken_languages.size(); i++) {
					if (i > 0 &&
							!TextUtils.isEmpty(longDescription)) {
						longDescription.append(" | ");

					}
					if (!TextUtils.isEmpty(movieDetail.get().spoken_languages.get(i).name)) {
						longDescription.append(movieDetail.get().spoken_languages.get(i).name);
					}


				}
			}
		}
		return longDescription;
	}

	public int getRuntimeVisibility() {
		if (null != movieDetail.get() && !TextUtils.isEmpty(movieDetail.get().runtime)) {
			return movieDetail.get().runtime.equalsIgnoreCase("0") ? View.GONE : View.VISIBLE;
		} else {
			return View.GONE;
		}
	}

	public int getOverviewVisibility() {
		if (null != movieDetail.get() && !TextUtils.isEmpty(movieDetail.get().overview)) {
			return View.VISIBLE;
		} else {
			return View.GONE;
		}
	}

	public int getLanguagesVisibility() {
		if (null != movieDetail.get() && null != movieDetail.get().spoken_languages &&
				movieDetail.get().spoken_languages.size() != 0) {
			return View.VISIBLE;
		} else {
			return View.GONE;
		}
	}

	public int getGenresVisibility() {
		if (null != movieDetail.get() && null != movieDetail.get().genres &&
				movieDetail.get().genres.size() != 0) {
			return View.VISIBLE;
		} else {
			return View.GONE;
		}
	}

	public SpannableStringBuilder getMoviePopularity() {
		SpannableStringBuilder longDescription = new SpannableStringBuilder();
		longDescription.append(context.getString(R.string.text_popularity) + "  ");

		String popularity = String.format("%.2f", moviesObj.popularity);
		int start = longDescription.length();
		longDescription.append(popularity);
		longDescription
				.setSpan(new ForegroundColorSpan(0xFFCC5500), start, longDescription.length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		longDescription.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start,
				longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		return longDescription;
	}

	public String getImageUrl() {
		// The URL will usually come from a model
		String url = "";
		if (null == moviesObj.backdrop_path) {
			url = moviesObj.poster_path == null ? "" : moviesObj.poster_path;
		}
		return "https://image.tmdb.org/t/p/w780" + url;
	}

	@BindingAdapter({"bind:imageUrl"})
	public static void loadImage(ImageView view, String imageUrl) {
		Glide.with(context).load(imageUrl).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.movie_placeholder).error(R.drawable.movie_placeholder)
				.into(view);
	}

	public String getMoviePoster() {
		return moviesObj.backdrop_path;
	}

	@Inject
	public MovieDetailViewModel(MoviesModel moviesModel, Context context) {
		this.id = moviesModel.id;
		this.context = context;
		moviesObj = moviesModel;
		getSelectedMoviesDetail();
	}

	public View.OnClickListener onBookNow() {
		return new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				launchBookNowUrl();
			}
		};
	}

	private void launchBookNowUrl() {
		Intent intent = new Intent(context, WebViewBookNowActivity.class);
		context.startActivity(intent);
	}

	private void getSelectedMoviesDetail() {
		mSubscriptions = new CompositeSubscription();
		DataManager mDataManager =
				MovieApplication.get(context).getmApplicationComponent().dataManager();
		mSubscriptions.add(mDataManager.getSelectedMovieDetail(this.id)
				.observeOn(AndroidSchedulers.mainThread()).subscribeOn(mDataManager.getScheduler())
				.subscribe(new Subscriber<MovieDetail>() {

					@Override
					public void onCompleted() {
					}

					@Override
					public void onError(Throwable e) {
						e.printStackTrace();
						Toast.makeText(context, "Error while loading", Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onNext(MovieDetail movieDetail) {
						setMovieDetail(movieDetail);
						notifyChange();
						//						viewDetailModel.setMovieDetail(movieDetail);
					}

				}));
	}

	@Override
	public void destroy() {
		this.context = null;
		if (mSubscriptions != null && !mSubscriptions.isUnsubscribed()) {
			mSubscriptions.unsubscribe();
		}
	}
}
