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
    private int age;
    private final Cities city;
    private final List<Double> niceScores = new ArrayList<Double>();
    private double averageNiceScore = 0;
    private final List<Category> giftPreference;
    private final List<Gift> receivedGifts = new ArrayList<Gift>();

    private ChildCategory childCategory;

    public Child(
            int id, String lastName, String firstName, int age,
            Cities city, Double niceScore, List<Category> giftPreference) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.city = city;
        niceScores.add(niceScore);
        this.giftPreference = giftPreference;
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

    public List<Double> getNiceScores() {
        return niceScores;
    }

    public List<Category> getGiftPreference() {
        return giftPreference;
    }

    public ChildCategory getChildCategory() {
        return childCategory;
    }

    public void setChildCategory() {
        if (age < 5) {
            childCategory = ChildCategory.Baby;
        } else if (age < 12) {
            childCategory = ChildCategory.Kid;
        } else if (age < 18) {
            childCategory = ChildCategory.Teen;
        } else {
            childCategory = ChildCategory.Young_Adult;
        }
    }

    public Double getAverageNiceScore() {
        return averageNiceScore;
    }

    public void setAverageNiceScore() {
        switch (childCategory) {
            case Baby:
                averageNiceScore = 10;
                break;
            case Kid:
                double sum = 0;
                for (Double d : niceScores) {
                    sum += d;
                }
                sum /= niceScores.size();
                averageNiceScore = sum;
                break;
            case Teen:
                double sump = 0;
                for (int i = 0; i < niceScores.size(); i++) {
                    sump += niceScores.get(i) * (i + 1);
                }
                sump /= niceScores.size() * (niceScores.size() + 1) / 2;
                averageNiceScore = sump;
                break;
            case Young_Adult:
                averageNiceScore = -1;
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
        niceScores.add(niceScore);
        setAverageNiceScore();
    }

    public void addNewPreference(Category category) {
        if (giftPreference.contains(category)) {
            giftPreference.remove(category);
        }
        giftPreference.add(0, category);
    }
}
