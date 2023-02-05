package com.mstudent.enums;

public enum RoomState {
    OPEN("OPEN"),
    CLOSE("CLOSE");

    private final String value;

    RoomState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
