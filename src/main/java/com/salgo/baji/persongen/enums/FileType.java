package com.salgo.baji.persongen.enums;

public enum FileType {
  WORD("application/msword"),
  JPG("image/jpeg"),
  MP3("audio/mpeg");

  private final String val;

  private FileType(String val) {
    this.val = val;
  }

  public static FileType fromString(String val) {
    for (FileType t : FileType.values()) {
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
