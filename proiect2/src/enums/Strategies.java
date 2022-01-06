package enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Strategies {
    @JsonProperty("id")
    ID("id"),

    @JsonProperty("niceScore")
    NICE_SCORE("niceScore"),

    @JsonProperty("niceScoreCity")
    NICE_SCORE_CITY("niceScoreCity");

    public final String value;

    Strategies(final String value) {
        this.value = value;
    }

    /**
     *Converts string to Enum element
     * @param label
     * @return
     */
    public static Strategies valueOfStrategyLabel(final String label) {
        for (Strategies s : values()) {
            if (s.value.equals(label)) {
                return s;
            }
        }
        return null;
    }
}
