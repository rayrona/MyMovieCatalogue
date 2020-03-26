package com.example.favorite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Cursor mCursor;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.bind(mCursor.moveToPosition(i));
    }

    public void setData(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_fav)
        TextView title;
        @BindView(R.id.description_fav)
        TextView description;
        @BindView(R.id.poster_fav)
        ImageView poster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(boolean moveToPosition) {
            if (moveToPosition) {
                title.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Utils.COLUMN_TITLE)));
                description.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Utils.COLUMN_DESCRIPTION)));
                Glide.with(context).load(Utils.POSTER_BASE_URL + mCursor.getString(mCursor.getColumnIndexOrThrow(Utils.COLUMN_POSTER))).into(poster);
            }
        }
    }
}

