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

    public static Database GetInstance(){
        if(Instance == null){
            Instance = new Database();
        }
        return Instance;
    }
}
