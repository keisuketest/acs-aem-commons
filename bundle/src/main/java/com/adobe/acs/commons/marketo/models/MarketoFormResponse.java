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
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Container object for a Marketo REST API response.
 */
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
