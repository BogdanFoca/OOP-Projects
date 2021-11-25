package Entities;

import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Video {
    List<Double> ratings = new ArrayList<Double>();

    public Movie(String title, int releaseYear, List<String> genres, List<String> cast, int duration) {
        this.title = title;
        this.releaseYear = releaseYear;
        for(String genre : genres) {
            this.genres.add(Utils.stringToGenre(genre));
        }
        this.cast = new ArrayList<String>(cast);
        this.duration = duration;
    }

    public void addRating(double rating) {
        ratings.add(rating);
        double average=0;
        for(Double d : ratings) {
            average += d;
        }
        average = average / ratings.size();
        setRating(average);
    }

    public List<Double> getRatings(){
        return ratings;
    }
}
