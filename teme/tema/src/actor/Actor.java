package actor;

import Entities.Video;

import java.util.List;
import java.util.Map;

public class Actor {
    private String name;
    public List<Video> filmography;
    private Map<ActorsAwards, Integer> awards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
