package Entities;

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

    public User(String username){
        this.username = username;
    }

    public String GetUsername(){
        return username;
    }
    /**
     * Adds a video to favorites
     * @param video Video to add
     */
    public void AddToFavorite(Video video){
        if(favoriteVideos.contains(video.toString())) {
            favoriteVideos.add(video.toString());
        }
    }

    public void WatchVideo(Video video){
        if(watchedVideos.containsKey(video.GetTitle())){
            watchedVideos.put(video.GetTitle(), watchedVideos.get(video) + 1);
        }
        else{
            watchedVideos.put(video.GetTitle(), 1);
        }
    }

    /**
     * Adds a rating to a movie
     * @param movie Movie to rate
     * @param rating Rating. Clamped to [1, 10]
     */
    public void RateVideo(Movie movie, double rating){
        if(rating < 1){
            rating = 1;
        }
        if(rating > 10){
            rating = 10;
        }
        if(!movieReviews.containsKey(movie.GetTitle())){
            movieReviews.put(movie.GetTitle(), rating);
            movie.AddRating(rating);
        }
    }

    /**
     * Adds a rating to a season of a show
     * @param show Show to rate
     * @param season Index of season
     * @param rating Rating. Clamped to [1, 10]
     */
    public void RateVideo(Show show, int season, double rating){
        if(rating < 1){
            rating = 1;
        }
        if(rating > 10){
            rating = 10;
        }
        if(!seasonReviews.containsKey(new Pair<String, Integer>(show.GetTitle(), season))){
            seasonReviews.put(new Pair<String, Integer>(show.GetTitle(), season), rating);
        }
    }
}

//User types
enum UserType{
    standard,
    premium
}
