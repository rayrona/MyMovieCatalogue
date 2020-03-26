package com.example.mymoviecatalogue.api;

import com.example.mymoviecatalogue.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpoints {
    @GET("discover/{type}")
    Call<MovieResponse> getMovies(@Path("type") String movieType);

    @GET("search/multi")
    Call<MovieResponse> searchMovies(@Query("query") String query);

    @GET("discover/movie")
    Call<MovieResponse> getReleasedMovies(@Query("primary_release_date.gte") String date, @Query("primary_release_date.lte") String today);
}