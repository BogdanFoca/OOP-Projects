package Database;

import Entities.Movie;
import Entities.Show;
import Entities.User;
import actor.Actor;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database Instance;

    public List<Actor> actors = new ArrayList<Actor>();
    public List<User> users = new ArrayList<User>();
    public List<Movie> movies = new ArrayList<Movie>();
    public List<Show> shows = new ArrayList<Show>();

    public static Database getInstance() {
        if(Instance == null){
            Instance = new Database();
        }
        return Instance;
    }

    public void addMovie(Movie movie) {
        if(movies.stream().filter(m -> m.getTitle().equals(movie.getTitle())).findAny().orElse(null) == null) {
            movies.add(movie);
        }
    }

    public void addShow(Show show) {
        if(shows.stream().filter(m -> m.getTitle().equals(show.getTitle())).findAny().orElse(null) == null) {
            shows.add(show);
        }
    }

    public void clearDatabase() {
        actors.clear();
        users.clear();
        movies.clear();
        shows.clear();
    }
}
