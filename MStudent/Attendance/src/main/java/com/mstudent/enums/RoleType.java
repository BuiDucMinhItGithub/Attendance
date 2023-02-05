package com.mstudent.enums;

public enum RoleType {
    ADMIN(1,"ROLE_ADMIN","ADMIN"),
    TEACHER(2,"ROLE_TEACHER","TEACHER");

    private final int key;
    private final String value;
    private final String role;

    RoleType(int key, String value, String role) {
        this.key = key;
        this.value = value;
        this.role = role;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getRole() {
        return role;
    }
}
