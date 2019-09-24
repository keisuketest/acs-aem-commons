package com.adobe.acs.commons.marketo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketoForm {

  private MarketoFolder folder;
  private int id;
  private String name;

  public String getDisplayName() {
    return folder.getFolderName() + "/" + name;
  }

  public MarketoFolder getFolder() {
    return folder;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setFolder(MarketoFolder folder) {
    this.folder = folder;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "MarketoForm [folder=" + getFolder() + ", id=" + getId() + ", name=" + getName() + "]";
  }

}
