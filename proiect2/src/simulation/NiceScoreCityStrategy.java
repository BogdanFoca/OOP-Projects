package simulation;

import enums.Cities;
import utils.Comparers;
import utils.JSONOutput;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NiceScoreCityStrategy implements GiftStrategy{
    Map<Cities, Double> cityScores = new HashMap<Cities, Double>();

    @Override
    public void applyStrategy(List<JSONOutput.OutputChild> outputChildren) {
        for (Cities c : Cities.values()) {
            List<JSONOutput.OutputChild> childrenInCity = outputChildren.stream().filter(oc -> oc.getCity() == c).collect(Collectors.toList());
            Double sum = 0.0;
            for (JSONOutput.OutputChild oc : childrenInCity) {
                sum += oc.getAverageScore();
            }
            sum /= childrenInCity.size();
            cityScores.put(c, sum);
        }
        outputChildren.sort(new Comparers().new CompareOutputChildrenById());
        outputChildren.sort(new CompareOutputChildrenByNiceScoreCity());
    }

    class CompareOutputChildrenByNiceScoreCity implements Comparator<JSONOutput.OutputChild> {

        @Override
        public int compare(JSONOutput.OutputChild o1, JSONOutput.OutputChild o2) {
            return cityScores.get(o1.getCity()).compareTo(cityScores.get(o2.getCity()));
        }
    }
}
