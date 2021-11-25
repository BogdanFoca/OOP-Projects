package action.Queries;

import database.Database;
import entities.Video;
import actor.Actor;
import actor.ActorsAwards;
import common.Constants;
import fileio.ActionInputData;
import utils.ActionResponse;
import utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ActorQuery extends Query {
    private ActorQuery() {

    }

    /**
     *
     * @param action
     * @return
     */
    public static ActionResponse solveQuery(final ActionInputData action) {
        ActionResponse actionResponse = new ActionResponse();
        actionResponse.setId(action.getActionId());
        List<Actor> sortedActorList = new ArrayList<>();
        for (Actor a : Database.getInstance().getActors()) {
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
                        Video video = Database.getInstance().getMovies().stream()
                                .filter(m -> m.getTitle().equals(v)).findAny().orElse(null);
                        if (video == null) {
                            video = Database.getInstance().getShows().stream()
                                    .filter(m -> m.getTitle().equals(v)).findAny().orElse(null);
                        }
                        if (video != null && video.getRating() > 0) {
                            ratingOverall += video.getRating();
                            size++;
                        }
                    }
                    if (size > 0) {
                        ratingOverall /= size;
                    } else {
                        ratingOverall = 0;
                    }
                    if (ratingOverall > 0) {
                        responseList.add(a);
                    }
                }
                sortedActorList = responseList;
                break;
            case Constants.AWARDS:
                sortedActorList = sortActorListByAwards(sortedActorList, action);
                break;
            case Constants.FILTER_DESCRIPTIONS:
                sortedActorList = sortActorListByFilters(sortedActorList, action);
                break;
            default:
                break;
        }
        if (action.getCriteria().equals(Constants.AVERAGE)) {
            for (int i = 0; i < Math.min(action.getNumber(), sortedActorList.size()); i++) {
                truncatedList.add(sortedActorList.get(i));
            }
            actionResponse.setResponse(actionResponse.outputActorsQuery(action, truncatedList));
        } else {
            actionResponse.setResponse(actionResponse.outputActorsQuery(action, sortedActorList));
        }

        return actionResponse;
    }

    static List<Actor> sortActorListByAverage(
            final List<Actor> actors, final ActionInputData action) {
        List<Actor> sorted = new ArrayList<Actor>(actors);
        sorted.sort(new SortActorsByName());
        sorted.sort(new SortActorsByRating());
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(sorted);
        }
        return sorted;
    }

    static List<Actor> sortActorListByAwards(
            final List<Actor> actors, final ActionInputData action) {
        List<Actor> filtered = new ArrayList<Actor>(actors);
        for (String award : action.getFilters().get(Constants.NUMBER_3)) {
            filtered.removeIf(actor -> !actor.getAwards().containsKey(Utils.stringToAwards(award)));
        }
        filtered.sort(new SortActorsByAwards());
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(filtered);
        }
        return filtered;
    }
    static boolean containsWord(final String mainString, final String word) {

        Pattern pattern = Pattern.compile(
                "\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(mainString);
        return matcher.find();
    }
    static List<Actor> sortActorListByFilters(
            final List<Actor> actors, final ActionInputData action) {
        List<Actor> filtered = new ArrayList<Actor>(actors);
        for (String word : action.getFilters().get(2)) {
            filtered = filtered.stream()
                    .filter(a -> containsWord(a.getCareerDescription().toLowerCase(Locale.ROOT),
                            word.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(filtered);
        }
        return filtered;
    }
    static class SortActorsByName implements Comparator<Actor> {
        @Override
        public int compare(final Actor a1, final Actor a2) {
            return a1.getName().compareTo(a2.getName());
        }
    }

    static class SortActorsByRating implements Comparator<Actor> {
        @Override
        public int compare(final Actor a1, final Actor a2) {
            double ratingOverall1 = 0;
            int size = 0;
            for (String v : a1.getFilmography()) {
                Video video = Database.getInstance().getMovies().stream()
                        .filter(m -> m.getTitle().equals(v)).findAny().orElse(null);
                if (video == null) {
                    video = Database.getInstance().getShows().stream()
                            .filter(m -> m.getTitle().equals(v)).findAny().orElse(null);
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
                Video video = Database.getInstance().getMovies().stream()
                        .filter(m -> m.getTitle().equals(v)).findAny().orElse(null);
                if (video == null) {
                    video = Database.getInstance().getShows().stream()
                            .filter(m -> m.getTitle().equals(v)).findAny().orElse(null);
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
        public int compare(final Actor a1, final Actor a2) {
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
