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

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * A Model retrieving the configuration for interacting with the Marketo REST API
 */
@Model(adaptables = Resource.class)
public class MarketoClientConfiguration {

  @ValueMapValue
  private String clientId;

  @ValueMapValue
  private String clientSecret;

  @ValueMapValue
  private String endpointHost;

  @ValueMapValue
  private String munchkinId;

  @ValueMapValue
  private String serverInstance;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MarketoClientConfiguration other = (MarketoClientConfiguration) obj;
    if (clientId == null) {
      if (other.clientId != null) {
        return false;
      }
    } else if (!clientId.equals(other.clientId)) {
      return false;
    }
    return true;
  }

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public String getEndpointHost() {
    return endpointHost;
  }

  public String getMunchkinId() {
    return munchkinId;
  }

  public String getServerInstance() {
    return serverInstance;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
    return result;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public void setEndpointHost(String endpointHost) {
    this.endpointHost = endpointHost;
  }

  public void setMunchkinId(String munchkinId) {
    this.munchkinId = munchkinId;
  }

  public void setServerInstance(String serverInstance) {
    this.serverInstance = serverInstance;
  }

  @Override
  public String toString() {
    return "MarketoClientConfiguration [endpointHost=" + endpointHost + ", clientId=" + clientId + ", clientSecret="
        + clientSecret + ", munchkinId=" + munchkinId + "]";
  }
}
