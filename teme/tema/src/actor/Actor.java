package actor;

import Entities.Video;

import java.util.List;
import java.util.Map;

public class Actor {
    private String name;
    String careerDescription;
    public List<Video> filmography;
    private Map<ActorsAwards, Integer> awards;
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
}
