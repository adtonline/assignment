package com.adtonline.exceptions;

public class NotDataForStartDateException extends RuntimeException {
  private static final long serialVersionUID = -7926278454297689931L;

  public NotDataForStartDateException(String suggest) {
    super("Not Data For Start Date. Suggest Start Date: " + suggest);
  }
}
