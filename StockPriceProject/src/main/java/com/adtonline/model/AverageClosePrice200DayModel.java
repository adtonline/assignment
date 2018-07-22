package com.adtonline.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AverageClosePrice200DayModel implements Serializable {
  private static final long serialVersionUID = -8715495829483350000L;
  @JsonProperty(value = "200dma")
  private AverageClosePriceModel averageClosePriceModel;

  public AverageClosePrice200DayModel() {
    super();
  }

  public AverageClosePriceModel getAverageClosePriceModel() {
    return averageClosePriceModel;
  }

  public void setAverageClosePriceModel(AverageClosePriceModel averageClosePriceModel) {
    this.averageClosePriceModel = averageClosePriceModel;
  }
}
