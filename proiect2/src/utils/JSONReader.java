package utils;

import common.Constants;
import database.Database;
import entities.Child;
import entities.Gift;
import enums.Category;
import enums.Cities;
import enums.Elfs;
import enums.Strategies;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JSONReader {
    private int numberOfYears;
    private double santaBudget;
    private final List<Child> initialChildList = new ArrayList<Child>();
    private List<Gift> initialGiftList = new ArrayList<Gift>();
    private List<AnnualChange> annualChanges = new ArrayList<AnnualChange>();

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

    public List<Child> getInitialChildList() {
        return initialChildList;
    }

    public List<Gift> getInitialGiftList() {
        return initialGiftList;
    }

    public List<AnnualChange> getAnnualChanges() {
        return annualChanges;
    }

    public void setAnnualChanges(final List<AnnualChange> annualChanges) {
        this.annualChanges = annualChanges;
    }

    /**
     * Reads file, converts data to corresponding objects, adds objects to Database
     * @param file
     * @throws IOException
     * @throws ParseException
     */
    public void parseFile(final File file) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(file));
        numberOfYears = (int) (long) jsonObject.get(Constants.NUMBER_OF_YEARS);
        santaBudget = (double) (long) jsonObject.get(Constants.SANTA_BUDGET);
        JSONArray jsonInitialChildList = (JSONArray) ((JSONObject) jsonObject
                .get(Constants.INITIAL_DATA)).get(Constants.CHILDREN);
        JSONArray jsonInitialGiftList = (JSONArray) ((JSONObject) jsonObject
                .get(Constants.INITIAL_DATA)).get(Constants.SANTA_GIFTS_LIST);
        JSONArray jsonAnnualChanges = (JSONArray) jsonObject
                .get(Constants.ANNUAL_CHANGES);

        if (jsonInitialChildList != null) {
            for (Object jo : jsonInitialChildList) {
                initialChildList.add(new Child.Builder(
                        (int) (long) ((JSONObject) jo).get(Constants.ID),
                        (String) ((JSONObject) jo).get(Constants.LAST_NAME),
                        (String) ((JSONObject) jo).get(Constants.FIRST_NAME),
                        (int) (long) ((JSONObject) jo).get(Constants.AGE),
                        Cities.valueOfCityLabel((String) ((JSONObject) jo).get(Constants.CITY)),
                        ((Number) ((JSONObject) jo).get(Constants.NICE_SCORE)).doubleValue(),
                        convertJSONArrayToGiftCategories(
                                (JSONArray) ((JSONObject) jo).get(Constants.GIFT_PREFERENCES)),
                        Elfs.valueOfElfLabel((String) ((JSONObject) jo).get(Constants.ELF)))
                        .niceScoreBonus((int) (long) ((JSONObject) jo)
                                .get(Constants.NICE_SCORE_BONUS))
                        .build());
            }
        }
        if (jsonInitialGiftList != null) {
            for (Object jo : jsonInitialGiftList) {
                initialGiftList.add(new Gift(
                        (String) ((JSONObject) jo).get(Constants.PRODUCT_NAME),
                        (double) (long) ((JSONObject) jo).get(Constants.PRICE),
                        Category.valueOfCategoryLabel(
                                (String) ((JSONObject) jo).get(Constants.CATEGORY)),
                        (int) (long) ((JSONObject) jo).get(Constants.QUANTITY)
                        ));
            }
        }
        initialGiftList = initialGiftList.stream().distinct().collect(Collectors.toList());
        if (jsonAnnualChanges != null) {
            for (Object o : jsonAnnualChanges) {
                annualChanges.add(new AnnualChange(
                        (double) (long) ((JSONObject) o).get(Constants.NEW_SANTA_BUDGET),
                        convertJSONArrayToGift(
                                (JSONArray) ((JSONObject) o).get(Constants.NEW_GIFTS)),
                        convertJSONArrayToChildren(
                                (JSONArray) ((JSONObject) o).get(Constants.NEW_CHILDREN)),
                        convertJSONArrayToChildUpdates(
                                (JSONArray) ((JSONObject) o).get(Constants.CHILDREN_UPDATES)),
                        Strategies.valueOfStrategyLabel((String) ((JSONObject) o)
                                .get(Constants.STRATEGY))
                        ));
            }
        }

        Database.getInstance().setNumberOfYears(numberOfYears);
        Database.getInstance().setSantaBudget(santaBudget);
        Database.getInstance().setChildren(initialChildList);
        Database.getInstance().setGifts(initialGiftList);
    }

    /**
     *Converts a JSONArray to a List of Gift Categories
     * @param array
     * @return
     */
    public static List<Category> convertJSONArrayToGiftCategories(final JSONArray array) {
        if (array != null) {
            ArrayList<Category> finalArray = new ArrayList<Category>();
            for (Object o : array) {
                finalArray.add(Category.valueOfCategoryLabel((String) o));
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     *Converts a JSONArray to a List of children
     * @param array
     * @return
     */
    public static List<Child> convertJSONArrayToChildren(final JSONArray array) {
        if (array != null) {
            ArrayList<Child> finalArray = new ArrayList<Child>();
            for (Object jo : array) {
                finalArray.add(new Child.Builder(
                        (int) (long) ((JSONObject) jo).get(Constants.ID),
                        (String) ((JSONObject) jo).get(Constants.LAST_NAME),
                        (String) ((JSONObject) jo).get(Constants.FIRST_NAME),
                        (int) (long) ((JSONObject) jo).get(Constants.AGE),
                        Cities.valueOfCityLabel((String) ((JSONObject) jo).get(Constants.CITY)),
                        ((Number) ((JSONObject) jo).get(Constants.NICE_SCORE)).doubleValue(),
                        convertJSONArrayToGiftCategories(
                                (JSONArray) ((JSONObject) jo).get(Constants.GIFT_PREFERENCES)),
                        Elfs.valueOfElfLabel((String) ((JSONObject) jo).get(Constants.ELF)))
                        .niceScoreBonus((int) (long) ((JSONObject) jo)
                                .get(Constants.NICE_SCORE_BONUS))
                        .build());
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     *Converts a JSONArray to a List of gifts
     * @param array
     * @return
     */
    public static List<Gift> convertJSONArrayToGift(final JSONArray array) {
        if (array != null) {
            ArrayList<Gift> finalArray = new ArrayList<Gift>();
            for (Object jo : array) {
                finalArray.add(new Gift(
                        (String) ((JSONObject) jo).get(Constants.PRODUCT_NAME),
                        (double) (long) ((JSONObject) jo).get(Constants.PRICE),
                        Category.valueOfCategoryLabel(
                                (String) ((JSONObject) jo).get(Constants.CATEGORY)),
                        (int) (long) ((JSONObject) jo).get(Constants.QUANTITY)
                ));
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     *Converts a JSONArray to a List of child updates
     * @param array
     * @return
     */
    public static List<AnnualChange.ChildUpdate>
    convertJSONArrayToChildUpdates(final JSONArray array) {
        if (array != null) {
            ArrayList<AnnualChange.ChildUpdate> finalArray
                    = new ArrayList<AnnualChange.ChildUpdate>();
            for (Object jo : array) {
                finalArray.add(new AnnualChange().new ChildUpdate(
                        (int) (long) ((JSONObject) jo).get(Constants.ID),
                        ((JSONObject) jo).get(Constants.NICE_SCORE) != null
                                ? ((Number) ((JSONObject) jo)
                                .get(Constants.NICE_SCORE)).doubleValue() : null,
                        convertJSONArrayToGiftCategories((JSONArray) ((JSONObject) jo)
                                .get(Constants.GIFT_PREFERENCES)),
                        Elfs.valueOfElfLabel((String) ((JSONObject) jo).get(Constants.ELF)))
                );
            }
            return finalArray;
        } else {
            return null;
        }
    }
}
