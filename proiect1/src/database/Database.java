package database;

import entities.Child;
import entities.Gift;
import enums.Cities;

import java.util.ArrayList;
import java.util.List;

public final class Database {
    static Database instance;

    private int numberOfYears;
    private double santaBudget;

    private List<Child> children = new ArrayList<Child>();
    private List<Gift> gifts = new ArrayList<Gift>();
    private List<Cities> cities = new ArrayList<Cities>();

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public double getSantaBudget() {
        return santaBudget;
    }

    public void setSantaBudget(double santaBudget) {
        this.santaBudget = santaBudget;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

    public List<Cities> getCities() {
        return cities;
    }

    public void setCities(List<Cities> cities) {
        this.cities = cities;
    }
}
