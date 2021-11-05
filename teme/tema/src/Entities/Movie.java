package Entities;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Video {
    int duration;

    List<Double> ratings = new ArrayList<Double>();

    public void AddRating(double rating){
        ratings.add(rating);
        double average=0;
        for(Double d : ratings){
            average += d;
        }
        average = average / ratings.size();
        SetRating(average);
    }
}
