package utils;

import entities.Gift;
import enums.Category;
import enums.Cities;

import java.util.ArrayList;
import java.util.List;

public final class JSONOutput {

    private ArrayList<Children> annualChildren = new ArrayList<Children>();

    public final class Children {
        private ArrayList<OutputChild> children = new ArrayList<OutputChild>();

        public Children(final ArrayList<OutputChild> children) {
            this.children = children;
        }

        public ArrayList<OutputChild> getChildren() {
            return children;
        }

        public void setChildren(final ArrayList<OutputChild> children) {
            this.children = children;
        }

    }

    public final class OutputChild {
        private int id;
        private String lastName;
        private String firstName;
        private Cities city;
        private int age;
        private List<Category> giftsPreferences = new ArrayList<Category>();
        private Double averageScore;
        private List<Double> niceScoreHistory = new ArrayList<Double>();
        private Double assignedBudget;
        private List<Gift> receivedGifts = new ArrayList<Gift>();

        public OutputChild(final int id, final String lastName, final String firstName,
                           final Cities city, final int age, final List<Category> giftsPreferences,
                           final Double averageScore, final List<Double> niceScoreHistory,
                           final Double assignedBudget) {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
            this.city = city;
            this.age = age;
            this.giftsPreferences.addAll(giftsPreferences);
            this.averageScore = averageScore;
            this.niceScoreHistory.addAll(niceScoreHistory);
            this.assignedBudget = assignedBudget;
        }

        public int getId() {
            return id;
        }

        public void setId(final int id) {
            this.id = id;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(final String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(final String firstName) {
            this.firstName = firstName;
        }

        public Cities getCity() {
            return city;
        }

        public void setCity(final Cities city) {
            this.city = city;
        }

        public int getAge() {
            return age;
        }

        public void setAge(final int age) {
            this.age = age;
        }

        public List<Category> getGiftsPreferences() {
            return giftsPreferences;
        }

        public void setGiftsPreferences(final List<Category> giftsPreferences) {
            this.giftsPreferences = giftsPreferences;
        }

        public Double getAverageScore() {
            return averageScore;
        }

        public void setAverageScore(final Double averageScore) {
            this.averageScore = averageScore;
        }

        public Double getAssignedBudget() {
            return assignedBudget;
        }

        public void setAssignedBudget(final Double assignedBudget) {
            this.assignedBudget = assignedBudget;
        }

        public List<Gift> getReceivedGifts() {
            return receivedGifts;
        }

        public void setReceivedGifts(final List<Gift> receivedGifts) {
            this.receivedGifts = receivedGifts;
        }

        /**
         *
         * @param gift
         */
        public void addGift(final Gift gift) {
            receivedGifts.add(gift);
        }

        public List<Double> getNiceScoreHistory() {
            return niceScoreHistory;
        }

        public void setNiceScoreHistory(final List<Double> niceScoreHistory) {
            this.niceScoreHistory = niceScoreHistory;
        }
    }

    public ArrayList<Children> getAnnualChildren() {
        return annualChildren;
    }

    /**
     *
     * @param annualChildren
     */
    public void setAnnualChildren(final ArrayList<Children> annualChildren) {
        this.annualChildren = annualChildren;
    }

    /**
     *
     * @param children
     */
    public void addOutputChildren(final ArrayList<OutputChild> children) {
        annualChildren.add(new Children(children));
    }
}
