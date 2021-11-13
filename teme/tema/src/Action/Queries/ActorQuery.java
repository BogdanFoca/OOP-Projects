package Action.Queries;

import Database.Database;
import Entities.Video;
import actor.Actor;
import common.Constants;
import fileio.ActionInputData;
import fileio.ActorInputData;
import utils.ActionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActorQuery {

    public ActionResponse SolveQuery(ActionInputData action){
        ActionResponse actionResponse = new ActionResponse();
        actionResponse.setId(action.getActionId());
        List<Actor> sortedActorList = Database.GetInstance().actors;
        switch(action.getCriteria()){
            case Constants.AVERAGE:
                sortedActorList = SortActorListByAverage(sortedActorList, action);
                if(sortedActorList.size() == 0){
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
            case Constants.AWARDS:
                sortedActorList = SortActorListByAwards(sortedActorList, action);
                if(sortedActorList.size() == 0){
                    actionResponse.setResponse(null);
                }
                else{
                    actionResponse.setResponse(actionResponse.OutputActorsQuery(action, sortedActorList));
                }
                break;
            case Constants.FILTER_DESCRIPTIONS:
                sortedActorList = SortActorListByFilters(sortedActorList, action);
                if(sortedActorList.size() == 0){
                    actionResponse.setResponse(null);
                }
                else{
                    actionResponse.setResponse(actionResponse.OutputActorsQuery(action, sortedActorList));
                }
                break;
        }

        return actionResponse;
    }

    List<Actor> SortActorListByAverage(List<Actor> actors, ActionInputData action){
        List<Actor> sorted = new ArrayList<Actor>(actors);
        sorted.sort(new SortActorsByName());
        sorted.sort(new SortActorsByRating());
        if(action.getSortType().equals(Constants.DESC)){
            Collections.reverse(sorted);
        }
        return sorted;
    }

    List<Actor> SortActorListByAwards(List<Actor> actors, ActionInputData action){
        List<Actor> filtered = new ArrayList<Actor>(actors);
        for(String award : action.getFilters().get(0)) {
            filtered.removeIf(actor -> !actor.getAwards().containsKey(award));
        }
        filtered.sort(new SortActorsByAwards());
        if(action.getSortType().equals(Constants.DESC)){
            Collections.reverse(filtered);
        }
        return filtered;
    }

    List<Actor> SortActorListByFilters(List<Actor> actors, ActionInputData action){
        List<Actor> filtered = new ArrayList<Actor>(actors);
        for(String word : action.getFilters().get(3)){
            for(Actor a : filtered){
                if(a.getCareerDescription().contains(word)){
                    filtered.remove(a);
                }
            }
        }
        return filtered;
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

class SortActorsByAwards implements Comparator<Actor>{
    @Override
    public int compare(Actor a1, Actor a2){
        return Integer.compare(a1.getAwards().size(), a2.getAwards().size());
    }
}
