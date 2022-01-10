package simulation;

import database.Database;
import entities.Child;
import enums.Cities;
import utils.Comparers;
import utils.JSONOutput;

import java.util.*;
import java.util.stream.Collectors;

public final class NiceScoreCityStrategy implements GiftStrategy {
    private Map<Cities, Double> cityScores = new HashMap<Cities, Double>();

    @Override
    public void applyStrategy(final List<JSONOutput.OutputChild> outputChildren) {
        for (Cities c : Cities.values()) {
            List<JSONOutput.OutputChild> childrenInCity = outputChildren.stream()
                    .filter(oc -> oc.getCity() == c).collect(Collectors.toList());
            Double sum = 0.0;
            for (JSONOutput.OutputChild oc : childrenInCity) {
                sum += oc.getAverageScore();
            }
            sum /= childrenInCity.size();
            cityScores.put(c, sum);
        }
        Database.getInstance().getChildren().sort(new Comparers.CompareChildrenById());
        Database.getInstance().getChildren().sort(new CompareChildrenByAlphaCity());
        Collections.reverse(Database.getInstance().getChildren());
        Database.getInstance().getChildren().sort(new CompareChildrenByNiceScoreCity());
        Collections.reverse(Database.getInstance().getChildren());
        outputChildren.sort(new Comparers.CompareOutputChildrenById());
        outputChildren.sort(new CompareOutputChildrenByAlphaCity());
        Collections.reverse(outputChildren);
        outputChildren.sort(new CompareOutputChildrenByNiceScoreCity());
        Collections.reverse(outputChildren);
    }

    class CompareOutputChildrenByNiceScoreCity implements Comparator<JSONOutput.OutputChild> {

        @Override
        public int compare(final JSONOutput.OutputChild o1,
                           final JSONOutput.OutputChild o2) {
            return cityScores.get(o1.getCity()).compareTo(cityScores.get(o2.getCity()));
        }
    }

    class CompareChildrenByNiceScoreCity implements Comparator<Child> {

        @Override
        public int compare(final Child o1,
                           final Child o2) {
            return cityScores.get(o1.getCity()).compareTo(cityScores.get(o2.getCity()));
        }
    }

    class CompareOutputChildrenByAlphaCity implements Comparator<JSONOutput.OutputChild> {

        @Override
        public int compare(final JSONOutput.OutputChild o1,
                           final JSONOutput.OutputChild o2) {
            return o1.getCity().toString().compareTo(o2.getCity().toString());
        }
    }

    class CompareChildrenByAlphaCity implements Comparator<Child> {

        @Override
        public int compare(final Child o1,
                           final Child o2) {
            return o1.getCity().toString().compareTo(o2.getCity().toString());
        }
    }
}
