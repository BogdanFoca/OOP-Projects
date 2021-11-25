package Action.Queries;

import Database.Database;
import Entities.Video;
import actor.Actor;
import actor.ActorsAwards;
import common.Constants;
import fileio.ActionInputData;
import utils.ActionResponse;
import utils.Utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ActorQuery extends Query {

    public static ActionResponse solveQuery(ActionInputData action) {
        ActionResponse actionResponse = new ActionResponse();
        actionResponse.setId(action.getActionId());
        List<Actor> sortedActorList = new ArrayList<Actor>();
        for (Actor a : Database.getInstance().actors) {
            sortedActorList.add(a);
        }
        sortedActorList.sort(new SortActorsByName());
        List<Actor> truncatedList = new ArrayList<Actor>();
        switch (action.getCriteria()) {
            case Constants.AVERAGE:
                sortedActorList = sortActorListByAverage(sortedActorList, action);
                List<Actor> responseList = new ArrayList<>();
                for (Actor a : sortedActorList) {
                    double ratingOverall = 0;
                    int size = 0;
                    for (String v : a.getFilmography()) {
                        Video video = Database.getInstance().movies.stream().filter(m -> m.getTitle().equals(v)).findAny().orElse(null);
                        if (video == null) {
                            video = Database.getInstance().shows.stream().filter(m -> m.getTitle().equals(v)).findAny().orElse(null);
                        }
                        if (video != null && video.getRating() > 0) {
                            //System.out.println(video.getRating());
                            ratingOverall += video.getRating();
                            size++;
                        }
                    }
                    if (size > 0) {
                        ratingOverall /= size;
                    }
                    else {
                        ratingOverall = 0;
                    }
                    if (ratingOverall > 0) {
                        responseList.add(a);
                    }
                }
                sortedActorList = responseList;
                break;
            case Constants.AWARDS:
                sortedActorList = SortActorListByAwards(sortedActorList, action);
                break;
            case Constants.FILTER_DESCRIPTIONS:
                sortedActorList = sortActorListByFilters(sortedActorList, action);
                break;
        }
        if (action.getCriteria().equals(Constants.AVERAGE)) {
            for (int i = 0; i < Math.min(action.getNumber(), sortedActorList.size()); i++) {
                truncatedList.add(sortedActorList.get(i));
            }
            actionResponse.setResponse(actionResponse.outputActorsQuery(action, truncatedList));
        }
        else {
            actionResponse.setResponse(actionResponse.outputActorsQuery(action, sortedActorList));
        }

        return actionResponse;
    }

    static List<Actor> sortActorListByAverage(List<Actor> actors, ActionInputData action) {
        List<Actor> sorted = new ArrayList<Actor>(actors);
        sorted.sort(new SortActorsByName());
        sorted.sort(new SortActorsByRating());
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(sorted);
        }
        return sorted;
    }

    static List<Actor> SortActorListByAwards(List<Actor> actors, ActionInputData action) {
        List<Actor> filtered = new ArrayList<Actor>(actors);
        for (String award : action.getFilters().get(3)) {
            filtered.removeIf(actor -> !actor.getAwards().containsKey(Utils.stringToAwards(award)));
        }
        filtered.sort(new SortActorsByAwards());
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(filtered);
        }
        return filtered;
    }
    static boolean containsWord(String mainString, String word) {

        Pattern pattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE); // "\\b" represents any word boundary.
        Matcher matcher = pattern.matcher(mainString);
        return matcher.find();
    }
    static List<Actor> sortActorListByFilters(List<Actor> actors, ActionInputData action) {
        List<Actor> filtered = new ArrayList<Actor>(actors);
        for (String word : action.getFilters().get(2)) {
            filtered = filtered.stream()
                    .filter(a -> containsWord(a.getCareerDescription().toLowerCase(Locale.ROOT), word.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        //filtered.sort(new SortActorsAlphabetically());
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(filtered);
        }
        return filtered;
    }
    static class SortActorsByName implements Comparator<Actor> {
        @Override
        public int compare(Actor a1, Actor a2) {
            return a1.getName().compareTo(a2.getName());
        }
    }

    static class SortActorsByRating implements Comparator<Actor> {
        @Override
        public int compare(Actor a1, Actor a2) {
            double ratingOverall1 = 0;
            int size = 0;
            for (String v : a1.getFilmography()) {
                Video video = Database.getInstance().movies.stream().filter(m -> m.getTitle().equals(v)).findAny().orElse(null);
                if (video == null) {
                    video = Database.getInstance().shows.stream().filter(m -> m.getTitle().equals(v)).findAny().orElse(null);
                }
                if (video != null) {
                    if (video.getRating() != 0) {
                        ratingOverall1 += video.getRating();
                        size++;
                    }
                }
            }
            ratingOverall1 /= size;

            double ratingOverall2 = 0;
            int size2 = 0;
            for (String v : a2.getFilmography()) {
                Video video = Database.getInstance().movies.stream().filter(m -> m.getTitle().equals(v)).findAny().orElse(null);
                if (video == null) {
                    video = Database.getInstance().shows.stream().filter(m -> m.getTitle().equals(v)).findAny().orElse(null);
                }
                if (video != null) {
                    if (video.getRating() != 0) {
                        ratingOverall2 += video.getRating();
                        size2++;
                    }
                }
            }
            ratingOverall2 /= size2;

            return Double.compare(ratingOverall1, ratingOverall2);
        }
    }

    static class SortActorsByAwards implements Comparator<Actor> {
        @Override
        public int compare(Actor a1, Actor a2) {
            int size1 = 0;
            for (Map.Entry<ActorsAwards, Integer> entry : a1.getAwards().entrySet()) {
                size1 += entry.getValue();
            }
            int size2 = 0;
            for (Map.Entry<ActorsAwards, Integer> entry : a2.getAwards().entrySet()) {
                size2 += entry.getValue();
            }
            return Integer.compare(size1, size2);
        }
    }
}
