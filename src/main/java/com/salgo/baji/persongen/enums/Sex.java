package com.salgo.baji.persongen.enums;

public enum Sex {
  MALE("MALE"),
  FEMALE("FEMALE");

  private final String val;

  private Sex(String val) {
    this.val = val;
  }

  public static Sex fromString(String val) {
    for (Sex t : Sex.values()) {
      if (t.val.equalsIgnoreCase(val)) {
        return t;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return val;
  }
}
