package actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Actor {
    private String name;
    private String careerDescription;
    private final List<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    public Actor(final String name, final String careerDescription,
                 final List<String> filmography, final Map<ActorsAwards, Integer> awards) {
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
    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }
    public List<String> getFilmography() {
        return filmography;
    }
}
