package com.example.mymoviecatalogue.adapter;

import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymoviecatalogue.BuildConfig;
import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.database.DataAccessObject;
import com.example.mymoviecatalogue.database.MovieDatabase;
import com.example.mymoviecatalogue.model.Movie;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MovieFavViewHolder> {
    private Context context;
    private List<Movie> movies = new ArrayList<>();

    public FavAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieFavViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_fav, viewGroup, false);
        return new FavAdapter.MovieFavViewHolder(mView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFavViewHolder movieFavViewHolder, int i) {
        movieFavViewHolder.bind(movies.get(i));
    }

    public class MovieFavViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_fav)
        TextView title;
        @BindView(R.id.description_fav)
        TextView description;
        @BindView(R.id.poster_fav)
        ImageView poster;
        @BindView(R.id.btn_delete)
        Button btnDelete;

        public MovieFavViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(final Movie movie) {
            Glide.with(context)
                    .load(BuildConfig.IMG_URL + movie.getPoster())
                    .apply(new RequestOptions())
                    .into(poster);
            title.setText(movie.getTitle());
            description.setText(movie.getDescription());
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle(R.string.confirm_title);
                    builder.setMessage(R.string.confirm_message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.pos_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataAccessObject movieDAO = Room.databaseBuilder(itemView.getContext(), MovieDatabase.class, "db_movie")
                                    .allowMainThreadQueries()
                                    .build()
                                    .getMovieDAO();
                            movieDAO.deleteByUid(movies.get(getAdapterPosition()).getUid());
                            movies.remove(movie);
                            notifyDataSetChanged();
                            Intent brIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                            context.sendBroadcast(brIntent);
                            Snackbar.make(v, R.string.success_info, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton(R.string.neg_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.setCancelable(true);
                        }
                    });
                    builder.show();
                }
            });
        }
    }
}
