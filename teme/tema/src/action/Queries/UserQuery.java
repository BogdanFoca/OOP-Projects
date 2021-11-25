package action.Queries;

import common.Constants;
import database.Database;
import entities.User;
import fileio.ActionInputData;
import utils.ActionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class UserQuery extends Query {
    private UserQuery() {

    }

    /**
     *
     * @param action
     * @return
     */
    public static ActionResponse solveQuery(final ActionInputData action) {
        ActionResponse response = new ActionResponse();
        response.setId(action.getActionId());
        List<User> sortedList = new ArrayList<User>();
        List<User> truncatedList = new ArrayList<User>();
        for (User u : Database.getInstance().getUsers()) {
            sortedList.add(u);
        }
        switch (action.getCriteria()) {
            case Constants.NUM_RATINGS:
                sortedList.sort(new SortUserAlphabetically());
                sortedList = sortUserListByNumberOfReviews(action, sortedList);
                break;
            default:
                break;
        }
        sortedList = sortedList.stream()
                .filter(u -> u.getNumberOfReviews() > 0).collect(Collectors.toList());
        for (int i = 0; i < Math.min(action.getNumber(), sortedList.size()); i++) {
            truncatedList.add(sortedList.get(i));
        }
        response.setResponse(response.outputUsersQuery(action, truncatedList));
        return response;
    }

    static List<User> sortUserListByNumberOfReviews(
            final ActionInputData action, final List<User> userList) {
        List<User> sortedList = new ArrayList<User>(userList);
        sortedList.sort(new SortUserByNumberOfRatings());
        if (action.getSortType().equals(Constants.DESC)) {
            Collections.reverse(sortedList);
        }
        return sortedList;
    }
    static class SortUserByNumberOfRatings implements Comparator<User> {
        @Override
        public int compare(final User u1, final User u2) {
            return Integer.compare(u1.getNumberOfReviews(), u2.getNumberOfReviews());
        }
    }
    static class SortUserAlphabetically implements Comparator<User> {
        @Override
        public int compare(final User u1, final User u2) {
            return u1.getUsername().compareTo(u2.getUsername());
        }
    }
}


