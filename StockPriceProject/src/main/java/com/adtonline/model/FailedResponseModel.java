package com.adtonline.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FailedResponseModel implements Serializable {
  private static final long serialVersionUID = -8510255166457473036L;
  private String dateTime;
  private String message;

  public FailedResponseModel() {
    super();
  }

  public FailedResponseModel(String dateTime, String message) {
    this.dateTime = dateTime;
    this.message = message;
  }

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
