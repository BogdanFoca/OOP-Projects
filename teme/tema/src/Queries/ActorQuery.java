package Queries;

import Database.Database;
import Entities.Video;
import actor.Actor;
import common.Constants;
import fileio.ActionInputData;
import utils.ActionResponse;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActorQuery {

    public ActionResponse SolveQuery(ActionInputData action){
        ActionResponse actionResponse = new ActionResponse();
        actionResponse.setId(action.getActionId());
        switch(action.getCriteria()){
            case Constants.AVERAGE:
                List<Actor> sortedActorList = Database.GetInstance().actors;
                sortedActorList = SortActorListByAverage(sortedActorList, action);
                if(sortedActorList == null){
                    actionResponse.setResponse(Constants.NO_QUERY);
                }
                else{
                    int index = 1;
                    List<Actor> responseList = new ArrayList<>();
                    for (Actor a : sortedActorList) {
                        double ratingOverall = 0;
                        for (Video v : a.filmography) {
                            ratingOverall += v.GetRating();
                        }
                        if (ratingOverall != 0) {
                            if (index <= action.getNumber()) {
                                responseList.add(a);
                            }
                            index++;
                        }
                    }
                    actionResponse.setResponse(actionResponse.OutputActorsQuery(action, responseList));
                }
                break;
        }

        return actionResponse;
    }

    List<Actor> SortActorListByAverage(List<Actor> actors, ActionInputData action){
        actors.sort(new SortActorsByName());
        actors.sort(new SortActorsByRating());
        if(action.getSortType().equals(Constants.DESC)){
            Collections.reverse(actors);
        }
        return actors;
    }
}

class SortActorsByName implements Comparator<Actor> {
    @Override
    public int compare(Actor a1, Actor a2) {
        return a1.getName().compareTo(a2.getName());
    }
}

class SortActorsByRating implements Comparator<Actor>{
    @Override
    public int compare(Actor a1, Actor a2) {
        double ratingOverall1 = 0;
        for (Video v : a1.filmography) {
            ratingOverall1 += v.GetRating();
        }
        ratingOverall1 /= a1.filmography.size();

        double ratingOverall2 = 0;
        for (Video v : a2.filmography) {
            ratingOverall2 += v.GetRating();
        }
        ratingOverall2 /= a2.filmography.size();

        return Double.compare(ratingOverall1, ratingOverall2);
    }
}
