package enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Cities {

    @JsonProperty("Bucuresti")
    BUCURESTI("Bucuresti"),

    @JsonProperty("Constanta")
    CONSTANTA("Constanta"),

    @JsonProperty("Buzau")
    BUZAU("Buzau"),

    @JsonProperty("Timisoara")
    TIMISOARA("Timisoara");

    public final String value;

    Cities(final String value) {
        this.value = value;
    }

    public static Cities valueOfCityLabel(String label) {
        for (Cities c : values()) {
            if (c.value.equals(label)) {
                return c;
            }
        }
        return null;
    }
}
