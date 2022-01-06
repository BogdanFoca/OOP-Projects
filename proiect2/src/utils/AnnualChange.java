package utils;

import entities.Child;
import entities.Gift;
import enums.Category;

import java.util.ArrayList;
import java.util.List;

public final class AnnualChange {
    private double newBudget;
    private List<Gift> newGifts = new ArrayList<Gift>();
    private List<Child> newChildren = new ArrayList<Child>();
    private List<ChildUpdate> childrenUpdates = new ArrayList<ChildUpdate>();

    public AnnualChange(final double newBudget, final List<Gift> newGifts,
                        final List<Child> newChildren, final List<ChildUpdate> childrenUpdates) {
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

    public final class ChildUpdate {
        private int id;
        private Double niceScore;
        private List<Category> newPreferences = new ArrayList<Category>();

        public ChildUpdate(final int id, final Double niceScore,
                           final List<Category> newPreferences) {
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
