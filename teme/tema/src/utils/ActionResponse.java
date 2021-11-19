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

    public String OutputActorsQuery(ActionInputData action, List<Actor> actorList) {
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

    public String OutputVideosQuery(ActionInputData action, List<Video> videoList) {
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(Constants.QUERY_RESULT_BEGIN);
        int listIndex = 1;
        for (Video v : videoList) {
            if (listIndex < videoList.size()) {
                outputBuilder.append(v.GetTitle()).append(", ");
            } else {
                outputBuilder.append(v.GetTitle());
            }
            listIndex++;
        }
        outputBuilder.append(Constants.QUERY_RESULT_END);
        return outputBuilder.toString();
    }

    public String OutputUsersQuery(ActionInputData action, List<User> userList) {
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(Constants.QUERY_RESULT_BEGIN);
        int listIndex = 1;
        for (User u : userList) {
            if (listIndex < userList.size()) {
                outputBuilder.append(u.GetUsername()).append(", ");
            } else {
                outputBuilder.append(u.GetUsername());
            }
            listIndex++;
        }
        outputBuilder.append(Constants.QUERY_RESULT_END);
        return outputBuilder.toString();
    }
}
