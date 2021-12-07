package entities;

import enums.Category;
import enums.ChildCategory;
import enums.Cities;

import java.util.ArrayList;
import java.util.List;

public final class Child {
    private final int id;
    private final String lastName;
    private final String firstName;
    private final Cities city;
    private int age;
    private List<Category> giftsPreferences = new ArrayList<Category>();
    private double averageScore = 0;
    private List<Double> niceScoreHistory = new ArrayList<Double>();
    private Double assignedBudget;
    private List<Gift> receivedGifts = new ArrayList<Gift>();

    private ChildCategory childCategory;

    public Child(
            int id, String lastName, String firstName, int age,
            Cities city, Double niceScore, List<Category> giftPreference) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.city = city;
        niceScoreHistory.add(niceScore);
        addNewPreferences(giftPreference);
    }

    public Child(Child c) {
        this.id = c.id;
        this.lastName = c.lastName;
        this.firstName = c.firstName;
        this.age = c.age;
        this.city = c.city;
        this.niceScoreHistory = new ArrayList<Double>(c.niceScoreHistory);
        this.giftsPreferences = new ArrayList<Category>(c.giftsPreferences);
        this.assignedBudget = new Double(c.assignedBudget);
        this.receivedGifts = new ArrayList<Gift>(c.receivedGifts);
        setChildCategory();
        setAverageNiceScore();
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getAge() {
        return age;
    }

    public Cities getCity() {
        return city;
    }

    public List<Double> getNiceScoreHistory() {
        return niceScoreHistory;
    }

    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    //this has a weird name so it is not the default getter so that the JSON parser does not write it
    public ChildCategory categoryOfChild() {
        return childCategory;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGiftsPreferences(List<Category> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public void setNiceScoreHistory(List<Double> niceScoreHistory) {
        this.niceScoreHistory = niceScoreHistory;
    }

    public List<Gift> getReceivedGifts() {
        return receivedGifts;
    }

    public void setReceivedGifts(List<Gift> receivedGifts) {
        this.receivedGifts = receivedGifts;
    }

    public Double getAssignedBudget() {
        return assignedBudget;
    }

    public void setAssignedBudget(Double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }

    public void setChildCategory() {
        if (age < 5) {
            childCategory = ChildCategory.Baby;
        } else if (age < 12) {
            childCategory = ChildCategory.Kid;
        } else if (age <= 18) {
            childCategory = ChildCategory.Teen;
        } else {
            childCategory = ChildCategory.Young_Adult;
        }
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageNiceScore() {
        switch (childCategory) {
            case Baby:
                averageScore = 10;
                break;
            case Kid:
                double sum = 0;
                for (Double d : niceScoreHistory) {
                    sum += d;
                }
                sum /= niceScoreHistory.size();
                averageScore = sum;
                break;
            case Teen:
                double sump = 0;
                for (int i = 0; i < niceScoreHistory.size(); i++) {
                    sump += niceScoreHistory.get(i) * (i + 1);
                }
                sump /= niceScoreHistory.size() * (niceScoreHistory.size() + 1) / 2;
                averageScore = sump;
                break;
            case Young_Adult:
                averageScore = 0;
                break;
            default:
                break;
        }
    }

    public void receiveGift(Gift gift) {
        receivedGifts.add(gift);
    }

    public void incrementAge() {
        age++;
        setChildCategory();
    }

    public void addNiceScore(Double niceScore) {
        niceScoreHistory.add(niceScore);
        setAverageNiceScore();
    }

    public void addNewPreferences(List<Category> categories) {
        System.out.print(firstName + " " + lastName + " ");
        for(Category c : categories) {
            System.out.print(c.value);
        }
        System.out.println("");
        for (int i = categories.size() - 1; i >= 0; i--) {
            if (giftsPreferences.contains(categories.get(i))) {
                giftsPreferences.remove(categories.get(i));
            }
            giftsPreferences.add(0, categories.get(i));
        }
    }
}
