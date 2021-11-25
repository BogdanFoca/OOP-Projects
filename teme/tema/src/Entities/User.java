package Entities;

import Database.Database;

import java.util.*;

public class User extends Object{
    String username;
    UserType userType;
    List<String> favoriteVideos = new ArrayList<String>();
    Map<String, Integer> watchedVideos = new HashMap<String, Integer>();

    //List of reviews left by this user
    Map<String, Double> movieReviews = new HashMap<String, Double>();
    Map<Pair<String, Integer>, Double> seasonReviews = new HashMap<Pair<String, Integer>, Double>();

    public User(String username, String userType, Map<String, Integer> history, List<String> favoriteVideos) {
        this.username = username;
        this.userType = UserType.valueOf(userType.toUpperCase(Locale.ROOT));
        watchedVideos = new HashMap<String, Integer>(history);
        for(Map.Entry<String, Integer> entry : watchedVideos.entrySet()) {
            Video video = Database.getInstance().movies.stream().filter(v -> v.getTitle().equals(entry.getKey())).findFirst().orElse(null);
            if(video == null) {
                video = Database.getInstance().shows.stream().filter(v -> v.getTitle().equals(entry.getKey())).findFirst().orElse(null);
            }
            video.incrementViews(entry.getValue());
        }
        this.favoriteVideos = new ArrayList<String>(favoriteVideos);
        for(String video : favoriteVideos) {
            Video v = Database.getInstance().movies.stream().filter(m -> m.getTitle().equals(video)).findFirst().orElse(null);
            if(v == null) {
                v = Database.getInstance().shows.stream().filter(s -> s.getTitle().equals(video)).findFirst().orElse(null);
            }
            v.incrementFavoriteCount();
        }
    }

    public String getUsername() {
        return username;
    }
    /**
     * Adds a video to favorites
     * @param video Video to add
     */
    public void addToFavorite(String video) {
        favoriteVideos.add(video);
        Video v = Database.getInstance().movies.stream().filter(m -> m.getTitle().equals(video)).findAny().orElse(null);
        if(v==null) {
            v = Database.getInstance().shows.stream().filter(m -> m.getTitle().equals(video)).findAny().orElse(null);
        }
        v.incrementFavoriteCount();
    }

    public List<String> getFavoriteVideos() {
        return favoriteVideos;
    }

    public void watchVideo(String video) {
        if(watchedVideos.containsKey(video)) {
            watchedVideos.put(video, watchedVideos.get(video) + 1);
        }
        else{
            watchedVideos.put(video, 1);
        }
        Video video1 = Database.getInstance().movies.stream().filter(v -> v.getTitle().equals(video)).findFirst().orElse(null);
        if(video1==null) {
            video1 = Database.getInstance().shows.stream().filter(v -> v.getTitle().equals(video)).findFirst().orElse(null);
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
    public void rateVideo(String movie, double rating) {
        movieReviews.put(movie, rating);
        Database.getInstance().movies.stream().filter(m -> m.getTitle().equals(movie)).findFirst().orElse(null).addRating(rating);
    }

    /**
     * Adds a rating to a season of a show
     * @param show Show to rate
     * @param season Index of season
     * @param rating Rating
     */
    public void rateVideo(String show, int season, double rating) {
        seasonReviews.put(new Pair<String, Integer>(show, season), rating);
        Database.getInstance().shows.stream().filter(m -> m.getTitle().equals(show)).findFirst().orElse(null).addRating(rating, season);
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

