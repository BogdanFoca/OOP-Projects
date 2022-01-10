package entities;

import common.Constants;
import enums.Category;
import enums.ChildCategory;
import enums.Cities;
import enums.Elfs;

import java.util.ArrayList;
import java.util.List;

public final class Child {
    private final int id;
    private final String lastName;
    private final String firstName;
    private final Cities city;
    private int age;
    private List<Category> giftsPreferences = new ArrayList<Category>();
    private int niceScoreBonus;
    private Elfs elf;
    private Double averageScore;
    private List<Double> niceScoreHistory = new ArrayList<Double>();
    private Double assignedBudget;
    private List<Gift> receivedGifts = new ArrayList<Gift>();

    private ChildCategory childCategory;

    public Child(final Builder builder) {
        this.id = builder.id;
        this.lastName = builder.lastName;
        this.firstName = builder.firstName;
        this.age = builder.age;
        this.city = builder.city;
        niceScoreHistory.add(builder.niceScore);
        addNewPreferences(builder.giftsPreference);
        this.niceScoreBonus = builder.niceScoreBonus;
        this.elf = builder.elf;
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
        this.niceScoreBonus = c.niceScoreBonus;
        this.elf = c.elf;
    }

    public static final class Builder {
        private final int id;
        private final String lastName;
        private final String firstName;
        private final Cities city;
        private final int age;
        private List<Category> giftsPreference = new ArrayList<Category>();
        private int niceScoreBonus = 0;
        private final Elfs elf;
        private final Double niceScore;

        public Builder(final int id, final String lastName, final String firstName, final int age,
                       final Cities city, final Double niceScore,
                       final List<Category> giftPreference, final Elfs elf) {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
            this.age = age;
            this.city = city;
            this.niceScore = niceScore;
            this.giftsPreference = giftPreference;
            this.elf = elf;
        }

        /**
         * Optional parameter niceScoreBonus
         * @param bonus the niceScoreBonus
         * @return
         */
        public Builder niceScoreBonus(final int bonus) {
            this.niceScoreBonus = bonus;
            return this;
        }

        /**
         *  Build the childs
         * @return the built child
         */
        public Child build() {
            return new Child(this);
        }
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
     *this has a weird name so it is not the default getter
     *so that the JSON parser does not write it
     * @return
     */
    public ChildCategory categoryOfChild() {
        return childCategory;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public void setGiftsPreferences(final List<Category> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }

    public int getNiceScoreBonus() {
        return niceScoreBonus;
    }

    public void setNiceScoreBonus(final int niceScoreBonus) {
        this.niceScoreBonus = niceScoreBonus;
    }

    public Elfs getElf() {
        return elf;
    }

    public void setElf(final Elfs elf) {
        this.elf = elf;
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
     *Sets score based on child category
     */
    public void setAverageNiceScore() {
        switch (childCategory) {
            case Baby:
                averageScore = Constants.BABY_SCORE;
                averageScore += averageScore * niceScoreBonus / Constants.ONE_HUNDRED;
                averageScore = averageScore > Constants.MAX_SCORE
                        ? Constants.MAX_SCORE : averageScore;
                break;
            case Kid:
                Double sum = 0.0;
                for (Double d : niceScoreHistory) {
                    sum += d;
                }
                sum /= niceScoreHistory.size();
                averageScore = sum;
                averageScore += averageScore * niceScoreBonus / Constants.ONE_HUNDRED;
                averageScore = averageScore > Constants.MAX_SCORE
                        ? Constants.MAX_SCORE : averageScore;
                break;
            case Teen:
                Double sump = 0.0;
                for (int i = 0; i < niceScoreHistory.size(); i++) {
                    sump += niceScoreHistory.get(i) * (i + 1);
                }
                sump /= (double) niceScoreHistory.size() * (niceScoreHistory.size() + 1) / 2;
                averageScore = sump;
                averageScore += averageScore * niceScoreBonus / Constants.ONE_HUNDRED;
                averageScore = averageScore > Constants.MAX_SCORE
                        ? Constants.MAX_SCORE : averageScore;
                break;
            case Young_Adult:
                averageScore = 0.0;
                break;
            default:
                break;
        }
    }

    /**
     *Adds gift
     * @param gift
     */
    public void receiveGift(final Gift gift) {
        receivedGifts.add(gift);
    }

    /**
     *Increments age
     */
    public void incrementAge() {
        age++;
        setChildCategory();
    }

    /**
     *Adds a nice score
     * @param niceScore
     */
    public void addNiceScore(final Double niceScore) {
        niceScoreHistory.add(niceScore);
        setAverageNiceScore();
    }

    /**
     *Adds new preference
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
