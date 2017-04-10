package com.movieapplication.swati.data;

import com.movieapplication.swati.model.MoviesListModel;
import com.movieapplication.swati.model.MoviesModel;
import com.movieapplication.swati.util.DefaultConfig;
import com.movieapplication.swati.utils.MockModelsUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import application.movie.swati.com.movieapplication.BuildConfig;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by aggarwal.swati on 4/7/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK, manifest = DefaultConfig.MANIFEST)
public class DataManagerTest {

	private DataManager mDataManager;
	private MoviesFetchService moviesFetchService;

	@Before
	public void setUp() throws Exception {
		moviesFetchService = mock(MoviesFetchService.class);
		mDataManager = new DataManager(moviesFetchService,
				Schedulers.immediate());
	}

	@Test
	public void shouldGetTopMovies() throws Exception {
		MoviesModel mockMovieOne = MockModelsUtil.createMockMovies();
		MoviesModel mockMovieTwo = MockModelsUtil.createMockMovies();
		MoviesModel mockMovieThree = MockModelsUtil.createMockMovies();
		MoviesModel mockMovieFour = MockModelsUtil.createMockMovies();

		List<MoviesModel> topStories = new ArrayList<>();
		topStories.add(mockMovieOne);
		topStories.add(mockMovieTwo);
		topStories.add(mockMovieThree);
		topStories.add(mockMovieFour);
		MoviesListModel model = new MoviesListModel();
		model.setMoviesModel(topStories);
		List<MoviesListModel> modelList = new ArrayList<>();
		modelList.add(model);
		when(moviesFetchService.getListOfMovies(0))
				.thenReturn(Observable.just(model));
		TestSubscriber<MoviesModel> result = new TestSubscriber<>();
		mDataManager.getMoviesFromServer(0).subscribe();
		result.assertNoErrors();
		//result.assertReceivedOnNext(topStories);
		//result.;
	}
	
}