package Entities;

import entertainment.Season;

import java.util.ArrayList;
import java.util.List;

public class Show extends Video {
    List<Season> seasons = new ArrayList<Season>();

    public void AddRating(double rating, int season){
        seasons.get(season).getRatings().add(rating);
        double average = 0;
        for(Season s : seasons){
            double average2 = 0;
            for(Double d : s.getRatings()){
                average2 += d;
            }
            average2 /= s.getRatings().size();
            average += average2;
        }
        average /= seasons.size();
        SetRating(average);
    }
}
