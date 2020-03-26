package com.example.mymoviecatalogue.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mymoviecatalogue.model.Movie;

//Database
@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract DataAccessObject getMovieDAO();
}
