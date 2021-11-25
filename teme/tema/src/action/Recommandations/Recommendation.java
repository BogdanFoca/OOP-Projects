package action.Recommandations;

import entities.Video;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Recommendation {
    protected Recommendation() {

    }
    /**
     *
     * @param videoList
     * @return
     */
    public static List<Video> sortVideoListByRating(
            final List<Video> videoList) {
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoByRatings());
        return sortedList;
    }
    static class SortVideoByRatings implements Comparator<Video> {
        @Override
        public int compare(final Video v1, final Video v2) {
            return Double.compare(v1.getRating(), v2.getRating());
        }
    }

    /**
     *
     * @param videoList
     * @return
     */
    public static List<Video> sortVideoListAlphabetically(
            final List<Video> videoList) {
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoAlphabetically());
        return sortedList;
    }
    static class SortVideoAlphabetically implements Comparator<Video> {
        @Override
        public int compare(final Video v1, final Video v2) {
            return v1.getTitle().compareTo(v2.getTitle());
        }
    }
}
