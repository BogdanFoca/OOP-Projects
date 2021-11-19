package Action.Command;

import common.Constants;
import Entities.User;
import fileio.ActionInputData;
import utils.ActionResponse;

public class Command {
    public ActionResponse SolveCommands(ActionInputData action, User actionUser){
        ActionResponse response = new ActionResponse();
        switch(action.getType()){
            case Constants.FAVORITE_MOVIES:
                actionUser.AddToFavorite(action.getTitle());
                break;
            case Constants.VIEW_MOVIES:
                actionUser.WatchVideo(action.getTitle());
                break;
            case Constants.REVIEWS:
                if(action.getSeasonNumber() != 0) {
                    actionUser.RateVideo(action.getTitle(), action.getSeasonNumber(), action.getGrade());
                }
                else{
                    actionUser.RateVideo(action.getTitle(), action.getGrade());
                }
                break;
        }
        return response;
    }
}
