package com.example.mymoviecatalogue.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mymoviecatalogue.model.Movie;

import java.util.List;

@Dao
public interface DataAccessObject {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie... movies);

    @Query("SELECT * FROM movie")
    Cursor selectAll();

    @Query("SELECT * FROM movie")
    List<Movie> getAllFavMovies();

    @Query("SELECT * FROM movie where uid = :uid")
    Cursor selectById(long uid);

    @Query("DELETE FROM movie WHERE uid = :uid")
    void deleteByUid(int uid);

    @Query("SELECT COUNT(uid) FROM movie WHERE title = :title")
    int getMovieByTitle(String title);

    @Query("SELECT * FROM movie WHERE movieType = :movieType")
    List<Movie> getMoviesByMovieType(String movieType);

}
