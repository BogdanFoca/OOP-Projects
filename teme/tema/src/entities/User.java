package entities;

import database.Database;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Locale;

public final class User {
    private String username;
    private UserType userType;
    private List<String> favoriteVideos = new ArrayList<String>();
    private Map<String, Integer> watchedVideos = new HashMap<String, Integer>();

    //List of reviews left by this user
    private Map<String, Double> movieReviews = new HashMap<String, Double>();
    private Map<Pair<String, Integer>, Double> seasonReviews
            = new HashMap<Pair<String, Integer>, Double>();

    public User(final String username, final String userType,
                final Map<String, Integer> history,
                final List<String> favoriteVideos) {
        this.username = username;
        this.userType = UserType.valueOf(userType.toUpperCase(Locale.ROOT));
        watchedVideos = new HashMap<String, Integer>(history);
        for (Map.Entry<String, Integer> entry : watchedVideos.entrySet()) {
            Video video = Database.getInstance().getMovies().stream()
                    .filter(v -> v.getTitle().equals(entry.getKey())).findFirst().orElse(null);
            if (video == null) {
                video = Database.getInstance().getShows().stream()
                        .filter(v -> v.getTitle().equals(entry.getKey())).findFirst().orElse(null);
            }
            if (video != null) {
                video.incrementViews(entry.getValue());
            }
        }
        this.favoriteVideos = new ArrayList<String>(favoriteVideos);
        for (String video : favoriteVideos) {
            Video v = Database.getInstance().getMovies().stream()
                    .filter(m -> m.getTitle().equals(video)).findFirst().orElse(null);
            if (v == null) {
                v = Database.getInstance().getShows().stream()
                        .filter(s -> s.getTitle().equals(video)).findFirst().orElse(null);
            }
            if (v != null) {
                v.incrementFavoriteCount();
            }
        }
    }

    public String getUsername() {
        return username;
    }
    /**
     * Adds a video to favorites
     * @param video Video to add
     */
    public void addToFavorite(final String video) {
        favoriteVideos.add(video);
        Video v = Database.getInstance().getMovies().stream()
                .filter(m -> m.getTitle().equals(video)).findAny().orElse(null);
        if (v == null) {
            v = Database.getInstance().getShows().stream()
                    .filter(m -> m.getTitle().equals(video)).findAny().orElse(null);
        }
        if (v != null) {
            v.incrementFavoriteCount();
        }
    }

    public List<String> getFavoriteVideos() {
        return favoriteVideos;
    }

    /**
     *
     * @param video
     */
    public void watchVideo(final String video) {
        if (watchedVideos.containsKey(video)) {
            watchedVideos.put(video, watchedVideos.get(video) + 1);
        } else {
            watchedVideos.put(video, 1);
        }
        Video video1 = Database.getInstance().getMovies().stream()
                .filter(v -> v.getTitle().equals(video)).findFirst().orElse(null);
        if (video1 == null) {
            video1 = Database.getInstance().getShows().stream()
                    .filter(v -> v.getTitle().equals(video)).findFirst().orElse(null);
        }
        video1.incrementViews();
    }

    public Map<String, Integer> getWatchedVideos() {
        return watchedVideos;
    }

    /**
     * Adds a rating to a movie
     * @param movie Movie to rate
     * @param rating Rating
     */
    public void rateVideo(final String movie, final double rating) {
        movieReviews.put(movie, rating);
        Database.getInstance().getMovies().stream()
                .filter(m -> m.getTitle().equals(movie)).findFirst()
                .orElse(null).addRating(rating);
    }

    /**
     * Adds a rating to a season of a show
     * @param show Show to rate
     * @param season Index of season
     * @param rating Rating
     */
    public void rateVideo(
            final String show, final int season, final double rating) {
        seasonReviews.put(new Pair<String, Integer>(show, season), rating);
        Database.getInstance().getShows().stream()
                .filter(m -> m.getTitle().equals(show)).findFirst()
                .orElse(null).addRating(rating, season);
    }

    public int getNumberOfReviews() {
        return movieReviews.size() + seasonReviews.size();
    }
    public Map<String, Double> getMovieReviews() {
        return movieReviews;
    }
    public Map<Pair<String, Integer>, Double> getShowReviews() {
        return seasonReviews;
    }

    public UserType getUserType() {
        return userType;
    }
}

