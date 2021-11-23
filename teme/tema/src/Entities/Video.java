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

    double rating;

    int views = 0;
    int favoriteCount = 0;

    public String GetTitle(){
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        Video v = (Video)obj;
        return v.GetTitle().equals(GetTitle());
    }

    public double GetRating(){
        return rating;
    }

    public void SetRating(double rating){
        this.rating = rating;
    }

    public List<Genre> GetGenres(){
        return genres;
    }

    public int GetDuration(){
        return duration;
    }

    public void IncrementViews(){
        views += 1;
    }

    public void IncrementViews(int no){
        views += no;
    }

    public void IncrementFavoriteCount(){
        favoriteCount++;
    }

    public int GetViews(){
        return views;
    }

    public int GetFavoriteCount(){
        return favoriteCount;
    }
}
