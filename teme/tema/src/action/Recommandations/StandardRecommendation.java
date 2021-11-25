package action.Recommandations;

import database.Database;
import entities.Movie;
import entities.Show;
import entities.User;
import entities.Video;
import common.Constants;
import fileio.ActionInputData;
import utils.ActionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class StandardRecommendation extends Recommendation {
    private StandardRecommendation() {
    }
    /**
     *
     * @param action
     * @param user
     * @return
     */
    public static ActionResponse solveRecommendation(
            final ActionInputData action, final User user) {
        ActionResponse actionResponse = new ActionResponse();
        List<Video> recommendedVideos = new ArrayList<Video>();
        switch (action.getType()) {
            case Constants.STANDARD:
                Video v = Database.getInstance().getMovies().stream()
                        .filter(m -> !user.getWatchedVideos().containsKey(m.getTitle()))
                        .findFirst().orElse(null);
                if (v == null) {
                    Database.getInstance().getShows().stream()
                            .filter(m -> !user.getWatchedVideos().containsKey(m.getTitle()))
                            .findFirst().orElse(null);
                }
                if (v != null) {
                    recommendedVideos.add(v);
                }
                break;
            case Constants.BEST_UNSEEN:
                List<Video> sortedList = new ArrayList<Video>();
                for (Movie m : Database.getInstance().getMovies()) {
                    sortedList.add(m);
                }
                for (Show s : Database.getInstance().getShows()) {
                    sortedList.add(s);
                }
                Collections.reverse(sortedList);
                sortedList = sortVideoListByRating(sortedList);

                Collections.reverse(sortedList);
                Video v2 = sortedList.stream()
                        .filter(m -> !user.getWatchedVideos().containsKey(m.getTitle()))
                        .findFirst().orElse(null);
                if (v2 == null) {
                    sortedList.stream()
                            .filter(m -> !user.getWatchedVideos().containsKey(m.getTitle()))
                            .findFirst().orElse(null);
                }
                if (v2 != null) {
                    recommendedVideos.add(v2);
                }
                break;
            default:
                break;
        }
        actionResponse.setResponse(actionResponse
                .outputRecommendation(recommendedVideos.size() != 0,
                        action, recommendedVideos));

        return actionResponse;
    }
}

