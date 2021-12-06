package utils;

import database.Database;
import entities.Gift;
import enums.Category;
import enums.Cities;

import java.util.ArrayList;
import java.util.List;

public class JSONOutput {

    ArrayList<ArrayList<OutputChild>> listOfListsOfOutputChildren = new ArrayList<ArrayList<OutputChild>>();

    public class OutputChild {
        int id;
        String lastName;
        String firstName;
        Cities city;
        int age;
        List<Category> giftPreferences;
        Double averageScore;
        Double assignedBudget;
        List<Gift> receivedGifts = new ArrayList<Gift>();

        public OutputChild(int id, String lastName, String firstName, Cities city, int age, List<Category> giftPreferences, Double averageScore, Double assignedBudget) {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
            this.city = city;
            this.age = age;
            this.giftPreferences = giftPreferences;
            this.averageScore = averageScore;
            this.assignedBudget = assignedBudget;
        }

        public void addGift(Gift gift) {
            receivedGifts.add(gift);
        }
    }

    /*public void initializeList(int numberOfYears) {
        for (int i = 0; i < numberOfYears; i++) {
            listOfListsOfOutputChildren.add(new ArrayList<OutputChild>());
        }
    }

    public void addOutputChild(OutputChild child, int index) {
        listOfListsOfOutputChildren.get(index).add(child);
    }*/

    public void addOutputChildren(ArrayList<OutputChild> children) {
        listOfListsOfOutputChildren.add(children);
    }
}
