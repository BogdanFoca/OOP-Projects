package Action.Queries;

import Database.Database;
import Entities.Movie;
import Entities.Show;
import Entities.User;
import Entities.Video;
import common.Constants;
import fileio.ActionInputData;
import utils.ActionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VideoQuery {
    public ActionResponse SolveQuery(ActionInputData action){
        ActionResponse response = new ActionResponse();
        response.setId(action.getActionId());
        List<Video> sortedVideoList = new ArrayList<Video>();
        for(Movie m : Database.GetInstance().movies){
            sortedVideoList.add(m);
        }
        for(Show s : Database.GetInstance().shows){
            sortedVideoList.add(s);
        }
        switch(action.getCriteria()){
            case Constants.RATINGS:
                sortedVideoList = SortVideoListByRating(sortedVideoList, action);
                break;
            case Constants.FAVORITE_MOVIES:
                sortedVideoList = SortVideoListByFavorite(sortedVideoList, action);
                break;
            case Constants.LONGEST:
                sortedVideoList = SortVideoListByDuration(sortedVideoList, action);
                break;
            case Constants.MOST_VIEWED:
                sortedVideoList = SortVideoListByViews(sortedVideoList, action);
                break;
        }

        return response;
    }

    List<Video> SortVideoListByRating(List<Video> videoList, ActionInputData action){
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoByLongest());
        if(action.getSortType().equals(Constants.DESC)){
            Collections.reverse(sortedList);
        }
        return sortedList;
    }

    List<Video> SortVideoListByDuration(List<Video> videoList, ActionInputData action){
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoByRatings());
        if(action.getSortType().equals(Constants.DESC)){
            Collections.reverse(sortedList);
        }
        return sortedList;
    }

    List<Video> SortVideoListByFavorite(List<Video> videoList, ActionInputData action){
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoByFavorite());
        if(action.getSortType().equals(Constants.DESC)){
            Collections.reverse(sortedList);
        }
        return sortedList;
    }

    List<Video> SortVideoListByViews(List<Video> videoList, ActionInputData action){
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoByViews());
        if(action.getSortType().equals(Constants.DESC)){
            Collections.reverse(sortedList);
        }
        return sortedList;
    }
}

class SortVideoByRatings implements Comparator<Video>{
    @Override
    public int compare(Video v1, Video v2){
        return Double.compare(v1.GetRating(), v2.GetRating());
    }
}

class SortVideoByLongest implements Comparator<Video>{
    @Override
    public int compare(Video v1, Video v2){
        return Integer.compare(v1.GetDuration(), v2.GetDuration());
    }
}

class SortVideoByFavorite implements Comparator<Video>{
    @Override
    public int compare(Video v1, Video v2){
        int f1 = 0;
        int f2 = 0;
        for(User u : Database.GetInstance().users){
            if(u.GetFavoriteVideos().contains(v1.GetTitle())){
                f1++;
            }
            if(u.GetFavoriteVideos().contains(v2.GetTitle())){
                f2++;
            }
        }
        return Integer.compare(f1, f2);
    }
}

class SortVideoByViews implements Comparator<Video>{
    @Override
    public int compare(Video v1, Video v2){
        int f1 = 0;
        int f2 = 0;
        for(User u : Database.GetInstance().users){
            if(u.GetWatchedVideos().containsKey(v1.GetTitle())){
                f1++;
            }
            if(u.GetWatchedVideos().containsKey(v2.GetTitle())){
                f2++;
            }
        }
        return Integer.compare(f1, f2);
    }
}
