package com.movieapplication.swati.utils;

import com.movieapplication.swati.model.MovieDetail;
import com.movieapplication.swati.model.MoviesModel;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class MockModelsUtil {

    public static Long generateRandomLong() {
        return new Random().nextLong();
    }

    public static String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    public static int generateRandomInt() {
        return new Random().nextInt(80 - 65) + 65;
    }

    public static MoviesModel createMockMovies() {
        MoviesModel movie = new MoviesModel();
        movie.id = generateRandomInt();
        movie.popularity = generateRandomLong();
        movie.title = generateRandomString();
        movie.poster_path="/1YTiBcBHFEifnsNiP7MP6oBANvD.jpg";
        movie.backdrop_path = "/1YTiBcBHFEifnsNiP7MP6oBANvD.jpg";
        return movie;
    }



    public static MovieDetail createMockDetail() {
        MovieDetail detail = new MovieDetail();
//        createMockMovies();
        detail.id = generateRandomInt();
        detail.popularity = generateRandomLong();
        detail.title = generateRandomString();
        detail.poster_path="/1YTiBcBHFEifnsNiP7MP6oBANvD.jpg";
        detail.backdrop_path = "/1YTiBcBHFEifnsNiP7MP6oBANvD.jpg";
        detail.genres = new ArrayList<>();
        detail.spoken_languages = new ArrayList<>();
        detail.runtime = String.valueOf(generateRandomInt());
        detail.overview = generateRandomString();
        return detail;
    }
    public static MovieDetail createMockDetailWithoutBackdrop() {
        MovieDetail detail = new MovieDetail();
        //        createMockMovies();
        detail.id = generateRandomInt();
        detail.popularity = generateRandomLong();
        detail.title = generateRandomString();
        detail.poster_path="/1YTiBcBHFEifnsNiP7MP6oBANvD.jpg";
        detail.genres = new ArrayList<>();
        detail.spoken_languages = new ArrayList<>();
        detail.runtime = String.valueOf(generateRandomInt());
        detail.overview = generateRandomString();
        return detail;
    }



}