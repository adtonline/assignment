package com.adtonline.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName(value = "Prices")
public class PricesModel implements Serializable {
  private static final long serialVersionUID = 6110533806724629775L;
  @JsonProperty(value = "Ticker")
  private String ticker;
  @JsonProperty(value = "DateClose")
  private List<ClosePriceModel> closePriceModels;

  public PricesModel() {
    super();
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public List<ClosePriceModel> getClosePriceModels() {
    return closePriceModels;
  }

  public void setClosePriceModels(List<ClosePriceModel> closePriceModels) {
    this.closePriceModels = closePriceModels;
  }
}
