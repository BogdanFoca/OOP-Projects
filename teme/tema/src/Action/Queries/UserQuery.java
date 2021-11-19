package Action.Queries;

import Entities.Video;
import common.Constants;
import Database.Database;
import Entities.User;
import fileio.ActionInputData;
import utils.ActionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserQuery extends Query {
    public ActionResponse SolveQuery(ActionInputData action) {
        ActionResponse response = new ActionResponse();
        response.setId(action.getActionId());
        List<User> sortedList = new ArrayList<User>();
        List<User> truncatedList = new ArrayList<User>();
        for(User u : Database.GetInstance().users){
            sortedList.add(u);
        }
        switch(action.getCriteria()){
            case Constants.NUM_RATINGS:
                sortedList = SortUserListByNumberOfReviews(action, sortedList);
                break;
        }
        if(sortedList.size() == 0){
            response.setResponse(null);
        }
        else{
            for (int i = 0; i < Math.min(action.getNumber(), sortedList.size()); i++) {
                truncatedList.add(sortedList.get(i));
            }
            response.setResponse(response.OutputUsersQuery(action, truncatedList));
        }
        return response;
    }

    List<User> SortUserListByNumberOfReviews(ActionInputData action, List<User> userList){
        List<User> sortedList = new ArrayList<User>(userList);
        sortedList.sort(new SortUserByNumberOfRatings());
        if(action.getSortType().equals(Constants.DESC)){
            Collections.reverse(sortedList);
        }
        return sortedList;
    }
}

class SortUserByNumberOfRatings implements Comparator<User>{
    @Override
    public int compare(User u1, User u2){
        return Integer.compare(u1.GetNumberOfReviews(), u2.GetNumberOfReviews());
    }
}
