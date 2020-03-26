package com.example.mymoviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymoviecatalogue.BuildConfig;
import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.database.DataAccessObject;
import com.example.mymoviecatalogue.database.MovieDatabase;
import com.example.mymoviecatalogue.model.Movie;

import java.util.ArrayList;

public class FavRemote implements RemoteViewsService.RemoteViewsFactory{
    private final ArrayList<Movie> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private MovieDatabase database;

    public FavRemote(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        final long identityToken = Binder.clearCallingIdentity();
        database = Room.databaseBuilder(mContext.getApplicationContext(), MovieDatabase.class, "db_movie")
                .allowMainThreadQueries()
                .build();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDataSetChanged() {
        try {
            DataAccessObject movieDAO = database.getMovieDAO();
            mWidgetItems.clear();
            mWidgetItems.addAll(movieDAO.getAllFavMovies());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(BuildConfig.IMG_URL + mWidgetItems.get(position).getPoster())
                    .apply(new RequestOptions().fitCenter())
                    .submit(800, 550)
                    .get();

            rv.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(FavWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public void onDestroy() {
        database.close();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }


}
