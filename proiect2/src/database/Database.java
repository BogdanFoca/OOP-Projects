package database;

import entities.Child;
import entities.Gift;

import java.util.ArrayList;
import java.util.List;

public final class Database {
    private static Database instance;

    private int numberOfYears;
    private double santaBudget;

    private List<Child> children = new ArrayList<Child>();
    private List<Gift> gifts = new ArrayList<Gift>();

    /**
     *Singleton getter
     * @return
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(final int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public double getSantaBudget() {
        return santaBudget;
    }

    public void setSantaBudget(final double santaBudget) {
        this.santaBudget = santaBudget;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(final List<Child> children) {
        this.children = children;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    /**
     *
     * @param gifts
     */
    public void setGifts(final List<Gift> gifts) {
        this.gifts = gifts;
    }

    /**
     *Clears Database
     */
    public void clear() {
        children.clear();
        gifts.clear();
    }
}
