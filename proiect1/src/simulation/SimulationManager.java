package simulation;

import database.Database;
import entities.Child;
import entities.Gift;
import enums.Category;
import utils.Comparers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        for (Child c : children) {
            for (Category cat : c.getGiftPreference()) {
                Gift gift = getAppropriateGift(budgetByChild.get(c.getFirstName()), cat);
                if(gift != null) {
                    c.receiveGift(gift);
                    budgetByChild.put(c, budgetByChild.get(c) - gift.getPrice());
                    Database.getInstance().getGifts().remove(gift);
                }
            }
        }
    }

    Gift getAppropriateGift(double budget, Category category) {
        List<Gift> giftsInCategory = new ArrayList<Gift>(Database.getInstance().getGifts());
        giftsInCategory = giftsInCategory.stream().filter(g -> g.getCategory().equals(category)).collect(Collectors.toList());
        giftsInCategory.sort(new Comparers.CompareGiftsByPrice());
        if (giftsInCategory.size() == 0 || giftsInCategory.get(0).getPrice() > budget) {
            return null;
        }
        return giftsInCategory.get(0);
    }
}
