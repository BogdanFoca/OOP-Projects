package utils;

import entities.Child;
import entities.Gift;
import enums.Category;

import java.util.ArrayList;
import java.util.List;

public class AnnualChange {
    double newBudget;
    List<Gift> newGifts = new ArrayList<Gift>();
    List<Child> newChildren = new ArrayList<Child>();
    List<ChildUpdate> childrenUpdates = new ArrayList<ChildUpdate>();

    public AnnualChange(double newBudget, List<Gift> newGifts, List<Child> newChildren, List<ChildUpdate> childrenUpdates) {
        this.newBudget = newBudget;
        this.newGifts = newGifts;
        this.newChildren = newChildren;
        this.childrenUpdates = childrenUpdates;
    }

    public AnnualChange() {

    }

    public double getNewBudget() {
        return newBudget;
    }

    public List<Gift> getNewGifts() {
        return newGifts;
    }

    public List<Child> getNewChildren() {
        return newChildren;
    }

    public List<ChildUpdate> getChildrenUpdates() {
        return childrenUpdates;
    }

    public class ChildUpdate {
        int id;
        Double niceScore;
        List<Category> newPreferences = new ArrayList<Category>();

        public ChildUpdate(int id, Double niceScore, List<Category> newPreferences) {
            this.id = id;
            this.niceScore = niceScore;
            this.newPreferences = newPreferences;
        }

        public int getId() {
            return id;
        }

        public Double getNiceScore() {
            return niceScore;
        }

        public List<Category> getNewPreferences() {
            return newPreferences;
        }
    }
}
