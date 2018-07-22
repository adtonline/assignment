package com.adtonline.exceptions;

public class EndDateGreaterThanStartDateException extends RuntimeException {
  private static final long serialVersionUID = 7998018116418886524L;

  public EndDateGreaterThanStartDateException() {
    super("End Date is greater than start date");
  }
}
