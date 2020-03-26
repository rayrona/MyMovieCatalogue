package com.example.mymoviecatalogue.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymoviecatalogue.MainViewModel;
import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.adapter.FavAdapter;
import com.example.mymoviecatalogue.database.DataAccessObject;
import com.example.mymoviecatalogue.database.MovieDatabase;
import com.example.mymoviecatalogue.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovFavFragment extends Fragment {
    @BindView(R.id.list_fav_mov)
    RecyclerView listFavMov;
    private FavAdapter adapter;

    public MovFavFragment() {
        // Required empty public constructor
    }

    private List<Movie> loadFavMovies() {
        MovieDatabase database = Room.databaseBuilder(getActivity(), MovieDatabase.class, "db_movie")
                .allowMainThreadQueries()
                .build();
        DataAccessObject movieDAO = database.getMovieDAO();
        return movieDAO.getMoviesByMovieType("movie");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mov_fav, container, false);
        ButterKnife.bind(this, rootView);
        listFavMov.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavAdapter(getActivity());
        listFavMov.setAdapter(adapter);

        ArrayList<Movie> data = (ArrayList<Movie>) loadFavMovies();

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setFavMovie(data);
        mainViewModel.getMovies().observe(this, getMovies);

        return rootView;
    }


    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setMovies(movies);
            }
        }
    };
}
