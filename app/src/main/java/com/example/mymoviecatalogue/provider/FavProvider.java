package com.example.mymoviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.mymoviecatalogue.database.DataAccessObject;
import com.example.mymoviecatalogue.database.MovieDatabase;

public class FavProvider extends ContentProvider {
    private MovieDatabase movieDatabase;
    private DataAccessObject movieDAO;
    private static final String DBNAME = "db_movie";
    private static final String AUTHORITY = "com.example.mymoviecatalogue.provider";
    private static final String DB_TABLE = "movie";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CODE_FAV_DIR = 1;
    private static final int CODE_FAV_ITEM = 2;

    static {
        uriMatcher.addURI(AUTHORITY, DB_TABLE, CODE_FAV_DIR);
        uriMatcher.addURI(AUTHORITY, DB_TABLE + "/#", CODE_FAV_ITEM);
    }

    @Override
    public boolean onCreate() {
        movieDatabase = Room.databaseBuilder(getContext(), MovieDatabase.class, DBNAME).build();
        movieDAO = movieDatabase.getMovieDAO();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = uriMatcher.match(uri);
        if (code == CODE_FAV_DIR || code == CODE_FAV_ITEM) {
            final Context context = getContext();
            if (context == null)
                return null;
            final Cursor cursor;
            if (code == CODE_FAV_DIR)
                cursor = movieDAO.selectAll();
            else
                cursor = movieDAO.selectById(ContentUris.parseId(uri));
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


}
