package utils;

import Entities.User;
import Entities.Video;
import actor.Actor;
import common.Constants;
import fileio.ActionInputData;

import java.util.List;

public class ActionResponse {
    private int id;
    private String response;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String outputActorsQuery(ActionInputData action, List<Actor> actorList) {
        id = action.getActionId();
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(Constants.QUERY_RESULT_BEGIN);
        int listIndex = 1;
        for (Actor a : actorList) {
            if (listIndex < actorList.size()) {
                outputBuilder.append(a.getName()).append(", ");
            } else {
                outputBuilder.append(a.getName());
            }
            listIndex++;
        }
        outputBuilder.append(Constants.QUERY_RESULT_END);
        return outputBuilder.toString();
    }

    public String outputVideosQuery(ActionInputData action, List<Video> videoList) {
        id = action.getActionId();
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(Constants.QUERY_RESULT_BEGIN);
        int listIndex = 1;
        for (Video v : videoList) {
            if (listIndex < videoList.size()) {
                outputBuilder.append(v.getTitle()).append(", ");
            } else {
                outputBuilder.append(v.getTitle());
            }
            listIndex++;
        }
        outputBuilder.append(Constants.QUERY_RESULT_END);
        return outputBuilder.toString();
    }

    public String outputUsersQuery(ActionInputData action, List<User> userList) {
        id = action.getActionId();
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(Constants.QUERY_RESULT_BEGIN);
        int listIndex = 1;
        for (User u : userList) {
            if (listIndex < userList.size()) {
                outputBuilder.append(u.getUsername()).append(", ");
            } else {
                outputBuilder.append(u.getUsername());
            }
            listIndex++;
        }
        outputBuilder.append(Constants.QUERY_RESULT_END);
        return outputBuilder.toString();
    }

    public String outputFavoriteCommand(boolean hasSeenMovie, boolean movieAlreadyInFavorites,
                                        String status, String movieName, int id) {
        this.id = id;
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(status).append(" -> ").append(movieName);

        if (!hasSeenMovie) {
            outputBuilder.append(Constants.NOT_SEEN);
        }

        if (movieAlreadyInFavorites) {
            outputBuilder.append(Constants.ALREADY_FAV);
        }

        if (hasSeenMovie && !movieAlreadyInFavorites) {
            outputBuilder.append(Constants.ADDED_FAV);
        }
        return outputBuilder.toString();
    }

    public String outputViewCommand(boolean hasSeenMovie, String status,
                                    String movieName, int id,
                                    int viewNumber) {
        this.id = id;
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(status).append(" -> ").append(movieName);
        outputBuilder.append(Constants.VIEWED);
        outputBuilder.append(viewNumber);

        return outputBuilder.toString();
    }

    public String outputRatingCommand(boolean hasSeenMovie, boolean hasAlreadyRated,
                                      String status, ActionInputData action) {
        this.id = action.getActionId();
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(status).append(" -> ").append(action.getTitle());

        if (!hasSeenMovie){
            outputBuilder.append(Constants.NOT_SEEN);
        } else {
            if (hasAlreadyRated) {
                outputBuilder.append(Constants.ALREADY_RATED);
            } else {
                outputBuilder.append(Constants.WAS_RATED);
                outputBuilder.append(action.getGrade());
                outputBuilder.append(Constants.BY);
                outputBuilder.append(action.getUsername());
            }
        }

        return outputBuilder.toString();
    }

    public String outputRecommendation(boolean status, ActionInputData action, List<Video> videoList, User user){
        id = action.getActionId();
        StringBuilder outputBuilder = new StringBuilder();
        switch (action.getType()) {
            case Constants.BEST_UNSEEN:
                outputBuilder.append(Constants.BEST_UNSEEN_RECOMMENDATION);
                break;
            case Constants.STANDARD:
                outputBuilder.append(Constants.STANDARD_RECOMMEDATION);
                break;
            case Constants.POPULAR:
                outputBuilder.append(Constants.POPULAR_RECOMMENDATION);
                break;
            case Constants.FAVORITE:
                outputBuilder.append(Constants.FAVORITE_RECOMMENDATION);
                break;
            case Constants.SEARCH:
                outputBuilder.append(Constants.SEARCH_RECOMMENDATION);
                break;
        }
        if(status) {
            outputBuilder.append(Constants.RECOMMENDATION_RESULT_BEGIN);
            if (action.getType().equals(Constants.SEARCH)){
                int listIndex = 1;
                outputBuilder.append("[");
                for (Video v : videoList) {
                    if (listIndex < videoList.size()) {
                        outputBuilder.append(v.getTitle()).append(", ");
                    } else {
                        outputBuilder.append(v.getTitle());
                    }
                    listIndex++;
                }
                outputBuilder.append("]");
            }
            else {
                outputBuilder.append(videoList.get(0).getTitle());
            }
        }
        else {
            outputBuilder.append(Constants.RECOMMENDATION_FAIL);
        }
        return outputBuilder.toString();
    }
}
