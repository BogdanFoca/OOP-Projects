package Action.Command;

import Entities.Pair;
import common.Constants;
import Entities.User;
import fileio.ActionInputData;
import utils.ActionResponse;

import java.util.Map;

public class Command {
    public static ActionResponse solveCommands(ActionInputData action, User actionUser) {
        ActionResponse response = new ActionResponse();
        switch (action.getType()) {
            case Constants.FAVORITE: {
                boolean isAlreadyFavorite = false;
                boolean isSeen = false;
                String status = Constants.ERROR;
                if (actionUser.getFavoriteVideos().contains(action.getTitle())) {
                    isAlreadyFavorite = true;
                }
                if (actionUser.getWatchedVideos().containsKey(action.getTitle())) {
                    isSeen = true;
                }
                if (!isAlreadyFavorite && isSeen) {
                    actionUser.addToFavorite(action.getTitle());
                    status = Constants.SUCCESS;
                }
                response.setResponse(response.outputFavoriteCommand
                        (isSeen, isAlreadyFavorite, status, action.getTitle(), action.getActionId()));
                break;
            }
            case Constants.VIEW_MOVIES: {
                boolean isSeen = false;
                String status = Constants.SUCCESS;
                if (actionUser.getWatchedVideos().containsKey(action.getTitle())) {
                    isSeen = true;
                }
                actionUser.watchVideo(action.getTitle());
                response.setResponse(
                        response.outputViewCommand(isSeen, status, action.getTitle(), action.getActionId(),
                                actionUser.getWatchedVideos().get(action.getTitle())));
                break;
            }
            case Constants.RATING: {
                boolean isSeen = false;
                boolean isAlreadyRated = false;
                String status = Constants.ERROR;

                if (actionUser.getWatchedVideos().containsKey(action.getTitle())) {
                    isSeen = true;
                }
                if (action.getSeasonNumber() != 0) {
                    for (Map.Entry entry : actionUser.getShowReviews().entrySet()) {
                        if (entry.getKey().equals(new Pair<String, Integer>(action.getTitle(),
                                action.getSeasonNumber()))) {
                            isAlreadyRated = true;
                        }
                    }
                    if (isSeen && !isAlreadyRated) {
                        actionUser.rateVideo(action.getTitle(), action.getSeasonNumber(), action.getGrade());
                        status = Constants.SUCCESS;
                    }
                } else {
                    if (actionUser.getMovieReviews().containsKey(action.getTitle())) {
                        isAlreadyRated = true;
                    }
                    if (isSeen && !isAlreadyRated) {
                        actionUser.rateVideo(action.getTitle(), action.getGrade());
                        status = Constants.SUCCESS;
                    }
                }
                response.setResponse(
                        response.outputRatingCommand(isSeen, isAlreadyRated, status, action));
                break;
            }
        }
        return response;
    }
}
