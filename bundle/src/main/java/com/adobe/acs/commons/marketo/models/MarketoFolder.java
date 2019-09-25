/*
 * #%L
 * ACS AEM Commons Bundle
 * %%
 * Copyright (C) 2019 Adobe
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.adobe.acs.commons.marketo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representation of a Folder from the Marketo API. Used to organize forms.
 */
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
