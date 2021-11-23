package Action.Recommandations;

import Entities.Video;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Recommendation {
    public List<Video> SortVideoListByRating(List<Video> videoList){
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoByRatings());
        return sortedList;
    }
    class SortVideoByRatings implements Comparator<Video> {
        @Override
        public int compare(Video v1, Video v2){
            return Double.compare(v1.GetRating(), v2.GetRating());
        }
    }
    public List<Video> SortVideoListAlphabetically(List<Video> videoList){
        List<Video> sortedList = new ArrayList<Video>(videoList);
        sortedList.sort(new SortVideoAlphabetically());
        return sortedList;
    }
    class SortVideoAlphabetically implements Comparator<Video>{
        @Override
        public int compare(Video v1, Video v2){
            return Character.compare(v1.GetTitle().charAt(0), v2.GetTitle().charAt(0));
        }
    }
}
