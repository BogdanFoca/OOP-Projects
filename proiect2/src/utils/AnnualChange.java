package utils;

import entities.Child;
import entities.Gift;
import enums.Category;
import enums.Elfs;
import enums.Strategies;

import java.util.ArrayList;
import java.util.List;

public final class AnnualChange {
    private double newBudget;
    private List<Gift> newGifts = new ArrayList<Gift>();
    private List<Child> newChildren = new ArrayList<Child>();
    private List<ChildUpdate> childrenUpdates = new ArrayList<ChildUpdate>();
    private Strategies strategy;

    public AnnualChange(final double newBudget, final List<Gift> newGifts,
                        final List<Child> newChildren, final List<ChildUpdate> childrenUpdates,
                        final Strategies strategy) {
        this.newBudget = newBudget;
        this.newGifts = newGifts;
        this.newChildren = newChildren;
        this.childrenUpdates = childrenUpdates;
        this.strategy = strategy;
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

    public Strategies getStrategy() {
        return strategy;
    }

    public final class ChildUpdate {
        private int id;
        private Double niceScore;
        private List<Category> newPreferences = new ArrayList<Category>();
        private Elfs newElf;

        public ChildUpdate(final int id, final Double niceScore,
                           final List<Category> newPreferences, final Elfs newElf) {
            this.id = id;
            this.niceScore = niceScore;
            this.newPreferences = newPreferences;
            this.newElf = newElf;
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

        public Elfs getNewElf() {
            return newElf;
        }
    }
}
