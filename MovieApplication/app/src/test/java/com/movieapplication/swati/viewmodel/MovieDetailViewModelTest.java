package com.movieapplication.swati.viewmodel;

import android.content.Context;
import android.view.View;

import com.movieapplication.swati.model.GenresModel;
import com.movieapplication.swati.model.MovieDetail;
import com.movieapplication.swati.model.MoviesModel;
import com.movieapplication.swati.model.SpokenLanguage;
import com.movieapplication.swati.util.DefaultConfig;
import com.movieapplication.swati.utils.MockModelsUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import application.movie.swati.com.movieapplication.BuildConfig;

import static org.junit.Assert.assertEquals;

/**
 * Created by aggarwal.swati on 4/6/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK, manifest = DefaultConfig.MANIFEST)
public class MovieDetailViewModelTest {
	private Context mContext;
	private MovieDetailViewModel viewModel;
	private MovieDetail mDetail;
	private MoviesModel movieModel;
	@Before
	public void setUp() {
		mContext = RuntimeEnvironment.application;
		movieModel= MockModelsUtil.createMockMovies();
		mDetail = MockModelsUtil.createMockDetail();
		viewModel = new MovieDetailViewModel(movieModel,mContext);
	}

	@Test
	public void shouldGetPostOverview() throws Exception {
		viewModel.setMovieDetail(mDetail);
		String overview = mDetail.overview;
		assertEquals(viewModel.getMovieOverview(), overview);
	}

	@Test
	public void shouldGetMovieTitle() throws Exception {
		assertEquals(viewModel.getMovieTitle(), movieModel.title);
	}

	@Test
	public void shouldGetMovieDuration() throws Exception {
		viewModel.setMovieDetail(mDetail);
		assertEquals(viewModel.getMovieDuration().toString(),mDetail.runtime +" mins");
	}

	@Test
	public void shouldGetGenresVisibility() throws Exception {
		mDetail.genres = null;
		assertEquals(viewModel.getGenresVisibility(), View.GONE);
		mDetail=MockModelsUtil.createMockDetail();
		viewModel.setMovieDetail(mDetail);
		mDetail.genres = new ArrayList<>();
		assertEquals(viewModel.getGenresVisibility(), View.GONE);
		mDetail.genres = new ArrayList<>();
		GenresModel model = new GenresModel("1","Hindi");
		mDetail.genres.add(model);
		assertEquals(viewModel.getGenresVisibility(), View.VISIBLE);
	}
	@Test
	public void shouldGetRuntimeVisibility() throws Exception {
		mDetail.runtime = "0";
		assertEquals(viewModel.getRuntimeVisibility(), View.GONE);
		mDetail=MockModelsUtil.createMockDetail();
		viewModel.setMovieDetail(mDetail);
		mDetail.runtime = "20";
		assertEquals(viewModel.getRuntimeVisibility(), View.VISIBLE);

	}
	@Test
	public void shouldGetOverViewVisibility() throws Exception {
		mDetail.overview = null;
		assertEquals(viewModel.getOverviewVisibility(), View.GONE);
		mDetail=MockModelsUtil.createMockDetail();
		viewModel.setMovieDetail(mDetail);
		mDetail.overview = MockModelsUtil.generateRandomString();
		assertEquals(viewModel.getOverviewVisibility(), View.VISIBLE);

	}
	@Test
	public void shouldGetLanguageText() throws Exception {
		mDetail.spoken_languages = null;
		assertEquals(viewModel.getMovieSpokenLanguages(), "");
		mDetail=MockModelsUtil.createMockDetail();
		viewModel.setMovieDetail(mDetail);
		mDetail.spoken_languages = new ArrayList<>();
		assertEquals(viewModel.getMovieSpokenLanguages(), "");
		SpokenLanguage  model = new SpokenLanguage("1","Hindi");
		mDetail.spoken_languages.add(model);
		assertEquals(viewModel.getMovieSpokenLanguages(), model.name);

	}
	@Test
	public void shouldGetPosterImageurl() throws Exception {
		mDetail.backdrop_path = null;
		mDetail.poster_path="";
		assertEquals(viewModel.getImageUrl(), "");
		mDetail=MockModelsUtil.createMockDetail();
		viewModel.setMovieDetail(mDetail);
		assertEquals(viewModel.getImageUrl(), "https://image.tmdb.org/t/p/w780"+mDetail.backdrop_path);
		mDetail=MockModelsUtil.createMockDetailWithoutBackdrop();
		viewModel.setMovieDetail(mDetail);
		assertEquals(viewModel.getImageUrl(), "https://image.tmdb.org/t/p/w780"+mDetail.poster_path);

	}

	@Test
	public void shouldGetLanguageVisibility() throws Exception {
		mDetail.spoken_languages = null;
		assertEquals(viewModel.getLanguagesVisibility(), View.GONE);
		mDetail=MockModelsUtil.createMockDetail();
		viewModel.setMovieDetail(mDetail);
		mDetail.spoken_languages = new ArrayList<>();
		assertEquals(viewModel.getLanguagesVisibility(), View.GONE);
		SpokenLanguage  model = new SpokenLanguage("1","Hindi");
		mDetail.spoken_languages.add(model);
		assertEquals(viewModel.getLanguagesVisibility(), View.VISIBLE);

	}
}