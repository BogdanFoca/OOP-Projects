package simulation;

import common.Constants;
import database.Database;
import entities.Child;
import entities.Gift;
import enums.Category;
import enums.ChildCategory;
import enums.Elfs;
import enums.Strategies;
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
     *Singleton getter
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
     *Starts the simulation. Does round0, then for each year does roundYear
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
        GiftStrategy strategy = StrategyFactory.createGiftStrategy(Strategies.ID);
        assert strategy != null;
        strategy.applyStrategy(outputChildren);
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
        GiftStrategy strategy2 = StrategyFactory.createGiftStrategy(Strategies.ID);
        assert strategy2 != null;
        strategy2.applyStrategy(outputChildren);
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
        GiftStrategy strategy = StrategyFactory.createGiftStrategy(annualChange.getStrategy());
        assert strategy != null;
        strategy.applyStrategy(outputChildren);
        giveGifts(outputChildren);
        giveYellowGift(outputChildren);
        for (int i = outputChildren.size() - 1; i >= 0; i--) {
            if (outputChildren.get(i).getAge() > Constants.TEEN_AGE) {
                outputChildren.remove(i);
            }
        }
        outputChildren.sort(new Comparers.CompareOutputChildrenById());
        jsonOutput.addOutputChildren(outputChildren);
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
            Double childBudget = c.getAverageScore() * budgetUnit;
            if (c.getElf() == Elfs.BLACK) {
                childBudget = childBudget - childBudget
                        * Constants.ELF_PERCENTAGE / Constants.ONE_HUNDRED;
            } else if (c.getElf() == Elfs.PINK) {
                childBudget = childBudget + childBudget
                        * Constants.ELF_PERCENTAGE / Constants.ONE_HUNDRED;
            }
            budgetByChild.put(c, childBudget);
            c.setAssignedBudget(childBudget);
        }
    }

    void giveGifts(final List<JSONOutput.OutputChild> outputChildren) {
        List<Child> children = Database.getInstance().getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getAverageScore() >= 0) {
                for (Category cat : children.get(i).getGiftsPreferences()) {
                    Gift gift = getAppropriateGift(budgetByChild.get(children.get(i)), cat);
                    if (gift != null) {
                        gift.reduceQuantity();
                        children.get(i).receiveGift(gift);
                        outputChildren.get(i).addGift(gift);
                        budgetByChild.put(children.get(i),
                                budgetByChild.get(children.get(i)) - gift.getPrice());
                    }
                }
            }
        }
    }

    Gift getAppropriateGift(final double budget, final Category category) {
        List<Gift> giftsInCategory = new ArrayList<Gift>(Database.getInstance().getGifts());
        giftsInCategory = giftsInCategory.stream()
                .filter(g -> g.getCategory().equals(category)).collect(Collectors.toList());
        giftsInCategory.sort(new Comparers.CompareGiftsByPrice());
        Gift selectedGift = null;
        for (Gift gift : giftsInCategory) {
            if (gift.getQuantity() > 0) {
                selectedGift = gift;
                break;
            }
        }
        if (selectedGift == null || selectedGift.getPrice() > budget) {
            return null;
        }
        return selectedGift;
    }

    void giveYellowGift(final List<JSONOutput.OutputChild> outputChildren) {
        List<Child> children = Database.getInstance().getChildren();
        for (int i = 0; i < children.size(); i++) {
            Child c = children.get(i);
            if (c.getElf() == Elfs.YELLOW && outputChildren.get(i).getReceivedGifts().size() == 0) {
                Gift selectedGift = null;
                if (c.getGiftsPreferences().size() > 0) {
                    Category cat = c.getGiftsPreferences().get(0);
                    List<Gift> giftsInCategory = new ArrayList<Gift>(
                            Database.getInstance().getGifts());
                    giftsInCategory = giftsInCategory.stream()
                            .filter(g -> g.getCategory().equals(cat)).collect(Collectors.toList());
                    giftsInCategory.sort(new Comparers.CompareGiftsByPrice());
                    if (giftsInCategory.size() > 0) {
                        if (giftsInCategory.get(0).getQuantity() > 0) {
                            selectedGift = giftsInCategory.get(0);
                        }
                    }
                }
                if (selectedGift != null) {
                    selectedGift.reduceQuantity();
                    children.get(i).receiveGift(selectedGift);
                    outputChildren.get(i).addGift(selectedGift);
                }
            }
        }
    }

    /**
     * Increases children age, removes adults,
     * adds nice scores, new preferences, adds new children and gifts
     * @param annualChange
     */
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
                if (cu.getNewElf() != null) {
                   child.setElf(cu.getNewElf());
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
