package com.computer.enums;

public enum AttendanceState {
    ABSENT("ABSENT"),
    PRESENT("PRESENT"),
    ABSENT_WITH_PERMISSION("ABSENTWITHPERMISSION"),
    BOT("BOT"),
    NO_LONGER("NOLONGER");

    private final String value;

    AttendanceState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
