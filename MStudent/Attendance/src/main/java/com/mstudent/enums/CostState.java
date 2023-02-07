package com.mstudent.enums;

public enum CostState {
  NOT_YET("NOT_YET"),
  DONE("DONE");

  private final String value;

  CostState(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
