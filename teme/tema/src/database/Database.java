package database;

import entities.Movie;
import entities.Show;
import entities.User;
import actor.Actor;

import java.util.ArrayList;
import java.util.List;

public final class Database {
    private static Database instance;

    private final List<Actor> actors = new ArrayList<Actor>();
    private final List<User> users = new ArrayList<User>();
    private final List<Movie> movies = new ArrayList<Movie>();
    private final List<Show> shows = new ArrayList<Show>();

    public List<Actor> getActors() {
        return actors;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Show> getShows() {
        return shows;
    }

    /**
     *
     * @return
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     *
     * @param movie
     */
    public void addMovie(final Movie movie) {
        if (movies.stream().filter(m -> m.getTitle()
                .equals(movie.getTitle())).findAny().orElse(null) == null) {
            movies.add(movie);
        }
    }

    /**
     *
     * @param show
     */
    public void addShow(final Show show) {
        if (shows.stream().filter(m -> m.getTitle()
                .equals(show.getTitle())).findAny().orElse(null) == null) {
            shows.add(show);
        }
    }

    /**
     *
     */
    public void clearDatabase() {
        actors.clear();
        users.clear();
        movies.clear();
        shows.clear();
    }
}
