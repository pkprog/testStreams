package ru.pk.testStreams.gufosite;

public enum GufoDict {
    SURNAMES("surnames_ru"),
    NAMES("names")

    ;

    private String value;

    GufoDict(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
