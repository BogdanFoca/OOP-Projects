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
    TIMISOARA("Timisoara"),

    @JsonProperty("Iasi")
    IASI("Iasi"),

    @JsonProperty("Oradea")
    ORADEA("Oradea"),

    @JsonProperty("Craiova")
    CRAIOVA("Craiova"),

    @JsonProperty("Cluj-Napoca")
    CLUJ_NAPOCA("Cluj-Napoca"),

    @JsonProperty("Braila")
    BRAILA("Braila"),

    @JsonProperty("Brasov")
    BRASOV("Brasov");

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
