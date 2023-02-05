package com.mstudent.enums;

public enum TeacherState {
    ACTIVE("ACTIVE"),

    BOT("BOT"),
    DEACTIVE("DEACTIVE");

    private final String value;

    TeacherState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
