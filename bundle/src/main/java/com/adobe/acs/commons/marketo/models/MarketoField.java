package com.adobe.acs.commons.marketo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A model representing a field in Marketo.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketoField {

  private String id;

  public String getId() {
    return id;
  }

}
