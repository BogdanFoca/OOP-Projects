package entities;

import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public final class Movie extends Video {
    private List<Double> ratings = new ArrayList<Double>();

    public Movie(final String title, final int releaseYear,
                 final List<String> genres, final List<String> cast, final int duration) {
        this.title = title;
        this.releaseYear = releaseYear;
        for (String genre : genres) {
            this.genres.add(Utils.stringToGenre(genre));
        }
        this.cast = new ArrayList<String>(cast);
        this.duration = duration;
    }

    /**
     *
     * @param rating
     */
    public void addRating(final double rating) {
        ratings.add(rating);
        double average = 0;
        for (Double d : ratings) {
            average += d;
        }
        average = average / ratings.size();
        setRating(average);
    }

    public List<Double> getRatings() {
        return ratings;
    }
}
