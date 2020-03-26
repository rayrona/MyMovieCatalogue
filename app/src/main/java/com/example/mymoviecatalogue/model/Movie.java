package com.example.mymoviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
// model
@Entity(tableName = "movie", indices = @Index(value = {"title"}, unique = true))
public class Movie implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @SerializedName(value = "title", alternate = {"name"})
    private String title;
    @SerializedName(value = "release_date", alternate = {"first_air_date"})
    private String releaseDate;
    @SerializedName("overview")
    private String description;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("media_type")
    private String movieType;

    @SerializedName("poster_path")
    private String poster;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeString(this.title);
        dest.writeString(this.releaseDate);
        dest.writeString(this.description);
        dest.writeString(this.voteAverage);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.poster);
        dest.writeString(this.movieType);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.uid = in.readInt();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.description = in.readString();
        this.voteAverage = in.readString();
        this.originalLanguage = in.readString();
        this.poster = in.readString();
        this.movieType = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
