package utils;

import common.Constants;
import entities.Child;
import entities.Gift;
import enums.Category;
import enums.Cities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class JSONReader {
    int numberOfYears;
    double santaBudget;
    List<Child> initialChildList = new ArrayList<Child>();
    List<Gift> initialGiftList = new ArrayList<Gift>();
    List<AnnualChange> annualChanges = new ArrayList<AnnualChange>();

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

    public List<Child> getInitialChildList() {
        return initialChildList;
    }

    public void setInitialChildList(List<Child> initialChildList) {
        this.initialChildList = initialChildList;
    }

    public List<Gift> getInitialGiftList() {
        return initialGiftList;
    }

    public void setInitialGiftList(List<Gift> initialGiftList) {
        this.initialGiftList = initialGiftList;
    }

    public List<AnnualChange> getAnnualChanges() {
        return annualChanges;
    }

    public void setAnnualChanges(List<AnnualChange> annualChanges) {
        this.annualChanges = annualChanges;
    }

    public void parseFile(final String fileName) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(fileName));
        numberOfYears = (int) jsonObject.get(Constants.NUMBER_OF_YEARS);
        santaBudget = (double) jsonObject.get(Constants.SANTA_BUDGET);
        JSONArray jsonInitialChildList = (JSONArray) ((JSONObject) jsonObject.get(Constants.INITIAL_DATA)).get(Constants.CHILDREN);
        JSONArray jsonInitialGiftList = (JSONArray) ((JSONObject) jsonObject.get(Constants.INITIAL_DATA)).get(Constants.SANTA_GIFTS_LIST);
        JSONArray jsonAnnualChanges = (JSONArray) ((JSONObject) jsonObject).get(Constants.ANNUAL_CHANGES);

        if (jsonInitialChildList != null) {
            for (Object jo : jsonInitialChildList) {
                initialChildList.add(new Child(
                        (int) ((JSONObject) jo).get(Constants.ID),
                        (String) ((JSONObject) jo).get(Constants.LAST_NAME),
                        (String) ((JSONObject) jo).get(Constants.FIRST_NAME),
                        (int) ((JSONObject) jo).get(Constants.AGE),
                        Cities.valueOf((String) ((JSONObject) jo).get(Constants.CITY)),
                        (Double) ((JSONObject) jo).get(Constants.NICE_SCORE),
                        convertJSONArrayToGiftCategories((JSONArray) ((JSONObject) jo).get(Constants.GIFT_PREFERENCES))
                        ));
            }
        }
        if (jsonInitialGiftList != null) {
            for (Object jo : jsonInitialGiftList) {
                initialGiftList.add(new Gift(
                        (String) ((JSONObject) jo).get(Constants.PRODUCT_NAME),
                        (double) ((JSONObject) jo).get(Constants.PRICE),
                        Category.valueOf((String) ((JSONObject) jo).get(Constants.CATEGORY))
                        ));
            }
        }
        if (jsonAnnualChanges != null) {
            for (Object o : jsonAnnualChanges) {
                annualChanges.add(new AnnualChange(
                        (double) ((JSONObject) o).get(Constants.NEW_SANTA_BUDGET),
                        convertJSONArrayToGift((JSONArray) ((JSONObject) o).get(Constants.NEW_GIFTS)),
                        convertJSONArrayToChildren((JSONArray) ((JSONObject) o).get(Constants.NEW_CHILDREN)),
                        convertJSONArrayToChildUpdates((JSONArray) ((JSONObject) o).get(Constants.CHILDREN_UPDATES))
                        ));
            }
        }
    }

    public static List<Category> convertJSONArrayToGiftCategories(final JSONArray array) {
        if (array != null) {
            ArrayList<Category> finalArray = new ArrayList<Category>();
            for (Object o : array) {
                finalArray.add(Category.valueOf((String) o));
            }
            return finalArray;
        } else {
            return null;
        }
    }

    public static List<Child> convertJSONArrayToChildren(final JSONArray array) {
        if (array != null) {
            ArrayList<Child> finalArray = new ArrayList<Child>();
            for (Object jo : array) {
                finalArray.add(new Child(
                        (int) ((JSONObject) jo).get(Constants.ID),
                        (String) ((JSONObject) jo).get(Constants.LAST_NAME),
                        (String) ((JSONObject) jo).get(Constants.FIRST_NAME),
                        (int) ((JSONObject) jo).get(Constants.AGE),
                        Cities.valueOf((String) ((JSONObject) jo).get(Constants.CITY)),
                        (Double) ((JSONObject) jo).get(Constants.NICE_SCORE),
                        convertJSONArrayToGiftCategories((JSONArray) ((JSONObject) jo).get(Constants.GIFT_PREFERENCES))
                ));
            }
            return finalArray;
        } else {
            return null;
        }
    }

    public static List<Gift> convertJSONArrayToGift(final JSONArray array) {
        if (array != null) {
            ArrayList<Gift> finalArray = new ArrayList<Gift>();
            for (Object jo : array) {
                finalArray.add(new Gift(
                        (String) ((JSONObject) jo).get(Constants.PRODUCT_NAME),
                        (double) ((JSONObject) jo).get(Constants.PRICE),
                        Category.valueOf((String) ((JSONObject) jo).get(Constants.CATEGORY))
                ));
            }
            return finalArray;
        } else {
            return null;
        }
    }

    public static List<AnnualChange.ChildUpdate> convertJSONArrayToChildUpdates(final JSONArray array) {
        if (array != null) {
            ArrayList<AnnualChange.ChildUpdate> finalArray = new ArrayList<AnnualChange.ChildUpdate>();
            for (Object jo : array) {
                finalArray.add(new AnnualChange().new ChildUpdate(
                        (int) ((JSONObject) jo).get(Constants.ID),
                        (Double) ((JSONObject) jo).get(Constants.NICE_SCORE),
                        convertJSONArrayToGiftCategories((JSONArray) ((JSONObject) jo).get(Constants.GIFT_PREFERENCES))
                ));
            }
            return finalArray;
        } else {
            return null;
        }
    }
}
