package com.example.mymoviecatalogue;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviecatalogue.activity.MainActivity;
import com.example.mymoviecatalogue.api.Api;
import com.example.mymoviecatalogue.api.ApiEndpoints;
import com.example.mymoviecatalogue.model.Movie;
import com.example.mymoviecatalogue.model.MovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();
    private ApiEndpoints apiService = Api.getClient().create(ApiEndpoints.class);

    public void searchMovie(String query) {
        Call<MovieResponse> call = apiService.searchMovies(query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                try {
                    ArrayList<Movie> movies = response.body().getResults();
                    movieList.postValue(movies);
                    Log.d(MainActivity.class.getSimpleName(), movies.toString());
                } catch (Exception e) {
                    Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(MainActivity.class.getSimpleName(), t.getLocalizedMessage());
            }
        });
    }

    public void setMovies(final String movieType) {
        Call<MovieResponse> call = apiService.getMovies(movieType);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                try {
                    ArrayList<Movie> movies = response.body().getResults();
                    for (Movie data : movies) {
                        data.setMovieType(movieType);
                    }
                    movieList.postValue(movies);
                } catch (Exception e) {
                    Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(MainActivity.class.getSimpleName(), t.getLocalizedMessage());
            }
        });
    }

    public void setFavMovie(ArrayList<Movie> movies) {
        movieList.postValue(movies);
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return movieList;
    }

}

