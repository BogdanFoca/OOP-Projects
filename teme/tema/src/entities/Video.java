package entities;

import entertainment.Genre;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Video extends Object {
    protected String title;
    protected int releaseYear;
    //Video genres
    protected List<Genre> genres = new ArrayList<Genre>();
    protected List<String> cast = new ArrayList<String>();

    protected int duration;

    private double rating = 0;

    private int views = 0;
    private int favoriteCount = 0;

    public final String getTitle() {
        return title;
    }

    public final int getReleaseYear() {
        return releaseYear;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        Video v = (Video) obj;
        return v.getTitle().equals(getTitle());
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    public final double getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     */
    public final void setRating(final double rating) {
        this.rating = rating;
    }

    public final List<Genre> getGenres() {
        return genres;
    }

    /**
     *
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     *
     */
    public final void incrementViews() {
        views += 1;
    }

    /**
     *
     * @param no
     */
    public final void incrementViews(final int no) {
        views += no;
    }

    /**
     *
     */
    public final void incrementFavoriteCount() {
        favoriteCount++;
    }

    public final int getViews() {
        return views;
    }

    public final int getFavoriteCount() {
        return favoriteCount;
    }
}
