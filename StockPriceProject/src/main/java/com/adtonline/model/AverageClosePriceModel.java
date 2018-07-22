package com.adtonline.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AverageClosePriceModel implements Serializable {
  private static final long serialVersionUID = 6150441214940401012L;
  @JsonProperty(value = "Ticker")
  private String ticker;
  @JsonProperty(value = "Avg")
  private String avg;

  public AverageClosePriceModel() {
    super();
  }

  public AverageClosePriceModel(String ticker, String avg) {
    super();
    this.ticker = ticker;
    this.avg = avg;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public String getAvg() {
    return avg;
  }

  public void setAvg(String avg) {
    this.avg = avg;
  }
}
