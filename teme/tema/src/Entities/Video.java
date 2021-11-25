package Entities;

import entertainment.Genre;

import java.util.List;
import java.util.ArrayList;

public class Video extends Object {
    String title;
    int releaseYear;
    //Video genres
    List<Genre> genres = new ArrayList<Genre>();
    List<String> cast = new ArrayList<String>();

    int duration;

    double rating = 0;

    int views = 0;
    int favoriteCount = 0;

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public boolean equals(Object obj) {
        Video v = (Video)obj;
        return v.getTitle().equals(getTitle());
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public int getDuration() {
        return duration;
    }

    public void incrementViews() {
        views += 1;
    }

    public void incrementViews(int no) {
        views += no;
    }

    public void incrementFavoriteCount() {
        favoriteCount++;
    }

    public int getViews() {
        return views;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }
}
