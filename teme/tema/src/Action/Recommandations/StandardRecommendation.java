package Action.Recommandations;

import Database.Database;
import Entities.Movie;
import Entities.Show;
import Entities.User;
import Entities.Video;
import common.Constants;
import fileio.ActionInputData;
import utils.ActionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StandardRecommendation extends Recommendation {
    public static ActionResponse solveRecommendation(ActionInputData action, User user) {
        ActionResponse actionResponse = new ActionResponse();
        List<Video> recommendedVideos = new ArrayList<Video>();
        switch (action.getType()) {
            case Constants.STANDARD:
                Video v = Database.getInstance().movies.stream().filter(m -> !user.getWatchedVideos().containsKey(m.getTitle())).findFirst().orElse(null);
                if (v==null) {
                    Database.getInstance().shows.stream().filter(m -> !user.getWatchedVideos().containsKey(m.getTitle())).findFirst().orElse(null);
                }
                if (v!=null) {
                    recommendedVideos.add(v);
                }
                break;
            case Constants.BEST_UNSEEN:
                List<Video> sortedList = new ArrayList<Video>();
                for (Movie m : Database.getInstance().movies) {
                    sortedList.add(m);
                }
                for (Show s : Database.getInstance().shows) {
                    sortedList.add(s);
                }
                Collections.reverse(sortedList);
                sortedList = sortVideoListByRating(sortedList);

                Collections.reverse(sortedList);
                Video v2 = sortedList.stream().filter(m -> !user.getWatchedVideos().containsKey(m.getTitle())).findFirst().orElse(null);
                if (v2 == null) {
                    sortedList.stream().filter(m -> !user.getWatchedVideos().containsKey(m.getTitle())).findFirst().orElse(null);
                }
                if (v2 != null) {
                    recommendedVideos.add(v2);
                }
                break;
        }
        actionResponse.setResponse(actionResponse.outputRecommendation(recommendedVideos.size() != 0, action, recommendedVideos, user));

        return actionResponse;
    }
}

