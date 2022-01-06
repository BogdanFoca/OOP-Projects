package enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Elfs {
    @JsonProperty("Yellow")
    YELLOW("Yellow"),

    @JsonProperty("Black")
    BLACK("Black"),

    @JsonProperty("Pink")
    PINK("Pink"),

    @JsonProperty("White")
    WHITE("White");

    public final String value;

    Elfs(final String value) {
        this.value = value;
    }

    /**
     *Converts string to Enum element
     * @param label
     * @return
     */
    public static Elfs valueOfElfLabel(final String label) {
        for (Elfs e : values()) {
            if (e.value.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
