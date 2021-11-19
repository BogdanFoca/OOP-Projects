package Entities;

import entertainment.Genre;
import entertainment.Season;

import java.util.ArrayList;
import java.util.List;

public class Show extends Video {
    private int numberOfSeasons;
    private List<Season> seasons = new ArrayList<Season>();

    public List<Season> GetSeasons(){
        return seasons;
    }

    public Show(String title, int releaseYear, List<String> genres, List<String> cast, int numberOfSeasons, List<Season> seasons){
        this.title = title;
        this.releaseYear = releaseYear;
        for(String genre : genres) {
            this.genres.add(Genre.valueOf(genre));
        }
        this.cast = new ArrayList<String>(cast);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = new ArrayList<Season>(seasons);
    }

    public int GetNumberOfSeasons(){
        return numberOfSeasons;
    }

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

    @Override
    public int GetDuration(){
        int sum = 0;
        for(Season s : seasons){
            sum += s.getDuration();
        }
        return sum;
    }
}
