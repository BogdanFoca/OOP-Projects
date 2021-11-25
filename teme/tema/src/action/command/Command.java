package action.command;

import entities.Pair;
import common.Constants;
import entities.User;
import fileio.ActionInputData;
import utils.ActionResponse;

import java.util.Map;

public final class Command {
    private Command() {

    }

    /**
     *
     * @param action
     * @param actionUser
     * @return
     */
    public static ActionResponse solveCommands(
            final ActionInputData action, final User actionUser) {
        ActionResponse response = new ActionResponse();
        switch (action.getType()) {
            case Constants.FAVORITE:
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
                response.setResponse(response
                        .outputFavoriteCommand(isSeen, isAlreadyFavorite,
                                status, action.getTitle(), action.getActionId()));
                break;
            case Constants.VIEW_MOVIES:
                isSeen = false;
                status = Constants.SUCCESS;
                if (actionUser.getWatchedVideos().containsKey(action.getTitle())) {
                    isSeen = true;
                }
                actionUser.watchVideo(action.getTitle());
                response.setResponse(
                        response.outputViewCommand(isSeen, status,
                                action.getTitle(), action.getActionId(),
                                actionUser.getWatchedVideos().get(action.getTitle())));
                break;
            case Constants.RATING:
                isSeen = false;
                boolean isAlreadyRated = false;
                status = Constants.ERROR;

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
                        actionUser.rateVideo(
                                action.getTitle(), action.getSeasonNumber(), action.getGrade());
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
            default:
                break;
        }
        return response;
    }
}
