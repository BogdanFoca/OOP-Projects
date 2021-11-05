package utils;

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
}
