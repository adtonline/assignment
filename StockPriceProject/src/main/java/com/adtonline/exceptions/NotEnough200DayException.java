package com.adtonline.exceptions;

public class NotEnough200DayException extends RuntimeException {
  private static final long serialVersionUID = -7782905704332080050L;

  public NotEnough200DayException(String suggest) {
    super("Not Enough 200 Day, Suggest Start Date : " + suggest);
  }
}
