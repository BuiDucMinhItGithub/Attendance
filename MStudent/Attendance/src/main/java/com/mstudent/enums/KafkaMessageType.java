package com.mstudent.enums;

public enum KafkaMessageType {
  EMAIL(1,"EMAIL"),
  SMS(2,"SMS");

  private final int key;
  private final String value;

  KafkaMessageType(int key, String value) {
    this.key = key;
    this.value = value;
  }

  public int getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

}
