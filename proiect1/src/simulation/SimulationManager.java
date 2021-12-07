package simulation;

import common.Constants;
import database.Database;
import entities.Child;
import entities.Gift;
import enums.Category;
import enums.ChildCategory;
import utils.AnnualChange;
import utils.Comparers;
import utils.JSONOutput;
import utils.JSONReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SimulationManager {
    private static SimulationManager instance;

    private final Map<Child, Double> budgetByChild = new HashMap<Child, Double>();
    private JSONOutput jsonOutput;

    /**
     *
     * @return
     */
    public static SimulationManager getInstance() {
        if (instance == null) {
            instance = new SimulationManager();
        }
        return instance;
    }

    public Map<Child, Double> getBudgetByChild() {
        return budgetByChild;
    }

    /**
     *
     * @param jsonReader
     * @return
     */
    public JSONOutput startSimulation(final JSONReader jsonReader) {
        jsonOutput = new JSONOutput();
        budgetByChild.clear();
        round0();
        for (int i = 0; i < jsonReader.getNumberOfYears(); i++) {
            roundYear(jsonReader.getAnnualChanges().get(i));
        }
        return jsonOutput;
    }

    void round0() {
        ArrayList<JSONOutput.OutputChild> outputChildren = new ArrayList<JSONOutput.OutputChild>();
        updateBudgets();
        for (Child c : Database.getInstance().getChildren()) {
            outputChildren.add(new JSONOutput().new OutputChild(
                    c.getId(),
                    c.getLastName(),
                    c.getFirstName(),
                    c.getCity(),
                    c.getAge(),
                    c.getGiftsPreferences(),
                    c.getAverageScore(),
                    c.getNiceScoreHistory(),
                    budgetByChild.get(c)
            ));
        }
        giveGifts(outputChildren);
        for (int i = outputChildren.size() - 1; i >= 0; i--) {
            if (outputChildren.get(i).getAge() > Constants.TEEN_AGE) {
                outputChildren.remove(i);
            }
        }
        jsonOutput.addOutputChildren(outputChildren);
    }

    void roundYear(final AnnualChange annualChange) {
        ArrayList<JSONOutput.OutputChild> outputChildren = new ArrayList<JSONOutput.OutputChild>();
        updateData(annualChange);
        updateBudgets();
        for (Child c : Database.getInstance().getChildren()) {
            outputChildren.add(new JSONOutput().new OutputChild(
                    c.getId(),
                    c.getLastName(),
                    c.getFirstName(),
                    c.getCity(),
                    c.getAge(),
                    c.getGiftsPreferences(),
                    c.getAverageScore(),
                    c.getNiceScoreHistory(),
                    budgetByChild.get(c)
            ));
        }
        giveGifts(outputChildren);
        for (int i = outputChildren.size() - 1; i >= 0; i--) {
            if (outputChildren.get(i).getAge() > Constants.TEEN_AGE) {
                outputChildren.remove(i);
            }
        }
        jsonOutput.addOutputChildren(outputChildren);
    }

    Gift getAppropriateGift(final double budget, final Category category) {
        List<Gift> giftsInCategory = new ArrayList<Gift>(Database.getInstance().getGifts());
        giftsInCategory = giftsInCategory.stream()
                .filter(g -> g.getCategory().equals(category)).collect(Collectors.toList());
        giftsInCategory.sort(new Comparers.CompareGiftsByPrice());
        if (giftsInCategory.size() == 0 || giftsInCategory.get(0).getPrice() > budget) {
            return null;
        }
        return giftsInCategory.get(0);
    }

    void updateBudgets() {
        List<Child> children = Database.getInstance().getChildren();
        budgetByChild.clear();
        Double averageScore = 0.0;
        for (Child c : children) {
            c.setChildCategory();
            c.setAverageNiceScore();
            averageScore += c.getAverageScore();
        }
        Double budgetUnit = Database.getInstance().getSantaBudget() / averageScore;
        for (Child c: children) {
            budgetByChild.put(c, c.getAverageScore() * budgetUnit);
            c.setAssignedBudget(c.getAverageScore() * budgetUnit);
        }
    }

    void giveGifts(final List<JSONOutput.OutputChild> outputChildren) {
        List<Child> children = Database.getInstance().getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getAverageScore() >= 0) {
                for (Category cat : children.get(i).getGiftsPreferences()) {
                    Gift gift = getAppropriateGift(budgetByChild.get(children.get(i)), cat);
                    if (gift != null) {
                        children.get(i).receiveGift(gift);
                        outputChildren.get(i).addGift(gift);
                        budgetByChild.put(children.get(i),
                                budgetByChild.get(children.get(i)) - gift.getPrice());
                    }
                }
            }
        }
    }

    void updateData(final AnnualChange annualChange) {
        Database.getInstance().setSantaBudget(annualChange.getNewBudget());
        List<Child> children = Database.getInstance().getChildren();
        for (int i = children.size() - 1; i >= 0; i--) {
            children.get(i).incrementAge();
            children.get(i).setChildCategory();
            if (children.get(i).categoryOfChild() == ChildCategory.Young_Adult) {
                children.remove(children.get(i));
            }
        }
        for (AnnualChange.ChildUpdate cu : annualChange.getChildrenUpdates()) {
            Child child = Database.getInstance().getChildren().stream()
                    .filter(c -> c.getId() == cu.getId()).findFirst().orElse(null);
            if (child != null) {
                if (cu.getNiceScore() != null) {
                    child.addNiceScore(cu.getNiceScore());
                }
                if (cu.getNewPreferences() != null) {
                    child.addNewPreferences(cu.getNewPreferences());
                }
            }
        }
        for (Child c : annualChange.getNewChildren()) {
            c.setChildCategory();
            if (c.categoryOfChild() != ChildCategory.Young_Adult) {
                children.add(c);
            }
        }
        Database.getInstance().getGifts().addAll(annualChange.getNewGifts());
    }
}
