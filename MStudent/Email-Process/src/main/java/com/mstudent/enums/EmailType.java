package com.mstudent.enums;

public enum EmailType {

  ATTENDANCE("ATTENDANCE"),
  COST("COST");

  private final String value;

  EmailType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
