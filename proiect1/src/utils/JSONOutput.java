package utils;

import entities.Gift;
import enums.Category;
import enums.Cities;

import java.util.ArrayList;
import java.util.List;

public class JSONOutput {

    ArrayList<Children> annualChildren = new ArrayList<Children>();

    public class Children {
        ArrayList<OutputChild> children = new ArrayList<OutputChild>();

        public Children(ArrayList<OutputChild> children) {
            this.children = children;
        }

        public ArrayList<OutputChild> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<OutputChild> children) {
            this.children = children;
        }

    }

    public class OutputChild {
        int id;
        String lastName;
        String firstName;
        Cities city;
        int age;
        List<Category> giftsPreferences = new ArrayList<Category>();
        Double averageScore;
        List<Double> niceScoreHistory = new ArrayList<Double>();
        Double assignedBudget;
        List<Gift> receivedGifts = new ArrayList<Gift>();

        public OutputChild(int id, String lastName, String firstName, Cities city, int age, List<Category> giftsPreferences, Double averageScore, List<Double> niceScoreHistory, Double assignedBudget) {
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

        public void setId(int id) {
            this.id = id;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public Cities getCity() {
            return city;
        }

        public void setCity(Cities city) {
            this.city = city;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<Category> getGiftsPreferences() {
            return giftsPreferences;
        }

        public void setGiftsPreferences(List<Category> giftsPreferences) {
            this.giftsPreferences = giftsPreferences;
        }

        public Double getAverageScore() {
            return averageScore;
        }

        public void setAverageScore(Double averageScore) {
            this.averageScore = averageScore;
        }

        public Double getAssignedBudget() {
            return assignedBudget;
        }

        public void setAssignedBudget(Double assignedBudget) {
            this.assignedBudget = assignedBudget;
        }

        public List<Gift> getReceivedGifts() {
            return receivedGifts;
        }

        public void setReceivedGifts(List<Gift> receivedGifts) {
            this.receivedGifts = receivedGifts;
        }

        public void addGift(Gift gift) {
            receivedGifts.add(gift);
        }

        public List<Double> getNiceScoreHistory() {
            return niceScoreHistory;
        }

        public void setNiceScoreHistory(List<Double> niceScoreHistory) {
            this.niceScoreHistory = niceScoreHistory;
        }
    }

    public ArrayList<Children> getAnnualChildren() {
        return annualChildren;
    }

    public void setAnnualChildren(ArrayList<Children> annualChildren) {
        this.annualChildren = annualChildren;
    }

    public void addOutputChildren(ArrayList<OutputChild> children) {
        annualChildren.add(new Children(children));
    }
}
