package com.example.mymoviecatalogue.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.mymoviecatalogue.BuildConfig;
import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.database.DataAccessObject;
import com.example.mymoviecatalogue.database.MovieDatabase;
import com.example.mymoviecatalogue.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_detail)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.overview)
    TextView description;
    @BindView(R.id.original_language)
    TextView originalLanguage;
    @BindView(R.id.release_date)
    TextView releaseDate;
    @BindView(R.id.vote_average)
    TextView voteAverage;
    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.background)
    ImageView backdrop;

    private Movie movie;
    private DataAccessObject DAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");

        ButterKnife.bind(this);
        initToolbar();
        showDetails(movie);

        DAO = Room.databaseBuilder(this, MovieDatabase.class, "db_movie")
                .allowMainThreadQueries()
                .build()
                .getMovieDAO();
    }

    private void showDetails(Movie movie) {
        title.setText(movie.getTitle());
        originalLanguage.setText(movie.getOriginalLanguage());
        description.setText(movie.getDescription());
        releaseDate.setText(movie.getReleaseDate());
        voteAverage.setText(movie.getVoteAverage());
        Glide.with(this)
                .load(BuildConfig.IMG_URL + movie.getPoster())
                .into(poster);
        Glide.with(this)
                .load(BuildConfig.IMG_URL + movie.getPoster())
                .into(backdrop);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.details);
    }

    private void markAsFavorite() {
        DAO.insert(movie);
        setResult(RESULT_OK);
        Intent brIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(brIntent);
    }

    private void setFavoriteSelected(MenuItem item) {
        item.setIcon(R.drawable.ic_favorite_white_24dp);
        item.setEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        if (DAO.getMovieByTitle(movie.getTitle()) > 0) {
            setFavoriteSelected(menu.getItem(0));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.fav_btn:
                try {
                    markAsFavorite();
                    setFavoriteSelected(item);
                    Toast.makeText(this, R.string.success_insert_fav, Toast.LENGTH_SHORT).show();
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
        }
        return true;
    }
}

