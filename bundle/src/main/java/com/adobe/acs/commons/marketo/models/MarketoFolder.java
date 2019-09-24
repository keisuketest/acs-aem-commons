package com.adobe.acs.commons.marketo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketoFolder {

  private String folderName;
  private String type;

  public String getFolderName() {
    return folderName;
  }

  public String getType() {
    return type;
  }

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "MarketoFolder [folderName=" + getFolderName() + ", type=" + getType() + "]";
  }
}
