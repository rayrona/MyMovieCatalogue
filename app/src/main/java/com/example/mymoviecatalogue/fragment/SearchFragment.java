package com.example.mymoviecatalogue.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mymoviecatalogue.MainViewModel;
import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.adapter.MovieAdapter;
import com.example.mymoviecatalogue.model.Movie;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    @BindView(R.id.shimmer_view)
    ShimmerFrameLayout mShimmerViewContainer;
    @BindView(R.id.list_mov_result)
    RecyclerView searchResult;
    private MovieAdapter adapter;
    private ProgressBar progressBar;

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, rootView);
        searchResult.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new MovieAdapter(getActivity());
        searchResult.setAdapter(adapter);
        mShimmerViewContainer.startShimmer();
        progressBar = rootView.findViewById(R.id.progressBar); //loading

        String query = getArguments().getString("query");

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.searchMovie(query);
        mainViewModel.getMovies().observe(this, getMovieResult);
        showLoading(true);
        return rootView;
    }


    Observer<ArrayList<Movie>> getMovieResult = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            if (movies != null && movies.size() > 0) {
                adapter.setMovies(movies);
            } else {
                Toast.makeText(getContext(), R.string.not_found, Toast.LENGTH_LONG).show();
            }
            showLoading(false);
        }
    };
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}

