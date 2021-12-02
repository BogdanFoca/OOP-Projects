package simulation;

import database.Database;
import entities.Child;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SimulationManager {
    private List<Double> niceScores = new ArrayList<Double>();
    private Map<Child, Double> budgetByChild = new HashMap<Child, Double>();

    public Map<Child, Double> getBudgetByChild() {
        return budgetByChild;
    }

    void round0() {
        List<Child> children = Database.getInstance().getChildren();
        double averageScore = 0;
        for (Child c : children) {
            c.setChildCategory();
            c.setAverageNiceScore();
            averageScore += c.getAverageNiceScore();
        }
        averageScore /= Database.getInstance().getChildren().size();
        double budgetUnit = Database.getInstance().getSantaBudget() / averageScore;
        for (Child c: children) {
            budgetByChild.put(c, c.getAverageNiceScore() * budgetUnit);
        }
    }
}
