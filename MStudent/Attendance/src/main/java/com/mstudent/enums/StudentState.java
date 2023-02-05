package com.mstudent.enums;

public enum StudentState {
    ACTIVE("ACTIVE"),
    DEACTIVE("DEACTIVE");

    private final String value;

    StudentState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
