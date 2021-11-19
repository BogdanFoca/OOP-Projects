package actor;

import Entities.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Actor {
    private String name;
    String careerDescription;
    List<String> filmography;
    private Map<ActorsAwards, Integer> awards;

    public Actor(String name, String careerDescription, List<String> filmography, Map<ActorsAwards, Integer> awards){
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = new ArrayList<String>(filmography);
        this.awards = new HashMap<ActorsAwards, Integer>(awards);
    }

    public String getName() {
        return name;
    }
    public String getCareerDescription() {
        return careerDescription;
    }
    public void setCareerDescription(String careerDescription) {
        this.careerDescription = careerDescription;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Map<ActorsAwards, Integer> getAwards(){
        return awards;
    }
    public List<String> GetFilmography(){
        return filmography;
    }
}
