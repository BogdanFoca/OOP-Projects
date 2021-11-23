package Action.Recommandations;

import Database.Database;
import Entities.Movie;
import Entities.Show;
import Entities.User;
import Entities.Video;
import common.Constants;
import entertainment.Genre;
import fileio.ActionInputData;
import utils.ActionResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PremiumRecommendation extends Recommendation {
    public ActionResponse SolveQuery(ActionInputData action, User user) {
        ActionResponse actionResponse = new ActionResponse();
        List<Video> recommendedVideos = new ArrayList<Video>();
        switch(action.getType()){
            case Constants.POPULAR:
                List<Video> popular = new ArrayList<Video>();
                popular.addAll(Database.GetInstance().movies.stream().filter(m -> !user.GetWatchedVideos().containsKey(m.GetTitle())).collect(Collectors.toList()));
                popular.addAll(Database.GetInstance().shows.stream().filter(m -> !user.GetWatchedVideos().containsKey(m.GetTitle())).collect(Collectors.toList()));
                int maxViews = 0;
                Genre maxGenre = Genre.ACTION;
                for(Genre genre : Genre.values()){
                    List<Video> videosInGenre = popular.stream().filter(v -> v.GetGenres().contains(genre)).collect(Collectors.toList());
                    int views = 0;
                    for(Video v : videosInGenre){
                        views += v.GetViews();
                    }
                    if(views > maxViews){
                        boolean existsUnwatchedVideo = false;
                        for(Video v : videosInGenre){
                            if(!user.GetWatchedVideos().containsKey(v.GetTitle())){
                                existsUnwatchedVideo = true;
                                break;
                            }
                        }
                        if(existsUnwatchedVideo) {
                            maxViews = views;
                            maxGenre = genre;
                        }
                    }
                }
                Genre g = maxGenre;
                Video video = Database.GetInstance().movies.stream().filter(v -> v.GetGenres().contains(g)).findFirst().orElse(null);
                if(video == null){
                    Database.GetInstance().shows.stream().filter(v -> v.GetGenres().contains(g)).findFirst().orElse(null);
                }
                if(video != null){
                    recommendedVideos.add(video);
                }
                break;
            case Constants.FAVORITE_MOVIES:
                Video video1 = null;
                int maxFavoriteCount = 0;
                for(Movie m : Database.GetInstance().movies){
                    if(m.GetFavoriteCount() > maxFavoriteCount){
                        video1 = m;
                        maxFavoriteCount = m.GetFavoriteCount();
                    }
                }
                for(Show s : Database.GetInstance().shows){
                    if(s.GetFavoriteCount() > maxFavoriteCount){
                        video1 = s;
                        maxFavoriteCount = s.GetFavoriteCount();
                    }
                }
                if(video1 != null){
                    recommendedVideos.add(video1);
                }
                break;
            case Constants.SEARCH:
                List<Video> videosInGenre = new ArrayList<Video>();
                videosInGenre.addAll(Database.GetInstance().movies.stream().filter(m -> m.GetGenres().contains(action.getFilters().get(0))).collect(Collectors.toList()));
                videosInGenre.addAll(Database.GetInstance().shows.stream().filter(m -> m.GetGenres().contains(action.getFilters().get(0))).collect(Collectors.toList()));
                videosInGenre = videosInGenre.stream().filter(v -> !user.GetWatchedVideos().containsKey(v.GetTitle())).collect(Collectors.toList());
                videosInGenre = SortVideoListAlphabetically(videosInGenre);
                videosInGenre = SortVideoListByRating(videosInGenre);
                recommendedVideos.addAll(videosInGenre);
                break;
        }

        if(recommendedVideos.size() == 0){
            actionResponse.setResponse(null);
        }
        else {
            actionResponse.setResponse(actionResponse.OutputVideosQuery(action, recommendedVideos));
        }

        return actionResponse;
    }
}
