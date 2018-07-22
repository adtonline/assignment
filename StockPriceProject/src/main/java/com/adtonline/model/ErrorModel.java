package com.adtonline.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

@JsonRootName(value = "error")
@Getter
@Setter
public class ErrorModel implements Serializable {
  private static final long serialVersionUID = 7863105440252349656L;
  private FailedResponseModel error;

  public ErrorModel() {
    super();
  }

  public ErrorModel(FailedResponseModel error) {
    this.error = error;
  }

  public FailedResponseModel getError() {
    return error;
  }

  public void setError(FailedResponseModel error) {
    this.error = error;
  }
}
