package utils;

import entities.Child;
import entities.Gift;

import java.util.ArrayList;
import java.util.List;

public class AnnualChange {
    double newBudget;
    List<Gift> newGifts = new ArrayList<Gift>();
    List<Child> newChildren = new ArrayList<Child>();

    public AnnualChange(double newBudget, List<Gift> newGifts, List<Child> newChildren) {
        this.newBudget = newBudget;
        this.newGifts = newGifts;
        this.newChildren = newChildren;
    }
}
