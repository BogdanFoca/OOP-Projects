package Action.Recommandations;

import Entities.Video;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Recommendation {
    public static List<Video> sortVideoListByRating(List<Video> videoList) {
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoByRatings());
        return sortedList;
    }
    static class SortVideoByRatings implements Comparator<Video> {
        @Override
        public int compare(Video v1, Video v2) {
            return Double.compare(v1.getRating(), v2.getRating());
        }
    }
    public static List<Video> sortVideoListAlphabetically(List<Video> videoList){
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoAlphabetically());
        return sortedList;
    }
    static class SortVideoAlphabetically implements Comparator<Video> {
        @Override
        public int compare(Video v1, Video v2) {
            return v1.getTitle().compareTo(v2.getTitle());
        }
    }
}
