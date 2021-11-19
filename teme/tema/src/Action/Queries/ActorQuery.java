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

public class ActorQuery extends Query {

    public ActionResponse SolveQuery(ActionInputData action){
        ActionResponse actionResponse = new ActionResponse();
        actionResponse.setId(action.getActionId());
        List<Actor> sortedActorList = new ArrayList<Actor>();
        for(Actor a : Database.GetInstance().actors){
            sortedActorList.add(a);
        }
        List<Actor> truncatedList = new ArrayList<Actor>();
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
                        for (String v : a.GetFilmography()) {
                            Video video = Database.GetInstance().movies.stream().filter(m -> m.GetTitle().equals(v)).findAny().orElse(null);
                            if(video == null){
                                video = Database.GetInstance().shows.stream().filter(m -> m.GetTitle().equals(v)).findAny().orElse(null);
                            }
                            ratingOverall += video.GetRating();
                        }
                        if (ratingOverall != 0) {
                            if (index <= action.getNumber()) {
                                responseList.add(a);
                            }
                            index++;
                        }
                    }
                }
                break;
            case Constants.AWARDS:
                sortedActorList = SortActorListByAwards(sortedActorList, action);
                break;
            case Constants.FILTER_DESCRIPTIONS:
                sortedActorList = SortActorListByFilters(sortedActorList, action);
                break;
        }
        if(sortedActorList.size() == 0){
            actionResponse.setResponse(null);
        }
        else{
            if(action.getCriteria().equals(Constants.AVERAGE)) {
                for (int i = 0; i < Math.min(action.getNumber(), sortedActorList.size()); i++) {
                    truncatedList.add(sortedActorList.get(i));
                }
                actionResponse.setResponse(actionResponse.OutputActorsQuery(action, truncatedList));
            }
            else {
                actionResponse.setResponse(actionResponse.OutputActorsQuery(action, sortedActorList));
            }
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
        for (String v : a1.GetFilmography()) {
            Video video = Database.GetInstance().movies.stream().filter(m -> m.GetTitle().equals(v)).findAny().orElse(null);
            if(video == null){
                video = Database.GetInstance().shows.stream().filter(m -> m.GetTitle().equals(v)).findAny().orElse(null);
            }
            ratingOverall1 += video.GetRating();
        }
        ratingOverall1 /= a1.GetFilmography().size();

        double ratingOverall2 = 0;
        for (String v : a2.GetFilmography()) {
            Video video = Database.GetInstance().movies.stream().filter(m -> m.GetTitle().equals(v)).findAny().orElse(null);
            if(video == null){
                video = Database.GetInstance().shows.stream().filter(m -> m.GetTitle().equals(v)).findAny().orElse(null);
            }
            ratingOverall2 += video.GetRating();
        }
        ratingOverall2 /= a2.GetFilmography().size();

        return Double.compare(ratingOverall1, ratingOverall2);
    }
}

class SortActorsByAwards implements Comparator<Actor>{
    @Override
    public int compare(Actor a1, Actor a2){
        return Integer.compare(a1.getAwards().size(), a2.getAwards().size());
    }
}