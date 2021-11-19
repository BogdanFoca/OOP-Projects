package Entities;

import Database.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class User extends Object{
    String username;
    public UserType userType;
    List<String> favoriteVideos = new ArrayList<String>();
    Map<String, Integer> watchedVideos = new HashMap<String, Integer>();

    //List of reviews left by this user
    Map<String, Double> movieReviews = new HashMap<String, Double>();
    Map<Pair<String, Integer>, Double> seasonReviews = new HashMap<Pair<String, Integer>, Double>();

    public User(String username, String userType, Map<String, Integer> history, List<String> favoriteVideos){
        this.username = username;
        this.userType = UserType.valueOf(userType);
        watchedVideos = new HashMap<String, Integer>(history);
        this.favoriteVideos = new ArrayList<String>(favoriteVideos);
    }

    public String GetUsername(){
        return username;
    }
    /**
     * Adds a video to favorites
     * @param video Video to add
     */
    public void AddToFavorite(String video){
        if(favoriteVideos.contains(video)) {
            favoriteVideos.add(video);
        }
    }

    public List<String> GetFavoriteVideos(){
        return favoriteVideos;
    }

    public void WatchVideo(String video){
        if(watchedVideos.containsKey(video)){
            watchedVideos.put(video, watchedVideos.get(video) + 1);
        }
        else{
            watchedVideos.put(video, 1);
        }
    }

    public Map<String, Integer> GetWatchedVideos(){
        return watchedVideos;
    }

    /**
     * Adds a rating to a movie
     * @param movie Movie to rate
     * @param rating Rating. Clamped to [1, 10]
     */
    public void RateVideo(String movie, double rating){
        if(rating < 1){
            rating = 1;
        }
        if(rating > 10){
            rating = 10;
        }
        if(!movieReviews.containsKey(movie)){
            movieReviews.put(movie, rating);
            Database.GetInstance().movies.stream().filter(m -> m.GetTitle().equals(movie)).findFirst().orElse(null).AddRating(rating);
        }
    }

    /**
     * Adds a rating to a season of a show
     * @param show Show to rate
     * @param season Index of season
     * @param rating Rating. Clamped to [1, 10]
     */
    public void RateVideo(String show, int season, double rating){
        if(rating < 1){
            rating = 1;
        }
        if(rating > 10){
            rating = 10;
        }
        if(!seasonReviews.containsKey(new Pair<String, Integer>(show, season))){
            seasonReviews.put(new Pair<String, Integer>(show, season), rating);
            Database.GetInstance().shows.stream().filter(m -> m.GetTitle().equals(show)).findFirst().orElse(null).GetSeasons().get(season).getRatings().add(rating);
        }
    }
}

//User types
enum UserType{
    standard,
    premium
}
