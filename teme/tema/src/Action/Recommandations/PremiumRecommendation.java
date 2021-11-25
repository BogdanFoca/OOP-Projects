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
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PremiumRecommendation extends Recommendation {
    public static ActionResponse solveRecommendation(ActionInputData action, User user) {
        ActionResponse actionResponse = new ActionResponse();
        List<Video> recommendedVideos = new ArrayList<Video>();
        boolean temp = false;
        switch (action.getType()) {
            case Constants.STANDARD: case Constants.BEST_UNSEEN:
                actionResponse = StandardRecommendation.solveRecommendation(action, user);
                temp = true;
                break;
            case Constants.POPULAR:
                List<Video> popular = new ArrayList<Video>();
                popular.addAll(Database.getInstance().movies.stream().filter(m -> !user.getWatchedVideos().containsKey(m.getTitle())).collect(Collectors.toList()));
                popular.addAll(Database.getInstance().shows.stream().filter(s -> !user.getWatchedVideos().containsKey(s.getTitle())).collect(Collectors.toList()));
                int maxViews = 0;
                Genre maxGenre = Genre.ACTION;
                for (Genre genre : Genre.values()) {
                    List<Video> videosInGenre = popular.stream().filter(v -> v.getGenres().contains(genre)).collect(Collectors.toList());
                    int views = 0;
                    for (Video v : videosInGenre) {
                        views += v.getViews();
                    }
                    if (views > maxViews) {
                        boolean existsUnwatchedVideo = false;
                        for (Video v : videosInGenre){
                            if (!user.getWatchedVideos().containsKey(v.getTitle())){
                                existsUnwatchedVideo = true;
                                break;
                            }
                        }
                        if (existsUnwatchedVideo) {
                            maxViews = views;
                            maxGenre = genre;
                        }
                    }
                }
                Genre g = maxGenre;
                Video video = popular.stream().filter(v -> v.getGenres().contains(g)).findFirst().orElse(null);
                if (video == null) {
                    Database.getInstance().shows.stream().filter(v -> v.getGenres().contains(g)).findFirst().orElse(null);
                }
                if (video != null) {
                    recommendedVideos.add(video);
                }
                break;
            case Constants.FAVORITE:
                Video video1 = null;
                int maxFavoriteCount = 0;
                for (Movie m : Database.getInstance().movies) {
                    if (m.getFavoriteCount() > maxFavoriteCount && !user.getWatchedVideos().containsKey(m.getTitle())) {
                        video1 = m;
                        maxFavoriteCount = m.getFavoriteCount();
                    }
                }
                for (Show s : Database.getInstance().shows) {
                    if (s.getFavoriteCount() > maxFavoriteCount && !user.getWatchedVideos().containsKey(s.getTitle())) {
                        video1 = s;
                        maxFavoriteCount = s.getFavoriteCount();
                    }
                }
                if (video1 != null) {
                    recommendedVideos.add(video1);
                }
                break;
            case Constants.SEARCH:
                List<Video> videosInGenre = new ArrayList<Video>();
                videosInGenre.addAll(Database.getInstance().movies.stream().filter(m -> m.getGenres().contains(Utils.stringToGenre(action.getGenre()))).collect(Collectors.toList()));
                videosInGenre.addAll(Database.getInstance().shows.stream().filter(m -> m.getGenres().contains(Utils.stringToGenre(action.getGenre()))).collect(Collectors.toList()));
                videosInGenre = videosInGenre.stream().filter(v -> !user.getWatchedVideos().containsKey(v.getTitle())).collect(Collectors.toList());
                videosInGenre = sortVideoListAlphabetically(videosInGenre);
                videosInGenre = sortVideoListByRating(videosInGenre);
                recommendedVideos.addAll(videosInGenre);
                break;
        }
        if (!temp) {
            actionResponse.setResponse(actionResponse.outputRecommendation(recommendedVideos.size() != 0, action, recommendedVideos, user));
        }
        return actionResponse;
    }
}
