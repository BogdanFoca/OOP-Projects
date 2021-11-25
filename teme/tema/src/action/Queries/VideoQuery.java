package action.Queries;

import database.Database;
import entities.Movie;
import entities.Show;
import entities.User;
import entities.Video;
import common.Constants;
import fileio.ActionInputData;
import utils.ActionResponse;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class VideoQuery extends Query {
    public VideoQuery() {

    }

    /**
     *
     * @param action
     * @return
     */
    public static ActionResponse solveQuery(final ActionInputData action) {
        ActionResponse response = new ActionResponse();
        response.setId(action.getActionId());
        List<Video> sortedMovieList = new ArrayList<Video>();
        List<Video> sortedShowList = new ArrayList<Video>();
        List<Video> truncatedVideoList = new ArrayList<Video>();
        for (Movie m : Database.getInstance().getMovies()) {
            sortedMovieList.add(m);
        }
        for (Show s : Database.getInstance().getShows()) {
            sortedShowList.add(s);
        }
        List<Video> sortedVideoList = new ArrayList<Video>();
        switch (action.getObjectType()) {
            case Constants.MOVIES:
                sortedVideoList = sortedMovieList;
                break;
            case Constants.SHOWS:
                sortedVideoList = sortedShowList;
                break;
            default:
                break;
        }
        sortedVideoList.sort(new SortVideoAlphabetically());
        switch (action.getCriteria()) {
            case Constants.RATINGS:
                sortedVideoList = sortVideoListByRating(sortedVideoList, action);
                if (action.getFilters().get(0).get(0) != null) {
                    sortedVideoList = sortedVideoList.stream()
                            .filter(v -> action.getFilters().get(0)
                                    .contains(Integer.toString(v.getReleaseYear())))
                            .collect(Collectors.toList());
                }
                if (action.getFilters().get(1).get(0) != null) {
                    for (int i = sortedVideoList.size() - 1; i >= 0; i--) {
                        for (String g : action.getFilters().get(1)) {
                            if (!sortedVideoList.get(i).getGenres()
                                    .contains(Utils.stringToGenre(g))) {
                                sortedVideoList.remove(sortedVideoList.get(i));
                            }
                        }
                    }
                }
                for (int i = sortedVideoList.size() - 1; i >= 0; i--) {
                    if (sortedVideoList.get(i).getRating() == 0) {
                        sortedVideoList.remove(i);
                    }
                }
                break;
            case Constants.FAVORITE:
                sortedVideoList = sortVideoListByFavorite(sortedVideoList, action);
                if (action.getFilters().get(0).get(0) != null) {
                    sortedVideoList = sortedVideoList.stream()
                            .filter(v -> action.getFilters().get(0)
                                    .contains(Integer.toString(v.getReleaseYear())))
                            .collect(Collectors.toList());
                }
                if (action.getFilters().get(1).get(0) != null) {
                    for (int i = sortedVideoList.size() - 1; i >= 0; i--) {
                        for (String g : action.getFilters().get(1)) {
                            if (!sortedVideoList.get(i).getGenres()
                                    .contains(Utils.stringToGenre(g))) {
                                sortedVideoList.remove(sortedVideoList.get(i));
                            }
                        }
                    }
                }
                sortedVideoList = sortedVideoList.stream()
                        .filter(v -> v.getFavoriteCount() != 0).collect(Collectors.toList());
                break;
            case Constants.LONGEST:
                sortedVideoList = sortVideoListByDuration(sortedVideoList, action);
                if (action.getFilters().get(0).get(0) != null) {
                    sortedVideoList = sortedVideoList.stream()
                            .filter(v -> action.getFilters().get(0)
                                    .contains(Integer.toString(v.getReleaseYear())))
                            .collect(Collectors.toList());
                }
                if (action.getFilters().get(1).get(0) != null) {
                    for (int i = sortedVideoList.size() - 1; i >= 0; i--) {
                        for (String g : action.getFilters().get(1)) {
                            if (!sortedVideoList.get(i).getGenres()
                                    .contains(Utils.stringToGenre(g))) {
                                sortedVideoList.remove(sortedVideoList.get(i));
                            }
                        }
                    }
                }
                break;
            case Constants.MOST_VIEWED:
                sortedVideoList = sortVideoListByViews(sortedVideoList, action);
                if (action.getFilters().get(0).get(0) != null) {
                    sortedVideoList = sortedVideoList.stream()
                            .filter(v -> action.getFilters().get(0)
                                    .contains(Integer.toString(v.getReleaseYear())))
                            .collect(Collectors.toList());
                }
                if (action.getFilters().get(1).get(0) != null) {
                    for (int i = sortedVideoList.size() - 1; i >= 0; i--) {
                        for (String g : action.getFilters().get(1)) {
                            if (!sortedVideoList.get(i).getGenres()
                                    .contains(Utils.stringToGenre(g))) {
                                sortedVideoList.remove(sortedVideoList.get(i));
                            }
                        }
                    }
                }
                sortedVideoList = sortedVideoList.stream()
                        .filter(v -> v.getViews() != 0).collect(Collectors.toList());
                break;
            default:
                break;
        }
        for (int i = 0; i < Math.min(action.getNumber(), sortedVideoList.size()); i++) {
            truncatedVideoList.add(sortedVideoList.get(i));
        }
        response.setResponse(response.outputVideosQuery(action, truncatedVideoList));
        return response;
    }

    static List<Video> sortVideoListByRating(
            final List<Video> videoList, final ActionInputData action) {
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoByRatings());
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(sortedList);
        }
        return sortedList;
    }

    static List<Video> sortVideoListByDuration(
            final List<Video> videoList, final ActionInputData action) {
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoByLongest());
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(sortedList);
        }
        return sortedList;
    }

    static List<Video> sortVideoListByFavorite(
            final List<Video> videoList, final ActionInputData action) {
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoByFavorite());
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(sortedList);
        }
        return sortedList;
    }

    static List<Video> sortVideoListByViews(
            final List<Video> videoList, final ActionInputData action) {
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoByViews());
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(sortedList);
        }
        return sortedList;
    }

    static class SortVideoByRatings implements Comparator<Video> {
        @Override
        public int compare(final Video v1, final Video v2) {
            return Double.compare(v1.getRating(), v2.getRating());
        }
    }

    static class SortVideoByLongest implements Comparator<Video> {
        @Override
        public int compare(final Video v1, final Video v2) {
            return Integer.compare(v1.getDuration(), v2.getDuration());
        }
    }

    static class SortVideoByFavorite implements Comparator<Video> {
        @Override
        public int compare(final Video v1, final Video v2) {
            int f1 = 0;
            int f2 = 0;
            for (User u : Database.getInstance().getUsers()) {
                if (u.getFavoriteVideos().contains(v1.getTitle())) {
                    f1++;
                }
                if (u.getFavoriteVideos().contains(v2.getTitle())) {
                    f2++;
                }
            }
            return Integer.compare(f1, f2);
        }
    }

    static class SortVideoByViews implements Comparator<Video> {
        @Override
        public int compare(final Video v1, final Video v2) {
            return Integer.compare(v1.getViews(), v2.getViews());
        }
    }
    static class SortVideoAlphabetically implements Comparator<Video> {
        @Override
        public int compare(final Video v1, final Video v2) {
            return v1.getTitle().compareTo(v2.getTitle());
        }
    }
}

