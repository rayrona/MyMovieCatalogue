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
public class MovFragment extends Fragment {
    @BindView(R.id.list_mov)
    RecyclerView listMov;
    @BindView(R.id.shimmer_view)
    ShimmerFrameLayout mShimmerViewContainer;

    private MovieAdapter adapter;
    private ProgressBar progressBar;

    public MovFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mov, container, false);
        ButterKnife.bind(this, rootView);
        listMov.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new MovieAdapter(getActivity());
        listMov.setAdapter(adapter);
        mShimmerViewContainer.startShimmer();
        progressBar = rootView.findViewById(R.id.progressBar);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setMovies("movie");
        mainViewModel.getMovies().observe(this, getMovies);
        showLoading(true);
        return rootView;
    }

    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if (movies != null) {
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
                adapter.setMovies(movies);
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
