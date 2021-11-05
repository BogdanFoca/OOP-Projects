package Entities;

import entertainment.Genre;

import java.util.List;
import java.util.ArrayList;

public class Video extends Object {
    String title;
    int releaseYear;
    //Video genres
    List<Genre> genres = new ArrayList<Genre>();

    double rating;

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
}
