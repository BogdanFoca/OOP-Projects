package entities;

import common.Constants;
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
    private Double averageScore;
    private List<Double> niceScoreHistory = new ArrayList<Double>();
    private Double assignedBudget;
    private List<Gift> receivedGifts = new ArrayList<Gift>();

    private ChildCategory childCategory;

    public Child(
            final int id, final String lastName, final String firstName, final int age,
            final Cities city, final Double niceScore, final List<Category> giftPreference) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.city = city;
        niceScoreHistory.add(niceScore);
        addNewPreferences(giftPreference);
    }

    public Child(final Child c) {
        this.id = c.id;
        this.lastName = c.lastName;
        this.firstName = c.firstName;
        this.age = c.age;
        this.city = c.city;
        this.niceScoreHistory = new ArrayList<Double>(c.niceScoreHistory);
        this.giftsPreferences = new ArrayList<Category>(c.giftsPreferences);
        this.assignedBudget = c.assignedBudget;
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

    /**
     *
     * @return
     */
    //this has a weird name so
    // it is not the default getter so that the JSON parser does not write it
    public ChildCategory categoryOfChild() {
        return childCategory;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public void setGiftsPreferences(final List<Category> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }

    public void setAverageScore(final double averageScore) {
        this.averageScore = averageScore;
    }

    public void setNiceScoreHistory(final List<Double> niceScoreHistory) {
        this.niceScoreHistory = niceScoreHistory;
    }

    public List<Gift> getReceivedGifts() {
        return receivedGifts;
    }

    public void setReceivedGifts(final List<Gift> receivedGifts) {
        this.receivedGifts = receivedGifts;
    }

    public Double getAssignedBudget() {
        return assignedBudget;
    }

    /**
     *
     * @param assignedBudget
     */
    public void setAssignedBudget(final Double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }

    /**
     *
     */
    public void setChildCategory() {
        if (age < Constants.BABY_AGE) {
            childCategory = ChildCategory.Baby;
        } else if (age < Constants.KID_AGE) {
            childCategory = ChildCategory.Kid;
        } else if (age <= Constants.TEEN_AGE) {
            childCategory = ChildCategory.Teen;
        } else {
            childCategory = ChildCategory.Young_Adult;
        }
    }

    public Double getAverageScore() {
        return averageScore;
    }

    /**
     *
     */
    public void setAverageNiceScore() {
        switch (childCategory) {
            case Baby:
                averageScore = Constants.BABY_SCORE;
                break;
            case Kid:
                Double sum = 0.0;
                for (Double d : niceScoreHistory) {
                    sum += d;
                }
                sum /= niceScoreHistory.size();
                averageScore = sum;
                break;
            case Teen:
                Double sump = 0.0;
                for (int i = 0; i < niceScoreHistory.size(); i++) {
                    sump += niceScoreHistory.get(i) * (i + 1);
                }
                sump /= (double) niceScoreHistory.size() * (niceScoreHistory.size() + 1) / 2;
                averageScore = sump;
                break;
            case Young_Adult:
                averageScore = 0.0;
                break;
            default:
                break;
        }
    }

    /**
     *
     * @param gift
     */
    public void receiveGift(final Gift gift) {
        receivedGifts.add(gift);
    }

    /**
     *
     */
    public void incrementAge() {
        age++;
        setChildCategory();
    }

    /**
     *
     * @param niceScore
     */
    public void addNiceScore(final Double niceScore) {
        niceScoreHistory.add(niceScore);
        setAverageNiceScore();
    }

    /**
     *
     * @param categories
     */
    public void addNewPreferences(final List<Category> categories) {
        for (int i = categories.size() - 1; i >= 0; i--) {
            if (giftsPreferences.contains(categories.get(i))) {
                giftsPreferences.remove(categories.get(i));
            }
            giftsPreferences.add(0, categories.get(i));
        }
    }
}
