package Action.Recommandations;

import Database.Database;
import Entities.Movie;
import Entities.Show;
import Entities.User;
import Entities.Video;
import common.Constants;
import fileio.ActionInputData;
import utils.ActionResponse;

import java.util.ArrayList;
import java.util.List;

public class StandardRecommendation extends Recommendation {
    public ActionResponse SolveQuery(ActionInputData action, User user) {
        ActionResponse actionResponse = new ActionResponse();
        List<Video> recommendedVideos = new ArrayList<Video>();
        switch(action.getType()){
            case Constants.STANDARD:
                Video v = Database.GetInstance().movies.stream().filter(m -> !user.GetWatchedVideos().containsKey(m.GetTitle())).findFirst().orElse(null);
                if(v==null){
                    Database.GetInstance().shows.stream().filter(m -> !user.GetWatchedVideos().containsKey(m.GetTitle())).findFirst().orElse(null);
                }
                if(v!=null){
                    recommendedVideos.add(v);
                }
                break;
            case Constants.BEST_UNSEEN:
                List<Video> sortedList = new ArrayList<Video>();
                for(Movie m : Database.GetInstance().movies){
                    sortedList.add(m);
                }
                for(Show s : Database.GetInstance().shows){
                    sortedList.add(s);
                }
                sortedList = SortVideoListByRating(sortedList);
                Video v2 = sortedList.stream().filter(m -> !user.GetWatchedVideos().containsKey(m.GetTitle())).findFirst().orElse(null);
                if(v2 == null){
                    sortedList.stream().filter(m -> !user.GetWatchedVideos().containsKey(m.GetTitle())).findFirst().orElse(null);
                }
                if(v2 != null){
                    recommendedVideos.add(v2);
                }
                break;
        }
        if(recommendedVideos.size() == 0){
            actionResponse.setResponse(null);
        }
        else {
            actionResponse.setResponse(actionResponse.OutputVideosQuery(action, recommendedVideos));
        }

        return actionResponse;
    }
}

