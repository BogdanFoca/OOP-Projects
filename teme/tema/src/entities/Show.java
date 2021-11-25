package entities;

import entertainment.Season;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public final class Show extends Video {
    private final int numberOfSeasons;
    private List<Season> seasons = new ArrayList<Season>();

    public List<Season> getSeasons() {
        return seasons;
    }

    public Show(final String title, final int releaseYear, final List<String> genres,
                final List<String> cast, final int numberOfSeasons, final List<Season> seasons) {
        this.title = title;
        this.releaseYear = releaseYear;
        for (String genre : genres) {
            this.genres.add(Utils.stringToGenre(genre));
        }
        this.cast = new ArrayList<String>(cast);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = new ArrayList<Season>(seasons);
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    /**
     *
     * @param rating
     * @param season
     */
    public void addRating(final double rating, final int season) {
        seasons.get(season - 1).getRatings().add(rating);
        double average = 0;
        for (Season s : seasons) {
            double average2 = 0;
            for (Double d : s.getRatings()) {
                average2 += d;
            }
            if (s.getRatings().size() != 0) {
                average2 /= s.getRatings().size();
            }
            average += average2;
        }
        average /= seasons.size();
        setRating(average);
    }

    @Override
    public int getDuration() {
        int sum = 0;
        for (Season s : seasons) {
            sum += s.getDuration();
        }
        return sum;
    }
}
