package com.dev.leo.testusers.enums;

public enum DatePattern {
    SERVER_DATE("yyyy-MM-dd"),
    USER_DATE("dd/MM/yyyy");

    private String value;

    DatePattern(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
