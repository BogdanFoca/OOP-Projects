package enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Category {

    @JsonProperty("Board Games")
    BOARD_GAMES("Board Games"),

    @JsonProperty("Books")
    BOOKS("Books"),

    @JsonProperty("Clothes")
    CLOTHES("Clothes"),

    @JsonProperty("Sweets")
    SWEETS("Sweets"),

    @JsonProperty("Technology")
    TECHNOLOGY("Technology"),

    @JsonProperty("Toys")
    TOYS("Toys");

    public final String value;

    Category(final String value) {
        this.value = value;
    }

    /**
     *Converts string to Enum element
     * @param label
     * @return
     */
    public static Category valueOfCategoryLabel(final String label) {
        for (Category c : values()) {
            if (c.value.equals(label)) {
                return c;
            }
        }
        return null;
    }
}
