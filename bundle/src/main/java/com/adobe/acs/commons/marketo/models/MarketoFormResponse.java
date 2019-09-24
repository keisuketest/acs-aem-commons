package com.adobe.acs.commons.marketo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketoFormResponse {

  private MarketoError[] errors;

  @JsonProperty("result")
  private MarketoForm[] result;
  private boolean success;

  public MarketoError[] getErrors() {
    return errors;
  }

  public MarketoForm[] getResult() {
    return result;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setErrors(MarketoError[] errors) {
    this.errors = errors;
  }

  public void setResult(MarketoForm[] result) {
    this.result = result;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

}
