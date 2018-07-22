package com.adtonline.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClosePriceModel implements Serializable {
  private static final long serialVersionUID = 7157786783203009608L;
  private String dateTime;
  private String closePrice;

  public ClosePriceModel() {
    super();
  }

  public ClosePriceModel(String dateTime, String closePrice) {
    this.setDateTime(dateTime);
    this.setClosePrice(closePrice);
  }

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public String getClosePrice() {
    return closePrice;
  }

  public void setClosePrice(String closePrice) {
    this.closePrice = closePrice;
  }
}
