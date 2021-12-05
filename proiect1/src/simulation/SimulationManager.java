package simulation;

import database.Database;
import entities.Child;
import entities.Gift;
import enums.Category;
import enums.ChildCategory;
import utils.AnnualChange;
import utils.Comparers;
import utils.JSONReader;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SimulationManager {
    private final List<Double> niceScores = new ArrayList<Double>();
    private final Map<Child, Double> budgetByChild = new HashMap<Child, Double>();

    public Map<Child, Double> getBudgetByChild() {
        return budgetByChild;
    }

    void startSimulation(JSONReader jsonReader) {
        round0();
        for (int i = 0; i < jsonReader.getNumberOfYears(); i++) {
            roundYear(jsonReader.getAnnualChanges().get(i));
        }
    }

    void round0() {
        updateBudgets();
        giveGifts();
    }

    void roundYear(AnnualChange annualChange) {
        updateData(annualChange);
        updateBudgets();
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

    void updateBudgets() {
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

    void giveGifts() {
        List<Child> children = Database.getInstance().getChildren();
        for (Child c : children) {
            if (c.getAverageNiceScore() >= 0) {
                for (Category cat : c.getGiftPreference()) {
                    Gift gift = getAppropriateGift(budgetByChild.get(c), cat);
                    if (gift != null) {
                        c.receiveGift(gift);
                        budgetByChild.put(c, budgetByChild.get(c) - gift.getPrice());
                        Database.getInstance().getGifts().remove(gift);
                    }
                }
            }
        }
    }

    void updateData(AnnualChange annualChange) {
        List<Child> children = Database.getInstance().getChildren();
        for (Child c : children) {
            c.incrementAge();
            c.setChildCategory();
            if (c.getChildCategory() == ChildCategory.Young_Adult) {
                children.remove(c);
            }
        }
        for (Child c : annualChange.getNewChildren()) {
            c.setChildCategory();
            if(c.getChildCategory() != ChildCategory.Young_Adult) {
                children.add(c);
            }
        }
        for (AnnualChange.ChildUpdate cu : annualChange.getChildrenUpdates()) {
            Child child = Database.getInstance().getChildren().stream().filter(c -> c.getId() == cu.getId()).findFirst().orElse(null);
            if (child != null) {
                if(cu.getNiceScore() != null) {
                    child.addNiceScore(cu.getNiceScore());
                }
            }
            for (Category c : cu.getNewPreferences()) {
                child.addNewPreference(c);
            }
        }
        Database.getInstance().getGifts().addAll(annualChange.getNewGifts());
    }
}
